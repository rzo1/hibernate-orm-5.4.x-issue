package com.example.service;

import com.example.entity.AbstractEntity;
import com.example.entity.Event;
import com.example.entity.Project;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

@Stateless
public class EntityService {


    @PersistenceContext(unitName = "example", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;


    public long store(AbstractEntity persistentObject) {
        persistentObject = entityManager.merge(persistentObject);
        entityManager.persist(persistentObject);
        entityManager.flush();
        return persistentObject.getObjectID();
    }

    public <T extends AbstractEntity> T getObjectByID(long objectID, Class<T> type) {
        return entityManager.find(type, objectID);
    }


    public Project getProjectByOID(long oid) {
        TypedQuery<Project> projectTypedQuery = entityManager.createQuery("SELECT p FROM Project p WHERE p.objectID = :oid", Project.class);

        projectTypedQuery.setParameter("oid", oid);

        return projectTypedQuery.getSingleResult();
    }

    public Event getEventByOID(long oid) {
        TypedQuery<Event> eventTypedQuery = entityManager.createQuery("SELECT e FROM Event e LEFT JOIN FETCH e.speakers LEFT JOIN FETCH e.children LEFT JOIN FETCH e.project WHERE e.objectID = :oid", Event.class);

        eventTypedQuery.setParameter("oid", oid);

        return eventTypedQuery.getSingleResult();
    }
}
