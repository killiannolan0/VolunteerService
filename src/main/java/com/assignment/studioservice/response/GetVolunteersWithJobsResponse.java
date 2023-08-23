package com.assignment.studioservice.response;

import com.assignment.studioservice.dto.VolunteerWithJobDTO;

import java.util.List;

public class GetVolunteersWithJobsResponse {

    private List<VolunteerWithJobDTO> volunteerWithJobs;
    private int currentPage;
    private long totalItems;

    public GetVolunteersWithJobsResponse(List<VolunteerWithJobDTO> volunteerWithJobs) {
        this.volunteerWithJobs = volunteerWithJobs;
    }

    public List<VolunteerWithJobDTO> getVolunteerWithJobs() {
        return volunteerWithJobs;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }
}
