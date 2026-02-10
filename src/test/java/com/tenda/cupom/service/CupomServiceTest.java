package com.tenda.cupom.service;

import com.tenda.cupom.model.Cupom;
import com.tenda.cupom.repository.CupomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CupomServiceTest {

    @Mock
    private CupomRepository repository;

    @InjectMocks
    private CupomService service;

    //METODO SALVAR

    @Test
    void deveSalvarCupomQuandoCodigoNaoExiste() {
        Cupom cupom = new Cupom(
                "ABC123",
                "Cupom teste",
                BigDecimal.valueOf(1.0),
                OffsetDateTime.now().plusDays(5)
        );

        when(repository.existsByCode("ABC123")).thenReturn(false);
        when(repository.save(cupom)).thenReturn(cupom);

        Cupom salvo = service.salvar(cupom);

        assertNotNull(salvo);
        verify(repository).existsByCode("ABC123");
        verify(repository).save(cupom);
    }

    @Test
    void deveLancarExcecaoQuandoCodigoJaExiste() {
        Cupom cupom = new Cupom(
                "ABC123",
                "Cupom duplicado",
                BigDecimal.valueOf(1.0),
                OffsetDateTime.now().plusDays(5)
        );

        when(repository.existsByCode("ABC123")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.salvar(cupom)
        );

        assertEquals("Já existe cupom com esse código", exception.getMessage());

        verify(repository).existsByCode("ABC123");
        verify(repository, never()).save(any());
    }
}
