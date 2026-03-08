package br.com.guilhermeRibeiro.backendGigaBank.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByNumber(String number);

    Optional<Card> findByCvv(String cvv);

    Optional<Card> findByNumberAndCvvAndExpirationDate(String number, String cvv, LocalDate expirationDate);

    Optional<Card> findByAccountId(Long idAccount);
}
