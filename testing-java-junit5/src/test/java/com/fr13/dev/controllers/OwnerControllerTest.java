package com.fr13.dev.controllers;

import com.fr13.dev.fauxspring.BindingResult;
import com.fr13.dev.fauxspring.Model;
import com.fr13.dev.model.Owner;
import com.fr13.dev.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private Owner owner;

    @InjectMocks
    private OwnerController controller;

    @Mock
    OwnerService service;
    @Mock
    BindingResult bindingResult;
    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        owner = new Owner(5L, "John", "Wall");
    }

    @Test
    void processCreationFormNoErrors() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(service.save(any())).thenReturn(owner);
        String viewName = controller.processCreationForm(owner, bindingResult);
        assertThat(viewName).startsWith("redirect").endsWith("5");
    }

    @Test
    void processCreationFormHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);
        final String viewName = controller.processCreationForm(owner, bindingResult);
        assertThat(viewName).doesNotStartWith("redirect").contains("createOrUpdateOwnerForm");
    }

    @Test
    void processFindFormWildCardString() {
        List<Owner> ownerList = new ArrayList<>();
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        when(service.findAllByLastNameLike(captor.capture())).thenReturn(ownerList);
        InOrder inOrder = inOrder(service, model);

        final String viewName = controller.processFindForm(owner, bindingResult, model);

        assertThat("%" + owner.getLastName() + "%").isEqualToIgnoringCase(captor.getValue());
        inOrder.verify(service, times(1)).findAllByLastNameLike(anyString());
    }

    @Test
    void processFindFormNotFound() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        //return custom objects from mock invocation
        when(service.findAllByLastNameLike(captor.capture())).thenAnswer(invocation -> {
            String lastName = invocation.getArgument(0);
            if (lastName.equals("%" + owner.getLastName() + "%")) {
                return List.of(owner);
            } else if (lastName.equals("%NoLastName%")) {
                return Collections.emptyList();
            } else {
                throw new RuntimeException("Invalid arg");
            }
        });

        Owner newOwner = new Owner(1L, null, "NoLastName");
        final String viewName = controller.processFindForm(newOwner, bindingResult, null);

        assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
        verifyZeroInteractions(model);
    }
}