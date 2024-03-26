package com.kartike.my_gate.config;

import com.kartike.my_gate.PaymentReconcileServiceGrpc;
import com.kartike.my_gate.repos.InvoiceRepository;
import com.kartike.my_gate.repos.PayableRepository;
import com.kartike.my_gate.service.InvoiceService;
import com.kartike.my_gate.service.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

//@Configuration
//public class grpcConfig {
//
//
//    @Bean
//    @DependsOn()
//    public PaymentReconcileServiceGrpc getPayment(){
//        var invoiceService = new InvoiceServiceImpl();
//        return new PaymentReconcileServiceGrpc(invoiceRepository,payableRepository,invoiceService);
//    }
//}
