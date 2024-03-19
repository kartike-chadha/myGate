package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    boolean existsByEmailIgnoreCase(String email);

}
