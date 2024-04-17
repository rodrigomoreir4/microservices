package com.rodrigomoreira.mscartoes.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartaoDTO {
    
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao cartao(){
        return new Cartao(nome, bandeira, renda, limite);
    }
}
