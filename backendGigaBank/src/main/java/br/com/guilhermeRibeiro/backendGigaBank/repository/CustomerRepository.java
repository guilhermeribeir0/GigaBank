package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCpf(String cpf);

    @Query(value = "SELECT * FROM t_customer WHERE UPPER(t_customer.nome) LIKE CONCAT('%',UPPER(:nome),'%')", nativeQuery = true)
    List<Customer> findByName(@Param("name") String name);
}
