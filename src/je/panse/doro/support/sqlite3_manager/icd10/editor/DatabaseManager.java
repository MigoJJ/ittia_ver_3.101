package je.panse.doro.support.sqlite3_manager.icd10.editor;

import java.sql.Connection;	
import java.sql.ResultSet;

public class DatabaseManager {
    private Connection conn;
    
    public DatabaseManager() { connectDatabase(); }
    
    private void connectDatabase() { /* connection logic */ }
    public void executeQuery(String query) { /* execution logic */ }
    public void executeUpdate(String query) { /* update logic */ }
    public void closeConnection() { /* closure logic */ }
}
