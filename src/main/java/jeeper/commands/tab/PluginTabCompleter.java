package jeeper.commands.tab;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used to help create tab-completers for commands.
 */
public abstract class PluginTabCompleter implements TabCompleter {

    /**
     * @return The name of the command this tab-completer is for.
     **/
    abstract public List<String> getNames();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> results = tabCompleter((Player) sender, args);

        if (results == null) {
            List<String> playerList = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                playerList.add(p.getName());
            }

            return playerList;
        }

        return results;

    }

    /**
     * This method is called when a player attempts to tab-complete a command
     *
     * @param player The player typing the command.
     * @param args The arguments currently typed in the command.
     * @return The list of Strings to be tab-completed.
     **/
    public abstract List<String> tabCompleter(Player player, @NotNull String[] args);

}
