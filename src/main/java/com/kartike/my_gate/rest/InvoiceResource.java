package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.InvoiceDTO;
import com.kartike.my_gate.model.PaymentReconcileDTO;
import com.kartike.my_gate.service.implementations.InvoiceServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
public class InvoiceResource {

    private final InvoiceServiceImpl invoiceService;

    public InvoiceResource(final InvoiceServiceImpl invoiceService) {
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

    @PostMapping("/reconcile")
    public ResponseEntity<Void> reconcile(@RequestBody @Valid final PaymentReconcileDTO paymentReconcileDTO) {
        invoiceService.reconcile(paymentReconcileDTO);
        return ResponseEntity.noContent().build();
    }

}
