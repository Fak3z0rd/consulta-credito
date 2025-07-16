package desafio.credit.domain.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import desafio.credit.config.KafkaProducer;
import desafio.credit.domain.service.CreditoService;
import desafio.credit.domain.model.Credito;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/creditos")
@CrossOrigin(origins = "http://localhost:4200")
public class CreditoController {

    private final CreditoService creditoService;

    @Autowired
    private KafkaProducer kafkaProducer;

    public CreditoController(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    @GetMapping("/{nfse}")
    public ResponseEntity<List<Credito>> buscarPorNfse(@PathVariable String nfse) {
        kafkaProducer.sendMessage("consulta-nfse", nfse);
        List<Credito> creditos = creditoService.buscarPorNfse(nfse);
        return ResponseEntity.ok(creditos);
    }

    @GetMapping("/numero/{numeroCredito}")
    public ResponseEntity<Credito> buscarPorNumeroCredito(@PathVariable String numeroCredito) {
        kafkaProducer.sendMessage("consulta-numero-credito", numeroCredito);
        Optional<Credito> credito = creditoService.buscarPorNumeroCredito(numeroCredito);
        if (credito.isPresent()) {
            return ResponseEntity.ok(credito.get());
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
