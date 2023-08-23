package com.assignment.studioservice.service.impl;

import com.assignment.studioservice.entity.JobEntity;
import com.assignment.studioservice.entity.VolunteerEntity;
import com.assignment.studioservice.exception.BadImportException;
import com.assignment.studioservice.repository.JobRepository;
import com.assignment.studioservice.repository.VolunteerRepository;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static com.assignment.studioservice.constants.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImportServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;
    @Mock
    private JobRepository jobRepository;
    @InjectMocks
    private ImportServiceImpl importService;
    private final ArgumentCaptor<JobEntity> jobCapture = ArgumentCaptor.forClass(JobEntity.class);
    private final ArgumentCaptor<List<JobEntity>> jobsCapture = ArgumentCaptor.forClass(List.class);
    private final ArgumentCaptor<List<VolunteerEntity>> volunteersCapture = ArgumentCaptor.forClass(List.class);

    @Test(expected = BadImportException.class)
    public void importJobs_BadFile_ExpectBadImportException() throws IOException {
        MultipartFile mockFile = getMultipartFile("src/test/resources/test-files/badCsvFile.csv");
        importService.importJobsByCsv(mockFile);
    }

    @Test(expected = BadImportException.class)
    public void importVolunteers_BadFile_ExpectBadImportException() throws IOException {
        MultipartFile mockFile = getMultipartFile("src/test/resources/test-files/badCsvFile.csv");
        importService.importVolunteersByCsv(mockFile);
    }

    @Test(expected = BadImportException.class)
    public void importJobsVolunteers_BadFile_ExpectBadImportException() throws IOException {
        MultipartFile mockFile = getMultipartFile("src/test/resources/test-files/badCsvFile.csv");
        importService.importJobsVolunteersCSV(mockFile);
    }

    @Test(expected = BadImportException.class)
    public void importJobsVolunteers_BadFileMissingValue_ExpectBadImportException() throws IOException {
        MultipartFile mockFile = getMultipartFile("src/test/resources/test-files/volunteers-file-missing-value.csv");
        importService.importJobsVolunteersCSV(mockFile);
    }

    @Test
    public void importJobs() throws IOException {
        MultipartFile mockFile = getMultipartFile("src/test/resources/test-files/jobs.csv");
        importService.importJobsByCsv(mockFile);
        verify(jobRepository, times(1)).saveAll(jobsCapture.capture());
        List<JobEntity> jobs = jobsCapture.getValue();
        assertEquals(3, jobs.size());

        assertEquals(Long.valueOf(1), jobs.get(0).getId());
        assertEquals(JOB_NAME_ONE, jobs.get(0).getJobName());
        assertEquals(JOB_DESCRIPTION_ONE, jobs.get(0).getJobDescription());

        assertEquals(Long.valueOf(2), jobs.get(1).getId());
        assertEquals(JOB_NAME_TWO, jobs.get(1).getJobName());
        assertEquals(JOB_DESCRIPTION_TWO, jobs.get(1).getJobDescription());

        assertEquals(Long.valueOf(3), jobs.get(2).getId());
        assertEquals(JOB_NAME_THREE, jobs.get(2).getJobName());
        assertEquals(JOB_DESCRIPTION_THREE, jobs.get(2).getJobDescription());
    }

    @Test
    public void importVolunteers() throws IOException {
        MultipartFile mockFile = getMultipartFile("src/test/resources/test-files/volunteers.csv");
        importService.importVolunteersByCsv(mockFile);
        verify(volunteerRepository, times(1)).saveAll(volunteersCapture.capture());
        List<VolunteerEntity> volunteers = volunteersCapture.getValue();
        assertEquals(3, volunteers.size());

        assertEquals(Long.valueOf(1), volunteers.get(0).getId());
        assertEquals(FIRST_NAME_ONE, volunteers.get(0).getFirstName());
        assertEquals(LAST_NAME_ONE, volunteers.get(0).getLastName());

        assertEquals(Long.valueOf(2), volunteers.get(1).getId());
        assertEquals(FIRST_NAME_TWO, volunteers.get(1).getFirstName());
        assertEquals(LAST_NAME_TWO, volunteers.get(1).getLastName());

        assertEquals(Long.valueOf(3), volunteers.get(2).getId());
        assertEquals(FIRST_NAME_THREE, volunteers.get(2).getFirstName());
        assertEquals(LAST_NAME_THREE, volunteers.get(2).getLastName());
    }

    @Test
    public void importJobsVolunteers() throws IOException {
        MultipartFile mockFile = getMultipartFile("src/test/resources/test-files/jobs-volunteers.csv");
        Mockito.when(jobRepository.findById(1L)).thenReturn(populateNewJob(1, JOB_NAME_ONE, JOB_DESCRIPTION_TWO));
        Mockito.when(volunteerRepository.findById(6L)).thenReturn(populateNewVolunteer(6, FIRST_NAME_ONE, FIRST_NAME_TWO));
        importService.importJobsVolunteersCSV(mockFile);

        verify(jobRepository, times(1)).save(jobCapture.capture());
        JobEntity job = jobCapture.getValue();
        assertNotNull(job);
    }

    private Optional<VolunteerEntity> populateNewVolunteer(long id, String firstName, String lastName) {
        VolunteerEntity job = new VolunteerEntity();
        job.setId(id);
        job.setFirstName(firstName);
        job.setFirstName(lastName);
        return Optional.of(job);
    }

    private Optional<JobEntity> populateNewJob(long id, String jobName, String jobDescription) {
        JobEntity job = new JobEntity();
        job.setId(id);
        job.setJobName(jobName);
        job.setJobDescription(jobDescription);
        return Optional.of(job);
    }

    private MultipartFile getMultipartFile(String path) throws IOException {
        File file = new File(path);
        byte[] bytes = FileUtils.readFileToByteArray(file);

        InputStream stream = new ByteArrayInputStream(bytes);
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getInputStream()).thenReturn(stream);
        return mockFile;
    }


}
