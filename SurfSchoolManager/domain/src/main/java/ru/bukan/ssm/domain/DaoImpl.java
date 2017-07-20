package ru.bukan.ssm.domain;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author by Ilin_ai on 11.07.2017.
 */
@Repository
@Transactional
public class DaoImpl implements Dao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public <ENTITY> void save(ENTITY t) {
        em.persist(t);
    }

    @Override
    public <ENTITY> void delete(ENTITY t){
        em.remove(t);
    }

    @Override
    public <E> E getByKey(Class<E> klazz, Object key) {
        return em.find(klazz, key);
    }

    @Override
    public EntityManager getEm() {
        return em;
    }
}