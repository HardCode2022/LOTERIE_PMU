package com.course.pmu.service;

import com.course.pmu.entity.Course;
import com.course.pmu.entity.Partant;
import com.course.pmu.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service pour la persistence des données , utilisant JPA repository
 *
 */
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Creation et sauvegarde de la course en BDD
     * @param course course
     * @return course
     */
    public Course creerCourse(Course course) throws RuntimeException {
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

    /**
     * Recuperer l'ensemble des courses
     * @return course
     */
    public List<Course> recupererLesCourses() throws RuntimeException{
        return courseRepository.findAll();
    }

    /**
     * Recuperer une course par Id
     * @param id id
     * @return course
     */
    public Course recupererCourseId(Long id) throws RuntimeException{
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucune course avec ce numero n'a été trouvé");
        }
    }

    /**
     * Suppression de la course
     * @param id id
     */
    public void suppressionCourse(Long id) throws RuntimeException{
        courseRepository.deleteById(id);
    }

    /**
     * Mise à jour de la course
     * @param course course
     * @return course
     */
    public Course mettreAjourCourse(Course course) throws RuntimeException {
        course.getPartants().forEach(partant -> {
                    partant.setCourse(course);
        });
        course.setPartants(course.getPartants().stream().sorted(Comparator.comparingInt(Partant::getNumero)).collect(Collectors.toList()));
        return courseRepository.save(course);
    }
}
