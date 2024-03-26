package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.Invoice;
import com.kartike.my_gate.domain.Payable;
import com.kartike.my_gate.model.InvoiceDTO;
import com.kartike.my_gate.model.PaymentReconcileDTO;
import com.kartike.my_gate.repos.InvoiceRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.repos.PayableRepository;
import com.kartike.my_gate.util.NotFoundException;
import com.kartike.my_gate.util.ReferencedWarning;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{

    private final InvoiceRepository invoiceRepository;
    private final OwnerRepository ownerRepository;
    private final PayableRepository payableRepository;

    @Override
    public List<InvoiceDTO> findAll() {
        final List<Invoice> invoices = invoiceRepository.findAll(Sort.by("invoiceId"));
        return invoices.stream()
                .map(invoice -> mapToDTO(invoice, new InvoiceDTO()))
                .toList();
    }
    @Override
    public InvoiceDTO get(final Integer invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .map(invoice -> mapToDTO(invoice, new InvoiceDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Override
    public Integer create(final InvoiceDTO invoiceDTO) {
        final Invoice invoice = new Invoice();
        mapToEntity(invoiceDTO, invoice);
        return invoiceRepository.save(invoice).getInvoiceId();
    }

    public void reconcile(PaymentReconcileDTO paymentReconcileDTO){
        invoiceRepository.findAllByDatePayableAfter(paymentReconcileDTO.getPayableAfter()).forEach(
                invoice -> {
                    Integer totalPayment = payableRepository.findByDateCreatedAfterAndInvoice(invoice.getDateCreated(),invoice)
                            .stream()
                            .mapToInt(Payable::getAmountPaid)
                            .sum();
                    if(totalPayment<paymentReconcileDTO.getInvoiceAmount()){
                        Integer remainingAmount = paymentReconcileDTO.getInvoiceAmount()-totalPayment;
                        Integer newAmountWithPenalty = remainingAmount+paymentReconcileDTO.getInvoiceAmount()+paymentReconcileDTO.getPenalty();
                        InvoiceDTO newInvoiceDTO = new InvoiceDTO();
                        newInvoiceDTO.setAmount(newAmountWithPenalty);
                        newInvoiceDTO.setOwnerId(invoice.getOwner().getId());
                        final Invoice newInvoice = new Invoice();
                        mapToEntity(newInvoiceDTO,newInvoice);
                        invoiceRepository.save(newInvoice);
                    }
                }
        );
    }
    @Override
    public void update(final Integer invoiceId, final InvoiceDTO invoiceDTO) {
        final Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(invoiceDTO, invoice);
        invoiceRepository.save(invoice);
    }
    @Override
    public void delete(final Integer invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }

    @Override
    public InvoiceDTO mapToDTO(final Invoice invoice, final InvoiceDTO invoiceDTO) {
        invoiceDTO.setInvoiceId(invoice.getInvoiceId());
        invoiceDTO.setAmount(invoice.getAmount());
        invoiceDTO.setDateCreated(invoice.getDateCreated());
        invoiceDTO.setDatePayable(invoice.getDatePayable());
        invoiceDTO.setOwnerId(invoice.getOwner().getId());
        return invoiceDTO;
    }

    @Override
    public Invoice mapToEntity(final InvoiceDTO invoiceDTO, final Invoice invoice) {
        invoice.setAmount(invoiceDTO.getAmount());
        invoice.setDateCreated(OffsetDateTime.now());
        invoice.setDatePayable(OffsetDateTime.now().plusDays(30));
        invoice.setInvoiceId(invoiceDTO.getInvoiceId());
        invoice.setOwner(ownerRepository.findById(invoiceDTO.getOwnerId())
                .orElseThrow(()->new RuntimeException("Owner not found")));
        return invoice;
    }

    public ReferencedWarning getReferencedWarning(final Integer invoiceId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(NotFoundException::new);
        final Payable invoicePayable = payableRepository.findFirstByInvoice(invoice);
        if (invoicePayable != null) {
            referencedWarning.setKey("invoice.payable.invoice.referenced");
            referencedWarning.addParam(invoicePayable.getPaymentId());
            return referencedWarning;
        }
        return null;
    }

}
