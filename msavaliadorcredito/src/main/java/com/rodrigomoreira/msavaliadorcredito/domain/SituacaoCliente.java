package com.rodrigomoreira.msavaliadorcredito.domain;

import java.util.List;

import lombok.Data;

@Data
public class SituacaoCliente {
    
    private DadosCliente client;
    private List<CartaoCliente> cartoes;
    
}
