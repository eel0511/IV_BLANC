package com.ivblanc.core.converter;

import javax.persistence.Converter;

import com.ivblanc.core.code.MFCode;


@Converter(autoApply = true)
// 성별 코드값의 Converter
public class MFCodeConverter extends AbstractBaseEnumConverter<MFCode, String> {

    @Override
    protected MFCode[] getValueList() {
        return MFCode.values();
    }
}
