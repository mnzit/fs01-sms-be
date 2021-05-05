package com.sudreeshya.sms.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO implements Serializable {
    private int count;
    private List<Report> reports;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Report implements Serializable {
        private Date date;
    }

}
