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
@Table(name = "TBL_APPLICATION_USER")
public class ApplicationUser extends Auditable<ApplicationUser> {

    @Column(name = "FIRST_NAME",
            length = 50,
            updatable = true,
            insertable = true,
            nullable = false
    )
    private String firstName;
    @Column(name = "LAST_NAME",
            length = 50,
            updatable = true,
            insertable = true,
            nullable = false
    )
    private String lastName;
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

    @Column(name = "isActive",
            columnDefinition = "CHAR default 'Y'",
            length = 1,
            nullable = false)
    private Character isActive;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "tbl_application_user_authority",
            joinColumns = {
                    @JoinColumn(name = "application_user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authority_id", referencedColumnName = "id")
            }
    )
    private List<Authority> authorities;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "COURSE_ID", referencedColumnName = "id")
    private Course course;


    public ApplicationUser(Long id) {
        super(id);
    }
}
