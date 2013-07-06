/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */

package com.hp.et.log.appender.log4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Description goes here.
 */
public class XmlConfigParser
{
    /**
     * Description goes here.
     *
     * @param el
     * @return
     */
    public static Object parseElement(Element el)
    {
        String className = el.getAttribute("class");
        if (className != null)
        {
            try
            {
                Class expectedClass = Class.forName(className);
                Object instance = expectedClass.newInstance();
                if (instance != null)
                {
                    NodeList children = el.getChildNodes();

                    final int length = children.getLength();

                    for (int loop = 0; loop < length; loop++)
                    {
                        Node currentNode = children.item(loop);
                        if (currentNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element currentElement = (Element)currentNode;
                            String tagName = currentElement.getTagName();
                            if (tagName.equals("param"))
                            {
                                parseElementToField(currentElement, expectedClass, instance);
                            }
                            else
                            {
                                Object obj = parseElement(currentElement);
                                PropertyDescriptor propDesc = new PropertyDescriptor(tagName, expectedClass);
                                propDesc.getWriteMethod().invoke(instance, obj);
                            }
                        }
                    }
                return instance;
                }
                return null;
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (InstantiationException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IntrospectionException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
    
    private static void parseElementToField(Element el, Class clazz, Object obj)
        throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        try{
            String fieldName = el.getAttribute("name");
            String fieldValue = el.getAttribute("value");
        

            //            Field field = clazz.getDeclaredField(fieldName);
            //            field.setAccessible(true);
            //            Class typeClass = field.getType();
            //            Constructor con = typeClass.getConstructor(typeClass);
            //            Object fieldObj = con.newInstance(fieldValue);
        
            PropertyDescriptor propDesc = new PropertyDescriptor(fieldName, clazz);
            Method method = propDesc.getWriteMethod();
            Class fieldType = method.getParameterTypes()[0];

            if ("int".equals(fieldType.getName()))
            {
                method.invoke(obj, Integer.parseInt(fieldValue));
            }
            else
            {
                method.invoke(obj, fieldValue);
            }
            

            //            field.setAccessible(false);
        }
        catch (SecurityException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
