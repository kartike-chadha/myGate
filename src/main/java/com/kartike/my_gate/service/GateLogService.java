package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.GateLog;
import com.kartike.my_gate.domain.Layout;
import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.domain.Vendor;
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
public class GateLogService {

    private final GateLogRepository gateLogRepository;
    private final OwnerRepository ownerRepository;
    private final VendorRepository vendorRepository;
    private final LayoutRepository layoutRepository;

    public GateLogService(final GateLogRepository gateLogRepository,
            final OwnerRepository ownerRepository, final VendorRepository vendorRepository,
            final LayoutRepository layoutRepository) {
        this.gateLogRepository = gateLogRepository;
        this.ownerRepository = ownerRepository;
        this.vendorRepository = vendorRepository;
        this.layoutRepository = layoutRepository;
    }

    public List<GateLogDTO> findAll() {
        final List<GateLog> gateLogs = gateLogRepository.findAll(Sort.by("logId"));
        return gateLogs.stream()
                .map(gateLog -> mapToDTO(gateLog, new GateLogDTO()))
                .toList();
    }

    public GateLogDTO get(final Integer logId) {
        return gateLogRepository.findById(logId)
                .map(gateLog -> mapToDTO(gateLog, new GateLogDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final GateLogDTO gateLogDTO) {
        final GateLog gateLog = new GateLog();
        mapToEntity(gateLogDTO, gateLog);
        return gateLogRepository.save(gateLog).getLogId();
    }

    public void update(final Integer logId, final GateLogDTO gateLogDTO) {
        final GateLog gateLog = gateLogRepository.findById(logId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(gateLogDTO, gateLog);
        gateLogRepository.save(gateLog);
    }

    public void delete(final Integer logId) {
        gateLogRepository.deleteById(logId);
    }

    private GateLogDTO mapToDTO(final GateLog gateLog, final GateLogDTO gateLogDTO) {
        gateLogDTO.setLogId(gateLog.getLogId());
        gateLogDTO.setHouseId(gateLog.getHouseId());
        gateLogDTO.setUserId(gateLog.getUserId());
        gateLogDTO.setUserType(gateLog.getUserType());
        gateLogDTO.setLogType(gateLog.getLogType());
        gateLogDTO.setLogTime(gateLog.getLogTime());
        gateLogDTO.setUser(gateLog.getUser() == null ? null : gateLog.getUser().getId());
        gateLogDTO.setUser(gateLog.getUser() == null ? null : gateLog.getUser().getVendorId());
        gateLogDTO.setHouse(gateLog.getHouse() == null ? null : gateLog.getHouse().getId());
        return gateLogDTO;
    }

    private GateLog mapToEntity(final GateLogDTO gateLogDTO, final GateLog gateLog) {
        gateLog.setHouseId(gateLogDTO.getHouseId());
        gateLog.setUserId(gateLogDTO.getUserId());
        gateLog.setUserType(gateLogDTO.getUserType());
        gateLog.setLogType(gateLogDTO.getLogType());
        gateLog.setLogTime(gateLogDTO.getLogTime());
        final Owner user = gateLogDTO.getUser() == null ? null : ownerRepository.findById(gateLogDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        gateLog.setUser(user);
        final Vendor user = gateLogDTO.getUser() == null ? null : vendorRepository.findById(gateLogDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        gateLog.setUser(user);
        final Layout house = gateLogDTO.getHouse() == null ? null : layoutRepository.findById(gateLogDTO.getHouse())
                .orElseThrow(() -> new NotFoundException("house not found"));
        gateLog.setHouse(house);
        return gateLog;
    }

}
