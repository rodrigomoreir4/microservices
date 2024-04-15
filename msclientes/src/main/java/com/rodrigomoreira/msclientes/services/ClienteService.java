package com.rodrigomoreira.msclientes.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rodrigomoreira.msclientes.domain.Cliente;
import com.rodrigomoreira.msclientes.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    
    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Cliente save(Cliente cliente){
        return repository.save(cliente);
    }
    
    public Optional<Cliente> getByCPF(String cpf){
        return repository.findByCpf(cpf);
    }
}
