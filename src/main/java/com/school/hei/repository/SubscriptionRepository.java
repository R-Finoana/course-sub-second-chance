package com.school.hei.repository;

import com.school.hei.repository.model.JSubscription;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<JSubscription, UUID> {}
