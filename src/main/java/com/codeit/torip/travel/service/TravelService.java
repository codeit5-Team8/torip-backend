package com.codeit.torip.travel.service;

import com.codeit.torip.auth.util.AuthenticationFacade;
import com.codeit.torip.travel.dto.CreateTravelRequest;
import com.codeit.torip.travel.dto.TravelResponse;
import com.codeit.torip.travel.dto.UpdateTravelRequest;
import com.codeit.torip.travel.entity.Travel;
import com.codeit.torip.travel.entity.TravelInvitation;
import com.codeit.torip.travel.entity.TravelMember;
import com.codeit.torip.travel.repository.TravelInvitationRepository;
import com.codeit.torip.travel.repository.TravelRepository;
import com.codeit.torip.user.dto.UserResponse;
import com.codeit.torip.user.entity.User;
import com.codeit.torip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public List<TravelResponse> getTravelList() {
        User userInfo = authenticationFacade.getUserInfo();

        List<Travel> travels = travelRepository.findAllByMembersUserIdOrderByCreatedAtDesc(userInfo.getId());

        return travels.stream().map(Travel::toResponse).toList();
    }

    public TravelResponse updateTravel(Long id, UpdateTravelRequest updateTravelRequest) {
        User userInfo = authenticationFacade.getUserInfo();

        Travel travel = travelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("여행이 존재하지 않습니다."));

        travel.checkOwner(userInfo);
        travel.update(userInfo, updateTravelRequest);

        return travel.toResponse();
    }

    public void requestTravelParticipation(Long id, Long inviterId) {
        User userInfo = authenticationFacade.getUserInfo();

        User inviter = userRepository.findUserById(inviterId).orElseThrow(() -> new IllegalArgumentException("초대자가 존재하지 않습니다."));
        Travel travel = travelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("여행이 존재하지 않습니다."));
        travel.checkMemberNotExists(userInfo);
        travel.checkMemberExists(inviter);

        TravelInvitation travelInvitation = new TravelInvitation(travel, inviter, userInfo);
        travelInvitationRepository.save(travelInvitation);
    }
}
