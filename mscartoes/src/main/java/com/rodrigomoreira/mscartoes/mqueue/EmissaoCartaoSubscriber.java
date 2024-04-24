package com.rodrigomoreira.mscartoes.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmissaoCartaoSubscriber {
    
    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolictacaoEmissao(@Payload String payload){
        System.out.println(payload);
    }
}