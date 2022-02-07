package com.fr13.dev.services.springdatajpa;

import com.fr13.dev.model.Speciality;
import com.fr13.dev.repositories.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialitySDJpaService service;

    private Speciality speciality;

    @BeforeEach
    void setUp() {
        speciality = new Speciality();
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, times(2)).deleteById(1L);
        verify(specialtyRepository, never()).delete(speciality);
    }

    @Test
    void deleteByIdAtLeastOnce() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void delete() {
        service.delete(speciality);
        verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void findById() {
        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));
        Speciality foundSpecialty = service.findById(1L);
        assertThat(foundSpecialty).isNotNull().isEqualTo(speciality);
        verify(specialtyRepository).findById(anyLong());
    }

    @Test
    void findByIdBDDTest() {
        //given
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));
        //when
        Speciality foundSpecialty = service.findById(1L);
        //then
        assertThat(foundSpecialty).isNotNull().isEqualTo(speciality);
        then(specialtyRepository).should(atLeastOnce()).findById(anyLong());
    }

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("Boom")).when(specialtyRepository).delete(any());
        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(speciality));
        verify(specialtyRepository).delete(any());
    }

    @Test
    void testSaveLambda() {
        String matchMe = "matchMe";
        Speciality spec = new Speciality(matchMe);

        Speciality savedSpecialty = new Speciality();
        savedSpecialty.setId(1L);

        when(specialtyRepository.save(argThat(arg -> arg.getDescription().equals(matchMe)))).thenReturn(savedSpecialty);

        Speciality returnedSpeciality = service.save(spec);
        assertThat(returnedSpeciality.getId()).isEqualTo(1L);
    }
}