package com.assignment.studioservice.service;

import com.assignment.studioservice.response.GetVolunteersResponse;
import com.assignment.studioservice.response.GetVolunteersWithJobsResponse;

public interface VolunteerService {

    GetVolunteersResponse getVolunteersByJobId(Long id);

    GetVolunteersWithJobsResponse getAllVolunteers(int page, int size);

}
