package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.Invoice;
import com.kartike.my_gate.domain.Payable;
import com.kartike.my_gate.model.InvoiceDTO;
import com.kartike.my_gate.repos.InvoiceRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.repos.PayableRepository;
import com.kartike.my_gate.util.NotFoundException;
import com.kartike.my_gate.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InvoiceServiceImpl implements InvoiceService{

    private final InvoiceRepository invoiceRepository;
    private final OwnerRepository ownerRepository;
    private final PayableRepository payableRepository;

    public InvoiceServiceImpl(final InvoiceRepository invoiceRepository,
                              final OwnerRepository ownerRepository, final PayableRepository payableRepository) {
        this.invoiceRepository = invoiceRepository;
        this.ownerRepository = ownerRepository;
        this.payableRepository = payableRepository;
    }
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

    private InvoiceDTO mapToDTO(final Invoice invoice, final InvoiceDTO invoiceDTO) {
        invoiceDTO.setInvoiceId(invoice.getInvoiceId());
        invoiceDTO.setAmount(invoice.getAmount());
        invoiceDTO.setDateCreated(invoice.getDateCreated());
        invoiceDTO.setDatePayable(invoice.getDatePayable());
        return invoiceDTO;
    }

    private Invoice mapToEntity(final InvoiceDTO invoiceDTO, final Invoice invoice) {
        invoice.setAmount(invoiceDTO.getAmount());
        invoice.setDateCreated(invoiceDTO.getDateCreated());
        invoice.setDatePayable(invoiceDTO.getDatePayable());
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
