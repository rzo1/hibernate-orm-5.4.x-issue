package com.example.service;

import org.apache.openejb.OpenEjbContainer;
import org.apache.openejb.junit.jee.EJBContainerRule;
import org.apache.openejb.junit.jee.InjectRule;
import org.apache.openejb.junit.jee.config.Properties;
import org.apache.openejb.junit.jee.config.Property;
import org.apache.openejb.junit.jee.resources.TestResource;
import org.junit.ClassRule;
import org.junit.Rule;

import javax.naming.Context;


@Properties(
        @Property(
                key = OpenEjbContainer.OPENEJB_EJBCONTAINER_CLOSE,
                value = OpenEjbContainer.OPENEJB_EJBCONTAINER_CLOSE_SINGLE))
public class AbstractBaseTest {

    @ClassRule
    public static final EJBContainerRule CONTAINER_RULE = new EJBContainerRule();

    protected static final Integer PORT = 4204;

    static {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.RemoteInitialContextFactory");
    }

    @Rule
    public final InjectRule injectRule = new InjectRule(this, CONTAINER_RULE);

    @TestResource
    private Context ctx;

}

