package com.school.hei.service;

import com.school.hei.dto.SubscriptionRequest;
import com.school.hei.endpoint.event.EventProducer;
import com.school.hei.endpoint.event.model.SubscriptionCreated;
import com.school.hei.mapper.SubscriptionMapper;
import com.school.hei.model.Subscription;
import com.school.hei.repository.SubscriptionRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository repository;
  private final SubscriptionMapper mapper;
  private final EventProducer<SubscriptionCreated> eventProducer;

  public Subscription createSubscription(UUID id, SubscriptionRequest request) {
    var asEntity = mapper.toEntity(id, request);
    var saved = mapper.toModel(repository.save(asEntity));
    eventProducer.accept(List.of(new SubscriptionCreated(saved)));
    return saved;
  }
}
