package com.sudreeshya.sms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "TBL_ATTENDANCE")
public class Attendance extends Auditable<Attendance> {

    @JoinColumn(name = "APPLICATION_USER_ID", referencedColumnName = "id")
    private ApplicationUser applicationUser;

    @JoinColumn(name = "COURSE_ID", referencedColumnName = "id")
    private Course course;

    public Attendance(Long id) {
        super(id);
    }
}
