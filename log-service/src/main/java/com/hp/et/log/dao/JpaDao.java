package com.hp.et.log.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

public abstract class JpaDao<K, E> implements IDao<K, E>
{
    protected Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public JpaDao()
    {
        initiateEntityClass();
    }

    

    @SuppressWarnings("unchecked")
    private void initiateEntityClass()
    {
        ParameterizedType genericSuperclass = (ParameterizedType)getClass().getGenericSuperclass();

        Type type = genericSuperclass.getActualTypeArguments()[1];

        if (type instanceof ParameterizedType)
        {
            this.entityClass = (Class<E>)((ParameterizedType)type).getRawType();
        }
        else
        {
            this.entityClass = (Class<E>)type;
        }

    }

    public void flush()
    {
    	this.entityManager.flush();
    }
    
    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager()
    {
        return entityManager;
    }

    public void persist(E entity)
    {
       entityManager.persist(entity);
    }
    
    public void merge(E entity)
    {
    	entityManager.merge(entity);
    }
    
    public void remove(E entity)
    {
        entityManager.remove(entity);
    }

    public E findById(K id)
    {
        return entityManager.find(entityClass, id);
    }
    
    public E getReferenceById(K id)
    {
        return entityManager.getReference(entityClass, id);
    }
    
    /**
     * merge the entity.  Checks to see
     * if the transaction is already started or not.
     * @param entity Object to merge
     */
    public void mergeUpdateEntity(E entity)
    {
    	EntityTransaction et = null;
        boolean alreadyActive = true;
        try
        {
        	et = entityManager.getTransaction();
            alreadyActive = et.isActive();  
        }
        catch(IllegalStateException e)
        {
            // Transaction is already active (JTA)
        }

        if (!alreadyActive && et != null)
        {
            et.begin();
        }

        try
        {
        	merge(entity);
            if (!alreadyActive && et != null)
            {
                et.commit();
            }
        }
        catch (Exception ex)
        {
            if (!alreadyActive && et != null)
            {
                et.rollback();
            }
            System.out.println(ex);
        }
    	
    }

   

    /**
     * Set one or more fields in the example entity.  All records whose fields
     * match the example's fields will be returned.
     * 
     * WARNING: Find by example does not filter on primary key.  Also, it does
     * not work for null values.
     * 
     * @param entity The example entity
     * @return A list of entities matching the example entities set fields.
     */
    @SuppressWarnings("unchecked")
	public List<E> findByExample(E entity)
    {
        Session session = (Session)getEntityManager().getDelegate();
        Example example = Example.create(entity).excludeZeroes();
        Criteria criteria = session.createCriteria(entity.getClass()).add(example);
        return (List<E>)criteria.list();
    }
    
    
    
    /**
     * 
     * @param nativeQuery
     * @param parameters
     * @throws Exception
     */
    public void executeNativeUpdate(String nativeQuery, Map<String, Object> parameters) throws Exception
    {
        EntityTransaction et = entityManager.getTransaction();
        
        // If JTA transactions are being used, getTransaction() will fail with an IllegalStateException
        // This means that there is already an active transaction.
        boolean alreadyActive = true;
        try
        {
            alreadyActive = et.isActive();  // returns true if transaction is in progress
        }
        catch(IllegalStateException e)
        {
            // Transaction is already active (JTA)
        }
        
        if (!alreadyActive)
        {
            et.begin();
        }
        
        try
        {
        	Query query = entityManager.createNativeQuery(nativeQuery, entityClass);

//        	for(String key : parameters.keySet())
//        	{
//        		query.setParameter(key, parameters.get(key));
//        	}
//			Fix bug reported by Findbugs - Performance - Inefficient use of keySet iterator instead of entrySet iterator 
        	for (Iterator<Entry<String, Object>> it = parameters.entrySet().iterator(); it.hasNext();){
        		Entry<String, Object> entry = it.next();  
        		query.setParameter(entry.getKey(), entry.getValue());
        	}

        	query.executeUpdate();
            if (!alreadyActive)
            {
                et.commit();
            }
        }
        catch (Exception ex)
        {
            if (!alreadyActive)
            {
                et.rollback();
            }
            System.out.println(ex);
        }    	
    }
}
