package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.GateLog;
import com.kartike.my_gate.model.GateLogDTO;
import com.kartike.my_gate.repos.GateLogRepository;
import com.kartike.my_gate.repos.LayoutRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.repos.VendorRepository;
import com.kartike.my_gate.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class GateLogServiceImpl implements GateLogService {

    private final GateLogRepository gateLogRepository;
    private final OwnerRepository ownerRepository;
    private final VendorRepository vendorRepository;
    private final LayoutRepository layoutRepository;

    public GateLogServiceImpl(final GateLogRepository gateLogRepository,
                              final OwnerRepository ownerRepository, final VendorRepository vendorRepository,
                              final LayoutRepository layoutRepository) {
        this.gateLogRepository = gateLogRepository;
        this.ownerRepository = ownerRepository;
        this.vendorRepository = vendorRepository;
        this.layoutRepository = layoutRepository;
    }
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
        final GateLog gateLog = new GateLog();
        mapToEntity(gateLogDTO, gateLog);
        return gateLogRepository.save(gateLog).getLogId();
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
        return gateLogDTO;
    }

    private GateLog mapToEntity(final GateLogDTO gateLogDTO, final GateLog gateLog) {
        gateLog.setUserType(gateLogDTO.getUserType());
        gateLog.setLogType(gateLogDTO.getLogType());
        gateLog.setLogTime(gateLogDTO.getLogTime());
        return gateLog;
    }

}
