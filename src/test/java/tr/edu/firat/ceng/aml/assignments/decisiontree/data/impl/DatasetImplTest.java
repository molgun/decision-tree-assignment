/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.firat.ceng.aml.assignments.decisiontree.data.impl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author molgun
 */
public class DatasetImplTest {

    public DatasetImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testClone() {
        ClassPropertyImpl propertyImpl = new ClassPropertyImpl("class");
        propertyImpl.getValues().add("a");
        propertyImpl.getValues().add("b");
        propertyImpl.getCopiedValues().remove("a");
        assertEquals((Number) 2, propertyImpl.size());
        DatasetImpl datasetImpl = new DatasetImpl(propertyImpl);
        DatasetImpl datasetImpl1 = new DatasetImpl(datasetImpl);
        assertNotSame(datasetImpl, datasetImpl1);
        assertNotSame(datasetImpl.getProperties(), datasetImpl1.getProperties());
        
    }
}
