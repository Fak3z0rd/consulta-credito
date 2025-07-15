package desafio.credit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import desafio.credit.domain.model.Credito;

import java.util.List;
import java.util.Optional;

public interface CreditoRepository extends JpaRepository<Credito, Long> {
    Optional<Credito> findByNumeroCredito(String numeroCredito);
    List<Credito> findByNumeroNfse(String numeroNfse);
}
