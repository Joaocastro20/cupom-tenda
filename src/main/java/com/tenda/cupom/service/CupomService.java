package com.tenda.cupom.service;

import com.tenda.cupom.model.Cupom;
import com.tenda.cupom.repository.CupomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CupomService {

    @Autowired
    private CupomRepository repository;

    public Cupom salvar(Cupom cupom){
        if (repository.existsByCode(cupom.getCode())) {
            throw new IllegalArgumentException("Já existe cupom com esse código");
        }
        return repository.save(cupom);
    }

    public Optional<Cupom> buscarCode(String code){
        return repository.findByCode(code);
    }

    @Transactional
    public void deletarId(String code) {
        if (!repository.existsByCode(code)) {
            throw new IllegalArgumentException("Nao existe um cupom com esse code");
        }
        repository.deleteByCode(code);
    }
}
