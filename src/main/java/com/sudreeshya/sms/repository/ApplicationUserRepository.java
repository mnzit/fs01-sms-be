package com.sudreeshya.sms.repository;

import com.sudreeshya.sms.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
}
