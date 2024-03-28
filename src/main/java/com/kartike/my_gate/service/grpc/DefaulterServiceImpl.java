package com.kartike.my_gate.service.grpc;

import com.google.protobuf.Empty;
import com.kartike.my_gate.DefaulterDetail;
import com.kartike.my_gate.DefaulterServiceGrpc;
import com.kartike.my_gate.Defaulters;
import com.kartike.my_gate.repos.InvoiceRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
@AllArgsConstructor
public class DefaulterServiceImpl extends DefaulterServiceGrpc.DefaulterServiceImplBase {
    final InvoiceRepository invoiceRepository;
    final OwnerRepository ownerRepository;
    @Override
    public void getOwnerWithMostDefaults(Empty request, StreamObserver<Defaulters> responseObserver) {
        Defaulters.Builder defaultersBuilder = Defaulters.newBuilder();
        invoiceRepository.findMostDefaults(1000).forEach(defaulterDetail -> {
            defaultersBuilder.addDefaulterList(DefaulterDetail
                    .newBuilder()
                    .setOwnerId(defaulterDetail.getDefaulterId().toString())
                    .setNumberOfDefaults(defaulterDetail.getNumberOfDefaults())
                    .setOwnerName(ownerRepository
                            .findById(defaulterDetail.getDefaulterId())
                            .orElseThrow(()-> new RuntimeException("Owner Not Found"))
                            .getName()).build());
                }
        );
        responseObserver.onNext(defaultersBuilder.build());
        responseObserver.onCompleted();
    }


}
