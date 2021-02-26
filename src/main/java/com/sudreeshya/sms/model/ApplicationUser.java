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
            nullable = false
    )
    private String firstName;
    @Column(name = "LAST_NAME",
            length = 50,
            nullable = false
    )
    private String lastName;
    @Column(name = "ADDRESS",
            length = 100
    )
    private String address;
    @Column(name = "CONTACT_NO",
            length = 20
    )
    private String contactNo;
    @Column(name = "EMAIL_ADDRESS",
            length = 50,
            nullable = false
    )
    private String emailAddress;
    @Column(name = "PASSWORD",
            length = 100,
            nullable = false
    )
    private String password;

    @Column(name = "isActive",
            columnDefinition = "CHAR default 'Y'",
            length = 1,
            nullable = false)
    private Character isActive;

    public ApplicationUser(Long id){
        super(id);
    }
}
