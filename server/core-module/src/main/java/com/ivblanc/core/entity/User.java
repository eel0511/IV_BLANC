package com.ivblanc.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Setter
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
public class User implements UserDetails {

    // User 테이블의 키값 = 회원의 고유 키값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    // 회원이 가입한 타입 (1:일반회원, 2:카카오, 3:구글, 4:네이버)
    @Column(nullable = false, length = 1, columnDefinition = "int(1) default 0")
    private int social;

    // 회원아이디(=이메일)
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    // 비밀번호
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 255)
    private String password;

    // 회원 이름
    @Column(nullable = false, length = 32)
    private String name;

    // 회원 휴대폰
    @Column(nullable = false, length = 32)
    private String phone;

    // 성별 (남자 1, 여자 2)
    @Column(nullable = false, length = 1, columnDefinition = "int(1) default 0")
    private int gender;

    // 회원 나이
    @Column(nullable = false, length = 3, columnDefinition = "int(3) default 0")
    private int age;

    // 장비 푸시용 토큰
    @Column(length = 255)
    private String token_jwt;

    @Column(length = 255)
    private String token_fcm;


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
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // =================================================================================================


    public void updatePassword(String password){
        this.password = password;
    }

    public void updateTokenJWT(String token_jwt){
        this.token_jwt = token_jwt;
    }

    public void updateTokenFCM(String token_fcm){
        this.token_fcm = token_fcm;
    }

}
