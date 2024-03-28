package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.GateLog;
import com.kartike.my_gate.domain.Layout;
import com.kartike.my_gate.model.SuspectsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;


public interface GateLogRepository extends JpaRepository<GateLog, Integer> {
    GateLog findFirstByHouse(Layout layout);


@Query("select  distinct log.userId as suspectId,log.userType as suspectType from GateLog log where log.logTime>=:startDateTime and log.logTime<=:endDateTime")
    List<SuspectsDetails> findAllByLogTimeBetween(@Param("startDateTime")OffsetDateTime startDateTime, @Param("endDateTime")OffsetDateTime endDateTime);

}
