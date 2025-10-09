package ar.edu.unsam.algo3

import org.uqbar.geodds.Point
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin(origins = ["http://localhost:5173"])
class LocalController {

    private val local = Local(
        nombre = "Taberna de Moe",
        urlImagenLocal = "https://www.clarin.com/img/2017/10/05/SkWTevV3-_1200x0.jpg",
        direccion = Direccion(calle = "Av. Siempre Viva",altura = 742,ubicacion = Point(39.808327,-89.643204)),
        porcentajeSobreCadaPlato = 10.0,
        porcentajeRegaliasDeAutor = 5.0
    )

    @GetMapping("/local")
    fun obtenerLocal(): Local {
        return local
    }
}