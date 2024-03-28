package com.kartike.my_gate.service.implementations;

import com.kartike.my_gate.domain.GateLog;
import com.kartike.my_gate.domain.Layout;
import com.kartike.my_gate.domain.UserState;
import com.kartike.my_gate.enums.LogTypeEnum;
import com.kartike.my_gate.model.GateLogDTO;
import com.kartike.my_gate.repos.*;
import com.kartike.my_gate.service.interfaces.GateLogService;
import com.kartike.my_gate.util.NotFoundException;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GateLogServiceImpl implements GateLogService {

    private final GateLogRepository gateLogRepository;
    private final OwnerRepository ownerRepository;
    private final VendorRepository vendorRepository;
    private final LayoutRepository layoutRepository;

    private final UserStateRepository userStateRepository;
    @Override
    public List<GateLogDTO> findAll() {
        final List<GateLog> gateLogs = gateLogRepository.findAll(Sort.by("logId"));
        return gateLogs.stream()
                .map(gateLog -> mapToDTO(gateLog, new GateLogDTO()))
                .toList();
    }
    @Override
    public GateLogDTO get(final Integer logId) {
        return gateLogRepository.findById(logId)
                .map(gateLog -> mapToDTO(gateLog, new GateLogDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Override
    public Integer create(final GateLogDTO gateLogDTO) {
        //check if user is in the same state as being passed
//        final Integer temp = 0;
        return userStateRepository.getByUserId(gateLogDTO.getUserId())
                .map(existingState -> {
                    if(existingState.getState()!=gateLogDTO.getLogType()){
                        existingState.setState(gateLogDTO.getLogType());
                        final GateLog gateLog = new GateLog();
                        mapToEntity(gateLogDTO, gateLog);
                        userStateRepository.save(existingState);
                        return gateLogRepository.save(gateLog).getLogId();
                    }else{
                        throw new RuntimeException("Check in or check out unsuccessful as user is already in the passed state");
                    }
                })
                .orElseGet(() -> {
                    if(LogTypeEnum.CHECK_IN==gateLogDTO.getLogType()){
                        final GateLog gateLog = new GateLog();
                        mapToEntity(gateLogDTO, gateLog);
                        UserState userState = new UserState();
                        mapGateLogToUserState(gateLogDTO, userState);
                        userStateRepository.save(userState);
                        return gateLogRepository.save(gateLog).getLogId();
                    }else{
                        throw new RuntimeException("User cant check out if never checked in first");
                    }
                });
//        if(userState.isPresent()){
//            UserState currentState = userState.get();
//            if(currentState.getState()==gateLogDTO.getLogType()){
//                throw new RuntimeException("User can't have gate log of same type as current state");
//            }
//        }

//        return userStateRepository.getByUserId(gateLogDTO.getUserId())
//                .map()
    }


    @Override
    public void update(final Integer logId, final GateLogDTO gateLogDTO) {
        final GateLog gateLog = gateLogRepository.findById(logId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(gateLogDTO, gateLog);
        gateLogRepository.save(gateLog);
    }
    @Override
    public void delete(final Integer logId) {
        gateLogRepository.deleteById(logId);
    }

    private GateLogDTO mapToDTO(final GateLog gateLog, final GateLogDTO gateLogDTO) {
        gateLogDTO.setLogId(gateLog.getLogId());
        gateLogDTO.setUserType(gateLog.getUserType());
        gateLogDTO.setLogType(gateLog.getLogType());
        gateLogDTO.setLogTime(gateLog.getLogTime());
        gateLogDTO.setHouseId(gateLog.getHouse().getId());
        gateLogDTO.setUserId(gateLog.getUserId());
        return gateLogDTO;
    }

    private GateLog mapToEntity(final GateLogDTO gateLogDTO, final GateLog gateLog) {
        gateLog.setUserType(gateLogDTO.getUserType());
        gateLog.setLogType(gateLogDTO.getLogType());
        gateLog.setLogTime(OffsetDateTime.now());
        Layout layout = layoutRepository.findById(gateLogDTO.getHouseId())
                .orElseThrow(()-> new RuntimeException("House does not exist"));
        gateLog.setHouse(layout);
        gateLog.setUserId(gateLogDTO.getUserId());
        return gateLog;
    }

    private UserState mapGateLogToUserState(final GateLogDTO gateLogDTO, final UserState userState){
        userState.setUserId(gateLogDTO.getUserId());
        userState.setState(gateLogDTO.getLogType());
        return userState;
    }


}
