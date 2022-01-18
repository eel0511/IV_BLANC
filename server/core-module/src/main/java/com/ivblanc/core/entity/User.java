package com.ivblanc.core.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivblanc.core.code.JoinCode;
import com.ivblanc.core.code.MFCode;
import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.converter.JoinCodeConverter;
import com.ivblanc.core.converter.MFCodeConverter;
import com.ivblanc.core.converter.YNCodeConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user",
    uniqueConstraints = {
            @UniqueConstraint(
                    columnNames={"user_id"}
            )
    }
)
// 회원 테이블
public class User extends BaseEntity {

    // User 테이블의 키값 = 회원의 고유 키값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    // 회원 이메일
    @Column(nullable = false, length = 255)
    private String email;

    // 비밀번호
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 15)
    private String password;

    // 회원 이름
    @Column(nullable = false, length = 20)
    private String name;

    // 회원 나이
    @Column(nullable = true, length = 3, columnDefinition = "int(3) default 0")
    private int age;

    // 성별 (남자 0, 여자 1)
    @Column(nullable = true, length = 1, columnDefinition = "int(1) default 0")
    private int gender;

    // 회원 휴대폰
    @Column(nullable = true, length = 15)
    private String phone;

    // 회원이 가입한 타입 (0:일반, 1:카카오, 2:구글, 3:네이버)
    @Column(nullable = true, length = 5)
    private int social;



    public void updateAge(int age){
        this.age = age;
    }

    public void updateGender(int gender){
        this.gender = gender;
    }

    public void updatePhone(String phone){
        this.phone = phone;
    }
}
