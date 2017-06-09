package com.course.mvc.repository;

import com.course.mvc.domain.Role;
import com.course.mvc.domain.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by IT-UNIVER3 on 03.06.2017.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.role = :roleEnum")
    Role findRoleByRoleName(@Param("roleEnum") RoleEnum roleEnum);
}
