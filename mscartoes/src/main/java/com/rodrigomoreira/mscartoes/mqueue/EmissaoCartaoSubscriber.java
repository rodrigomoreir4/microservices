package com.rodrigomoreira.mscartoes.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigomoreira.mscartoes.domain.Cartao;
import com.rodrigomoreira.mscartoes.domain.ClienteCartao;
import com.rodrigomoreira.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import com.rodrigomoreira.mscartoes.repositories.CartaoRepository;
import com.rodrigomoreira.mscartoes.repositories.ClienteCartaoRepository;

@Component
public class EmissaoCartaoSubscriber {

    private CartaoRepository cartaoRepository;
    private ClienteCartaoRepository clienteCartaoRepository;
    
    public EmissaoCartaoSubscriber(CartaoRepository cartaoRepository, ClienteCartaoRepository clienteCartaoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.clienteCartaoRepository = clienteCartaoRepository;
    }

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolictacaoEmissao(@Payload String payload){
        var mapper = new ObjectMapper();
        try {
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
            
            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());
        
            clienteCartaoRepository.save(clienteCartao);
        
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
