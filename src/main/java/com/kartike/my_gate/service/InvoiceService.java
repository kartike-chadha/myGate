package com.kartike.my_gate.service;

import com.kartike.my_gate.model.InvoiceDTO;

import java.util.List;

public interface InvoiceService {
    public List<InvoiceDTO> findAll();
    public InvoiceDTO get(Integer invoiceId);
    public Integer create(InvoiceDTO invoiceDTO);
    public void update(Integer invoiceId, InvoiceDTO invoiceDTO);
    public void delete(Integer invoiceId);
}