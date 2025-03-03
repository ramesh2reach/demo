package com.imps.repository;

import com.imps.entity.User;
import com.imps.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@Repository
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(Role.valueOf("USER"));
        entityManager.persistAndFlush(user);

        // When
        Optional<User> found = userRepository.findByUsername(user.getUsername());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenFindByInvalidUsername_thenReturnEmpty() {
        // When
        Optional<User> found = userRepository.findByUsername("nonexistent");

        // Then
        assertThat(found).isNotPresent();
    }

    @Test
    public void whenExistsByUsername_thenReturnTrue() {
        // Given
        User user = new User();
        user.setUsername("existinguser");
        user.setPassword("password");
        user.setRole(Role.valueOf("USER"));
        entityManager.persistAndFlush(user);

        // When
        boolean exists = userRepository.existsByUsername(user.getUsername());

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    public void whenExistsByInvalidUsername_thenReturnFalse() {
        // When
        boolean exists = userRepository.existsByUsername("nonexistent");

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    public void whenSaveUser_thenUserIsPersisted() {
        // Given
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password");
        user.setRole(Role.valueOf("USER"));

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(entityManager.find(User.class, savedUser.getId())).isEqualTo(savedUser);
    }

    @Test
    public void whenDeleteUser_thenUserIsRemoved() {
        // Given
        User user = new User();
        user.setUsername("tobedeleted");
        user.setPassword("password");
        user.setRole(Role.valueOf("USER"));
        entityManager.persistAndFlush(user);

        // When
        userRepository.delete(user);

        // Then
        assertThat(entityManager.find(User.class, user.getId())).isNull();
    }
}