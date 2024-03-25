package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.domain.UserState;
import com.kartike.my_gate.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserStateRepository extends JpaRepository<UserState, Integer> {
    Optional<UserState> getByUserId(UUID userId);
}
