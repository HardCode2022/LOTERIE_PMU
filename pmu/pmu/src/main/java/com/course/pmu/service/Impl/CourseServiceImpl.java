package com.course.pmu.service.Impl;

import com.course.pmu.entity.Course;
import com.course.pmu.entity.Partant;
import com.course.pmu.repository.CourseRepository;
import com.course.pmu.repository.CourseServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseServiceInterface {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course creationCourse(Course course) throws RuntimeException {
        if (course.getPartants().size() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une course doit avoir au moins 3 partants");
        }
        Set<Integer> numPartants = new HashSet<>();
        int i = 1;
        for (Partant partant : course.getPartants()) {
            if (partant.getNumero() != i++) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les partants d'une course doivent être numérotés à partir de 1, sans doublon ni trou");
            }
            if (partant.getNom() == null || partant.getNom().isEmpty() || partant.getNumero() == 0) {
                throw new IllegalArgumentException("Chaque partant doit avoir un nom et un numéro.");
            }
            if (partant.getNumero() < 1) {
                throw new IllegalArgumentException("Les numéros de partant doivent être supérieurs ou égaux à 1.");
            }
            if (!numPartants.add(partant.getNumero())) {
                throw new IllegalArgumentException("Les numéros de partant doivent être uniques.");
            }
             partant.setCourse(course); // Ajout de cette ligne pour lier les entités Course et Partant
        }

        course.setPartants(course.getPartants().stream().sorted(Comparator.comparingInt(Partant::getNumero)).collect(Collectors.toList()));
        return courseRepository.save(course);
    }

    @Override
    public List<Course> recupererToutesCourses() throws  RuntimeException {
        return courseRepository.findAll();
    }

    @Override
    public Course recupererCourseParId(Long id) throws  RuntimeException {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucune course avec ce numero n'a été trouvé");
        }
    }

    @Override
    public void supprimerCourse(Long id) throws  RuntimeException {
        courseRepository.deleteById(id);
    }

    @Override
    public Course miseAjourCourse(Course course) throws  RuntimeException {
        for (Partant partant : course.getPartants()) {
            partant.setCourse(course); // Ajout de cette ligne pour lier les entités Course et Partant
        }
        course.setPartants(course.getPartants().stream().sorted(Comparator.comparingInt(Partant::getNumero)).collect(Collectors.toList()));
        return courseRepository.save(course);
    }
}
