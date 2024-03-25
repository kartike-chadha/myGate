package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.domain.UserState;
import com.kartike.my_gate.domain.Vendor;
import com.kartike.my_gate.model.GateLogDTO;
import com.kartike.my_gate.model.UserStateDTO;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.repos.UserStateRepository;
import com.kartike.my_gate.repos.VendorRepository;
import com.kartike.my_gate.util.NotFoundException;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserStateServiceImpl implements UserStateService{

    private final UserStateRepository userStateRepository;
    private final OwnerRepository ownerRepository;
    private final VendorRepository vendorRepository;

    public UserStateServiceImpl(final UserStateRepository userStateRepository,
                                final OwnerRepository ownerRepository, final VendorRepository vendorRepository) {
        this.userStateRepository = userStateRepository;
        this.ownerRepository = ownerRepository;
        this.vendorRepository = vendorRepository;
    }
    @Override
    public List<UserStateDTO> findAll(){
        final List<UserState> userStates = userStateRepository.findAll(Sort.by("stateId"));
        return userStates.stream()
                .map(userState -> mapToDTO(userState, new UserStateDTO()))
                .toList();
    }
    @Override
    public UserStateDTO get(final Integer stateId) {
        return userStateRepository.findById(stateId)
                .map(userState -> mapToDTO(userState, new UserStateDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Override
    public Integer create(final UserStateDTO userStateDTO) {
        final UserState userState = new UserState();
        mapToEntity(userStateDTO, userState);
        return userStateRepository.save(userState).getStateId();
    }
    @Override
    public void update(final Integer stateId, final UserStateDTO userStateDTO) {
        final UserState userState = userStateRepository.findById(stateId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userStateDTO, userState);
        userStateRepository.save(userState);
    }
    @Override
    public void delete(final Integer stateId) {
        userStateRepository.deleteById(stateId);
    }

    private UserStateDTO mapToDTO(final UserState userState, final UserStateDTO userStateDTO) {
        userStateDTO.setStateId(userState.getStateId());
        userStateDTO.setState(userState.getState());
        userStateDTO.setUserId(userState.getUserId());
        return userStateDTO;
    }

    private UserState mapToEntity(final UserStateDTO userStateDTO, final UserState userState) {
        userState.setState(userStateDTO.getState());
        userState.setUserId(userStateDTO.getUserId());
        return userState;
    }


}
