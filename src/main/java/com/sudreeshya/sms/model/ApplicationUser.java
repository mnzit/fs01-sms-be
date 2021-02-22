package com.sudreeshya.sms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "TBL_APPLICATION_USER")
public class ApplicationUser extends Auditable<ApplicationUser>{

    @Column(name = "FIRST_NAME",
            length = 50,
            updatable = true,
            insertable = true,
            nullable = false
    )
    private String firstname;
    @Column(name = "LAST_NAME",
            length = 50,
            updatable = true,
            insertable = true,
            nullable = false
    )
    private String lastname;
    @Column(name = "ADDRESS",
            length = 100,
            updatable = true,
            insertable = true,
            nullable = true
    )
    private String address;
    @Column(name = "CONTACT_NO",
            length = 20,
            updatable = true,
            insertable = true,
            nullable = true
    )
    private String contactNo;
    @Column(name = "EMAIL_ADDRESS",
            length = 50,
            updatable = true,
            insertable = true,
            nullable = false
    )
    private String emailAddress;
    @Column(name = "PASSWORD",
            length = 100,
            updatable = true,
            insertable = true,
            nullable = false
    )
    private String password;

    public ApplicationUser(Long id){
        super(id);
    }
}
