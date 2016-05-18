package database;

import java.util.ResourceBundle;

/**
 * This is the class that provides access to resources that are needed to connect to the database.
 */
public class DBResourceManager {

    private final static DBResourceManager instance = new DBResourceManager();
    private ResourceBundle bundle =
            ResourceBundle.getBundle("database.db");

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
