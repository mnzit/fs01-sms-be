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
@Table(name = "TBL_AUTHORITY")
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 10)
    private Long id;

    @Column(name = "NAME",
            length = 100,
            updatable = true,
            insertable = true,
            nullable = false
    )
    private String name;


    public Authority(Long id){ }
}
