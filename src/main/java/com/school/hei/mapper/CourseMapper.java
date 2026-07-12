package com.school.hei.mapper;

import com.school.hei.model.Course;
import com.school.hei.repository.model.JCourse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseMapper {
  public Course toModel(JCourse entity) {
    return Course.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .start(entity.getStart())
        .end(entity.getEnd())
        .build();
  }

  public JCourse toEntity(Course model) {
    return JCourse.builder()
        .id(model.id())
        .title(model.title())
        .start(model.start())
        .end(model.end())
        .build();
  }
}
