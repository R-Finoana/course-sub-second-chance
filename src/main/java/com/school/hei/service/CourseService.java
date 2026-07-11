package com.school.hei.service;

import com.school.hei.mapper.CourseMapper;
import com.school.hei.model.Course;
import com.school.hei.repository.CourseRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {
  private CourseMapper mapper;
  private CourseRepository repository;

  public Course getById(UUID id) {
    return mapper.toModel(
        repository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Course with id " + id + " is not found")));
  }
}
