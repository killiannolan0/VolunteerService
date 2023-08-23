package com.assignment.studioservice.service.impl;

import com.assignment.studioservice.dto.VolunteerDTO;
import com.assignment.studioservice.dto.VolunteerWithJobDTO;
import com.assignment.studioservice.entity.JobEntity;
import com.assignment.studioservice.entity.VolunteerEntity;
import com.assignment.studioservice.repository.VolunteerRepository;
import com.assignment.studioservice.response.GetVolunteersResponse;
import com.assignment.studioservice.response.GetVolunteersWithJobsResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.assignment.studioservice.constants.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class VolunteerServiceImplTest {

    private static final Long JOB_ID = 1l;
    private static final Long ID_FIVE = 5l;
    private static final Long ID_SIX = 6l;
    private static List<VolunteerEntity> volunteers;

    @Mock
    private VolunteerRepository volunteerRepository;
    @InjectMocks
    private VolunteerServiceImpl volunteerService;

    @Before
    public void setup() {
        volunteerService.setModelMapper(new ModelMapper());
    }

    @Test
    public void getVolunteersByJobId_ZeroResults_ReturnEmptyList() {
        GetVolunteersResponse response = volunteerService.getVolunteersByJobId(JOB_ID);
        assertEquals(0, response.getVolunteers().size());
    }

    @Test
    public void getVolunteersByJobId_VolunteersReturned_ReturnResponseWithVolunteers() {
        populateVolunteers(true);
        Mockito.when(volunteerRepository.findByJobs_Id(JOB_ID)).thenReturn(volunteers);
        List<VolunteerDTO> volunteersResponse = volunteerService.getVolunteersByJobId(JOB_ID).getVolunteers();
        assertEquals(2, volunteersResponse.size());

        assertEquals(ID_FIVE, volunteersResponse.get(0).getId());
        assertEquals(FIRST_NAME_ONE, volunteersResponse.get(0).getFirstName());
        assertEquals(LAST_NAME_ONE, volunteersResponse.get(0).getLastName());

        assertEquals(ID_SIX, volunteersResponse.get(1).getId());
        assertEquals(FIRST_NAME_TWO, volunteersResponse.get(1).getFirstName());
        assertEquals(LAST_NAME_TWO, volunteersResponse.get(1).getLastName());
    }

    @Test
    public void getAllVolunteers_ZeroResults_ReturnEmptyResponse() {
        Page<VolunteerEntity> page = new PageImpl<>(Collections.emptyList());
        Mockito.when(volunteerRepository.findAll(any(PageRequest.class))).thenReturn(page);
        GetVolunteersWithJobsResponse response = volunteerService.getAllVolunteers(0, 100);
        assertEquals(0, response.getTotalItems());
        assertEquals(0, response.getVolunteerWithJobs().size());
    }

    @Test
    public void getAllVolunteers_VolunteersReturned_ReturnVolunteersWithJobDetails() {
        populateVolunteers(true);
        Page<VolunteerEntity> page = new PageImpl<>(volunteers);
        Mockito.when(volunteerRepository.findAll(any(PageRequest.class))).thenReturn(page);
        GetVolunteersWithJobsResponse volunteersResponse = volunteerService.getAllVolunteers(0, 10);
        List<VolunteerWithJobDTO> volunteerWithJobs = volunteersResponse.getVolunteerWithJobs();
        assertEquals(2, volunteerWithJobs.size());
        assertEquals(2, volunteersResponse.getTotalItems());
        assertEquals(0, volunteersResponse.getCurrentPage());

        assertEquals(ID_FIVE, volunteerWithJobs.get(0).getVolunteer().getId());
        assertEquals(FIRST_NAME_ONE, volunteerWithJobs.get(0).getVolunteer().getFirstName());
        assertEquals(LAST_NAME_ONE, volunteerWithJobs.get(0).getVolunteer().getLastName());
        assertEquals(JOB_ID, volunteerWithJobs.get(0).getJobs().get(0).getJobId());
        assertEquals(JOB_NAME_ONE, volunteerWithJobs.get(0).getJobs().get(0).getJobName());
        assertEquals(JOB_DESCRIPTION_ONE, volunteerWithJobs.get(0).getJobs().get(0).getJobDescription());

        assertEquals(ID_SIX, volunteerWithJobs.get(1).getVolunteer().getId());
        assertEquals(FIRST_NAME_TWO, volunteerWithJobs.get(1).getVolunteer().getFirstName());
        assertEquals(LAST_NAME_TWO, volunteerWithJobs.get(1).getVolunteer().getLastName());
        assertEquals(JOB_ID, volunteerWithJobs.get(1).getJobs().get(0).getJobId());
        assertEquals(JOB_NAME_ONE, volunteerWithJobs.get(1).getJobs().get(0).getJobName());
        assertEquals(JOB_DESCRIPTION_ONE, volunteerWithJobs.get(1).getJobs().get(0).getJobDescription());
    }

    @Test
    public void getAllVolunteers_VolunteersReturnedWithNoJobs_ReturnVolunteersWithNoJobs() {
        populateVolunteers(false);
        Page<VolunteerEntity> page = new PageImpl<>(volunteers);
        Mockito.when(volunteerRepository.findAll(any(PageRequest.class))).thenReturn(page);

        GetVolunteersWithJobsResponse volunteersResponse = volunteerService.getAllVolunteers(0, 10);
        List<VolunteerWithJobDTO> volunteerWithJobs = volunteersResponse.getVolunteerWithJobs();
        assertEquals(2, volunteerWithJobs.size());
        assertEquals(2, volunteersResponse.getTotalItems());
        assertEquals(0, volunteersResponse.getCurrentPage());

        assertEquals(ID_FIVE, volunteerWithJobs.get(0).getVolunteer().getId());
        assertEquals(FIRST_NAME_ONE, volunteerWithJobs.get(0).getVolunteer().getFirstName());
        assertEquals(LAST_NAME_ONE, volunteerWithJobs.get(0).getVolunteer().getLastName());
        assertEquals(0, volunteerWithJobs.get(0).getJobs().size());

        assertEquals(ID_SIX, volunteerWithJobs.get(1).getVolunteer().getId());
        assertEquals(FIRST_NAME_TWO, volunteerWithJobs.get(1).getVolunteer().getFirstName());
        assertEquals(LAST_NAME_TWO, volunteerWithJobs.get(1).getVolunteer().getLastName());
        assertEquals(0, volunteerWithJobs.get(1).getJobs().size());
    }

    private void populateVolunteers(boolean withJobs) {
        volunteers = new ArrayList<>();
        VolunteerEntity entity1 = new VolunteerEntity();
        entity1.setFirstName(FIRST_NAME_ONE);
        entity1.setLastName(LAST_NAME_ONE);
        entity1.setId(ID_FIVE);
        VolunteerEntity entity2 = new VolunteerEntity();
        entity2.setFirstName(FIRST_NAME_TWO);
        entity2.setLastName(LAST_NAME_TWO);
        entity2.setId(ID_SIX);
        if (withJobs) {
            JobEntity job = new JobEntity();
            job.setId(JOB_ID);
            job.setJobName(JOB_NAME_ONE);
            job.setJobDescription(JOB_DESCRIPTION_ONE);
            entity1.setJobs(new HashSet<>(List.of(job)));
            entity2.setJobs(new HashSet<>(List.of(job)));
        }
        volunteers.add(entity1);
        volunteers.add(entity2);
    }
}
