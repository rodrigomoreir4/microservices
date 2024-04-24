package com.rodrigomoreira.msavaliadorcredito.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigomoreira.msavaliadorcredito.domain.DadosAvaliacao;
import com.rodrigomoreira.msavaliadorcredito.domain.DadosSolicitacaoEmissaoCartao;
import com.rodrigomoreira.msavaliadorcredito.domain.ProtocoloSolicitacaoCartao;
import com.rodrigomoreira.msavaliadorcredito.domain.RetornoAvaliacaoCliente;
import com.rodrigomoreira.msavaliadorcredito.domain.SituacaoCliente;
import com.rodrigomoreira.msavaliadorcredito.exceptions.DadosClienteNotFoundException;
import com.rodrigomoreira.msavaliadorcredito.exceptions.ErroComunicacaoMicroserviceException;
import com.rodrigomoreira.msavaliadorcredito.exceptions.ErroSolicitacaoCartaoException;
import com.rodrigomoreira.msavaliadorcredito.services.AvaliadorCreditoService;

@RestController
@RequestMapping("avaliacoes-credito")
public class AvaliadorCreditoController {

    private AvaliadorCreditoService avaliadorCreditoService;

    public AvaliadorCreditoController(AvaliadorCreditoService avaliadorCreditoService) {
        this.avaliadorCreditoService = avaliadorCreditoService;
    }

    @GetMapping
    public String status(){
        return "ok";
    }
    
    @SuppressWarnings("rawtypes")
    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultarSituacaoCliente(@RequestParam("cpf") String cpf){        
        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e ){
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroserviceException e){
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados){
        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFoundException e ){
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroserviceException e){
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
        
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados){
        try {
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService.solicitarEmissaoCartao(dados);
            return ResponseEntity.ok(protocoloSolicitacaoCartao);
        } catch (ErroSolicitacaoCartaoException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
