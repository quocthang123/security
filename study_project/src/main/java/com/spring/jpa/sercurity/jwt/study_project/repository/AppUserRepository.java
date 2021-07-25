package com.spring.jpa.sercurity.jwt.study_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.sercurity.jwt.study_project.entity.AppUser;

@Transactional
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
//    List<AppUser> findByUserName(String username);
//
//    @Query("select appUser from AppUser appUser where appUser.username = ?1 and appUser.password = ?2")
//    AppUser findUserByQuery(String username, String password);

    Optional<AppUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
