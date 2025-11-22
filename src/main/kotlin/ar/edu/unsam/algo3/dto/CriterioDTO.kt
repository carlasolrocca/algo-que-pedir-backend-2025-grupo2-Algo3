package ar.edu.unsam.algo3.ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.UsuarioCombinadoStrategy
import ar.edu.unsam.algo3.UsuarioConservadorStrategy
import ar.edu.unsam.algo3.UsuarioExquisitoStrategy
import ar.edu.unsam.algo3.UsuarioFielStrategy
import ar.edu.unsam.algo3.UsuarioGeneralStrategy
import ar.edu.unsam.algo3.UsuarioImpacienteStrategy
import ar.edu.unsam.algo3.UsuarioMarketingStrategy
import ar.edu.unsam.algo3.UsuarioStrategy
import ar.edu.unsam.algo3.UsuarioVeganoStrategy

data class CriterioDTO(
    val tipo: TipoCriterioDTO,
    val localesPreferidos: Set<LocalCriterioDTO>? = null,
    val palabrasClave: Set<String>? = null,
    val subCriterios: Set<CriterioDTO>? = null
)
enum class TipoCriterioDTO {
    COMBINADO,
    VEGANO,
    EXQUISITO,
    CONSERVADOR,
    FIEL,
    MARKETING,
    IMPACIENTE,
    GENERAL
}

fun UsuarioStrategy.toCriterioDTO(): CriterioDTO {
    return when (this) {
        is UsuarioCombinadoStrategy -> CriterioDTO(
            tipo = TipoCriterioDTO.COMBINADO,
            subCriterios = this.requisitosParticulares.map { it.toCriterioDTO() }.toSet()
        )
        is UsuarioVeganoStrategy -> CriterioDTO( tipo = TipoCriterioDTO.VEGANO)
        is UsuarioExquisitoStrategy -> CriterioDTO( tipo = TipoCriterioDTO.EXQUISITO)
        is UsuarioConservadorStrategy -> CriterioDTO( tipo = TipoCriterioDTO.CONSERVADOR)
        is UsuarioFielStrategy -> CriterioDTO(
            tipo = TipoCriterioDTO.FIEL,
            localesPreferidos = this.localesPreferidos.mapNotNull { it.toCriterioDTO() }.toSet()
        )
        is UsuarioMarketingStrategy -> CriterioDTO(
            tipo = TipoCriterioDTO.MARKETING,
            palabrasClave = this.textoLlamativo.toSet()
        )
        is UsuarioImpacienteStrategy -> CriterioDTO( tipo = TipoCriterioDTO.IMPACIENTE )
        is UsuarioGeneralStrategy -> CriterioDTO( tipo = TipoCriterioDTO.GENERAL)
        else -> throw IllegalArgumentException("Tipo de criterio desconocido.")
    }
}