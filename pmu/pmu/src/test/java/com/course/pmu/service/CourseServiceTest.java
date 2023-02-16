package com.course.pmu.service;

import com.course.pmu.entity.Course;
import com.course.pmu.entity.Partant;
import com.course.pmu.repository.CourseRepository;
import com.course.pmu.repository.CourseServiceInterface;
import com.course.pmu.service.Impl.CourseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

        @Mock
        private CourseRepository courseRepository;

        @InjectMocks
        private CourseServiceImpl courseService;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        @DisplayName("Test : creation d'une course")
        public void testCreationCourse() throws Exception {
            Partant partant1 = new Partant(1,"Partant 1", null);
            Partant partant2 = new Partant(2,"Partant 2", null);
            Partant partant3 = new Partant(3,"Partant 3", null);
            //initier liste de partants
            List<Partant> partants = new ArrayList<>();
            partants.add(partant1);
            partants.add(partant2);
            partants.add(partant3);
            Course course = new Course(1,"hasard", LocalDate.now(), partants);
            Mockito.when(courseRepository.save(course)).thenReturn(course);
            //Mockito.when(courseServiceInterface.creationCourse(course)).thenReturn(course);
            Course savedCourse = courseService.creationCourse(course);
            //verfier la veracité des valeurs
            Mockito.verify(courseRepository, Mockito.times(1)).save(course);
            Assertions.assertEquals(course, savedCourse);
        }

        @Test
        @DisplayName("Test : Verfier la levée d'une exception avec 1 partant")
        public void testCreationCourseWithLessThanThreePartants() {
            // Mock de données
            Partant partant1 = new Partant(1,"Partant 1", null);
            List<Partant> partants = new ArrayList<>();
            partants.add(partant1);
            Course course = new Course(1,"hasard", LocalDate.now(), partants);

            // Appel à la methode et verfier l'exception
            Assertions.assertThrows(ResponseStatusException.class, () -> courseService.creationCourse(course));
        }

        @Test
        @DisplayName("Test: verifier la non duplication")
        public void testCreationCourseSansDuplication() {
            // Mock de données
            Partant partant1 = new Partant(1,"Partant 1", null);
            Partant partant2 = new Partant(2,"Partant 2", null);
            Partant partant3 = new Partant(3,"Partant 3", null);
            Partant partant4 = new Partant(1,"Partant 4", null);
            List<Partant> partants = new ArrayList<>();
            partants.add(partant1);
            partants.add(partant2);
            partants.add(partant3);
            partants.add(partant4);
            Course course = new Course(1,"Partant 1", LocalDate.now(), partants);
            // Appel à la methode et verfier la levée d'une exception
            Assertions.assertThrows(ResponseStatusException.class, () -> courseService.creationCourse(course));
        }
    }

