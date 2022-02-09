package com.ivblanc.api.dto;


import com.ivblanc.api.dto.req.MakeStyleDetailReqDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StyleConverter implements Converter<String,List<MakeStyleDetailReqDTO>> {

    private ObjectMapper objectMapper;
    public StyleConverter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public List<MakeStyleDetailReqDTO> convert(String value){
        return objectMapper.readValue(value,new TypeReference<>(){});
    }
}
