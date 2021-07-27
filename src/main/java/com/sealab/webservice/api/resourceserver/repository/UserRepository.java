package com.sealab.webservice.api.resourceserver.repository;

import com.sealab.webservice.api.resourceserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);


    User findByUserId(Long userId);

    User findByUsername(String username);

}