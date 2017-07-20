package ru.bukan.ssm.domain;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author by Ilin_ai on 11.07.2017.
 */
@ContextConfiguration(locations = {"classpath:domain-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Component
public abstract class BasicTest {
    @Autowired
    private Dao dao;

    public Dao getDao() {
        return dao;
    }
}