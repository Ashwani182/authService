package com.expense.tracker.auth.repository;

import com.expense.tracker.auth.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);
    //in Spring Jpa return type for find is table name
    // CrudRepository<RefreshToken, Integer> repository is RefreshToken




}
