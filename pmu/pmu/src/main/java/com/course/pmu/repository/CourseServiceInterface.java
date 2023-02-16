package com.course.pmu.repository;

import com.course.pmu.entity.Course;

import java.util.List;

public interface CourseServiceInterface {
    Course creationCourse(Course course) throws RuntimeException;
    List<Course> recupererToutesCourses() throws RuntimeException;
    Course recupererCourseParId(Long id)throws RuntimeException;
    void supprimerCourse(Long id) throws RuntimeException;
    Course miseAjourCourse(Course course) throws RuntimeException;
}
