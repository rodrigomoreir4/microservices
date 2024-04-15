package com.rodrigomoreira.msclientes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomoreira.msclientes.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    Optional<Cliente> findById(String cpf);
    
}
