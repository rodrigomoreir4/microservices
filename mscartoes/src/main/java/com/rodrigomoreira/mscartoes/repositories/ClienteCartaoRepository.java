package com.rodrigomoreira.mscartoes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomoreira.mscartoes.domain.ClienteCartao;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {
    List<ClienteCartao> findByCpf(String cpf);
}
