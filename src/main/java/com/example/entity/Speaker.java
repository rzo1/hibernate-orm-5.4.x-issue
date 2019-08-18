package com.example.entity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Speaker extends AbstractEntity {


    @ManyToOne(targetEntity = Project.class, fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Project project;

    public Speaker() {

    }

    public Speaker(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
