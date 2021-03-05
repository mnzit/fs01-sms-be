package com.sudreeshya.sms.repository;

import com.sudreeshya.sms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
