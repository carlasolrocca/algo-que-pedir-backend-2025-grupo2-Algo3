/*package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.EnumGrupoAlimenticio
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import ar.edu.unsam.algo3.repositorios.PlatoRepositorio
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service

@Service
@DependsOn("ingredienteBootstrap")
class PlatoBootstrap(
    val platoRepositorio: PlatoRepositorio,
    val ingredienteRepositorio: IngredienteRepositorio
): InitializingBean {






    override fun afterPropertiesSet() {
        this.crearPlatos()
    }
}*/