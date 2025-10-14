package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.service.LocalService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.BDDMockito.given

@WebMvcTest(LocalController::class)
@DisplayName("Dado el perfil de un local")
class LocalControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var localService: LocalService

    @Test
    fun `hacer un get devuelve la información que se le solicita`() {

        val localDTO = LocalDTO(
            nombre = "Taberna de Moe",
            urlImagenLocal = "https://www.clarin.com/img/2017/10/05/SkWTevV3-_1200x0.jpg",
            direccion = "Av. Siempre Viva",
            altura = 742,
            latitud = 39.808327,
            longitud = -89.643204,
            porcentajeSobreCadaPlato = 10.0,
            porcentajeRegaliasDeAutor = 5.0,
            mediosDePago = setOf()
        )

        given(localService.obtenerLocalDTO()).willReturn(localDTO)

        mockMvc.perform(MockMvcRequestBuilders.get("/local"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nombre").value("Taberna de Moe"))
            .andExpect(jsonPath("$.direccion").value("Av. Siempre Viva"))
            .andExpect(jsonPath("$.altura").value(742))
            .andExpect(jsonPath("$.porcentajeSobreCadaPlato").value(10.0))
            .andExpect(jsonPath("$.porcentajeRegaliasDeAutor").value(5.0))
    }
}