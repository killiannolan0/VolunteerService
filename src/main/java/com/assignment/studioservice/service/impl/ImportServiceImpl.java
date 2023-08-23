package com.assignment.studioservice.service.impl;

import com.assignment.studioservice.entity.JobEntity;
import com.assignment.studioservice.entity.VolunteerEntity;
import com.assignment.studioservice.exception.BadImportException;
import com.assignment.studioservice.repository.JobRepository;
import com.assignment.studioservice.repository.VolunteerRepository;
import com.assignment.studioservice.service.ImportService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {

    private static final Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);
    private JobRepository jobRepository;
    private VolunteerRepository volunteerRepository;


    public List<JobEntity> importJobsByCsv(MultipartFile file) throws IOException {
        logger.info("Importing jobs from submitted CSV file");
        final CSVParser parser = getCsvRecords(file, CSVFormat.EXCEL.withHeader());
        List<JobEntity> jobEntityList = new ArrayList<>();
        try {
            for (CSVRecord csvRecord : parser) {
                JobEntity entity = new JobEntity();
                entity.setId(Long.valueOf(csvRecord.get("id")));
                entity.setJobName(csvRecord.get("job_name"));
                entity.setJobDescription(csvRecord.get("job_desc"));
                jobEntityList.add(entity);
            }
            if (jobEntityList.isEmpty()) {
                logger.info("Zero records found on csv file.");
                return jobEntityList;
            }
            return jobRepository.saveAll(jobEntityList);
        } catch (IllegalArgumentException e) {
            throw new BadImportException(e.getMessage());
        }
    }

    public List<VolunteerEntity> importVolunteersByCsv(MultipartFile file) throws IOException {
        logger.info("Importing volunteers from submitted CSV file");
        final CSVParser parser = getCsvRecords(file, CSVFormat.DEFAULT.withHeader().withIgnoreEmptyLines(true).withTrim());
        List<VolunteerEntity> volunteers = new ArrayList<>();
        try {
            for (CSVRecord csvRecord : parser) {
                VolunteerEntity entity = new VolunteerEntity();
                if (csvRecord.get("id").isEmpty()) {
                    break;
                }
                entity.setId(Long.valueOf(csvRecord.get("id")));
                entity.setFirstName(csvRecord.get("first_name"));
                entity.setLastName(csvRecord.get("last_name"));
                volunteers.add(entity);
            }
            return volunteerRepository.saveAll(volunteers);
        } catch (IllegalArgumentException e) {
            throw new BadImportException(e.getMessage());
        }
    }

    public void importJobsVolunteersCSV(MultipartFile file) throws IOException {
        logger.info("Importing jobs-volunteers relationships from submitted CSV file");
        final CSVParser parser = getCsvRecords(file, CSVFormat.DEFAULT.withHeader().withIgnoreEmptyLines(true).withTrim());
        List<Pair<Long, Long>> volunteers = new ArrayList<>();
        try {
            for (CSVRecord csvRecord : parser) {
                Pair<Long, Long> jobVolunteer = Pair.of(Long.valueOf(csvRecord.get("job_id")),
                        Long.valueOf(csvRecord.get("volunteer_id")));
                volunteers.add(jobVolunteer);
            }
        } catch (IllegalArgumentException e) {
            throw new BadImportException(e.getMessage());
        }

        for (Pair<Long, Long> pair : volunteers) {
            JobEntity job = jobRepository.findById(pair.getFirst()).orElse(null);
            VolunteerEntity volunteer = volunteerRepository.findById(pair.getSecond()).orElse(null);

            if (job != null && volunteer != null) {
                job.getVolunteers().add(volunteer);
                jobRepository.save(job);
            }
        }
    }

    private CSVParser getCsvRecords(MultipartFile file, CSVFormat EXCEL) throws IOException {
        final Reader reader = new InputStreamReader(new BOMInputStream(file.getInputStream()), StandardCharsets.UTF_8);
        return new CSVParser(reader, EXCEL);
    }

    @Autowired
    public void setJobRepo(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Autowired
    public void setVolunteerRepo(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }
}
