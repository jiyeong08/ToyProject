package com.toy.dto;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userCode;

    private String userId;

    private String password;

    private String nickname;

    private String userName;

    private String phoneNum;

    private String email;

    private Date joinDate;

}
