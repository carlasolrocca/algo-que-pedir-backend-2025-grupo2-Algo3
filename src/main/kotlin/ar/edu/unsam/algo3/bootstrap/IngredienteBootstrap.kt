/*package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.EnumGrupoAlimenticio
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service

@Service
class IngredienteBootstrap(
    val ingredienteRepositorio: IngredienteRepositorio
): InitializingBean {

    private lateinit var tomate: Ingrediente
    private lateinit var pechugaDePollo: Ingrediente
    private lateinit var arroz: Ingrediente
    private lateinit var leche: Ingrediente
    private lateinit var palta: Ingrediente

    fun crearIngredientes() {
        tomate = Ingrediente(
            "Tomate",
            0.50,
            EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS,
            false
        )
        pechugaDePollo = Ingrediente(
            "Pechuga de pollo",
            3.00,
            EnumGrupoAlimenticio.PROTEINAS,
            true
        )
        arroz = Ingrediente(
            "Arroz",
            1.00,
            EnumGrupoAlimenticio.CEREALES_Y_TUBERCULOS,
            false
        )
        leche = Ingrediente(
            "Leche",
            2.00,
            EnumGrupoAlimenticio.LACTEOS,
            true
        )
        palta = Ingrediente(
            "Palta",
            1.50,
            EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS,
            false
        )
        ingredienteRepositorio.apply {
            create(tomate)
            create(pechugaDePollo)
            create(arroz)
            create(leche)
            create(palta)
        }
    }



}*/