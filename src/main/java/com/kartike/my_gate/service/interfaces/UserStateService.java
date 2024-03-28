package com.kartike.my_gate.service.interfaces;

import com.kartike.my_gate.model.UserStateDTO;

import java.util.List;

public interface UserStateService {
    public List<UserStateDTO> findAll();
    public UserStateDTO get(Integer stateId);
    public Integer create(UserStateDTO userStateDTO);
    public void update(Integer stateId, UserStateDTO userStateDTO);
    public void delete(Integer stateId);
}
