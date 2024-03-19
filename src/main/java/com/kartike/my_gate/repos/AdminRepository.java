package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Admin;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, UUID> {
}
