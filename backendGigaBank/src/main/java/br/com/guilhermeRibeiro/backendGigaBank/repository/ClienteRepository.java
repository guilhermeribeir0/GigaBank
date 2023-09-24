package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    public Cliente findByCpf(String cpf);

    @Query(value = "SELECT * FROM t_clientes WHERE UPPER(t_clientes.nome) LIKE CONCAT('%',UPPER(:nome),'%')", nativeQuery = true)
    public List<Cliente> findByNome(@Param("nome") String nome);
}
