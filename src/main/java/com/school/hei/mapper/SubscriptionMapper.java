package com.school.hei.mapper;

import com.school.hei.dto.SubscriptionRequest;
import com.school.hei.model.Subscription;
import com.school.hei.model.SubscriptionStatus;
import com.school.hei.repository.model.JSubscription;
import com.school.hei.service.CourseService;
import com.school.hei.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class SubscriptionMapper {
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;
    private final UserService userService;
    private final CourseService courseService;

    public List<Subscription> toModel(List<JSubscription> entities) {
        return entities.stream().map(this::toModel).toList();
    }

    public Subscription toModel(JSubscription entity) {
        return Subscription.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .status(entity.getStatus())
                .courseId(entity.getCourse().getId())
                .userId(entity.getUser().getId())
                .build();
    }

    public List<JSubscription> toEntity(List<Subscription> models) {
        return models.stream().map(this::toEntity).toList();
    }

    public JSubscription toEntity(Subscription model) {
        var user = userService.getById(model.userId());
        var course = courseService.getById(model.courseId());
        return JSubscription.builder()
                .id(model.id())
                .createdAt(model.createdAt())
                .status(model.status())
                .course(courseMapper.toEntity(course))
                .user(userMapper.toEntity(user))
                .build();
    }

    public JSubscription toEntity(UUID courseId, SubscriptionRequest subscriptionRequest) {
        var user = userService.getById(subscriptionRequest.userId());
        var course = courseService.getById(courseId);
        return JSubscription.builder()
                .id(UUID.randomUUID())
                .createdAt(Instant.now())
                .status(SubscriptionStatus.ACTIVE)
                .course(courseMapper.toEntity(course))
                .user(userMapper.toEntity(user))
                .build();
    }
}
