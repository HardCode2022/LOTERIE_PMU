package com.course.pmu.service;

import com.course.pmu.entity.Course;
import com.course.pmu.entity.Partant;
import com.course.pmu.repository.CourseRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

        @Mock
        private CourseRepository courseRepository;
        @InjectMocks
        private CourseService courseService;
        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        @DisplayName("Test : creation d'une course")
        public void testCreationCourse() throws Exception {
            Partant partant1 = new Partant(1L, 1,"Partant 1",null);
            Partant partant2 = new Partant(2L, 2,"Partant 2",null);
            Partant partant3 = new Partant(3L,3,"Partant 3", null);
            //initier liste de partants
            List<Partant> partants = new ArrayList<>();
            partants.add(partant1);
            partants.add(partant2);
            partants.add(partant3);
            Course course = new Course(1L,1,"hasard", LocalDate.now(), partants);
            when(courseRepository.save(course)).thenReturn(course);
            //Mockito.when(courseServiceInterface.creationCourse(course)).thenReturn(course);
            Course savedCourse = courseService.creerCourse(course);
            //verfier la veracité des valeurs
            Mockito.verify(courseRepository, times(1)).save(course);
            Assertions.assertEquals(course, savedCourse);
        }

        @Test
        @DisplayName("Test : Verfier la levée d'une exception avec 1 partant")
        public void testCreationCourseAvec1partant() {
            // Mock de données
            Partant partant1 = new Partant(1L, 1,"Partant 1",null);
            List<Partant> partants = new ArrayList<>();
            partants.add(partant1);
            Course course = new Course(1L,1,"hasard", LocalDate.now(), partants);
            // Appel à la methode et verfier l'exception
            assertThrows(ResponseStatusException.class, () -> courseService.creerCourse(course));
        }

        @Test
        @DisplayName("Test: verifier la non duplication")
        public void testCreationCourseSansDuplication() {
            // Mock de données
            Partant partant1 = new Partant(1L, 1,"Partant 1",null);
            Partant partant2 = new Partant(1L,1,"Partant 2", null);
            Partant partant3 = new Partant(3L,3,"Partant 3", null);
            Partant partant4 = new Partant(4L,4,"Partant 4", null);
            List<Partant> partants = new ArrayList<>();
            partants.add(partant1);
            partants.add(partant2);
            partants.add(partant3);
            partants.add(partant4);
            Course course = new Course(1L,1,"Partant 1", LocalDate.now(), partants);
            // Appel à la methode et verfier la levée d'une exception
            assertThrows(ResponseStatusException.class, () -> courseService.creerCourse(course));
        }

    @Test
    @DisplayName("Test:Recuperer toutes les courses")
    public void testRecupererToutesCourses() {

        Course course1 = new Course(1L,1,"Partant 1", LocalDate.now(), null);
        Course course2 = new Course(2L,2,"Partant 2", LocalDate.now(), null);
        List<Course> courses = Arrays.asList(course1, course2);
        when(courseRepository.findAll()).thenReturn(courses);
        List<Course> result = courseService.recupererLesCourses();
        Assertions.assertEquals(courses, result);
      }
    @Test
    @DisplayName("Test:Recuperer course par Id cas Ok")
    public void testRecupererCourseParId_ExistingCourse() {

        Course course = new Course(1L,1,"Partant A", LocalDate.now(), null);
        course.setId(1L);
        Optional<Course> optionalCourse = Optional.of(course);
        when(courseRepository.findById(1L)).thenReturn(optionalCourse);
        Course result = courseService.recupererCourseId(1L);
        Assertions.assertAll(
                ()->Assertions.assertEquals(course.getId(), result.getId()),
                ()->Assertions.assertEquals(course.getNumero(), result.getNumero())
        );
    }

    @Test
    @DisplayName("Test:Recuperer course par Id cas KO")
    public void testRecupererCourseParId_NonExistingCourse() {
        Optional<Course> optionalCourse = Optional.empty();
        when(courseRepository.findById(1L)).thenReturn(optionalCourse);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            courseService.recupererCourseId(1L);
        });
        Assertions.assertEquals("400 BAD_REQUEST \"Aucune course avec ce numero n'a été trouvé\"", exception.getMessage());
      }

    @Test
    @DisplayName("Test : suppression OK avec course")
    public void testSupprimerCourse_ExistingCourse() {
        courseService.suppressionCourse(1L);
        Mockito.verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test : Mise a jour de course")
    public void testMiseAjourCourse() {
        Course course1 = new Course(1L,1,"Partant 1", LocalDate.now(), null);
        Partant partant1 = new Partant(1L, 1,"Partant 1",course1);
        Partant partant2 = new Partant(2L,2,"Partant 2", null);
        Partant partant3 = new Partant(3L,3,"Partant 3", null);
        Partant partant4 = new Partant(4L,4,"Partant 4", null);
        List<Partant> partants = new ArrayList<>();
        partants.add(partant1);
        partants.add(partant2);
        partants.add(partant3);
        partants.add(partant4);
        Course course = new Course(1L,1,"Partant B", LocalDate.now(), partants);
        course.setId(1L);
        when(courseRepository.save(course)).thenReturn(course);
        Course result = courseService.mettreAjourCourse(course);
        Assertions.assertEquals(course, result);
    }
}

