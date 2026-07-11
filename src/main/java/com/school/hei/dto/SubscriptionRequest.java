package com.school.hei.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SubscriptionRequest(UUID userId) {
}
