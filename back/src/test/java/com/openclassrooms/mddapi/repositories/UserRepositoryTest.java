package com.openclassrooms.mddapi.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testDataInitialization() {
        long userCount = userRepository.count();
        assertEquals(3, userCount, "The number of users should be 3");
    }

    @Test
    @Transactional
    public void testDuplicateEmail() {
        User user1 = new User();
        user1.setEmail("test@example.com");
        user1.setUserName("user1");
        user1.setPassword("password1");
        userRepository.save(user1);
        entityManager.flush(); // Force flush to check constraints

        User user2 = new User();
        user2.setEmail("test@example.com"); // Duplicate email
        user2.setUserName("user2");
        user2.setPassword("password2");

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user2);
            entityManager.flush(); // Force flush to check constraints

        });
    }

    @Test
    public void testDuplicateUserName() {
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setUserName("duplicateUserName");
        user1.setPassword("password1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setUserName("duplicateUserName"); // Duplicate user_name
        user2.setPassword("password2");

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user2);
        });
    }
}