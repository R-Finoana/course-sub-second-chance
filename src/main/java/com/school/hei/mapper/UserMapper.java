package com.school.hei.mapper;

import com.school.hei.model.User;
import com.school.hei.repository.model.JUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
  public User toModel(JUser entity) {
    return User.builder()
        .id(entity.getId())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .username(entity.getUsername())
        .email(entity.getEmail())
        .build();
  }

  public JUser toEntity(User model) {
    return JUser.builder()
        .id(model.id())
        .firstName(model.firstName())
        .lastName(model.lastName())
        .username(model.username())
        .email(model.email())
        .build();
  }
}
