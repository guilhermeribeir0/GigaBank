package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    Optional<Cartao> findByNumero(String numero);

    Optional<Cartao> findByCvv(String cvv);

    Optional<Cartao> findByNumeroAndCvvAndDataVencimento(String numero, String cvv, LocalDate dataVencimento);

    Optional<Cartao> findByContaBancariaId(Long idConta);
}
