package com.spring.jpa.sercurity.jwt.study_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.sercurity.jwt.study_project.entity.AppRole;
import com.spring.jpa.sercurity.jwt.study_project.entity.EAppRole;

@Repository
@Transactional
public interface AppRoleRepository extends JpaRepository<AppRole, Integer>{

//    @Query("select appRole.roleName from AppRole appRole where appRole.delete = 0")
//    List<String> findAllRoleName();
//
//    @Query("select appRole from AppRole appRole where appRole.delete = 0")
//    List<AppRole> findAllRole();
    
    Optional<AppRole> findByRoleName(EAppRole roleName);
}
