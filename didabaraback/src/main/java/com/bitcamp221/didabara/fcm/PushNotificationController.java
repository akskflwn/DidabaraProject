package com.bitcamp221.didabara.fcm;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class PushNotificationController {


  private final PushNotificationService pushNotificationService;
  private final FCMService fcmService;

  @PostMapping("/token")
  public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) throws ExecutionException, InterruptedException {
    pushNotificationService.sendPushNotificationToToken(request);
//        fcmService.send(request);
    System.out.println("princr");
    return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
  }


  @GetMapping("/notification")
  public ResponseEntity<?> test() throws FirebaseMessagingException {
    String topic = "highScores";
    List<String> registrationTokens = Arrays.asList(
            "cvLSo8_E8Fjb9RKxI5lb_y:APA91bFjRraQgkf61xsdgzAKuqWSJwxNpM1P528W4ODLIxOH5ILl0PDuyswn3G2RNDluN792wWVj4DlPEAx1Ez3vcm3Zmg_h3MVHmZRgpwCWsJXqymZKiWlh5S8Dq2AVJMvoCE35JbND"
    );

    TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
            registrationTokens, topic);

    System.out.println(response + " tokens were subscribed successfully");
    return ResponseEntity.ok().body("test");
  }

  @GetMapping("/webpush")
  public ResponseEntity<?> test2() throws FirebaseMessagingException, ExecutionException, InterruptedException {
    PushNotificationRequest pr = new PushNotificationRequest();
    pr.setTopic("highScores");
    pr.setTitle("title1");
    pr.setMessage("message1");

    fcmService.send(pr);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String jsonOutput = gson.toJson(message);
//        System.out.println("jsonOutput = " + jsonOutput);
    return ResponseEntity.ok().body("test2");
  }

  @GetMapping("/condition")
  public ResponseEntity<?> test3() throws FirebaseMessagingException {

    Message message = Message.builder()
            .setApnsConfig(ApnsConfig.builder()
                    .putHeader("apns-priority", "10")
                    .setAps(Aps.builder()
                            .setAlert(ApsAlert.builder()
                                    .setTitle("$GOOG up 1.43% on the day")
                                    .setBody("$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.")
                                    .build())
                            .setBadge(42)
                            .build())
                    .build())
            .setTopic("high")
            .build();

    String send = FirebaseMessaging.getInstance().send(message);
    System.out.println("send = " + send);

    return ResponseEntity.ok().body("");
  }
}
