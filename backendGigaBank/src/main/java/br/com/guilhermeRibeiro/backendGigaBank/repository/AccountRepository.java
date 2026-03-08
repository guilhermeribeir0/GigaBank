package br.com.guilhermeRibeiro.backendGigaBank.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAgencyAndNumber(String agency, String number);

    Optional<Account> findByCustomerCpf(String cpf);

}
