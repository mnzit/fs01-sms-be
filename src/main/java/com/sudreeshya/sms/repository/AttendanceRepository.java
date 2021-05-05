package com.sudreeshya.sms.repository;

import com.sudreeshya.sms.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query(value = "SELECT * FROM TBL_ATTENDANCE WHERE APPLICATION_USER_ID=:id", nativeQuery = true)
    List<Attendance> findAllByApplicationUserId(Long id);

    @Query(value = "SELECT * FROM TBL_ATTENDANCE WHERE COURSE_ID=:id", nativeQuery = true)
    List<Attendance> finAllByCourseId(Long id);

    @Query(value = " SELECT * FROM TBL_ATTENDANCE WHERE DATE(CREATED_DATE) = :date AND APPLICATION_USER_ID=:userId AND COURSE_ID=:courseId", nativeQuery = true)
    Optional<Attendance> findByCreatedDate(String date, Long userId, Long courseId);
}
