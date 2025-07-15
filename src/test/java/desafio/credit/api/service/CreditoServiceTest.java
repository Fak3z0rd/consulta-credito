package desafio.credit.api.service;

import desafio.credit.domain.model.Credito;
import desafio.credit.domain.service.CreditoService;
import desafio.credit.domain.repository.CreditoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreditoServiceTest {

    @InjectMocks
    private CreditoService creditoService;

    @Mock
    private CreditoRepository creditoRepository;

    private Credito mockCredito;

    @BeforeEach
    void setUp() {
        mockCredito = new Credito(1L, "123456", "7891011", LocalDate.parse("2024-02-25"), new BigDecimal("1500.75"), "ISSQN", true, new BigDecimal("5.0"), new BigDecimal("30000.00"), new BigDecimal("5000.00"), new BigDecimal("25000.00"));
    }

    @Test
    void testBuscarPorNfse() {
        when(creditoRepository.findByNumeroNfse("7891011")).thenReturn(Optional.of(mockCredito));
        
        Optional<Credito> result = creditoService.buscarPorNfse("7891011");
        assertEquals(mockCredito, result.get());
        verify(creditoRepository, times(1)).findByNumeroNfse("7891011");
    }

    @Test
    void testBuscarPorNumeroCredito() {
        when(creditoRepository.findByNumeroCredito("123456")).thenReturn(List.of(mockCredito));
        
        List<Credito> result = creditoService.buscarPorNumeroCredito("123456");
        assertEquals(1, result.size());
        assertEquals(mockCredito, result.get(0));
        verify(creditoRepository, times(1)).findByNumeroCredito("123456");
    }

    @Test
    void testBuscarPorNfse_QuandoNaoEncontrado() {
        when(creditoRepository.findByNumeroNfse("999999")).thenReturn(Optional.empty());
        
        Optional<Credito> result = creditoService.buscarPorNfse("999999");
        assertTrue(result.isEmpty());
        verify(creditoRepository, times(1)).findByNumeroNfse("999999");
    }

    @Test
    void testBuscarPorNumeroCredito_QuandoNaoEncontrado() {
        when(creditoRepository.findByNumeroCredito("999999")).thenReturn(List.of());
        
        List<Credito> result = creditoService.buscarPorNumeroCredito("999999");
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(creditoRepository, times(1)).findByNumeroCredito("999999");
    }

    @Test
    void testBuscarPorNumeroCredito_QuandoEncontraMultiplosCreditos() {
        Credito mockCredito2 = new Credito(2L, "123456", "7891012", LocalDate.parse("2024-02-26"), 
                                          new BigDecimal("2000.50"), "ISSQN", true, new BigDecimal("5.0"), 
                                          new BigDecimal("40000.00"), new BigDecimal("6000.00"), 
                                          new BigDecimal("34000.00"));
        
        when(creditoRepository.findByNumeroCredito("123456")).thenReturn(List.of(mockCredito, mockCredito2));
        
        List<Credito> result = creditoService.buscarPorNumeroCredito("123456");
        assertEquals(2, result.size());
        assertTrue(result.contains(mockCredito));
        assertTrue(result.contains(mockCredito2));
        verify(creditoRepository, times(1)).findByNumeroCredito("123456");
    }

    @Test
    void testBuscarPorNfse_ComParametroNulo() {
        when(creditoRepository.findByNumeroNfse(null)).thenReturn(Optional.empty());
        
        Optional<Credito> result = creditoService.buscarPorNfse(null);
        assertTrue(result.isEmpty());
        verify(creditoRepository, times(1)).findByNumeroNfse(null);
    }

    @Test
    void testBuscarPorNumeroCredito_ComParametroNulo() {
        when(creditoRepository.findByNumeroCredito(null)).thenReturn(List.of());
        
        List<Credito> result = creditoService.buscarPorNumeroCredito(null);
        assertTrue(result.isEmpty());
        verify(creditoRepository, times(1)).findByNumeroCredito(null);
    }

    @Test
    void testBuscarPorNfse_ComParametroVazio() {
        when(creditoRepository.findByNumeroNfse("")).thenReturn(Optional.empty());
        
        Optional<Credito> result = creditoService.buscarPorNfse("");
        assertTrue(result.isEmpty());
        verify(creditoRepository, times(1)).findByNumeroNfse("");
    }

    @Test
    void testBuscarPorNumeroCredito_ComParametroVazio() {
        when(creditoRepository.findByNumeroCredito("")).thenReturn(List.of());
        
        List<Credito> result = creditoService.buscarPorNumeroCredito("");
        assertTrue(result.isEmpty());
        verify(creditoRepository, times(1)).findByNumeroCredito("");
    }
}
