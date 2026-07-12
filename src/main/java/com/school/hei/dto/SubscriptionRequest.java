package com.school.hei.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record SubscriptionRequest(UUID userId) {}
