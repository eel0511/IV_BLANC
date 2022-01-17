package com.ivblanc.core.converter;

import javax.persistence.Converter;

import com.ivblanc.core.code.YNCode;


@Converter(autoApply = true)
// YN 코드값의 Converter
public class YNCodeConverter extends AbstractBaseEnumConverter<YNCode, String> {

    @Override
    protected YNCode[] getValueList() {
        return YNCode.values();
    }
}
