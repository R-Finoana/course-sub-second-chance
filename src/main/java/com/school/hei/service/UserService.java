package com.school.hei.service;

import com.school.hei.mapper.UserMapper;
import com.school.hei.model.User;
import com.school.hei.repository.UserRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserMapper mapper;
  private final UserRepository repository;

  public User getById(UUID id) {
    return mapper.toModel(
        repository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("User with id " + id + " is not found")));
  }
}
