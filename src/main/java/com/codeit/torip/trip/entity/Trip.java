package com.codeit.torip.trip.entity;

import com.codeit.torip.common.entity.BaseUserEntity;
import com.codeit.torip.task.entity.Task;
import com.codeit.torip.trip.dto.request.CreateTripRequest;
import com.codeit.torip.trip.dto.request.UpdateTripRequest;
import com.codeit.torip.trip.dto.response.TripResponse;
import com.codeit.torip.trip.note.entity.TripNote;
import com.codeit.torip.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.codeit.torip.common.contant.ToripConstants.Task.TASK_LIMIT_PER_TRIP_STATUS;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "trip")
public class Trip extends BaseUserEntity {
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TripMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TripNote> notes = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // 여행지 (2차 개발)
    // private String destination;

    // 여행 대표 이미지 (2차 개발)
    // private String representativeImage;

    public Trip(CreateTripRequest request, User owner) {
        this.name = Objects.requireNonNull(request.getName());
        this.startDate = Objects.requireNonNull(request.getStartDate());
        this.endDate = Objects.requireNonNull(request.getEndDate());
        this.owner = Objects.requireNonNull(owner);
        this.lastcreatedUser = owner;
        this.lastUpdatedUser = owner;
        this.members.add(new TripMember(this, owner, TripMemberRole.OWNER));

        checkStartDateBeforeEndDate(this.startDate, this.endDate);
    }

    public void update(User updateUser, UpdateTripRequest request) {
        checkOwner(updateUser);
        this.lastUpdatedUser = Objects.requireNonNull(updateUser);
        this.name = Objects.requireNonNullElse(request.getName(), this.name);
        this.startDate = Objects.requireNonNullElse(request.getStartDate(), this.startDate);
        this.endDate = Objects.requireNonNullElse(request.getEndDate(), this.endDate);
        checkStartDateBeforeEndDate(this.startDate, this.endDate);
    }

    // Task 추가 함수
    public void addTask(Task newTask) {

        Objects.requireNonNull(newTask);
        var status = newTask.getStatus().name();
        if (tasks.stream().map((task) -> task.getStatus().name().equals(status)).count() > TASK_LIMIT_PER_TRIP_STATUS) {
            throw new IllegalArgumentException("여행 상태 별 할일은 최대 60개까지 생성하실 수 있습니다.");
        }
        if (tasks.stream().anyMatch(task -> task.equals(newTask))) {
            throw new IllegalArgumentException("해당 Task는 여행에 이미 속해있습니다 않습니다.");
        }

        this.tasks.add(newTask);
    }

    // 오너 확인 함수
    public void checkOwner(User user) {
        if (!owner.getId().equals(user.getId())) {
            throw new IllegalArgumentException("여행 오너가 아닙니다.");
        }
    }

    public void addMember(User newUser) {
        Objects.requireNonNull(newUser);
        checkMemberNotExists(newUser);
        this.members.add(new TripMember(this, newUser, TripMemberRole.MEMBER));
    }

    // 멤버인지 확인 함수
    public void checkMemberExists(User user) {
        if (members.stream().noneMatch(member -> member.getUser().getId().equals(user.getId()))
                || members.stream().noneMatch(member -> member.getUser().getEmail().equals(user.getEmail()))) {
            throw new IllegalArgumentException("여행에 참가하지 않은 사용자입니다.");
        }
    }

    // 멤버 중복 확인 함수
    public void checkMemberNotExists(User newUser) {
        if (members.stream().anyMatch(member -> member.getUser().getId().equals(newUser.getId()))) {
            throw new IllegalArgumentException("이미 여행에 참가한 사용자입니다.");
        }
    }


    private void checkStartDateBeforeEndDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("여행 시작 날짜가 여행 종료 날짜보다 늦습니다.");
        }
    }


    public TripResponse toResponse() {
        return TripResponse.builder()
                .id(id)
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .owner(owner.toResponse())
                .lastUpdatedUser(lastUpdatedUser.toResponse())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
