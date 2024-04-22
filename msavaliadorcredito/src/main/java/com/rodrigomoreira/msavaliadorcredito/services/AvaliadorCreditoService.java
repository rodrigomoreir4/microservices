package com.rodrigomoreira.msavaliadorcredito.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rodrigomoreira.msavaliadorcredito.clientes.ClienteResourceClient;
import com.rodrigomoreira.msavaliadorcredito.domain.DadosCliente;
import com.rodrigomoreira.msavaliadorcredito.domain.SituacaoCliente;

@Service
public class AvaliadorCreditoService {
    
    private ClienteResourceClient clientesClient;

    public AvaliadorCreditoService(ClienteResourceClient clientesClient) {
        this.clientesClient = clientesClient;
    }

    public SituacaoCliente obterSituacaoCliente(String cpf){

        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);

        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .build();
    }
}
