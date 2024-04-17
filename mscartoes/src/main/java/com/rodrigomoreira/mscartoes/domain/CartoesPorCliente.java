package com.rodrigomoreira.mscartoes.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorCliente {
    
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorCliente fromModel(ClienteCartao model){
        return new CartoesPorCliente(
            model.getCartao().getNome(),
            model.getCartao().getBandeira().toString(),
            model.getLimite()
        );
    }
}
