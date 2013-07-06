package com.hp.et.log.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import provider.ObjectProvider;
@Transactional
public class LogApplication extends Application
{
    private Set<Object> singletons = new HashSet<Object>();

    private Set<Class<?>> classes = new HashSet<Class<?>>();

    public LogApplication()
   {
        //BeanFactory bf = new ClassPathXmlApplicationContext(new String[] {"classpath:applicationContext-*.xml"},
        //        ContextLoader.getCurrentWebApplicationContext());
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        singletons.add(context.getBean("eventResource"));
        singletons.add(context.getBean("applicationResource"));
        singletons.add(context.getBean("authentication"));
        singletons.add(context.getBean("hostResource"));
        singletons.add(context.getBean("fileTransferResource"));
        singletons.add(context.getBean("monitorService"));
        singletons.add(context.getBean("appEnvRuleResource"));
        classes.add(ObjectProvider.class);
//        classes.add(XStreamProvider.class);
//      singletons.add(new EventResource());
//      singletons.add(new Authentication());
//      classes.add(NotFoundExceptionMapper.class);
   }

    @Override
    public Set<Class<?>> getClasses()
    {
        return classes;
    }

    @Override
    public Set<Object> getSingletons()
    {
        return singletons;
    }
}
