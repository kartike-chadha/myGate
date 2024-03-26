package com.kartike.my_gate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MyGateApplication {

    public static void main(final String[] args) {

        var temp = SpringApplication.run(MyGateApplication.class, args);
//        temp.getBean("PaymentReconcileServiceImp");
    }

}
