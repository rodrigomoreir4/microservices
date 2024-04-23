package com.rodrigomoreira.msavaliadorcredito.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rodrigomoreira.msavaliadorcredito.clientes.CartoesResourceClient;
import com.rodrigomoreira.msavaliadorcredito.clientes.ClienteResourceClient;
import com.rodrigomoreira.msavaliadorcredito.domain.Cartao;
import com.rodrigomoreira.msavaliadorcredito.domain.CartaoAprovado;
import com.rodrigomoreira.msavaliadorcredito.domain.CartaoCliente;
import com.rodrigomoreira.msavaliadorcredito.domain.DadosCliente;
import com.rodrigomoreira.msavaliadorcredito.domain.RetornoAvaliacaoCliente;
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

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) 
    throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException{
        try{
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.SC_NOT_FOUND == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
        }
    }
}
