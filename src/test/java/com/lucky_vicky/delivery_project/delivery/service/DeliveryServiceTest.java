package com.lucky_vicky.delivery_project.delivery.service;

import com.lucky_vicky.delivery_project.delivery.domain.Delivery;
import com.lucky_vicky.delivery_project.delivery.repository.DeliveryRepository;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@Transactional
@DataJpaTest
class DeliveryServiceTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    @Test
    void test() {
        UUID uuid = UUID.randomUUID();
        User user1 = userRepository.findById(uuid).get();
        User user = userRepository.findByUsername("~~~").get();
        Delivery byUser = deliveryRepository.findByUser(user);
        Delivery byUser1 = deliveryRepository.findByUser(user1);

    }
}