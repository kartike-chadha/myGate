package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface OwnerRepository extends JpaRepository<Owner, UUID> {

    boolean existsByEmailIgnoreCase(String email);

}
