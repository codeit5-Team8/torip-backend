package com.codeit.torip.trip.note.service;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.common.exception.AlertException;
import com.codeit.torip.task.note.repository.TaskNoteRepository;
import com.codeit.torip.trip.entity.Trip;
import com.codeit.torip.trip.note.dto.request.TripNoteListRequest;
import com.codeit.torip.trip.note.dto.request.TripNoteModRequest;
import com.codeit.torip.trip.note.dto.request.TripNoteRegRequest;
import com.codeit.torip.trip.note.dto.response.TripNoteDetailListResponse;
import com.codeit.torip.trip.note.dto.response.TripNoteDetailResponse;
import com.codeit.torip.trip.note.entity.TripNote;
import com.codeit.torip.trip.note.repository.TripNoteRepository;
import com.codeit.torip.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.codeit.torip.common.contant.ToripConstants.Note.PAGE_SIZE;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripNoteService {

    private final TripNoteRepository tripNoteRepository;
    private final TaskNoteRepository taskNoteRepository;
    private final TripRepository tripRepository;

    public TripNoteDetailListResponse getNoteList(TripNoteListRequest tripNoteListRequest) {
        var tripEntity = checkTripMember(tripNoteListRequest.getTripId());
        var tripNoteList = tripNoteRepository.selectTripNoteDetailList(tripNoteListRequest);
        var taskNoteList = taskNoteRepository.selectTaskNoteDetailListFromTripId(tripNoteListRequest);
        // 전체 5개에서 각 리스트의 비율 계산
        int tripCount = 0;
        int taskCount = 0;
        for (int i = 0; i < PAGE_SIZE; i++) {
            if (tripNoteList.size() > tripCount) {
                var tripNote = tripNoteList.get(tripCount);
                if (taskNoteList.size() > taskCount) {
                    var taskNote = taskNoteList.get(taskCount);
                    if (tripNote.getCreatedAt().isAfter(taskNote.getCreatedAt())) tripCount++;
                    else taskCount++;
                } else {
                    tripCount++;
                }
            } else if (taskNoteList.size() > taskCount) {
                taskCount++;
            }
        }
        // 노트 목록 조회
        return TripNoteDetailListResponse.builder()
                .tripTitle(tripEntity.getName())
                .tripNoteDetails(tripNoteList.subList(0, tripCount))
                .taskNoteDetails(taskNoteList.subList(0, taskCount))
                .build();
    }

    public TripNoteDetailResponse getNoteDetail(long tripNoteId) {
        // 노트 상세 조회
        return tripNoteRepository.selectTripNoteDetail(tripNoteId);
    }

    @Transactional
    public Long registerNode(TripNoteRegRequest tripNoteRegRequest) {
        // 권한 체크
        var tripEntity = checkTripMember(tripNoteRegRequest.getTripId());
        // 노트 세팅
        var tripNoteEntity = TripNote.from(tripNoteRegRequest);
        tripNoteEntity.setTrip(tripEntity);
        // 노트 등록
        var result = tripNoteRepository.save(tripNoteEntity);
        return result.getId();
    }

    @Transactional
    public Long modifyNote(TripNoteModRequest tripNoteModRequest) {
        var tripNoteId = tripNoteModRequest.getTripNoteId();
        // 권한 체크
        var isModifiable = tripNoteRepository.isAuthorizedToModify(tripNoteId);
        if (isModifiable) throw new AlertException("여행 노트를 수정할 권한이 없습니다.");
        // 노트 조회
        var taskNoteEntity = tripNoteRepository.findById(tripNoteId)
                .orElseThrow(() -> new AlertException("여행 노트 정보가 존재하지 않습니다"));
        // 노트 수정
        taskNoteEntity.modifyTo(tripNoteModRequest);
        var result = tripNoteRepository.save(taskNoteEntity);
        return result.getId();
    }

    @Transactional
    public void deleteNote(long tripNoteId) {
        // 권한 체크
        var isDeletable = tripNoteRepository.isAuthorizedToModify(tripNoteId);
        if (!isDeletable) throw new AlertException("여행 노트를 삭제할 권한이 없습니다.");
        // 노트 삭제
        tripNoteRepository.deleteById(tripNoteId);
    }

    private Trip checkTripMember(long tripId) {
        var tripEntity = tripRepository.findByIdWithMembers(tripId)
                .orElseThrow(() -> new AlertException("여행이 존재하지 않습니다."));
        tripEntity.checkMemberExists(AuthUtil.getUserInfo());
        return tripEntity;
    }

}
