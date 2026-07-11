package com.school.hei.model;

import java.util.UUID;
import lombok.Builder;

@Builder
public record User(UUID id, String firstName, String lastName, String username, String email) {}
