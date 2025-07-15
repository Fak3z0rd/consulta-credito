package desafio.credit.domain.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import desafio.credit.domain.repository.CreditoRepository;
import desafio.credit.domain.model.Credito;

import java.util.List;
import java.util.Optional;

@Service
public class CreditoService {
    private static final Logger logger = LoggerFactory.getLogger(CreditoService.class);
    private final CreditoRepository creditoRepository;

    public CreditoService(CreditoRepository creditoRepository) {
        this.creditoRepository = creditoRepository;
    }

    public List<Credito> buscarPorNfse(String nfse) {
        logger.info("Buscando créditos por NFSE: {}", nfse);
        List<Credito> resultado = creditoRepository.findByNumeroNfse(nfse);
        logger.info("Resultado da busca por NFSE: {} créditos encontrados", resultado.size());
        return resultado;
    }

    public Optional<Credito> buscarPorNumeroCredito(String numeroCredito) {
        logger.info("Buscando crédito por número: {}", numeroCredito);
        Optional<Credito> resultado = creditoRepository.findByNumeroCredito(numeroCredito);
        logger.info("Resultado da busca por número: {}", resultado.isPresent() ? "Encontrado" : "Não encontrado");
        return resultado;
    }

}
