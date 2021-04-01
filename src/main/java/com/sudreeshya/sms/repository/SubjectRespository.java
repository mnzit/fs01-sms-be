package com.sudreeshya.sms.repository;

import com.sudreeshya.sms.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRespository extends JpaRepository<Subject, Long> {
}
