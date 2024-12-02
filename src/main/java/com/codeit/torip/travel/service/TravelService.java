package com.codeit.torip.travel.service;

import com.codeit.torip.auth.util.AuthenticationFacade;
import com.codeit.torip.travel.dto.*;
import com.codeit.torip.travel.entity.Travel;
import com.codeit.torip.travel.entity.TravelInvitation;
import com.codeit.torip.travel.entity.TravelInvitationStatus;
import com.codeit.torip.travel.entity.TravelMember;
import com.codeit.torip.travel.repository.TravelInvitationRepository;
import com.codeit.torip.travel.repository.TravelRepository;
import com.codeit.torip.user.dto.UserResponse;
import com.codeit.torip.user.entity.User;
import com.codeit.torip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class TravelService {

    private final TravelRepository travelRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final TravelInvitationRepository travelInvitationRepository;
    private final int PAGE_SIZE = 3;

    public TravelResponse createTravel(CreateTravelRequest createTravelRequest) {
        User userInfo = authenticationFacade.getUserInfo();

        Travel travel = new Travel(createTravelRequest, userInfo);
        travelRepository.save(travel);

        return travel.toResponse();
    }

    public TravelResponse getTravel(Long id) {
        User userInfo = authenticationFacade.getUserInfo();

        Travel travel = travelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("여행이 존재하지 않습니다."));
        travel.checkMemberExists(userInfo);

        return travel.toResponse();
    }

    public void deleteTravel(Long id) {
        User userInfo = authenticationFacade.getUserInfo();

        Travel travel = travelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("여행이 존재하지 않습니다."));
        travel.checkOwner(userInfo);

        travelInvitationRepository.deleteAllByTravelId(id);

        travelRepository.delete(travel);
    }

    public List<UserResponse> getTravelMembers(Long id) {
        User userInfo = authenticationFacade.getUserInfo();

        Travel travel = travelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("여행이 존재하지 않습니다."));
        travel.checkMemberExists(userInfo);

        return travel.getMembers().stream()
                .map(TravelMember::getUser)
                .map(User::toResponse).toList();

    }

    public PageCollectionResponse<TravelResponse> getTravelList(Long lastSeenId) {
        User userInfo = authenticationFacade.getUserInfo();

        List<Travel> travels = travelRepository.findAllByMembersUserIdAndIdGreaterThanOrderByIdAsc(userInfo.getId(), lastSeenId, PageRequest.of(0, PAGE_SIZE));


        return PageCollectionResponse.<TravelResponse>builder()
                .lastSeenId(lastSeenId + travels.size())
                .content(travels.stream().map(Travel::toResponse).toList())
                .build();
    }

    public TravelResponse updateTravel(Long id, UpdateTravelRequest updateTravelRequest) {
        User userInfo = authenticationFacade.getUserInfo();

        Travel travel = travelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("여행이 존재하지 않습니다."));

        travel.checkOwner(userInfo);
        travel.update(userInfo, updateTravelRequest);

        return travel.toResponse();
    }

    public TravelInvitationResponse requestTravelParticipation(Long id, Long inviterId) {
        User userInfo = authenticationFacade.getUserInfo();

        User inviter = userRepository.findUserById(inviterId).orElseThrow(() -> new IllegalArgumentException("초대자가 존재하지 않습니다."));
        Travel travel = travelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("여행이 존재하지 않습니다."));
        travel.checkMemberNotExists(userInfo);
        travel.checkMemberExists(inviter);

        TravelInvitation travelInvitation = new TravelInvitation(travel, inviter, userInfo);
        travelInvitationRepository.save(travelInvitation);

        return travelInvitation.toResponse();
    }

    public TravelInvitationResponse acceptTravelParticipation(Long id) {
        User userInfo = authenticationFacade.getUserInfo();

        TravelInvitation travelInvitation = travelInvitationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("초대가 존재하지 않습니다."));
        travelInvitation.getTravel().checkOwner(userInfo);
        travelInvitation.getTravel().checkMemberNotExists(travelInvitation.getInvitee());

        travelInvitation.accept();

        return travelInvitation.toResponse();
    }

    public List<TravelInvitationResponse> getTravelInvitations(Long id) {
        User userInfo = authenticationFacade.getUserInfo();

        Travel travel = travelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("여행이 존재하지 않습니다."));
        travel.checkOwner(userInfo);

        List<TravelInvitation> travelInvitations = travelInvitationRepository.findAllByTravelIdAndStatusOrderByCreatedAt(id, TravelInvitationStatus.Pending);

        return travelInvitations.stream()
                .map(TravelInvitation::toResponse)
                .toList();
    }
}
