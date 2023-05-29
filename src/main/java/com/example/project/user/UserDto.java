package com.example.project.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class UserDto {

    private Integer id;

    @Size(min = 5, max = 20, message = "아이디는 5~20 자리로 입력해주세요.")
    private String userId;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 영문과 특수문자, 숫자를 포함하며 8자 이상이어야 합니다.")
    private String password;

    private String userName;
    @Enumerated(EnumType.STRING)

    private String eMail;

}
