package com.assignment.studioservice.service;

import com.assignment.studioservice.entity.JobEntity;
import com.assignment.studioservice.entity.VolunteerEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImportService {

    List<JobEntity> importJobsByCsv(MultipartFile file) throws IOException;

    List<VolunteerEntity> importVolunteersByCsv(MultipartFile file) throws IOException;

    void importJobsVolunteersCSV(MultipartFile file) throws IOException;
}
