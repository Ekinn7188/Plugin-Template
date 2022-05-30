package jeeper.database;

import PluginTemplate.db.Tables;
import jeeper.Main;
import org.jooq.DSLContext;

import java.util.UUID;

/**
 * A handy class for database operations.
 */
public class DatabaseTools {

    static DSLContext dslContext = Main.getDslContext();

    /**
     * Add a user to the database, providing player's UUID
     */
    public static void addUser(UUID uuid) {
        dslContext.insertInto(Tables.USERS, Tables.USERS.USERUUID).values(uuid.toString()).execute();
    }

    /**
     * Add a user to the database, providing player's UUID
     */
    public static void addUser(String uuid) {
        dslContext.insertInto(Tables.USERS, Tables.USERS.USERUUID).values(uuid).execute();
    }

    /**
     * Return the userid stored in the database, using the player's UUID
     * @return the id stored, or -1 if it does not exist
     */
    public static int getUserID(UUID uuid) {
        var record = dslContext.select(Tables.USERS.USERID)
                .from(Tables.USERS).where(Tables.USERS.USERUUID.eq(uuid.toString())).fetchAny();
        if (record == null) {
            return -1;
        }

        return record.get(Tables.USERS.USERID);
    }

}
