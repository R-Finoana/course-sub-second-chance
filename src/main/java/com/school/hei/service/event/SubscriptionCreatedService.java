package com.school.hei.service.event;

import com.school.hei.endpoint.event.model.SubscriptionCreated;
import com.school.hei.mail.Email;
import com.school.hei.mail.Mailer;
import com.school.hei.service.CourseService;
import com.school.hei.service.UserService;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionCreatedService implements Consumer<SubscriptionCreated> {
  private final Mailer mailer;
  private final UserService userService;
  private final CourseService courseService;

  @SneakyThrows
  @Override
  public void accept(SubscriptionCreated subscriptionCreated) {
    var subscription = subscriptionCreated.getSubscription();
    var user = userService.getById(subscription.userId());
    var course = courseService.getById(subscription.courseId());

    InternetAddress recipientAddress = new InternetAddress(user.email());
    mailer.accept(
        new Email(
            recipientAddress,
            List.of(),
            List.of(),
            "Subscription confirmation",
            "You are subscribed to the course: " + course.title(),
            List.of()));
  }
}
