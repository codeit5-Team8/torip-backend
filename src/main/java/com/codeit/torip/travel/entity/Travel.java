package com.codeit.torip.travel.entity;

import com.codeit.torip.common.entity.BaseEntity;
import com.codeit.torip.task.entity.Task;
import com.codeit.torip.travel.dto.CreateTravelRequest;
import com.codeit.torip.travel.dto.TravelResponse;
import com.codeit.torip.travel.dto.UpdateTravelRequest;
import com.codeit.torip.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "travel")
public class Travel extends BaseUserEntity {
    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TravelMember> members = new ArrayList<>();
    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Task> tasks = new ArrayList<>();
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

    @Builder.Default
    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelMember> members = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    // 여행지 (2차 개발)
    // private String destination;

    // 여행 대표 이미지 (2차 개발)
    // private String representativeImage;

    public Travel(CreateTravelRequest request, User owner) {
        this.name = Objects.requireNonNull(request.getName());
        this.startDate = Objects.requireNonNull(request.getStartDate());
        this.endDate = Objects.requireNonNull(request.getEndDate());
        this.owner = Objects.requireNonNull(owner);
        this.lastcreatedUser = owner;
        this.lastUpdatedUser = owner;
        this.members.add(new TravelMember(this, owner, TravelMemberRole.OWNER));
    }

    // 오너 확인 함수
    public void checkOwner(User user) {
        if (!owner.getId().equals(user.getId())) {
            throw new IllegalArgumentException("여행 오너가 아닙니다.");
        }
    }

    // 멤버인지 확인 함수
    public void checkMemberExists(User user) {
        if (members.stream().noneMatch(member -> member.getUser().getId().equals(user.getId()))) {
            throw new IllegalArgumentException("여행에 참가하지 않은 사용자입니다.");
        }
    }

    // 멤버 중복 확인 함수
    public void checkMemberNotExists(User newUser) {
        if (members.stream().anyMatch(member -> member.getUser().getId().equals(newUser.getId()))) {
            throw new IllegalArgumentException("이미 여행에 참가한 사용자입니다.");
        }
    }

    public void update(User updateUser, UpdateTravelRequest request) {
        checkOwner(updateUser);
        this.lastUpdatedUser = Objects.requireNonNull(updateUser);
        this.name = Objects.requireNonNullElse(request.getName(), this.name);
        this.startDate = Objects.requireNonNullElse(request.getStartDate(), this.startDate);
        this.endDate = Objects.requireNonNullElse(request.getEndDate(), this.endDate);
    }

    public void addMember(User newUser) {

        Objects.requireNonNull(newUser);

        checkMemberNotExists(newUser);

        this.members.add(new TravelMember(this, newUser, TravelMemberRole.MEMBER));
    }


    // Task 추가 함수
    public void addTask(Task newTask) {

        Objects.requireNonNull(newTask);

        if (tasks.stream().anyMatch(task -> task.equals(newTask))) {
            throw new IllegalArgumentException("해당 Task는 여행에 이미 속해있습니다 않습니다.");
        }

        // TODO task 쪽에서 travel 정보를 업데이트 해주는 로직이 필요할 수도 있음
        this.tasks.add(newTask);
    }

    public TravelResponse toResponse() {
        return TravelResponse.builder()
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
