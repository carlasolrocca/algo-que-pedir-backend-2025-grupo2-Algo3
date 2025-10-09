package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.Local
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.MedioDePago
import org.uqbar.geodds.Point
import org.springframework.stereotype.Service

@Service
class LocalService {

    // Simulamos un local
    private val local = Local(
        nombre = "Taberna de Moe",
        urlImagenLocal = "https://www.clarin.com/img/2017/10/05/SkWTevV3-_1200x0.jpg",
        direccion = Direccion(calle = "Av. Siempre Viva", altura = 742, ubicacion = Point(39.808327, -89.643204)),
        porcentajeSobreCadaPlato = 10.0,
        porcentajeRegaliasDeAutor = 5.0
    ).apply {
        agregarMedioDePago(MedioDePago.EFECTIVO)
        agregarMedioDePago(MedioDePago.TRANSFERENCIA_BANCARIA)
    }

    fun obtenerLocalDTO(): LocalDTO {
        return LocalDTO(
            nombre = local.nombre,
            urlImagenLocal = local.urlImagenLocal,
            direccion = local.direccion.calle,
            altura = local.direccion.altura,
            latitud = local.direccion.ubicacion.latitude(),
            longitud = local.direccion.ubicacion.longitude(),
            porcentajeSobreCadaPlato = local.porcentajeSobreCadaPlato,
            porcentajeRegaliasDeAutor = local.porcentajeRegaliasDeAutor,
            mediosDePago = local.mediosDePago
        )
    }
}
