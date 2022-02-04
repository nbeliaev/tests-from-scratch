package com.fr13.dev.services.springdatajpa;

import com.fr13.dev.model.Visit;
import com.fr13.dev.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    private VisitRepository repository;

    @InjectMocks
    private VisitSDJpaService service;

    private Visit visit0;
    private Visit visit1;

    @BeforeEach
    void setUp() {
        visit0 = new Visit(1L, LocalDate.of(2022, 2, 2));
        visit1 = new Visit(2L, LocalDate.now());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Set.of(visit0, visit1));
        final Set<Visit> visits = service.findAll();
        assertThat(visits).isNotEmpty().hasSize(2).contains(visit0, visit1);
        verify(repository).findAll();
    }

    @Test
    void save() {
        when(repository.save(visit0)).thenReturn(visit0);

        final Visit savedVisit0 = service.save(visit0);
        final Visit savedVisit1 = service.save(visit1);

        assertAll("Test saved visits",
                () -> assertThat(savedVisit0).isNotNull().isEqualTo(visit0),
                () -> assertThat(savedVisit1).isNull());
        verify(repository, times(2)).save(any(Visit.class));
    }
}