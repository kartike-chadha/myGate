package com.kartike.my_gate.service.grpc;

import com.google.protobuf.Empty;
import com.kartike.my_gate.MostActiveUser;
import com.kartike.my_gate.MostActiveUserServiceGrpc;
import com.kartike.my_gate.MostActiveUsersList;
import com.kartike.my_gate.repos.GateLogRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class MostActiveUserServiceImpl extends MostActiveUserServiceGrpc.MostActiveUserServiceImplBase {
    final GateLogRepository gateLogRepository;

    @Override
    public void getMostActiveUsers(Empty request, StreamObserver<MostActiveUsersList> responseObserver) {
        //implement getmostactive users and handle building the list
        MostActiveUsersList.Builder mostActiveUsersListBuilder = MostActiveUsersList.newBuilder();
        gateLogRepository.findMostActive().forEach(mostActiveUserDetails -> {
            mostActiveUsersListBuilder.addMostActiveUserList(MostActiveUser.newBuilder()
                    .setUserId(mostActiveUserDetails.getMostActiveUserId().toString())
                    .setNumberOfActivities(mostActiveUserDetails.getNumberOfLogs())).build();
        });
        responseObserver.onNext(mostActiveUsersListBuilder.build());
        responseObserver.onCompleted();

    }
}
