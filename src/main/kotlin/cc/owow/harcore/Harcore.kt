package cc.owow.harcore

import org.bukkit.plugin.java.JavaPlugin
import java.util.Locale
import java.util.ResourceBundle

class Harcore : JavaPlugin() {
    private lateinit var locale: Locale
    private lateinit var messages: ResourceBundle
    private lateinit var pluginConfig: ConfigModel

    override fun onEnable() {
        saveDefaultConfig()
        loadConfig()

        getCommand("harcore")?.setExecutor(CommandExecutor(this))

        server.pluginManager.registerEvents(PlayerListener(this), this)
        logger.info(getMessage("enabled"))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun getMessage(key: String): String {
        return messages.getString(key)
    }

    fun getPluginConfig(): ConfigModel {
        return pluginConfig
    }

    fun loadConfig() {
        pluginConfig = ConfigModel.load(this)
        locale = Locale.forLanguageTag(pluginConfig.language)
        messages = ResourceBundle.getBundle("localization.messages", locale)
    }
}