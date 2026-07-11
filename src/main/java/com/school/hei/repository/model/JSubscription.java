package com.school.hei.repository.model;

import static jakarta.persistence.EnumType.STRING;

import com.school.hei.model.SubscriptionStatus;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "subscription")
public class JSubscription {
  @Id @GeneratedValue private UUID id;

  @CreationTimestamp private Instant createdAt;

  @Enumerated(STRING)
  private SubscriptionStatus status;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private JCourse course;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private JUser user;
}
