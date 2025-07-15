package desafio.credit.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "credito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_credito")
    private String numeroCredito;
    
    @Column(name = "numero_nfse")
    private String numeroNfse;
    
    @Column(name = "data_constituicao")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataConstituicao;
    
    @Column(name = "valor_issqn")
    private BigDecimal valorIssqn;
    
    @Column(name = "tipo_credito")
    private String tipoCredito;
    
    @Column(name = "simples_nacional")
    private boolean simplesNacional;
    
    @Column(name = "aliquota")
    private BigDecimal aliquota;
    
    @Column(name = "valor_faturado")
    private BigDecimal valorFaturado;
    
    @Column(name = "valor_deducao")
    private BigDecimal valorDeducao;
    
    @Column(name = "base_calculo")
    private BigDecimal baseCalculo;
}
