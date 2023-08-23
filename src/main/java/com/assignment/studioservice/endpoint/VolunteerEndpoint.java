package com.assignment.studioservice.endpoint;

import com.assignment.studioservice.response.GetVolunteersResponse;
import com.assignment.studioservice.response.GetVolunteersWithJobsResponse;
import com.assignment.studioservice.service.VolunteerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteer")
public class VolunteerEndpoint {

    @Autowired
    private VolunteerService service;

    @GetMapping("/job/{id}")
    @ApiOperation(value = "Returns all volunteers for a given job ID.")
    public ResponseEntity<GetVolunteersResponse> getVolunteersByJobId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.getVolunteersByJobId(id));
    }

    @GetMapping("/")
    @ApiOperation(value = "Returns all volunteers with job details with ability to specify page and size.")
    public ResponseEntity<GetVolunteersWithJobsResponse> getAllVolunteers(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(service.getAllVolunteers(page, size));
    }
}
