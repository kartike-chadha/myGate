package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Invoice;
import com.kartike.my_gate.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    Invoice findFirstByOwner(Owner owner);

}
