package com.kartike.my_gate.service.grpc;

import com.kartike.my_gate.Suspect;
import com.kartike.my_gate.SuspectServiceGrpc;
import com.kartike.my_gate.SuspectsList;
import com.kartike.my_gate.TimeRange;
import com.kartike.my_gate.enums.UserTypeEnum;
import com.kartike.my_gate.repos.GateLogRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.repos.VendorRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.OffsetDateTime;

@GrpcService
@AllArgsConstructor
public class SuspectServiceImpl extends SuspectServiceGrpc.SuspectServiceImplBase {

    final GateLogRepository gateLogRepository;
    final OwnerRepository ownerRepository;
    final VendorRepository vendorRepository;
    @Override
    public void getSuspectList(TimeRange request, StreamObserver<SuspectsList> responseObserver) {
        SuspectsList.Builder suspectBuilder = SuspectsList.newBuilder();

        gateLogRepository.findAllByLogTimeBetween(OffsetDateTime.parse(request.getStartDateTime()),OffsetDateTime.parse(request.getEndDateTime()))
                .stream()
                .forEach(suspectDetail -> {
                    if(suspectDetail.getSuspectType() == UserTypeEnum.OWNER.toString()){
                        Suspect suspect = Suspect.newBuilder()
                                .setUserName(ownerRepository.findById(suspectDetail
                                        .getSuspectId()).orElseThrow(()-> new RuntimeException("Owner Not found")).getName())
                                .setUserId(suspectDetail
                                        .getSuspectId()
                                        .toString())
                                .build();
                        suspectBuilder.addSuspectList(suspect);


                    }else{
                        Suspect suspect = Suspect.newBuilder().setUserName(vendorRepository.findById(suspectDetail
                                        .getSuspectId())
                                .orElseThrow(()-> new RuntimeException("Owner Not found")).
                                getVendorName())
                                .setUserId(suspectDetail
                                        .getSuspectId()
                                        .toString()).build();
                        suspectBuilder.addSuspectList(suspect);
                    }


                });
        responseObserver.onNext(suspectBuilder.build());
        responseObserver.onCompleted();
    }
}
