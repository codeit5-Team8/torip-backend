package com.codeit.torip.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.codeit.torip.common.contant.ToripConstants.HttpConstant.SUCCESS_CODE;
import static com.codeit.torip.common.contant.ToripConstants.HttpConstant.SUCCESS_MESSAGE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private Boolean success;
    private Integer code;
    private String message;
    private Object result;

    public static ResponseDto success(Object result) {
        return ResponseDto.builder()
                .success(true)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .result(result)
                .build();
    }

}
