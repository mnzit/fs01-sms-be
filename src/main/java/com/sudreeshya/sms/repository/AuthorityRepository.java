package com.sudreeshya.sms.repository;

import com.sudreeshya.sms.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Modifying
    @Query(value = "INSERT INTO tbl_application_user_authority (application_user_id, authority_id) VALUES(:userId, :authorityId)", nativeQuery = true)
    void saveAuthority(Long userId, Long authorityId);



}
