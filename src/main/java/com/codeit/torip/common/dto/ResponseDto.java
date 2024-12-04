package com.codeit.torip.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "성공 여부")
    private Boolean success;
    @Schema(description = "결과 코드")
    private Integer code;
    @Schema(description = "결과 메세지")
    private String message;
    @Schema(description = "결과 데이터")
    private Object result;

    public static ResponseDto success(Object result) {
        return ResponseDto.builder()
                .success(true)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .result(result)
                .build();
    }

    public static ResponseDto fail(Integer code, String message) {
        return ResponseDto.builder()
                .success(false)
                .code(code)
                .message(message)
                .build();
    }

}
