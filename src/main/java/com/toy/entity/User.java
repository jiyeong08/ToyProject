package com.toy.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "user_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private Long userCode;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "email")
    private String email;

    @Column(name = "join_date")
    private Date joinDate;

    @Builder
    public User(Long userCode, String userId, String password, String nickname, String userName, String phoneNum,
                String email, Date joinDate) {
        this.userCode = userCode;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.joinDate = joinDate;
    }

}
