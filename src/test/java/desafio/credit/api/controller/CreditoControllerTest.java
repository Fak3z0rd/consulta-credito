package desafio.credit.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import desafio.credit.domain.controller.CreditoController;
import desafio.credit.domain.service.CreditoService;
import desafio.credit.domain.model.Credito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CreditoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreditoService creditoService;

    @InjectMocks
    private CreditoController creditoController;

    private Credito mockCredito;
    private Credito mockCredito2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(creditoController).build();
        
        mockCredito = new Credito(1L, "123456", "7891011", LocalDate.parse("2024-02-25"), 
                                 new BigDecimal("1500.75"), "ISSQN", true, new BigDecimal("5.0"), 
                                 new BigDecimal("30000.00"), new BigDecimal("5000.00"), 
                                 new BigDecimal("25000.00"));
        
        mockCredito2 = new Credito(2L, "123456", "7891012", LocalDate.parse("2024-02-26"), 
                                  new BigDecimal("2000.50"), "ISSQN", true, new BigDecimal("5.0"), 
                                  new BigDecimal("40000.00"), new BigDecimal("6000.00"), 
                                  new BigDecimal("34000.00"));
    }

    @Test
    void testBuscarPorNfse_QuandoEncontrado() throws Exception {
        when(creditoService.buscarPorNfse("7891011")).thenReturn(Optional.of(mockCredito));

        mockMvc.perform(get("/creditos/nfse/7891011"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.numeroCredito").value("123456"))
               .andExpect(jsonPath("$.numeroNfse").value("7891011"))
               .andExpect(jsonPath("$.dataConstituicao").value("2024-02-25"))
               .andExpect(jsonPath("$.valorIssqn").value(1500.75))
               .andExpect(jsonPath("$.tipoCredito").value("ISSQN"))
               .andExpect(jsonPath("$.simplesNacional").value(true))
               .andExpect(jsonPath("$.aliquota").value(5.0))
               .andExpect(jsonPath("$.valorFaturado").value(30000.00))
               .andExpect(jsonPath("$.valorDeducao").value(5000.00))
               .andExpect(jsonPath("$.baseCalculo").value(25000.00));

        verify(creditoService, times(1)).buscarPorNfse("7891011");
    }

    @Test
    void testBuscarPorNfse_QuandoNaoEncontrado() throws Exception {
        when(creditoService.buscarPorNfse("999999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/creditos/nfse/999999"))
               .andExpect(status().isNotFound());

        verify(creditoService, times(1)).buscarPorNfse("999999");
    }

    @Test
    void testBuscarPorNfse_ComParametroVazio() throws Exception {
        when(creditoService.buscarPorNfse(" ")).thenReturn(Optional.empty());

        mockMvc.perform(get("/creditos/nfse/ "))
               .andExpect(status().isNotFound());

        verify(creditoService, times(1)).buscarPorNfse(" ");
    }

    @Test
    void testBuscarPorNumeroCredito_QuandoEncontrado() throws Exception {
        when(creditoService.buscarPorNumeroCredito("123456")).thenReturn(List.of(mockCredito));

        mockMvc.perform(get("/creditos/numero/123456"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].numeroCredito").value("123456"))
               .andExpect(jsonPath("$[0].numeroNfse").value("7891011"))
               .andExpect(jsonPath("$[0].valorIssqn").value(1500.75))
               .andExpect(jsonPath("$[0].tipoCredito").value("ISSQN"))
               .andExpect(jsonPath("$[0].simplesNacional").value(true));

        verify(creditoService, times(1)).buscarPorNumeroCredito("123456");
    }

    @Test
    void testBuscarPorNumeroCredito_QuandoEncontraMultiplosCreditos() throws Exception {
        when(creditoService.buscarPorNumeroCredito("123456")).thenReturn(List.of(mockCredito, mockCredito2));

        mockMvc.perform(get("/creditos/numero/123456"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].numeroNfse").value("7891011"))
               .andExpect(jsonPath("$[1].id").value(2))
               .andExpect(jsonPath("$[1].numeroNfse").value("7891012"))
               .andExpect(jsonPath("$[1].valorIssqn").value(2000.50));

        verify(creditoService, times(1)).buscarPorNumeroCredito("123456");
    }

    @Test
    void testBuscarPorNumeroCredito_QuandoNaoEncontrado() throws Exception {
        when(creditoService.buscarPorNumeroCredito("999999")).thenReturn(List.of());

        mockMvc.perform(get("/creditos/numero/999999"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$").isEmpty());

        verify(creditoService, times(1)).buscarPorNumeroCredito("999999");
    }

    @Test
    void testBuscarPorNumeroCredito_ComParametroVazio() throws Exception {
        when(creditoService.buscarPorNumeroCredito(" ")).thenReturn(List.of());

        mockMvc.perform(get("/creditos/numero/ "))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$").isEmpty());

        verify(creditoService, times(1)).buscarPorNumeroCredito(" ");
    }

    @Test
    void testEndpointNaoExiste() throws Exception {
        mockMvc.perform(get("/creditos/invalid/endpoint"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testMetodoNaoSuportado() throws Exception {
        mockMvc.perform(post("/creditos/nfse/7891011"))
               .andExpect(status().isMethodNotAllowed());
    }
}
