package com.hp.et.log.job;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DbCleanupJob {
	
	private static Logger logger = LoggerFactory.getLogger(DbCleanupJob.class);

    private final String SELECT_PATITIONS_NAME = "SELECT t.partition_name FROM user_tab_partitions t WHERE t.table_name='EVENT' order by t.partition_position";
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private final int expectPartitionCount = 7;
    
    private Connection conn = null;

    private Statement stmt = null;

    public static void main(String[] main)
    {
        DbCleanupJob db = new DbCleanupJob();
        db.execute();
    }
	//Execute this job
	public void execute()
	{
		logger.info("ENTER DbCleanupJOb's Execute function");
		
        try
        {
            setDBConnection();

            List<String> partitionsName = getEventPartitions();
            List<String> expectPartitionsName = initExpectPartitionsName();

            int dropIndex = getDropPartitionIndex(partitionsName);
            if (dropIndex >= 0)
            {
                for (int index = 0; index <= dropIndex; index++)
                {
                    removePartition(partitionsName.get(index));
                }

            }
            
            String latestPartitionName = partitionsName.get(partitionsName.size() - 1);
            int splitIndex = expectPartitionsName.indexOf(latestPartitionName);
            int index = 0;

            if (splitIndex < expectPartitionCount)
            {
                if (splitIndex != -1)
                {
                    index = splitIndex + 1;
                }
                for (; index <= expectPartitionCount; index++)
                {
                    splitMaxValuePartition(latestPartitionName, expectPartitionsName.get(index));
                    latestPartitionName = expectPartitionsName.get(index);
                }

            }
        }
        catch (SQLException e)
        {

        }
        finally
        {
            close();
        }

	}

    /**
     * Description goes here.
     *
     * @param partitionsName
     * @return
     */
    private int getDropPartitionIndex(List<String> partitionsName)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -expectPartitionCount);
        String dropPartitionName = "EVENT_" + sdf.format(calendar.getTime());
        if (partitionsName.contains(dropPartitionName))
        {
            return partitionsName.lastIndexOf(dropPartitionName);
        }
        return -1;
    }

    /**
     * Description goes here.
     *
     * @param partitionName
     */
    private void removePartition(String partitionName)
        throws SQLException
    {
        final String DROP_PARTITION_SQL = "alter table EVENT drop partition " + partitionName;
        stmt = conn.createStatement();
        stmt.execute(DROP_PARTITION_SQL);
    }

    /**
     * Split max value partition.
     *
     * @param oldPartitionName the old partition name
     * @param newPartitionName the new partition name
     */
    private void splitMaxValuePartition(String oldPartitionName, String newPartitionName)
        throws SQLException
    {
        if (oldPartitionName.equalsIgnoreCase(newPartitionName))
            return;

        int beginIndex = oldPartitionName.indexOf("_");
        String keyValue = oldPartitionName.substring(beginIndex + 1);
        final String MAX_VALUE_SQL = "alter table EVENT split partition " + oldPartitionName + " at(to_date("
                + keyValue + ",'YYYYMMDD')) into (partition event_temp, partition " + newPartitionName + ")";
        stmt = conn.createStatement();
        stmt.execute(MAX_VALUE_SQL);
        renamePartition("event_temp", oldPartitionName);
    }

    /**
     * rename the EVENT_TEMP partition.
     *
     * @param oldName
     * @param newName
     */
    private void renamePartition(String oldName, String newName)
        throws SQLException
    {
        final String RENAME_PARTITION_SQL = "alter table EVENT rename partition " + oldName + " to " + newName;
        stmt.execute(RENAME_PARTITION_SQL);
      }

    /**
     * Description goes here.
     *
     * @return
     */
    private List<String> initExpectPartitionsName()
    {
        List<String> expectNames = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        String expectName;
        expectName = "EVENT_" + sdf.format(calendar.getTime());
        expectNames.add(expectName);

        for (int index = 1; index <= expectPartitionCount; index++)
        {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            expectName = "EVENT_" + sdf.format(calendar.getTime());
            expectNames.add(expectName);
        }
        
        return expectNames;
    }


    /**
     * retrieve all partitions of table events.
     *
     * @return
     */
    private List<String> getEventPartitions()
        throws SQLException
    {
        ResultSet rs = null;
        List<String> partitions = new ArrayList<String>();
        try
        {
            rs = stmt.executeQuery(SELECT_PATITIONS_NAME);
            String partitionName = "";
            while (rs.next())
            {
                partitionName = rs.getString("PARTITION_NAME");
                if (partitionName != null && !("".equals(partitionName)))
                {
                    partitions.add(partitionName);
                }
            }
        }
        finally
        {
            resultSetClose(rs);
        }
        return partitions;

    }

    /**
     * initial the database connection.
     *
     */
    private void setDBConnection()
        throws SQLException
    {
        Properties prop = PropertiesLoader.loadProperties("datasource.properties");
        String driver = prop.getProperty("db.driver");
        String url = prop.getProperty("db.url");
        String username = prop.getProperty("db.username");
        String password = prop.getProperty("db.password");
        try
        {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
        }
        conn = DriverManager.getConnection(url, username, password);
        stmt = conn.createStatement();
    }

    private void resultSetClose(ResultSet rs)
    {
        if (rs != null)
        {
            try
            {
                rs.close();
                rs = null;
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * close the connection,statement and resultset
     * if they are not null.
     *
     */
    private void close()
    {

        if (stmt != null)
        {
            try
            {
                stmt.close();
                stmt = null;
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (conn != null)
        {
            try
            {
                conn.close();
                conn = null;
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
