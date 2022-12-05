package com.apu.multiple.database.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apu.multiple.database.api.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
