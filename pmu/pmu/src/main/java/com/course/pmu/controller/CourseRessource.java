package com.course.pmu.controller;

import com.course.pmu.entity.Course;
import com.course.pmu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Api REST pour la mise à disposition des resources
 * je pouvais ajouter les autres verbes comme : GET , PUT , DELETE , mais je me limite au context de l'exercice
 */
@RestController
@RequestMapping("/api")
@Api(value = "PMU API", tags = {"Mon API"}, description = "API pour la gestion des courses de PMU")
public class CourseRessource {

    private static Logger LOGGER = Logger.getLogger(CourseRessource.class.getName());

    private CourseService courseService;

    private KafkaTemplate<String, Course> kafkaTemplate;

    public CourseRessource(CourseService courseService, KafkaTemplate<String, Course> kafkaTemplate) {
        this.courseService = courseService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/createcourses")
    @ApiOperation(value = "Crée une nouvelle course", notes = "Crée une nouvelle course en BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Course created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Course> createCourse(@RequestBody Course course) throws  RuntimeException {
        LOGGER.info("Entrée dans la creation de course");
        Course savegardeCourse = courseService.creerCourse(course);
        envoyerNouvelleCourse(savegardeCourse);
        return ResponseEntity.status(HttpStatus.CREATED).body(savegardeCourse);
    }

    /**
     * Envoie vers le Broker Kafka
     * @param savegardeCourse savegardeCourse
     */
    private void envoyerNouvelleCourse(Course savegardeCourse) throws  RuntimeException{
        kafkaTemplate.send("course_created", savegardeCourse);
    }

    @GetMapping("/getcourses")
    @ApiOperation(value = "Recuperer l'ensemble des courses", notes = "Recuperer l'ensemble des courses en BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO content"),
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<Course>> recupererCourses() throws  RuntimeException{
        LOGGER.info("Recuperation de la liste des courses");
        return new ResponseEntity<>(courseService.recupererLesCourses(),HttpStatus.OK);
    }

    /**
     * Recuperation de la course par numero
     * @param id numero
     * @return response
     */
    @GetMapping("courses/{id}")
    @ApiOperation(value = "Recuperer une course par numero", notes = "Recuperer une course par numero en BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Course> recupererCourseParId(@PathVariable Long id) throws  RuntimeException {
            LOGGER.info("Recuperation ");
            return new ResponseEntity<>(courseService.recupererCourseId(id), HttpStatus.OK);
    }

    /**
     * Supprimer course par Id
     * @param id id
     * @return noContent
     */
    @DeleteMapping("courses/suppression/{id}")
    @ApiOperation(value = "Supprimer une course par id", notes = "supprimer une course par id en BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> suppressionCourseParId(@PathVariable Long id) throws  RuntimeException {
         courseService.suppressionCourse(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Mis à jour de la course
     * @param id id
     * @param course course
     * @return responseEntity
     *
     */
    @PutMapping("course/miseAjour/{id}")
    @ApiOperation(value = "Mettre à jour une course ", notes = "Mettre à jour une course en BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Course created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Course> miseAjourCourse(@PathVariable Long id, @RequestBody Course course) throws  RuntimeException {
        return new ResponseEntity<>(courseService.mettreAjourCourse(course), HttpStatus.OK);
    }
}
