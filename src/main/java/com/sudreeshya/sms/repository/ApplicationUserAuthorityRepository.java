package com.sudreeshya.sms.repository;

import com.sudreeshya.sms.model.ApplicationUserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Repository
public interface ApplicationUserAuthorityRepository extends JpaRepository<ApplicationUserAuthority, Long> {

  ApplicationUserAuthority findApplicationUserAuthoritiesByApplicationUser_Id(Long id);
}
