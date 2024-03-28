package com.kartike.my_gate.service.interfaces;

import com.kartike.my_gate.model.PayableDTO;

import java.util.List;

public interface PayableService {
    public List<PayableDTO> findAll();

    public PayableDTO get(Integer paymentId);
    public Integer create (PayableDTO payableDTO);
    public void update (Integer paymentId, PayableDTO payableDTO);
    public void delete(Integer paymentId);
}
