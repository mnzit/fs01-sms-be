package com.sudreeshya.sms.repository;

import com.sudreeshya.sms.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByEmailAddress(String emailAddress);
}
