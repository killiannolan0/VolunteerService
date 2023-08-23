package com.assignment.studioservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "JOBS")
public class JobEntity {

    @Id
    @Column(name = "JOB_ID")
    @NotNull
    private Long id;

    @Column(name = "JOB_NAME")
    @NotNull
    private String jobName;

    @Column(name = "JOB_DESCRIPTION", length = 600)
    @NotNull
    private String jobDescription;

    @ManyToMany
    @JoinTable(
            name = "jobs_volunteers",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "volunteer_id")
    )
    private Set<VolunteerEntity> volunteers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Set<VolunteerEntity> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(Set<VolunteerEntity> volunteers) {
        this.volunteers = volunteers;
    }
}
