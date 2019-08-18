package com.example.service;

import com.example.entity.Event;
import com.example.entity.Project;
import com.example.entity.Speaker;
import org.apache.openejb.junit.OpenEjbRunner;
import org.apache.openejb.junit.jee.EJBContainerRunner;
import org.apache.openejb.junit.jee.config.PropertyFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

@PropertyFile("openejb-junit.properties")
@RunWith(EJBContainerRunner.class)
public class EntityServiceTest extends AbstractBaseTest{

    @Inject
    private EntityService service;

    private Project defaultProject;

    @Before
    public void setUp() {
        assertNotNull(service);

        long id = service.store(new Project());

        defaultProject = service.getProjectByOID(id);
        assertNotNull(defaultProject);
}

    @Test
    public void testObjectChain() {

        Event root = new Event(null, defaultProject);

        long rootId = service.store(root);

        Event rootFromDB = service.getEventByOID(rootId);
        assertNotNull(rootFromDB);
        assertEquals(0, rootFromDB.getChildren().size());
        assertEquals(0, rootFromDB.getSpeakers().size());
        assertEquals(root, rootFromDB);

        Speaker speaker = new Speaker(defaultProject);

        long speakerID = service.store(speaker);
        speaker = service.getObjectByID(speakerID, Speaker.class);
        assertNotNull(speaker);

        Event child = new Event(rootFromDB, defaultProject);
        child.addSpeaker(speaker);


        rootId = service.store(rootFromDB);
        rootFromDB = service.getEventByOID(rootId);
        assertNotNull(rootFromDB);
        assertEquals(1, rootFromDB.getChildren().size());
        assertEquals(0, rootFromDB.getSpeakers().size());


    }

}
