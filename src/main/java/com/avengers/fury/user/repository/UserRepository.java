package com.avengers.fury.user.repository;

import com.avengers.fury.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByEmailIgnoreCaseOrNameIgnoreCase(String email, String name);

    Optional<User> findByEmailIgnoreCase(String email);
}
