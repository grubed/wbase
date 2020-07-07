package com.mrwind.windbase.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MultiIOSource {

    String OUTPUT1 = "output1";
    String INPUT1 = "input1";
    String OUTPUT2 = "output2";

    @Input(INPUT1)
    SubscribableChannel input1();

    @Output(OUTPUT1)
    MessageChannel output1();

    @Output(OUTPUT2)
    MessageChannel output2();
}
