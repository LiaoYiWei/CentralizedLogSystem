package com.hp.et.log.dao;

import java.util.List;

import javax.persistence.EntityManager;

public interface IDao<K, E>
{
    void          setEntityManager(EntityManager entityManager);
    EntityManager getEntityManager();
    
    void          persist( E entity );
    void          remove( E entity );
    void merge(E entity);
    void flush();
    
    E             findById( K id );
    E             getReferenceById(K id);
    List<E>       findByExample( E example );
}
