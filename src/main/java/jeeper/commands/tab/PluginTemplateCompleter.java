package jeeper.commands.tab;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PluginTemplateCompleter extends PluginTabCompleter {
    @Override
    public List<String> getNames() {
        return List.of("PluginTemplate"); // List of all commands that use this tab completer
    }

    @Override
    public List<String> tabCompleter(Player player, @NotNull String[] args) {
        if (args.length == 1) return List.of("reload"); // If player is typing the first argument, return all tab completions
        else return List.of();
    }
}
