package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.domain.UserState;
import com.kartike.my_gate.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserStateRepository extends JpaRepository<UserState, Integer> {

    UserState findFirstByOwner(Owner owner);

    UserState findFirstByVendor(Vendor vendor);

}
