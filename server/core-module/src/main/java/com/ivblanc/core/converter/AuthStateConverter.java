package com.ivblanc.core.converter;

import javax.persistence.Converter;

import com.ivblanc.core.code.AuthState;


@Converter(autoApply = true)
// 인증 상태 코드값의 Converter
public class AuthStateConverter extends AbstractBaseEnumConverter<AuthState, String> {

    @Override
    protected AuthState[] getValueList() {
        return AuthState.values();
    }
}
