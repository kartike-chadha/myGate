package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Invoice;
import com.kartike.my_gate.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    Invoice findFirstByOwner(Owner owner);
    @Query("select inv from Invoice inv where inv.datePayable >= :payableAfter")
    List<Invoice> findAllByDatePayableAfter(@Param("payableAfter") OffsetDateTime payableAfter);
    Optional<Invoice> findByDatePayable(OffsetDateTime payableAfter);

}
