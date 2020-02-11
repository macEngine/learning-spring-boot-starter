package com.niuge.learning.bootstarterjpa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
@SpringBootTest
public class SpringBootJPATest {

  @Autowired
  private GenericEntityRepository genericEntityRepository;

  @Test
  public void givenGenericEntityRepository_whenSaveAndRetrieveEntity_thenOK() {
    GenericEntity genericEntity =
        genericEntityRepository.save(new GenericEntity("test"));
    GenericEntity foundedEntity =
        genericEntityRepository.findById(genericEntity.getId()).orElse(null);

    assertNotNull(foundedEntity);
    assertEquals(genericEntity.getValue(), foundedEntity.getValue());
    System.out.println("=========test");
  }
}

