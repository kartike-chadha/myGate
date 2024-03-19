package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Block;
import com.kartike.my_gate.domain.Layout;
import com.kartike.my_gate.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LayoutRepository extends JpaRepository<Layout, Integer> {

    Layout findFirstByBlock(Block block);

    Layout findFirstByOwner(Owner owner);

}
