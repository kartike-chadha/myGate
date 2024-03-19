package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlockRepository extends JpaRepository<Block, Integer> {
}
