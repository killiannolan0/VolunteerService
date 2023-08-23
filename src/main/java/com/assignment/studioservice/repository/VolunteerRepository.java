package com.assignment.studioservice.repository;

import com.assignment.studioservice.entity.VolunteerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRepository extends JpaRepository<VolunteerEntity, Long> {

    List<VolunteerEntity> findByJobs_Id(Long jobId);

}
