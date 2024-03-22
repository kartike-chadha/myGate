package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Amenity;
import com.kartike.my_gate.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AmenityRepository extends JpaRepository<Amenity, Integer> {

}
