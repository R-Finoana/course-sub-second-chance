package com.school.hei.service;

import com.school.hei.dto.SubscriptionRequest;
import com.school.hei.endpoint.event.EventProducer;
import com.school.hei.endpoint.event.model.SubscriptionCreated;
import com.school.hei.exception.AlreadySubscribedException;
import com.school.hei.file.bucket.BucketComponent;
import com.school.hei.mapper.SubscriptionMapper;
import com.school.hei.model.Subscription;
import com.school.hei.repository.CourseRepository;
import com.school.hei.repository.SubscriptionRepository;
import com.school.hei.repository.UserRepository;
import com.school.hei.repository.model.JCourse;
import com.school.hei.repository.model.JUser;
import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository repository;
  private final SubscriptionMapper mapper;
  private final EventProducer<SubscriptionCreated> eventProducer;
  private final BucketComponent bucketComponent;
  private final PdfGenerationService pdfGenerationService;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

  public Subscription createSubscription(UUID id, SubscriptionRequest request) {
    if (repository.existsByUserIdAndCourseId(request.userId(), id)) {
      throw new AlreadySubscribedException("You are already subscribed to this course");
    }
    var asEntity = mapper.toEntity(id, request);
    var saved = mapper.toModel(repository.save(asEntity));
    eventProducer.accept(List.of(new SubscriptionCreated(saved)));
    return saved;
  }

  @SneakyThrows
  public String uploadSubscriptionPdf(UUID userId, UUID courseId) {
    JUser user =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    JCourse course =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

    File pdfFile =
        pdfGenerationService.generateSubscriptionPdf(
            user.getFirstName() + " " + user.getLastName(), course.getTitle());

    var bucketKey = "course-sub" + ".pdf";
    bucketComponent.upload(pdfFile, bucketKey);

    pdfFile.delete();

    return bucketComponent.presign(bucketKey, Duration.ofMinutes(10)).toString();
  }
}
