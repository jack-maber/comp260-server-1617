import java.sql.*;
/* TO ALLOW JAVA TO CONNECT TO THE DATABASE YOU NEED TO ADD sqlite-jdbc TO YOUR IMPORTED LIBRARIES. (Currently using version 3.15.1)*/
/* sqlite-jdbc can be downloaded form here: https://bitbucket.org/xerial/sqlite-jdbc/downloads */
// http://www.sqlitetutorial.net/sqlite-java/

public class Database {
	static String url = "jdbc:sqlite:./db/" + "highscores.db";
	static String problemSetsUrl = "jdbc:sqlite:./db/" + "problemSets.db";

	// SQL statement that is to be executed
	static String highscoresTable = "CREATE TABLE IF NOT EXISTS highscores (\n"
			+ "	id integer PRIMARY KEY,\n"
			+ "	name text NOT NULL,\n"
			+ " score integer NOT NULL\n"
			+ ");";
	
	// String to create problemQuestions Database
	static String problemQuestions = "CREATE TABLE IF NOT EXISTS problemSets(\n"
			+ "	id integer PRIMARY KEY,\n"
			+ "	Questions text NOT NULL\n"
			+ ");";

	// Will try and access database, however if the filename does not exist, it
	// will create the database.
	public static synchronized void accessDatabase(String url, String DatabaseName) {
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				// Execute SQL statement
				try (Statement stmt = conn.createStatement()) {
					stmt.execute(DatabaseName);

				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				System.out.println(DatabaseName + "Database has been created.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static synchronized void printHighscoreDatabase() {
		String sql = "SELECT id, name, score FROM highscores";

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getInt("score"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static synchronized void insertIntoHighscoreDatabase(int ID, String name, int score) {
		String sql = "INSERT INTO highscores(id,name,score) VALUES(?,?,?)";

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, ID);
			pstmt.setString(2, name);
			pstmt.setInt(3, score);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Insert problem sets into problemsetDatabase
	public static synchronized void insertProblemSets()
	{
		// check to see if database exists
		accessDatabase(problemSetsUrl, problemQuestions);
		String problemSetQuery = "INSERT INTO problemSets(id, Questions) VALUES(?,?)";
		String QuestionOne = "Question One";
		int problemSetSize = 3;
		
		for(int i = 0; i < problemSetSize; i++)
		{
			try (Connection conn = DriverManager.getConnection(problemSetsUrl); PreparedStatement pstmt = conn.prepareStatement(problemSetQuery)) {
				pstmt.setInt(1, i);
				pstmt.setString(2, QuestionOne + i);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static synchronized void getProblemSet()
	{
		String problem = "SELECT id, Questions FROM problemSets";
		
		try (Connection conn = DriverManager.getConnection(problemQuestions);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(problem)) {

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("Questions"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	

}
