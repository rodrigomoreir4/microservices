package com.rodrigomoreira.mscartoes.controllers;

import java.util.List;

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
import com.rodrigomoreira.mscartoes.services.CartaoService;

@RestController
@RequestMapping("cartoes")
public class CartoesResource {

    private CartaoService service;

    public CartoesResource(CartaoService service) {
        this.service = service;
    }

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity<CartaoDTO> cadastra(@RequestBody CartaoDTO request){
        Cartao cartao = request.cartao();
        service.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    } 

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
        List<Cartao> list = service.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }
}
