package com.codeit.torip.trip.note.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripNoteRegRequest {

    @NotNull(message = "여행 고유키는 필수 값입니다.")
    @Schema(description = "여행 고유키", example = "1")
    private long id;
    @NotBlank(message = "여행 노트 제목은 비어있을 수 없습니다.")
    @Size(max = 30, message = "여행 노트 제목은 30자를 초과하실 수 없습니다")
    @Schema(description = "여행 노트 제목", example = "여행 노트 제목")
    private String noteTitle;
    @NotBlank(message = "여행 노트 내용은 비어있을 수 없습니다.")
    @Schema(description = "여행 노트 내용", example = "여행 노트 내용")
    private String noteContent;

}
