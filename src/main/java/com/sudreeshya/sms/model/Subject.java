package com.sudreeshya.sms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Entity
@Table(name = "TBL_SUBJECTS")
public class Subject extends Auditable<ApplicationUser>{

    @Column(name = "NAME",
            length = 50,
            updatable = true,
            insertable = true,
            nullable = false
    )
    private String name;
    @Column(name = "DESCRIPTION",
            length = 500,
            updatable = true,
            insertable = true,
            nullable = false
    )
    private String description;
    @Column(name = "CODE",
            length = 100,
            updatable = true,
            insertable = true,
            nullable = true
    )
    private String code;
}
