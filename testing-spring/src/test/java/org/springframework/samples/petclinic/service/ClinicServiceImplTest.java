package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ClinicServiceImplTest {

    @Mock
    PetRepository repository;

    ClinicService clinicService;

    @BeforeEach
    void setUp() {
        clinicService = new ClinicServiceImpl(repository, null, null, null);
        when(repository.findPetTypes()).thenReturn(List.of(new PetType()));
    }

    @Test
    void findPetTypes() {
        Collection<PetType> petTypes = clinicService.findPetTypes();
        assertThat(petTypes).hasSize(1);
    }
}