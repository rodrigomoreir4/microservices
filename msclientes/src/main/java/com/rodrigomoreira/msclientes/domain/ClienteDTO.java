package com.rodrigomoreira.msclientes.domain;

import lombok.Data;

@Data
public class ClienteDTO {
    
    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente cliente(){
        return new Cliente(cpf, nome, idade);
    }
}
