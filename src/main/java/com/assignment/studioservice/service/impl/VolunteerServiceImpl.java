package com.assignment.studioservice.service.impl;

import com.assignment.studioservice.dto.VolunteerDTO;
import com.assignment.studioservice.dto.VolunteerWithJobDTO;
import com.assignment.studioservice.entity.VolunteerEntity;
import com.assignment.studioservice.repository.VolunteerRepository;
import com.assignment.studioservice.response.GetVolunteersResponse;
import com.assignment.studioservice.response.GetVolunteersWithJobsResponse;
import com.assignment.studioservice.service.VolunteerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    private static final Logger logger = LoggerFactory.getLogger(VolunteerServiceImpl.class);
    private VolunteerRepository volunteerRepository;
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public GetVolunteersResponse getVolunteersByJobId(Long id) {
        logger.info("Getting all volunteers for job id {}", id);
        List<VolunteerEntity> volunteers = volunteerRepository.findByJobs_Id(id);
        List<VolunteerDTO> volunteerDTOS = mapList(volunteers, VolunteerDTO.class);
        return new GetVolunteersResponse(volunteerDTOS);
    }

    @Transactional(readOnly = true)
    public GetVolunteersWithJobsResponse getAllVolunteers(int page, int size) {
        logger.info("Getting {} volunteers with job details", size);
        Page<VolunteerEntity> volunteerPage = volunteerRepository.findAll(PageRequest.of(page, size));
        List<VolunteerWithJobDTO> volunteerWithJobDTOs = mapList(volunteerPage.getContent(), VolunteerWithJobDTO.class);
        GetVolunteersWithJobsResponse response = new GetVolunteersWithJobsResponse(volunteerWithJobDTOs);
        response.setTotalItems(volunteerPage.getTotalElements());
        response.setCurrentPage(volunteerPage.getNumber());
        return response;
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    @Autowired
    public void setVolunteerRepo(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Autowired
    void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
