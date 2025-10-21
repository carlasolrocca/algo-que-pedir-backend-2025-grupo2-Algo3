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
import ar.edu.unsam.algo3.controller.LocalController
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
    fun `hacer un get por id devuelve el local correcto`() {
        val localDTO = LocalDTO(
            idLocal = 2,
            nombre = "Café Tortoni",
            urlImagenLocal = "https://upload.wikimedia.org/wikipedia/commons/5/51/Caf%C3%A9_Tortoni.JPG",
            direccion = "Avenida de Mayo",
            altura = 825,
            latitud = -34.6086531,
            longitud = -58.3782121,
            porcentajeSobreCadaPlato = 3.0,
            porcentajeRegaliasDeAutor = 3.0,
            mediosDePago = setOf()
        )

        given(localService.obtenerLocalPorId(2)).willReturn(localDTO)

        mockMvc.perform(MockMvcRequestBuilders.get("/local/2"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nombre").value("Café Tortoni"))
    }

    @Test
    fun `hacer un put actualiza el local y devuelve el JSON actualizado`() {
        
        val localDTO = LocalDTO(
            idLocal = 2,
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

} // Fin LocalControllerTest
