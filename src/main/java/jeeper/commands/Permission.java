package jeeper.commands;

/**
 * All permissions used throughout the plugin.
 */
public enum Permission {
    PERMISSION("example.permission"),
    PLUGIN_TEMPLATE("jeeper.plugintemplate");

    private final String name;

    Permission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
