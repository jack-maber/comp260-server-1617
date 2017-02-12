import java.sql.*;
/* TO ALLOW JAVA TO CONNECT TO THE DATABASE YOU NEED TO ADD sqlite-jdbc TO YOUR IMPORTED LIBRARIES. */
/* sqlite-jdbc can be downloaded form here: https://bitbucket.org/xerial/sqlite-jdbc/downloads */
// http://www.sqlitetutorial.net/sqlite-java/

public class Database {
	
	static String cellDatabaseFileName = "cellDatabase";
	
	
	// SQL statement that is to be executed
    static String cellTableData = "CREATE TABLE IF NOT EXISTS highscores (\n"
            + "	id integer PRIMARY KEY,\n"
            + "	name text NOT NULL,\n"
            + "	capacity real\n"
            + ");";
	
	// Will try and access database, however if the filename does not exist, it will create the database.
	public static void accessDatabase(String fileName) 
	{
		 
        String url = "jdbc:sqlite:./db/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                
                // Execute SQL statement
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(cellTableData);
                    
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
