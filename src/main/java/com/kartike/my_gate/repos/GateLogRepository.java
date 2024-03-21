package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.GateLog;
import com.kartike.my_gate.domain.Layout;
import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GateLogRepository extends JpaRepository<GateLog, Integer> {

    GateLog findFirstByOwner(Owner owner);

    GateLog findFirstByVendor(Vendor vendor);

    GateLog findFirstByHouse(Layout layout);

}
