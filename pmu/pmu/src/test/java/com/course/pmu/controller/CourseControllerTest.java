package com.course.pmu.controller;

import com.course.pmu.entity.Course;
import com.course.pmu.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private CourseService courseService;

    @Mock
    private KafkaTemplate<String, Course> kafkaTemplate;

    private CourseRessource courseController;

    @BeforeEach
    public void setUp() {
        courseController = new CourseRessource(courseService, kafkaTemplate);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        objectMapper  = new ObjectMapper();
    }

    @Test
    @DisplayName("Test: creation d'une course")
    public void testCreationCourse() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setNom("Course 1");
        when(courseService.creerCourse(any(Course.class))).thenReturn(course);
        mockMvc.perform(post("/api/createcourses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isCreated());
        verify(courseService).creerCourse(any(Course.class));
        verify(kafkaTemplate).send("course_created", course);
    }
}
