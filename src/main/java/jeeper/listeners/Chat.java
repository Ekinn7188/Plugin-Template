package jeeper.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import jeeper.utils.MessageTools;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * An example listener
 */
public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        e.setCancelled(true);
        String plainText = PlainTextComponentSerializer.builder().build().serialize(e.message());

        var newChatMessage =
            e.getPlayer().displayName()
                .append(Component.text(" » "))
                .append(MessageTools.parseText(plainText)).color(TextColor.fromHexString("#FFFFFF"));

        Bukkit.broadcast(newChatMessage);
    }
}
