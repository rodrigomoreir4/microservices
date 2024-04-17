package com.rodrigomoreira.mscartoes.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigomoreira.mscartoes.domain.Cartao;
import com.rodrigomoreira.mscartoes.domain.CartaoDTO;
import com.rodrigomoreira.mscartoes.domain.CartoesPorCliente;
import com.rodrigomoreira.mscartoes.domain.ClienteCartao;
import com.rodrigomoreira.mscartoes.services.CartaoService;
import com.rodrigomoreira.mscartoes.services.ClienteCartaoService;

@RestController
@RequestMapping("cartoes")
public class CartoesResource {

    private CartaoService cartaoService;
    private ClienteCartaoService clienteCartaoService;

    public CartoesResource(CartaoService cartaoService, ClienteCartaoService clienteCartaoService) {
        this.cartaoService = cartaoService;
        this.clienteCartaoService = clienteCartaoService;
    }

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity<CartaoDTO> cadastra(@RequestBody CartaoDTO request){
        Cartao cartao = request.cartao();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    } 

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf){
        List<ClienteCartao> lista = clienteCartaoService.listCartoesByCpf(cpf);
        List<CartoesPorCliente> resultList = lista.stream()
            .map(CartoesPorCliente::fromModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }
}
