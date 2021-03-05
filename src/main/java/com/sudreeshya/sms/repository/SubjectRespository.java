package com.sudreeshya.sms.repository;

import com.sudreeshya.sms.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRespository extends JpaRepository<Subject, Long> {
}
