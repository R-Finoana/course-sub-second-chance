package com.school.hei.repository;

import com.school.hei.repository.model.JUser;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<JUser, UUID> {}
