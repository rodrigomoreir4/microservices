package com.rodrigomoreira.msavaliadorcredito.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigomoreira.msavaliadorcredito.domain.SituacaoCliente;
import com.rodrigomoreira.msavaliadorcredito.exceptions.DadosClienteNotFoundException;
import com.rodrigomoreira.msavaliadorcredito.exceptions.ErroComunicacaoMicroserviceException;
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

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf){        
        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e ){
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroserviceException e){
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

}
