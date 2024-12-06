package com.dlrs.dlrsdemo.repository;
import com.dlrs.dlrsdemo.common.UserRole;
import com.dlrs.dlrsdemo.model.AppUser;
import com.dlrs.dlrsdemo.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);

    AppUser findByPhone(String email);

    List<AppUser> findAllByRole(UserRole role);

}
