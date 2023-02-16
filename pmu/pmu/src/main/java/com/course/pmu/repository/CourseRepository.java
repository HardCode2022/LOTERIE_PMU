package com.course.pmu.repository;

import com.course.pmu.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository pour permettre la persistence des données en BDD
 */
@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
}
