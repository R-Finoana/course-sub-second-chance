package com.school.hei.service.event;

import com.school.hei.endpoint.event.model.SubscriptionCreated;
import com.school.hei.mail.Email;
import com.school.hei.mail.Mailer;
import com.school.hei.service.CourseService;
import com.school.hei.service.SubscriptionService;
import com.school.hei.service.UserService;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.UUID;
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
  private final SubscriptionService subscriptionService;

  @SneakyThrows
  @Override
  public void accept(SubscriptionCreated subscriptionCreated) {
    sendConfirmationEmailToUser(
        subscriptionCreated.getSubscription().userId(),
        subscriptionCreated.getSubscription().courseId());
  }

  private void sendConfirmationEmailToUser(UUID userId, UUID courseId) throws AddressException {
    var user = userService.getById(userId);
    var course = courseService.getById(courseId);
    var to = user.email();
    var subject = "Subscription confirmation: %s".formatted(course.title());

    String pdfUrl = subscriptionService.uploadSubscriptionPdf(userId, courseId);

    var htmlBody =
        """
<html>
  <body>
    <p>Dear %s,</p>
    <p>Your subscription has been confirmed. You now have full access to your course.</p>
    <p>Please find your subscription certificate below:</p>
       <p><a href="%s" style="background-color: #3498db; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Download your certificate (PDF)</a></p>
       <p><small>This link is valid for 10 minutes.</small></p>
    <p>Thank you for joining us!</p>
    <p>Best regards,</p>
    <p>The Team</p>
  </body>
</html>
"""
            .formatted(user.username(), pdfUrl);
    var email =
        new Email(new InternetAddress(to), List.of(), List.of(), subject, htmlBody, List.of());
    mailer.accept(email);
  }
}
