package com.kabianga.tp.poster.repository;

import com.kabianga.tp.poster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
