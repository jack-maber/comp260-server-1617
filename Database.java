import java.sql.*;
/* TO ALLOW JAVA TO CONNECT TO THE DATABASE YOU NEED TO ADD sqlite-jdbc TO YOUR IMPORTED LIBRARIES. (Currently using version 3.15.1)*/
/* sqlite-jdbc can be downloaded form here: https://bitbucket.org/xerial/sqlite-jdbc/downloads */
// http://www.sqlitetutorial.net/sqlite-java/

public class Database {
	
	static String url = "jdbc:sqlite:./db/" + "highscores.db";
	
	// SQL statement that is to be executed
    static String highscoresTable = "CREATE TABLE IF NOT EXISTS highscores (\n"
            + "	id integer PRIMARY KEY,\n"
            + "	name text NOT NULL,\n"
            + " score integer NOT NULL\n"
            + ");";
    
    
    
	
	// Will try and access database, however if the filename does not exist, it will create the database.
	public static void accessHighscoreDatabase() 
	{
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                
                // Execute SQL statement
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(highscoresTable);
                    
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public static void insertHighscoreData(int ID, String name, int score)
	{
		String sql = "INSERT INTO highscores(id,name,score) VALUES(?,?,?)";
		 
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            pstmt.setString(2, name);
            pstmt.setInt(3, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

}
