package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GateLogRepository extends JpaRepository<GateLog, Integer> {
    GateLog findFirstByHouse(Layout layout);

}
