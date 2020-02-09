package com.niuge.learningspringbootstarter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SpringBootMailTest {
  @Autowired
  private JavaMailSender javaMailSender;

  private Wiser wiser;

  private String userTo = "tom@163.com";
  private String userFrom = "pony@qq.com";
  private String subject = "周报";
  private String textMail = "本周休息";

  @BeforeEach
  public void setUp() {
    final int TEST_PORT = 10086;
    wiser = new Wiser(TEST_PORT);
    wiser.start();
  }

  @AfterEach
  public void tearDown() {
    wiser.stop();
  }

  @Test
  public void givenMail_whenSendAndReceived_thenCorrect() throws Exception {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(userTo);
    mailMessage.setReplyTo(userFrom);
    mailMessage.setFrom(userFrom);
    mailMessage.setSubject(subject);
    mailMessage.setText(textMail);

    javaMailSender.send(mailMessage);
    List<WiserMessage> messages = wiser.getMessages();

    assertThat(messages, hasSize(1));
    WiserMessage wiserMessage = messages.get(0);
    assertEquals(userFrom, wiserMessage.getEnvelopeSender());
    assertEquals(userTo, wiserMessage.getEnvelopeReceiver());
    assertEquals(subject, getSubject(wiserMessage));
    assertEquals(textMail, getMessage(wiserMessage));
    System.out.println(textMail);
  }

  private String getMessage(WiserMessage wiserMessage)
      throws MessagingException, IOException {
    return wiserMessage.getMimeMessage().getContent().toString().trim();
  }

  private String getSubject(WiserMessage wiserMessage) throws MessagingException {
    return wiserMessage.getMimeMessage().getSubject();
  }
}
