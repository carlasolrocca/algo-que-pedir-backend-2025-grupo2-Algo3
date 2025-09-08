package ar.edu.unsam.algo2.servicios

class IngredientesAdapter(
    val servicio: IServiceIngredientes
) : IExternalService {
    override fun get(): String {
        return servicio.getIngredientes()
    }
}

class LocalesAdapter(
    val servicio: IServiceLocales
) : IExternalService {
    override fun get(): String {
        return servicio.getLocales()
    }
}