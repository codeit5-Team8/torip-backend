package com.codeit.torip.trip.service;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.common.exception.AlertException;
import com.codeit.torip.trip.dto.PageCollection;
import com.codeit.torip.trip.dto.request.CreateTripRequest;
import com.codeit.torip.trip.dto.request.UpdateTripRequest;
import com.codeit.torip.trip.dto.response.TripInvitationResponse;
import com.codeit.torip.trip.dto.response.TripResponse;
import com.codeit.torip.trip.entity.Trip;
import com.codeit.torip.trip.entity.TripInvitation;
import com.codeit.torip.trip.entity.TripInvitationStatus;
import com.codeit.torip.trip.entity.TripMember;
import com.codeit.torip.trip.repository.TripInvitationRepository;
import com.codeit.torip.trip.repository.TripRepository;
import com.codeit.torip.user.dto.UserResponse;
import com.codeit.torip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.codeit.torip.common.contant.ToripConstants.Trip.PAGE_SIZE;

@Transactional
@RequiredArgsConstructor
@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TripInvitationRepository tripInvitationRepository;

    public TripResponse createTrip(CreateTripRequest createTripRequest) {
        User userInfo = AuthUtil.getUserInfo();

        Trip trip = new Trip(createTripRequest, userInfo);
        tripRepository.save(trip);

        return trip.toResponse();
    }

    @Transactional(readOnly = true)
    public TripResponse getTrip(Long id) {
        User userInfo = AuthUtil.getUserInfo();

        Trip trip = tripRepository.findById(id).orElseThrow(() -> new AlertException("여행이 존재하지 않습니다."));
        trip.checkMemberExists(userInfo);

        return trip.toResponse();
    }

    public void deleteTrip(Long id) {
        User userInfo = AuthUtil.getUserInfo();

        Trip trip = tripRepository.findById(id).orElseThrow(() -> new AlertException("여행이 존재하지 않습니다."));
        trip.checkOwner(userInfo);

        tripInvitationRepository.deleteAllByTripId(id);

        tripRepository.delete(trip);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getTripMembers(Long id) {
        User userInfo = AuthUtil.getUserInfo();

        Trip trip = tripRepository.findById(id).orElseThrow(() -> new AlertException("여행이 존재하지 않습니다."));
        trip.checkMemberExists(userInfo);

        return trip.getMembers().stream()
                .map(TripMember::getUser)
                .map(User::toResponse).toList();

    }

    @Transactional(readOnly = true)
    public PageCollection<TripResponse> getTripList(Long lastSeenId) {
        User userInfo = AuthUtil.getUserInfo();

        List<Trip> trips = tripRepository.findAllByMembersUserIdAndIdGreaterThanOrderByIdAsc(
                userInfo.getId(), lastSeenId, PageRequest.of(0, PAGE_SIZE));


        return PageCollection.<TripResponse>builder()
                .lastSeenId(lastSeenId + trips.size())
                .content(trips.stream().map(Trip::toResponse).toList())
                .build();
    }

    public TripResponse updateTrip(Long id, UpdateTripRequest updateTripRequest) {
        User userInfo = AuthUtil.getUserInfo();

        Trip trip = tripRepository.findById(id).orElseThrow(() -> new AlertException("여행이 존재하지 않습니다."));

        trip.checkOwner(userInfo);
        trip.update(userInfo, updateTripRequest);

        return trip.toResponse();
    }

    public TripInvitationResponse requestTripParticipation(Long id) {
        User userInfo = AuthUtil.getUserInfo();

        Trip trip = tripRepository.findById(id).orElseThrow(() -> new AlertException("여행이 존재하지 않습니다."));
        trip.checkMemberNotExists(userInfo);

        if (tripInvitationRepository.existsByInviteeIdAndTripId(userInfo.getId(), id)) {
            return reapplyForTripInvitation(id, userInfo);
        }

        TripInvitation tripInvitation = new TripInvitation(trip, userInfo);
        tripInvitationRepository.save(tripInvitation);

        return tripInvitation.toResponse();
    }

    private TripInvitationResponse reapplyForTripInvitation(Long id, User userInfo) {
        TripInvitation tripInvitation = tripInvitationRepository.findByInviteeIdAndTripId(userInfo.getId(), id);
        if (tripInvitation.getStatus() == TripInvitationStatus.Pending) {
            throw new AlertException("이미 참가 요청을 보냈습니다.");
        }

        if(tripInvitation.getStatus() == TripInvitationStatus.Accepted) {
            throw new AlertException("이미 참가 요청이 수락되었습니다.");
        }

        tripInvitation.reapply();
        return tripInvitation.toResponse();
    }


    public TripInvitationResponse acceptTripParticipation(Long id) {
        User userInfo = AuthUtil.getUserInfo();

        TripInvitation tripInvitation = tripInvitationRepository.findById(id).orElseThrow(() -> new AlertException("초대가 존재하지 않습니다."));
        tripInvitation.getTrip().checkOwner(userInfo);
        tripInvitation.getTrip().checkMemberNotExists(tripInvitation.getInvitee());

        tripInvitation.accept();

        return tripInvitation.toResponse();
    }

    public TripInvitationResponse rejectTripParticipation(Long id) {
        User userInfo = AuthUtil.getUserInfo();

        TripInvitation tripInvitation = tripInvitationRepository.findById(id).orElseThrow(() -> new AlertException("초대가 존재하지 않습니다."));
        tripInvitation.getTrip().checkOwner(userInfo);
        tripInvitation.getTrip().checkMemberNotExists(tripInvitation.getInvitee());

        tripInvitation.reject();

        return tripInvitation.toResponse();
    }


    @Transactional(readOnly = true)
    public List<TripInvitationResponse> getTripInvitations(Long id) {
        User userInfo = AuthUtil.getUserInfo();

        Trip trip = tripRepository.findById(id).orElseThrow(() -> new AlertException("여행이 존재하지 않습니다."));
        trip.checkOwner(userInfo);

        List<TripInvitation> tripInvitations = tripInvitationRepository.findAllByTripIdAndStatusOrderByCreatedAt(id, TripInvitationStatus.Pending);

        return tripInvitations.stream()
                .map(TripInvitation::toResponse)
                .toList();
    }
}
