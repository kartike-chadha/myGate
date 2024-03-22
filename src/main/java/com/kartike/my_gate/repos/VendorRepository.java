package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Amenity;
import com.kartike.my_gate.domain.Vendor;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VendorRepository extends JpaRepository<Vendor, UUID> {

    Vendor findFirstByAmenity(Amenity amenity);

    boolean existsByAmenityId(Integer id);

}
