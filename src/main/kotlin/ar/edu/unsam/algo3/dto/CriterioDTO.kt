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

fun UsuarioStrategy.toTipoCriterioDTO(): TipoCriterioDTO {
    return when (this) {
        is UsuarioCombinadoStrategy -> TipoCriterioDTO.COMBINADO
        is UsuarioVeganoStrategy -> TipoCriterioDTO.VEGANO
        is UsuarioExquisitoStrategy -> TipoCriterioDTO.EXQUISITO
        is UsuarioConservadorStrategy -> TipoCriterioDTO.CONSERVADOR
        is UsuarioFielStrategy -> TipoCriterioDTO.FIEL
        is UsuarioMarketingStrategy -> TipoCriterioDTO.MARKETING
        is UsuarioImpacienteStrategy -> TipoCriterioDTO.IMPACIENTE
        is UsuarioGeneralStrategy -> TipoCriterioDTO.GENERAL
        else -> throw IllegalArgumentException("Tipo de criterio desconocido.")
    }
}