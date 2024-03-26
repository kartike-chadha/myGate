package com.kartike.my_gate.service;

import com.kartike.my_gate.model.GateLogDTO;
import javax.persistence.*;

import java.util.List;

public interface GateLogService {
    public List<GateLogDTO> findAll();
    public GateLogDTO get(Integer logId);
    public Integer create(GateLogDTO gateLogDTO);
    public void update(Integer logId, GateLogDTO gateLogDTO);
    public void delete(Integer logId);
}
