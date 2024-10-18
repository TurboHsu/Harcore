package cc.owow.harcore

data class ConfigModel(
    val language: String,
    val respawnPenalty: RespawnPenalty,
    val healthRecoverFood: Map<String, Double>,
) {
    companion object {
        fun load(plugin: Harcore): ConfigModel {
            return ConfigModel(
                plugin.config.getString("language", "en").toString(),
                plugin.config.getConfigurationSection("respawn-penalty")?.let { section ->
                    RespawnPenalty(
                        section.getInt("health-decrement-boundary-upper", 10),
                        section.getInt("health-decrement-boundary-lower", 1),
                    )
                } ?: RespawnPenalty(10, 1),
                plugin.config
                    .getConfigurationSection("health-recover-food")
                    ?.getValues(false)
                    ?.mapValues { it.value as Double } ?: emptyMap(),
            )
        }
    }
}

data class RespawnPenalty(
    val upper: Int,
    val lower: Int,
)
