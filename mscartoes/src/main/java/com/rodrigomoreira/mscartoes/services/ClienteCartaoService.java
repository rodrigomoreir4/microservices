package com.rodrigomoreira.mscartoes.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rodrigomoreira.mscartoes.domain.ClienteCartao;
import com.rodrigomoreira.mscartoes.repositories.ClienteCartaoRepository;

@Service
public class ClienteCartaoService {
    
    private ClienteCartaoRepository repository;

    public ClienteCartaoService(ClienteCartaoRepository repository) {
        this.repository = repository;
    }

    public List<ClienteCartao> listCartoesByCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}
