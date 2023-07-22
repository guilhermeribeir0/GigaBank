package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {

    List<Extrato> findByContaBancaria(ContaBancaria contaBancaria);
}
