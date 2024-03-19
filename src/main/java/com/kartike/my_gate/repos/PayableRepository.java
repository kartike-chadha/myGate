package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Invoice;
import com.kartike.my_gate.domain.Payable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PayableRepository extends JpaRepository<Payable, Integer> {

    Payable findFirstByInvoice(Invoice invoice);

}
