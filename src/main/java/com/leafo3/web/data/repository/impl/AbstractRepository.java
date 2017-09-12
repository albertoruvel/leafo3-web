package com.leafo3.web.data.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AbstractRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractRepository(){

    }
}
