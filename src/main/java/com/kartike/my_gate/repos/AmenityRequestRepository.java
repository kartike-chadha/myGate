package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Amenity;
import com.kartike.my_gate.domain.AmenityRequest;
import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.enums.RequestStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;


public interface AmenityRequestRepository extends JpaRepository<AmenityRequest, Integer> {

    AmenityRequest findFirstByRequestedAmenity(Amenity amenity);

    AmenityRequest findFirstByOwner(Owner owner);

    @Query("SELECT COUNT(req) FROM AmenityRequest req WHERE req.requestedAmenity = :requestedAmenity AND req.requestStatus != :requestStatus")
    Integer countByAmenityIdAndAmenityStatusNotEqualTo(@Param("requestedAmenity") Amenity requestedAmenity, @Param("requestStatus") RequestStatusEnum requestStatus);


}
