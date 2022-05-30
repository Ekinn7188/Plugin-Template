# Plugin Setup

* Edit [plugin.yml](src/main/resources/plugin.yml)

* Check group, minecraft version, description, and pluginName in [build.gradle.kts](build.gradle.kts).

* Change ``rootProject.name`` in [settings.gradle.kts](settings.gradle.kts).

* For config file creation, see [Main#startFileSetup()](src/main/java/jeeper/Main.java). 
Also see the [PluginTemplate](src/main/java/jeeper/commands/PluginTemplate.java) command for reloading configuration files.

* Check out [PluginTemplateCompleter](src/main/java/jeeper/commands/tab/PluginTemplateCompleter.java) for an example of a tab completer.

* For larger plugins, [reflections](https://github.com/ronmamo/reflections) is used to initialize all commands, listeners, and tab completers.

* I use [SQLite](https://www.sqlite.org/index.html) as a database. Check the database section below for information on setup.

* You can run the ``runServer`` gradle task to run/generate a server and compile the plugin for testing. The server will be created in a directory titled ``run``.

* The ``cleanServer`` task will clear database info, config files, and player info from the server 

* The ``emptyConfig`` task will remove all config files from the plugin. 

# Important Classes

* The [PluginTabCompleter](src/main/java/jeeper/commands/tab/PluginTabCompleter.java) class is an abstract class made to 
help create TabCompleters. If you return null with the ``tabCompleter(player, args)`` method, a player list will be shown in the completer.

* The [PluginCommand](src/main/java/jeeper/commands/PluginCommand.java) class is an abstract class made to help create Commands. 
It has a method for player-only commands (``execute(player, args)``) and a method for commands that can be 
executed both in console and by players (``execute(sender, args)``). It can do permission checks for 
permissions located in the [Permission](src/main/java/jeeper/commands/Permission.java) enum. 

* The [Permission](src/main/java/jeeper/commands/Permission.java) enum is used 
to store all registered permissions within the plugin.

# Database
* Go to [build.gradle.kts](build.gradle.kts) and search for ``dbName``
This will be the name of the database in the plugin's generated files.

* The [V1__PluginTemplate.sql](src/main/resources/db/migration/V1__PluginTemplate.sql) file is used to create the database. 
You can see an example table written.

* Everything in the [database](src/main/java/jeeper/database) package is used to interact with the database. 
It shouldn't need any configuration.

* Check out the ``onLoad()`` method in [Main.java](src/main/java/jeeper/Main.java). The database is initialized here. 
The ``dbName`` String in ``SQLite#databaseSetup(dbPath, dbName)`` should be the same as the ``dbName`` set in gradle earlier.

* In order to use jooq to interact with the database in code, you need to run the ``generateJooq`` gradle task.

Note to self:
* Create task cleanServer() and task emptyConfig() in build.gradle.kts after testing plugin