package com.ivblanc.core.converter;

import javax.persistence.Converter;

import com.ivblanc.core.code.JoinCode;


@Converter(autoApply = true)
// 가입 타입 코드값의 Converter
public class JoinCodeConverter extends AbstractBaseEnumConverter<JoinCode, String> {

    @Override
    protected JoinCode[] getValueList() {
        return JoinCode.values();
    }
}
