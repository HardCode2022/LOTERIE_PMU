package com.course.pmu.listener;

import com.course.pmu.entity.Course;
import com.course.pmu.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CourseCreatedListener {

    @Autowired
    private CourseRepository courseRepository;

    @KafkaListener(topics = "course_Created")
    public void onCourseCreated(Course course) {
        courseRepository.save(course);
    }
}
