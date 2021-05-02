package com.sudreeshya.sms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "TBL_APPLICATION_USER_AUTHORITY")
public class ApplicationUserAuthority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 10)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "APPLICATION_USER_ID",
            referencedColumnName = "id",
            updatable = true,
            insertable = true,
            nullable = false)
    private ApplicationUser applicationUser;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHORITY_ID",
            referencedColumnName = "id",
            updatable = true,
            insertable = true,
            nullable = false)
    private Authority authority;

    public ApplicationUserAuthority(Long id){ }
}
