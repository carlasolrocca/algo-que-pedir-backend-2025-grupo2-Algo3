package ar.edu.unsam.algo3.controller

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import ar.edu.unsam.algo3.MedioDePago
import ar.edu.unsam.algo3.dto.LocalDTO
import org.junit.jupiter.api.DisplayName
import org.springframework.http.MediaType
import ar.edu.unsam.algo3.service.LocalService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(LocalController::class)
@DisplayName("Dado el perfil de un local")
class LocalControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var localService: LocalService

    @Autowired
    lateinit var objectMapper: ObjectMapper

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

    @Test
    fun `hacer un put actualiza el local y devuelve el JSON actualizado`() {
        
        val localDTO = LocalDTO(
            nombre = "Café Tortoni",
            urlImagenLocal = "https://upload.wikimedia.org/wikipedia/commons/5/51/Caf%C3%A9_Tortoni.JPG",
            direccion = "Avenida de Mayo",
            altura = 825,
            latitud = -34.6086531,
            longitud = -58.3782121,
            porcentajeSobreCadaPlato = 3.0,
            porcentajeRegaliasDeAutor = 3.0,
            mediosDePago = setOf(MedioDePago.EFECTIVO, MedioDePago.TRANSFERENCIA_BANCARIA)
            
        )

        given(localService.actualizarLocalDesdeDTO(localDTO)).willReturn(localDTO)

        val json = objectMapper.writeValueAsString(localDTO)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/local")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nombre").value("Café Tortoni"))
            .andExpect(jsonPath("$.altura").value(825))
            .andExpect(jsonPath("$.porcentajeSobreCadaPlato").value(3))
            .andExpect(jsonPath("$.porcentajeRegaliasDeAutor").value(3))
    }

}