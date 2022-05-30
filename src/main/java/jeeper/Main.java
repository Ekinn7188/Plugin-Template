package jeeper;

import jeeper.commands.PluginCommand;
import jeeper.commands.tab.PluginTabCompleter;
import jeeper.database.SQLite;
import jeeper.utils.config.Config;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jooq.DSLContext;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class Main extends JavaPlugin {

    // The instance of the Main class that was used to run onEnable()
    private static Main plugin;
    // The configuration file of the plugin
    private static Config config;
    // A class used to edit the SQLite database
    private static DSLContext dslContext;

    @Override
    public void onEnable() {
        initializeClasses();

        // Use this to double-check dependencies for plugins
        // PluginEnable.checkForPluginDependencies(List.of("Plugin1", "Plugin2"), this.getName());
    }

    // onLoad() runs before onEnable()
    @Override
    public void onLoad() {
        plugin = this;
        startFileSetup();
        try {
            dslContext = SQLite.databaseSetup(this.getDataFolder().getCanonicalPath(), "jeeper.db");
        } catch (IOException e) {
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
        }
    }

    public static Main getPlugin() {
        return plugin;
    }
    public static Config config() {
        return config;
    }
    public static DSLContext getDslContext() {
        return dslContext;
    }

    private void startFileSetup(){
        saveDefaultConfig();

        /*


        config.yml


        */

        config = new Config("config", this.getName().toLowerCase());
        config.get().options().parseComments(true);
        config.save();

        //if making another file, add it to /PluginTemplate reload
    }

    protected static void initializeClasses(){
        String packageName = Main.getPlugin().getClass().getPackage().getName();
        //load Listeners in listeners package
        for(Class<?> listenerClass :new Reflections(packageName +".listeners").getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) listenerClass.getDeclaredConstructor().newInstance(); //must have empty constructor
                Main.getPlugin().getServer().getPluginManager().registerEvents(listener, Main.getPlugin());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }


        //load PluginCommands in commands package
        for(Class<? extends PluginCommand> commandClass :new Reflections(packageName +".commands").getSubTypesOf(PluginCommand.class)) {
            try {
                PluginCommand pluginCommand = commandClass.getDeclaredConstructor().newInstance();
                Objects.requireNonNull(Main.getPlugin().getCommand(pluginCommand.getName())).setExecutor(pluginCommand);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        //load PluginTabCompleters in commands.tab package
        for(Class<? extends PluginTabCompleter> completerClass :new Reflections(packageName +".commands.tab").getSubTypesOf(PluginTabCompleter.class)) {
            try {
                PluginTabCompleter tabCompleter = completerClass.getDeclaredConstructor().newInstance();
                for (String commandName : tabCompleter.getNames()) {
                    System.out.println(commandName);
                    Objects.requireNonNull(Main.getPlugin().getCommand(commandName)).setTabCompleter(tabCompleter);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

        }

    }
}


