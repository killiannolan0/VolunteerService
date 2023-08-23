package com.assignment.studioservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VOLUNTEERS")
public class VolunteerEntity {

    @Id
    @Column(name = "VOLUNTEER_ID")
    @NotNull
    private Long id;

    @Column(name = "FIRST_NAME")
    @NotNull
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "JOBS_VOLUNTEERS",
            joinColumns = @JoinColumn(name = "VOLUNTEER_ID"),
            inverseJoinColumns = @JoinColumn(name = "JOB_ID")
    )
    private Set<JobEntity> jobs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<JobEntity> getJobs() {
        return jobs;
    }

    public void setJobs(Set<JobEntity> jobs) {
        this.jobs = jobs;
    }
}
