package com.codeit.torip.task.note.service;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.common.exception.AlertException;
import com.codeit.torip.task.entity.Task;
import com.codeit.torip.task.entity.TaskAssignee;
import com.codeit.torip.task.note.dto.request.TaskNoteListRequest;
import com.codeit.torip.task.note.dto.request.TaskNoteModRequest;
import com.codeit.torip.task.note.dto.request.TaskNoteRegRequest;
import com.codeit.torip.task.note.dto.response.TaskNoteDeletedResponse;
import com.codeit.torip.task.note.dto.response.TaskNoteDetailListResponse;
import com.codeit.torip.task.note.dto.response.TaskNoteDetailResponse;
import com.codeit.torip.task.note.entity.TaskNote;
import com.codeit.torip.task.note.repository.TaskNoteRepository;
import com.codeit.torip.task.repository.task.TaskRepository;
import com.codeit.torip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaskNoteService {

    private final TaskNoteRepository taskNoteRepository;
    private final TaskRepository taskRepository;

    public TaskNoteDetailListResponse getTaskNoteList(TaskNoteListRequest taskNoteListRequest) {
        var taskEntity = checkTaskAssignee(taskNoteListRequest.getTaskId());
        var taskNoteList = taskNoteRepository.selectTaskNoteDetailList(taskNoteListRequest);
        // 노트 목록 조회
        return TaskNoteDetailListResponse.builder()
                .tripTitle(taskEntity.getTrip().getName())
                .details(taskNoteList)
                .build();
    }

    public TaskNoteDetailResponse getTaskNoteDetail(long noteId) {
        // 노트 상세 조회
        return taskNoteRepository.selectTaskNoteDetail(noteId);
    }

    @Transactional
    public Long registerTaskNote(TaskNoteRegRequest taskNoteRegRequest) {
        // 권한 체크
        var taskEntity = checkTaskAssignee(taskNoteRegRequest.getTaskId());
        // 노트 세팅
        var taskNoteEntity = TaskNote.from(taskNoteRegRequest);
        taskNoteEntity.setTask(taskEntity);
        // 노트 등록
        var result = taskNoteRepository.save(taskNoteEntity);
        return result.getId();
    }

    @Transactional
    public Long modifyTaskNote(TaskNoteModRequest taskNoteModRequest) {
        var taskNoteId = taskNoteModRequest.getTaskNoteId();
        // 권한 체크
        var isModifiable = taskNoteRepository.isAuthorizedToModify(taskNoteId);
        if (isModifiable) throw new AlertException("할일 노트를 수정할 권한이 없습니다.");
        // 노트 조회
        var noteEntity = taskNoteRepository.findById(taskNoteModRequest.getTaskNoteId())
                .orElseThrow(() -> new AlertException("할일 노트 정보가 존재하지 않습니다"));
        // 노트 수정
        noteEntity.modifyTo(taskNoteModRequest);
        var result = taskNoteRepository.save(noteEntity);
        return result.getId();
    }

    @Transactional
    public TaskNoteDeletedResponse deleteTaskNote(long taskNoteId) {
        // 권한 체크
        var isDeletable = taskNoteRepository.isAuthorizedToModify(taskNoteId);
        if (!isDeletable) throw new AlertException("할일 노트를 삭제할 권한이 없습니다.");
        // 노트 삭제
        var deletedTaskNote = taskNoteRepository.deletedTaskNote(taskNoteId);
        taskNoteRepository.deleteById(taskNoteId);
        return deletedTaskNote;
    }

    private Task checkTaskAssignee(long taskId) {
        var email = AuthUtil.getEmail();
        var taskEntity = taskRepository.findByIdWithAssignees(taskId)
                .orElseThrow(() -> new AlertException("할일이 존재하지 않습니다."));
        var isAssignee = taskEntity.getAssignees().stream().map(TaskAssignee::getAssignee)
                .map(User::getEmail).anyMatch((e) -> e.equals(email));
        if (!isAssignee) throw new AlertException("할일의 담당자가 아닙니다.");
        return taskEntity;
    }

}
