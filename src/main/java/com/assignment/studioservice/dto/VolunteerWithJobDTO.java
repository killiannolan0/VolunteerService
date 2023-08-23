package com.assignment.studioservice.dto;

import java.io.Serializable;
import java.util.List;

public class VolunteerWithJobDTO implements Serializable {

    private VolunteerDTO volunteer;
    private List<JobDTO> jobs;

    public VolunteerDTO getVolunteer() {
        return volunteer;
    }

    public List<JobDTO> getJobs() {
        return jobs;
    }

    public void setVolunteer(VolunteerDTO volunteer) {
        this.volunteer = volunteer;
    }

    public void setJobs(List<JobDTO> jobs) {
        this.jobs = jobs;
    }
}
