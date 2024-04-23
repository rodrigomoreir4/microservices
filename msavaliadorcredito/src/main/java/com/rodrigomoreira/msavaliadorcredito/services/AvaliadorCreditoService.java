package com.rodrigomoreira.msavaliadorcredito.services;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rodrigomoreira.msavaliadorcredito.clientes.CartoesResourceClient;
import com.rodrigomoreira.msavaliadorcredito.clientes.ClienteResourceClient;
import com.rodrigomoreira.msavaliadorcredito.domain.CartaoCliente;
import com.rodrigomoreira.msavaliadorcredito.domain.DadosCliente;
import com.rodrigomoreira.msavaliadorcredito.domain.SituacaoCliente;
import com.rodrigomoreira.msavaliadorcredito.exceptions.DadosClienteNotFoundException;
import com.rodrigomoreira.msavaliadorcredito.exceptions.ErroComunicacaoMicroserviceException;

import feign.FeignException;

@Service
public class AvaliadorCreditoService {
    
    private ClienteResourceClient clientesClient;
    private CartoesResourceClient cartoesClient;

    public AvaliadorCreditoService(ClienteResourceClient clientesClient, CartoesResourceClient cartoesClient) {
        this.clientesClient = clientesClient;
        this.cartoesClient = cartoesClient;
    }

    public SituacaoCliente obterSituacaoCliente(String cpf) 
    throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException{
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.SC_NOT_FOUND == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
        }
    }
}
