package ar.edu.unsam.algo3.service

import org.uqbar.geodds.Point
import ar.edu.unsam.algo3.Local
import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.MedioDePago
import org.springframework.stereotype.Service


@Service
class LocalService {

    fun obtenerLocal(): Local {
        val local = Local(
            nombre = "Taberna de Moe",
            urlImagenLocal = "https://www.clarin.com/img/2017/10/05/SkWTevV3-_1200x0.jpg",
            direccion = Direccion(
                calle = "Av. Siempre Viva",
                altura = 742,
                ubicacion = Point(39.808327, -89.643204)
            ),
            porcentajeSobreCadaPlato = 10.0,
            porcentajeRegaliasDeAutor = 5.0
        )

        // Agregamos medios de pago
        local.agregarMedioDePago(MedioDePago.EFECTIVO)
        local.agregarMedioDePago(MedioDePago.TRANSFERENCIA_BANCARIA)

        return local
    }
}