package ar.edu.unsam.algo2

import ar.edu.unsam.algo2.repositorios.TipoRepositorio
import com.fasterxml.jackson.annotation.JsonProperty


enum class EnumGrupoAlimenticio {
    CEREALES_Y_TUBERCULOS,
    AZUCARES_Y_DULCES,
    LACTEOS,
    FRUTAS_Y_VERDURAS,
    GRASAS_Y_ACEITES,
    PROTEINAS
}

data class Ingrediente(
    @JsonProperty("nombre") val nombre: String = "",
    @JsonProperty("costo") val costoMercado: Double = 0.0,
    @JsonProperty("grupo") val grupoAlimenticio: EnumGrupoAlimenticio = EnumGrupoAlimenticio.CEREALES_Y_TUBERCULOS,
    @JsonProperty("origenAnimal") val origenAnimal: Boolean = false
) : TipoRepositorio()

