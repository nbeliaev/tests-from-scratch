package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    @Autowired
    OwnerController controller;

    @Autowired
    ClinicService clinicService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void tearDown() {
        reset(clinicService);
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void findByNameNotFound() throws Exception {
        mockMvc.perform(get("/owners").param("lastName", "Unknown"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void findExactlyOneOwner() throws Exception {
        String lastName = "Wall";
        int id = 1;
        Owner owner = new Owner();
        owner.setId(id);
        owner.setLastName(lastName);
        when(clinicService.findOwnerByLastName(lastName)).thenReturn(Collections.singletonList(owner));

        mockMvc.perform(get("/owners").param("lastName", lastName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + id));
    }

    @Test
    void findAllOwners() throws Exception {
        List<Owner> owners = List.of(new Owner(), new Owner());
        when(clinicService.findOwnerByLastName(any())).thenReturn(owners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selections"))
                .andExpect(view().name("owners/ownersList"));
    }

    @Test
    void newOwnerPostValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "Jimmy")
                .param("lastName", "Wall")
                .param("address", "UK")
                .param("city", "London")
                .param("telephone", "123"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    void newOwnerPostInvalid() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "Jimmy")
                .param("lastName", "Wall")
                .param("address", "UK")
                .param("city", "London"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));

    }

    @Test
    void processUpdateOwnerFormValid() throws Exception {
        Owner owner = new Owner();
        owner.setFirstName("Jimmy");
        owner.setLastName("Wall");
        owner.setCity("London");
        owner.setAddress("UK");
        owner.setTelephone("123");

        mockMvc.perform(post("/owners/{ownerId}/edit", 13)
                .param("firstName", owner.getFirstName())
                .param("lastName", owner.getLastName())
                .param("address", owner.getAddress())
                .param("city", owner.getCity())
                .param("telephone", owner.getTelephone()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }
}