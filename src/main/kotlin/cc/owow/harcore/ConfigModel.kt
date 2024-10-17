package cc.owow.harcore

data class ConfigModel(
    val language: String,
    val healthDecrement: Double,
    val healthBottom: Double,
    val healthRecoverFood: Map<String, Double>,
) {
    companion object {
        fun load(plugin: Harcore): ConfigModel {
            return ConfigModel(
                plugin.config.getString("language", "en").toString(),
                plugin.config.getDouble("health-decrement", 1.0),
                plugin.config.getDouble("health-bottom", 1.0),
                plugin.config
                    .getConfigurationSection("health-recover-food")
                    ?.getValues(false)
                    ?.mapValues { it.value as Double } ?: emptyMap(),
            )
        }
    }
}

