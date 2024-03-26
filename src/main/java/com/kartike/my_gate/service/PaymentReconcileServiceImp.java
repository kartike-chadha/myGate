package com.kartike.my_gate.service;
import com.kartike.my_gate.PaymentReconcileDTO;
import com.kartike.my_gate.PaymentReconcileServiceGrpc;
import com.kartike.my_gate.domain.Invoice;
import com.kartike.my_gate.domain.Payable;
import com.kartike.my_gate.model.InvoiceDTO;
import com.kartike.my_gate.repos.InvoiceRepository;
import com.kartike.my_gate.repos.PayableRepository;
import lombok.RequiredArgsConstructor;
import com.google.protobuf.Empty;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.OffsetDateTime;

@GrpcService
@RequiredArgsConstructor
public class PaymentReconcileServiceImp extends PaymentReconcileServiceGrpc.PaymentReconcileServiceImplBase{

    private final InvoiceRepository invoiceRepository;
    private final PayableRepository payableRepository;

    private final InvoiceService invoiceService;
    @Override
    public void reconcile(PaymentReconcileDTO paymentReconcileDTO, io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver){
        invoiceRepository.findAllByDatePayableAfter(OffsetDateTime.parse(paymentReconcileDTO.getPayableAfter())).forEach(
                invoice -> {
                    Integer totalPayment = payableRepository.findByDateCreatedAfterAndInvoice(invoice.getDateCreated(),invoice)
                            .stream()
                            .mapToInt(Payable::getAmountPaid)
                            .sum();
                    if(totalPayment<paymentReconcileDTO.getInvoiceAmount()){
                        Integer remainingAmount = paymentReconcileDTO.getInvoiceAmount()-totalPayment;
                        Integer newAmountWithPenalty = remainingAmount + paymentReconcileDTO.getInvoiceAmount()+paymentReconcileDTO.getPenalty();
                        InvoiceDTO newInvoiceDTO = new InvoiceDTO();
                        newInvoiceDTO.setAmount(newAmountWithPenalty);
                        newInvoiceDTO.setOwnerId(invoice.getOwner().getId());
                        final Invoice newInvoice = new Invoice();
                        invoiceService.mapToEntity(newInvoiceDTO,newInvoice);
                        invoiceRepository.save(newInvoice);
                    }
                }
        );
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
