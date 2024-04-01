package com.kartike.my_gate.repos;

import com.kartike.my_gate.domain.Invoice;
import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.model.DefaulterDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    Invoice findFirstByOwner(Owner owner);
    @Query("select inv from Invoice inv where inv.dateCreated >= :payableAfter")
    List<Invoice> findAllByDatePayableAfter(@Param("payableAfter") OffsetDateTime payableAfter);
    Optional<Invoice> findByDatePayable(OffsetDateTime payableAfter);

    @Query(value = "select CAST(inv.owner_id as varchar) as defaulterId, count(inv.amount) as numberOfDefaults from Invoice inv where inv.amount>:amountPayable group by inv.owner_id having count(inv.amount) = (select max(numberOfDefaults) from (select count(inv.amount) as numberOfDefaults from Invoice inv where inv.amount>:amountPayable group by inv.owner_id) as max_counts)",nativeQuery = true)
    List<DefaulterDetails> findMostDefaults(@Param("amountPayable") Integer amountPayable);

}
