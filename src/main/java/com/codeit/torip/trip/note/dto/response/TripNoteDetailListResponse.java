package com.codeit.torip.trip.note.dto.response;

import com.codeit.torip.task.note.dto.NoteDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripNoteDetailListResponse {

    @Schema(description = "여행 제목", example = "여행 제목")
    private String tripTitle;
    @Schema(description = "노트 상세")
    private List<NoteDetailDto> noteDetails;

}
