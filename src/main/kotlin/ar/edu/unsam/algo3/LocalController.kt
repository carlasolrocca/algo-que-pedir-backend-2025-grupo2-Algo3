package ar.edu.unsam.algo3

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin(origins = ["http://localhost:5173"])
class LocalController {

    private val local = Local(
        nombre = "Algo que Pedir - Grupo 2",
        direccion = Direccion("Av. Siempre Viva 742"),
        porcentajeSobreCadaPlato = 10.0,
        porcentajeRegaliasDeAutor = 5.0
    )

    @GetMapping("/local")
    fun obtenerLocal(): Local {
        return local
    }
}