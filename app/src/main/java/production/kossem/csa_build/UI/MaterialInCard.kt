package production.kossem.csa_build.UI

data class MaterialInCard(
    val materialId: Int,
    val specification: String,
    val type: String,
    val price: Float,
    val count: Int
)