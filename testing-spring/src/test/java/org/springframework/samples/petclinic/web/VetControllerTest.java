package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;
    VetController controller;
    List<Vet> vets = List.of(new Vet(), new Vet());
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        when(clinicService.findVets()).thenReturn(vets);
        controller = new VetController(clinicService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testControllerShowVetList() throws Exception {
        mockMvc.perform(get("/vets.html"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/vetList"));
    }

    @Test
    void showVetList() {
        Map<String, Object> model = new HashMap<>();
        String viewName = controller.showVetList(model);
        assertThat(model).hasSize(1).containsKey("vets");
        assertThat(model.get("vets")).isNotNull();
        assertThat(viewName).isEqualTo("vets/vetList");
    }

    @Test
    void showResourcesVetList() {
        Vets vetList = controller.showResourcesVetList();
        assertThat(vetList.getVetList()).isNotNull().hasSize(2);
    }
}