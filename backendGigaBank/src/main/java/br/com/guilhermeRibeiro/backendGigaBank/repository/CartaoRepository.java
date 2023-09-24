package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    public Cartao findByNumero(String numero);

    public Cartao findByCvv(String cvv);

    public Cartao findByNumeroAndCvvAndDataVencimento(String numero, String cvv, LocalDate dataVencimento);

    public Cartao findByContaBancariaId(Long idConta);
}
