package jeeper.commands;

import jeeper.Main;
import jeeper.utils.config.Config;
import org.bukkit.command.CommandSender;

/**
 * Command used to reload configuration files without restarting the plugin.
 */
public class PluginTemplate extends PluginCommand{
    @Override
    public String getName() {
        return "PluginTemplate";
    }

    @Override
    protected Permission getPermissionType() {
        return Permission.PLUGIN_TEMPLATE;
    }

    @Override
    public boolean isRequiresPlayer() {
        return false;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            Config config = Main.config();
            config.save();
            config.reload();
        }
    }
}
