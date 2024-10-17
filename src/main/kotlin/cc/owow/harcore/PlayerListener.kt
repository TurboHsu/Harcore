package cc.owow.harcore

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent

class PlayerListener(private val plugin: Harcore): Listener {
    @EventHandler
    fun onPlayerRespawn(event: PlayerPostRespawnEvent) {
        val player = event.player
        val maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
        val healthDecrement = plugin.getPluginConfig().healthDecrement
        val healthBottom = plugin.getPluginConfig().healthBottom

        player.foodLevel = 10
        player.saturation = 5.0f

        if ((maxHealth?.value?.minus(healthDecrement) ?: 0.0) >= healthBottom) {
            maxHealth?.baseValue = maxHealth?.value?.minus(healthDecrement) ?: healthBottom
            player.health = maxHealth?.value?.div(2 ) ?: healthBottom
            player.sendMessage("${plugin.getMessage("respawn")} §a-${healthDecrement}❤")
        } else {
            player.sendMessage("§c${plugin.getMessage("noob")}")
        }
    }

    @EventHandler
    fun onPlayerEat(event: PlayerItemConsumeEvent) {
        val player = event.player
        val item = event.item
        val foods = plugin.getPluginConfig().healthRecoverFood

        if (foods.containsKey(item.type.name)) {
            val maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
            val increment = foods[item.type.name] ?: 0.0
            if ((maxHealth?.value?.plus(increment) ?: 20.0) >= 20.0) {
                maxHealth?.baseValue = 20.0
                return
            }
            maxHealth?.baseValue = maxHealth?.value?.plus(increment) ?: 20.0
            player.sendMessage("${plugin.getMessage("recover")} §a+${increment}❤")
        }
    }
}