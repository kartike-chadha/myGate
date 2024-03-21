package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.Payable;
import com.kartike.my_gate.model.PayableDTO;
import com.kartike.my_gate.repos.InvoiceRepository;
import com.kartike.my_gate.repos.PayableRepository;
import com.kartike.my_gate.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PayableServiceImpl implements PayableService{

    private final PayableRepository payableRepository;
    private final InvoiceRepository invoiceRepository;

    public PayableServiceImpl(final PayableRepository payableRepository,
                              final InvoiceRepository invoiceRepository) {
        this.payableRepository = payableRepository;
        this.invoiceRepository = invoiceRepository;
    }
    @Override
    public List<PayableDTO> findAll() {
        final List<Payable> payables = payableRepository.findAll(Sort.by("paymentId"));
        return payables.stream()
                .map(payable -> mapToDTO(payable, new PayableDTO()))
                .toList();
    }
    @Override
    public PayableDTO get(final Integer paymentId) {
        return payableRepository.findById(paymentId)
                .map(payable -> mapToDTO(payable, new PayableDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Override
    public Integer create(final PayableDTO payableDTO) {
        final Payable payable = new Payable();
        mapToEntity(payableDTO, payable);
        return payableRepository.save(payable).getPaymentId();
    }
    @Override
    public void update(final Integer paymentId, final PayableDTO payableDTO) {
        final Payable payable = payableRepository.findById(paymentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(payableDTO, payable);
        payableRepository.save(payable);
    }
    @Override
    public void delete(final Integer paymentId) {
        payableRepository.deleteById(paymentId);
    }

    private PayableDTO mapToDTO(final Payable payable, final PayableDTO payableDTO) {
        payableDTO.setPaymentId(payable.getPaymentId());
        payableDTO.setAmountPaid(payable.getAmountPaid());
        return payableDTO;
    }

    private Payable mapToEntity(final PayableDTO payableDTO, final Payable payable) {
        payable.setAmountPaid(payableDTO.getAmountPaid());
        return payable;
    }

}
