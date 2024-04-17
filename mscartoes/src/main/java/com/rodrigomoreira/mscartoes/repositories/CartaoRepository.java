package com.rodrigomoreira.mscartoes.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomoreira.mscartoes.domain.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{
    
    List<Cartao> findByRendaLessThanEqual(BigDecimal renda);
}
