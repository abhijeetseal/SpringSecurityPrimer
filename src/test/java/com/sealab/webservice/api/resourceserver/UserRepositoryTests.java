package com.sealab.webservice.api.resourceserver;

import static org.assertj.core.api.Assertions.assertThat;

import com.sealab.webservice.api.resourceserver.entity.User;
import com.sealab.webservice.api.resourceserver.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    // test methods go below

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("abhijeet@gmail.com");
        user.setPassword("sddf");
        user.setFirstName("abhijeet");
        user.setLastName("seal");
        user.setUsername("seal");
        user.setActive(true);
        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getUserId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }
}