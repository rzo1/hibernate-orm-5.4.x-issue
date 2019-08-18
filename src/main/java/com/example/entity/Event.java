package com.example.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Event extends AbstractEntity {

    @ManyToOne(targetEntity = Event.class, fetch = FetchType.EAGER)
    private Event parent;

    @OneToMany(targetEntity = Event.class, cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "parent")
    private Set<Event> children = new HashSet<>();

    @ManyToOne(targetEntity = Project.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Project project;

    @ManyToMany(targetEntity = Speaker.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Speaker> speakers = new HashSet<>();


    public Event() {
        //framework purpose
    }

    public Event(Event parent, Project project) {
       setParent(parent);
       setProject(project);

        if (parent == null) {
            //nothing to do here, Event has no parent
        } else {
            parent.addChild(this);
        }
    }

    public void setParent(Event parent) {
        this.parent = parent;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Event getParent() {
        return parent;
    }

    public Project getProject() {
        return project;
    }

    public Set<Speaker> getSpeakers() {
        return Collections.unmodifiableSet(speakers);
    }

    public Set<Event> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public void addSpeaker(Speaker speaker) {
        assert speaker != null;
        this.speakers.add(speaker);
    }

    public void addChild(Event event) {
        assert event != null;
        this.children.add(event);
        event.setParent(this);
    }

}
