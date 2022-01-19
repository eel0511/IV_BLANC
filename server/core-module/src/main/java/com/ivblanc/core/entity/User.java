package com.ivblanc.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames={"uid"}
        )
    }
)
// 회원 테이블
public class User implements UserDetails {

    // User 테이블의 키값 = 회원의 고유 키값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 회원이 가입한 타입 (none:일반, sns:소셜회원가입)
    // @Convert(converter = JoinCodeConverter.class)
    @Column(nullable = false, length = 5)
    private int social;

    // 회원아이디(=이메일)
    @Column(nullable = false, unique = true, length = 120)
    private String uid;

    // 비밀번호
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 255)
    private String password;

    // 회원 이름
    @Column(nullable = false, length = 32)
    private String name;

    // 회원 휴대폰
    @Column(nullable = true, length = 32)
    private String phone;

    // 성별 (남자 M, 여자 F)
    @Column(nullable = false, length = 1, columnDefinition = "varchar(1) default ''")
    private int gender;

    // 회원 나이
    @Column(nullable = false, length = 3, columnDefinition = "int(3) default 0")
    private int age;

    // 장비 푸시용 토큰
    @Column(length = 255)
    private String token_jwt;

    @Column(length = 255)
    private String token_fcm;

    @Column(length = 255)
    private String code;





    // =================================================================================================
    // JWT
    // =================================================================================================
    @Column(length = 100)
    private String provider;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // =================================================================================================



    public void updateAge(int age){
        this.age = age;
    }

    public void updateGender(int gender){
        this.gender = gender;
    }


}
