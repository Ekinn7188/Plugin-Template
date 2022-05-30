package jeeper.commands;

import jeeper.utils.MessageTools;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * An example command to show what the PluginCommand class can do.
 */
public class TestCommand extends PluginCommand {

    @Override
    public String getName() {
        return "test"; // Make sure this is the same as the name in plugin.yml
        // Required method
    }

    @Override
    protected Permission getPermissionType() {
        return Permission.PERMISSION; // Make sure the name of the permission is the same in plugin.yml
        // If no permission is needed, method is not necessary
    }

    @Override
    public boolean isRequiresPlayer() {
        return true; // Player only command!!
        // isRequiresPlayer() is true by default, so it's not necessary to add this method for player-only commands
    }

    /**
     *
     * @see PluginCommand#execute(CommandSender, String[]) for when isRequiresPlayer() is false
     */
    @Override
    public void execute(Player player, String[] args) {
        // Command code goes here

        player.sendMessage(MessageTools.parseText("<green>Hi <player>!",
                Placeholder.component("player", player.displayName())));
    }
}
