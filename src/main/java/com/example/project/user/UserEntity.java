package com.example.project.user;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity{
    @Id
    private String userId;

    private String password;

    private String userName;

    private String eMail;
    public static UserEntity createUserEntity(UserDto userDto){
        return new UserEntity(
                userDto.getUserId(),
                userDto.getPassword(),
                userDto.getUserName(),
                userDto.getEMail()
        );
    }

}