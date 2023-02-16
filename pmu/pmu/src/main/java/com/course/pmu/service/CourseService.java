package com.course.pmu.service;

import com.course.pmu.entity.Course;
import com.course.pmu.repository.CourseServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service pour la persistence des données , utilisant JPA repository
 *
 */
@Service
public class CourseService {

    @Autowired
    private CourseServiceInterface courseServiceInterface;

    /**
     * Creation et sauvegarde de la course en BDD
     * @param course course
     * @return course
     */
    public Course creerCourse(Course course) throws RuntimeException {
        return courseServiceInterface.creationCourse(course);
    }

    /**
     * Recuperer l'ensemble des courses
     * @return course
     */
    public List<Course> recupererLesCourses() throws RuntimeException{
        return courseServiceInterface.recupererToutesCourses();
    }

    /**
     * Recuperer une course par Id
     * @param id id
     * @return course
     */
    public Course recupererCourseId(Long id) throws RuntimeException{
        return courseServiceInterface.recupererCourseParId(id);
    }

    /**
     * Suppression de la course
     * @param id id
     */
    public void suppressionCourse(Long id) throws RuntimeException{
        courseServiceInterface.supprimerCourse(id);
    }

    /**
     * Mise à jour de la course
     * @param course course
     * @return course
     */
    public Course mettreAjourCourse(Course course) throws RuntimeException {
        return courseServiceInterface.miseAjourCourse(course);
    }
}
