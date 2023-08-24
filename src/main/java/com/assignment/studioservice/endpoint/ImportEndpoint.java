package com.assignment.studioservice.endpoint;

import com.assignment.studioservice.entity.JobEntity;
import com.assignment.studioservice.entity.VolunteerEntity;
import com.assignment.studioservice.service.ImportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/import")
public class ImportEndpoint {

    @Autowired
    private ImportService service;

    @PutMapping("/csv/jobs")
    @ApiOperation(value = "Provides functionality for importing jobs via a CSV file.")
    public ResponseEntity<List<JobEntity>> importJobsByExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(service.importJobsByCsv(file));
    }

    @PutMapping("/csv/volunteers")
    @ApiOperation(value = "Provides functionality for importing volunteers via a CSV file.")
    public ResponseEntity<List<VolunteerEntity>> importVolunteersByCsv(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(service.importVolunteersByCsv(file));
    }

    @PutMapping("/csv/job-volunteers")
    @ApiOperation(value = "Provides functionality for importing jobs-volunteers relationships via a CSV file.")
    public ResponseEntity<Object> importJobVolunteersByCsv(@RequestParam("file") MultipartFile file) throws IOException {
        service.importJobsVolunteersCSV(file);
        return ResponseEntity.ok().body("Success");
    }
}
