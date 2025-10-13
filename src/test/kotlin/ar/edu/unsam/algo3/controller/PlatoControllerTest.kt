package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.EnumGrupoAlimenticio
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import ar.edu.unsam.algo3.repositorios.PlatoRepositorio
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.jvm.java

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Dado un controller de platos")
class PlatoControllerTest(@Autowired val mockMvc: MockMvc) {

    @Autowired
    lateinit var platoRepositorio: PlatoRepositorio
    @Autowired
    lateinit var ingredienteRepositorio: IngredienteRepositorio

    lateinit var plato: Plato
    lateinit var ingrediente: Ingrediente

    @BeforeEach
    fun init() {
        platoRepositorio.memoria.clear()
        ingredienteRepositorio.memoria.clear()
        ingrediente = ingredienteRepositorio.create(Ingrediente(nombre="queso"))
        plato = platoRepositorio.create(buildPlato())
        platoRepositorio.create(buildPlato()).also {
            it.nombre="Vigilante"
            it.descripcion="Falta el dulce"
            it.valorBase=5.5
        }
    }

    // region GET /plato
    @Test
    fun `se pueden obtener todos los platos`() {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/plato"))
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.length()").value(2))
    }
    // endregion

    // region GET /plato/{id}
    @Test
    fun `se puede obtener un plato por su id`() {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/plato/" + plato.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(plato.id))
            .andExpect(jsonPath("$.descripcion").value(plato.descripcion))
    }

    @Test
    fun `si se pide una tarea con un id que no existe se produce un error`() {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/plato/20000"))
            .andExpect(status().isNotFound)
    }
    // endregion

    // region [actualizar] PUT /plato/{id}
    @Test
    fun `actualizar un plato a un valor valido actualiza correctamente`() {
        val platoValido = buildPlato().apply {
            id = plato.id
            descripcion="Cambie la descripcion"
        }
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .put("/plato/" + platoValido.id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectMapper().writeValueAsString(platoValido))
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.descripcion").value("Cambie la descripcion"))
    }

    @Test
    fun `si se intenta actualizar un plato omitiendo su id en json, el sistema rechaza la operacion`() {
        val platoInvalido = buildPlato().apply {
            id = null
        }

        val errorMessage = mockMvc
            .perform(
                MockMvcRequestBuilders
                    .put("/plato/" + plato.id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectMapper().writeValueAsString(platoInvalido))
            )
            .andExpect(status().isBadRequest)
            .andReturn().resolvedException?.message

        assertEquals(errorMessage, "El objeto debe tener un ID")
    }

    @Test
    fun `si se intenta actualizar una tarea con datos vacios, el sistema rechaza la operacion`() {
        val platoInvalido = buildPlato().apply {
            id = plato.id
            descripcion = ""
        }

        val errorMessage = mockMvc
            .perform(
                MockMvcRequestBuilders
                    .put("/plato/" + plato.id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectMapper().writeValueAsString(platoInvalido))
            )
            .andExpect(status().isBadRequest)
            .andReturn().resolvedException?.message

        assertEquals(errorMessage, "Debe ingresar una descripcion")
    }

    @Test
    fun `se puede desasignar omitiendo el ingrediente, esto actualiza la tarea correctamente`() {
        val platoSinIngredientes = """
            {
                "id": ${plato.id},
                "descripcion":  "Resolver testeo unitario del plato",
                "valorBase": 5.5
            }
        """.trimIndent()

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .put("/plato/${plato.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(platoSinIngredientes)
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.listaDeIngredientes").isEmpty)
            .andExpect(jsonPath("$.valorBase").value(5.5))
    }


    @Test
    fun `si se intenta actualizar una tarea con id diferente en el request y en el body, el sistema rechaza la operacion`() {
        val platoJson = """
            {
                "id": ${plato.id},
                "descripcion":  "Resolver testeo unitario del plato",
                "valorBase": 5.5
            }
        """.trimIndent()

        val errorMessage = mockMvc
            .perform(
                MockMvcRequestBuilders
                    .put("/plato/" + 2)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(platoJson)
            )
            .andExpect(status().isBadRequest)
            .andReturn().resolvedException?.message

        assertEquals(errorMessage, "Id en URL distinto del id que viene en el body")
    }
    // endregion

    // region [crear] POST /plato
    @Test
    fun `crear un plato a un valor valido actualiza correctamente`() {
        val descripcionNuevoPlato = "Implementar un servicio REST para crear un plato"
        val mapper = ObjectMapper()
        val platoValido = buildPlato().apply {
            descripcion = descripcionNuevoPlato
        }
        val nuevoPlatoResponse = mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/plato")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(platoValido))
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json"))
            .andReturn().response.contentAsString

        val nuevoPlatoObject = mapper.readValue(nuevoPlatoResponse, Plato::class.java)
        val nuevoPlato = platoRepositorio.getById(nuevoPlatoObject.id!!)
        assertEquals(nuevoPlato.descripcion, descripcionNuevoPlato)
    }

    @Test
    fun `si se intenta crear una tarea con datos incorrectos, el sistema rechaza la operacion`() {
        val platoInvalido = buildPlato().apply {
            valorBase = 0.0
        }

        val errorMessage = mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/plato")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectMapper().writeValueAsString(platoInvalido))
            )
            .andExpect(status().isBadRequest)
            .andReturn().resolvedException?.message

        assertEquals(errorMessage, "El precio debe ser mayor a cero")
    }

    @Test
    fun `si se intenta crear una tarea pasando un id, el sistema rechaza la operacion`() {
        val platoInvalido = buildPlato().apply {
            id = 100
            descripcion = ""
        }

        val errorMessage = mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/plato")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectMapper().writeValueAsString(platoInvalido))
            )
            .andExpect(status().isBadRequest)
            .andReturn().resolvedException?.message

        assertEquals(errorMessage, "No se debe pasar el identificador del plato")
    }
    // endregion

    // region DELETE /tarea/{descripcion}
    @Test
    fun `se puede eliminar un plato existente en forma exitosa`() {
        platoRepositorio.memoria.clear() //Hago el clear acá de nuevo porque el beforeEach crea 2 platos siempre después del clear
        val plato = buildPlato()
        platoRepositorio.create(plato)

        mockMvc.delete("/plato/${plato.id}")
            .andExpect { status { isOk() } }

        assertEquals(0, platoRepositorio.findAll().size)
    }

    @Test
    fun `si pasamos tareas que no existen no se pueden borrar`() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/plato/inexistente"))
            .andExpect(status().isBadRequest)
    }
    // endregion

    fun buildPlato(): Plato {
        return  Plato().apply {
            nombre="Pizza"
            descripcion="Muy sabrosa, clásica"
            valorBase=20.0
            agregarIngrediente(ingrediente)
        }
    }
}