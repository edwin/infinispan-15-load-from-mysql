package com.edw.model;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

import java.io.Serializable;

/**
 * <pre>
 *  com.edw.model.Employee
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 16 Oct 2024 16:09
 */
@Indexed
public class Employee implements Serializable {

    private String gender;
    private Long id;
    private String firstname;
    private String lastname;

    public Employee() {
    }

    @ProtoFactory
    public Employee(String gender, Long id, String firstname, String lastname) {
        this.gender = gender;
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @ProtoField(1)
    @Basic
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @ProtoField(2)
    @Basic
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ProtoField(3)
    @Basic
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @ProtoField(4)
    @Basic
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
