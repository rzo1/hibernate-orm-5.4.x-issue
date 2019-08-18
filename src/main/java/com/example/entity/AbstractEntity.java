package com.example.entity;

import javax.persistence.*;
import java.util.Random;
import java.util.UUID;

@MappedSuperclass
public class AbstractEntity {

    static long INVALID_OBJECT_ID = 0;

    @Transient
    protected static final Random RANDOM_GENERATOR = new Random();

    @SequenceGenerator(name = "sequence-object", sequenceName = "ID_MASTER_SEQ")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-object")
    @Column(name = "id")
    protected Long objectID = INVALID_OBJECT_ID;

    @Version
    private int version;

    @Column(nullable = false, unique = true, length = 36)
    private final String bID;

    protected AbstractEntity() {
        bID = UUID.nameUUIDFromBytes((Long.toString(System.currentTimeMillis()) + RANDOM_GENERATOR.nextInt())
                .getBytes()).toString();
    }

    public int getVersion() {
        return version;
    }

    public long getObjectID() {
        return objectID;
    }

    public static boolean isValidObjectID(long id) {
        return (id > 0 && id != AbstractEntity.INVALID_OBJECT_ID);
    }

    public boolean isPersistent() {
        return isValidObjectID(getObjectID());
    }

    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getObjectID());
    }

    public String getBID() {
        return bID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEntity that = (AbstractEntity) o;

        return bID != null ? bID.equals(that.bID) : that.bID == null;

    }

    @Override
    public int hashCode() {
        return bID != null ? bID.hashCode() : 0;
    }
}
