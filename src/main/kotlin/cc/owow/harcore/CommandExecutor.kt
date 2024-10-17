package cc.owow.harcore

import org.bukkit.attribute.Attribute
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandExecutor(private val plugin: Harcore) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command,
                           label: String, args: Array<out String>?): Boolean {
        if (command.name == "harcore") {
            if (args.isNullOrEmpty()) {
                sender.sendMessage(plugin.getMessage("command-help"))
                return true
            }
            when (args[0]) {
                "reload" -> {
                    plugin.loadConfig()
                    sender.sendMessage(plugin.getMessage("reload"))
                }
                "reset" -> {
                    val playerName = args.getOrNull(1) ?.toString()?: run {
                        sender.sendMessage(plugin.getMessage("reset-command-usage"))
                        return true
                    }
                    val player = plugin.server.getPlayer(playerName) ?: run {
                        sender.sendMessage(plugin.getMessage("player-not-found"))
                        return true
                    }
                    val maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                    maxHealth?.baseValue = 20.0

                    sender.sendMessage(plugin.getMessage("player-reset"))
                }
                else -> {
                    sender.sendMessage(plugin.getMessage("command-help"))
                }
            }
            return true
        }
        return false
    }
}