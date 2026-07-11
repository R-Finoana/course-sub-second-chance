package com.school.hei.model;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record Subscription(
    UUID id, Instant createdAt, SubscriptionStatus status, UUID courseId, UUID userId) {}
