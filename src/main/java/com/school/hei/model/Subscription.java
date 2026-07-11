package com.school.hei.model;

import java.time.Instant;
import java.util.UUID;

public record Subscription(
    UUID id, Instant createdAt, SubscriptionStatus status, UUID courseId, UUID userId) {}
