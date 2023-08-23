package com.assignment.studioservice.response;

import com.assignment.studioservice.dto.VolunteerDTO;

import java.util.List;

public class GetVolunteersResponse {

    private List<VolunteerDTO> volunteers;

    public GetVolunteersResponse() {
    }

    public GetVolunteersResponse(List<VolunteerDTO> volunteers) {
        this.volunteers = volunteers;
    }

    public List<VolunteerDTO> getVolunteers() {
        return volunteers;
    }
}
