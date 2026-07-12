package com.school.hei.endpoint.rest.controller.health;

import com.school.hei.dto.SubscriptionRequest;
import com.school.hei.model.Subscription;
import com.school.hei.service.SubscriptionService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  @PostMapping("/courses/{courseId}/subscriptions")
  public Subscription subscribe(
      @PathVariable UUID courseId, @RequestBody SubscriptionRequest request) {
    return subscriptionService.createSubscription(courseId, request);
  }
}
