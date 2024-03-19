package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.InvoiceDTO;
import com.kartike.my_gate.service.InvoiceService;
import com.kartike.my_gate.util.ReferencedException;
import com.kartike.my_gate.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
public class InvoiceResource {

    private final InvoiceService invoiceService;

    public InvoiceResource(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.findAll());
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDTO> getInvoice(
            @PathVariable(name = "invoiceId") final Integer invoiceId) {
        return ResponseEntity.ok(invoiceService.get(invoiceId));
    }

    @PostMapping
    public ResponseEntity<Integer> createInvoice(@RequestBody @Valid final InvoiceDTO invoiceDTO) {
        final Integer createdInvoiceId = invoiceService.create(invoiceDTO);
        return new ResponseEntity<>(createdInvoiceId, HttpStatus.CREATED);
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<Integer> updateInvoice(
            @PathVariable(name = "invoiceId") final Integer invoiceId,
            @RequestBody @Valid final InvoiceDTO invoiceDTO) {
        invoiceService.update(invoiceId, invoiceDTO);
        return ResponseEntity.ok(invoiceId);
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(
            @PathVariable(name = "invoiceId") final Integer invoiceId) {
        final ReferencedWarning referencedWarning = invoiceService.getReferencedWarning(invoiceId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        invoiceService.delete(invoiceId);
        return ResponseEntity.noContent().build();
    }

}
