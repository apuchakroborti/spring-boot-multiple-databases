package com.apu.multiple.database.api.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apu.multiple.database.api.mysql.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
