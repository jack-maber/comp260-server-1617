import java.sql.*;
/* TO ALLOW JAVA TO CONNECT TO THE DATABASE YOU NEED TO ADD sqlite-jdbc TO YOUR IMPORTED LIBRARIES. (Currently using version 3.15.1)*/
/* sqlite-jdbc can be downloaded form here: https://bitbucket.org/xerial/sqlite-jdbc/downloads */
// http://www.sqlitetutorial.net/sqlite-java/

public class Database {
	static final String highscoreUrl = "jdbc:sqlite:./db/" + "highscores.db";
	static final String problemSetsUrl = "jdbc:sqlite:./db/" + "problemSets.db";

	// SQL statement that is to be executed
	static final String highscoresTable = "CREATE TABLE IF NOT EXISTS highscores (\n"
			+ "	id integer PRIMARY KEY,\n"
			+ "	name text NOT NULL,\n"
			+ " score integer NOT NULL\n"
			+ ");";
	
	// String to create problemQuestions Database
	static final String problemQuestions = "CREATE TABLE IF NOT EXISTS problemSets(\n"
			+ "	id integer PRIMARY KEY,\n"
			+ "	Questions text NOT NULL,\n"
			+ " Answers text NOT NULL \n"
			+ ");";
	
	// Keep the questions and answers in the same order
	static final String[] questionList = {"Question 1", "Question 2", "Question 3"};
	static final String[] answerList = {"Q1Answer", "Q2Answer", "Q3Answer"};

	/* Will try and access database, however if the filename does not exist, it will create the database */
    static synchronized void accessDatabase(String url, String DatabaseName) {
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				// Execute SQL statement
				try (Statement stmt = conn.createStatement()) {
					stmt.execute(DatabaseName);

				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("Database has been created.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/* Prints the highscore database */
	public static synchronized void printHighscoreDatabase() {
		final String sql = "SELECT id, name, score FROM highscores";

		// Connect to the database and execute the sql string
		try (Connection conn = DriverManager.getConnection(highscoreUrl);
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

	/* Insert a new player into the highscore database */
	public static synchronized void insertIntoHighscoreDatabase(int ID, String name, int score) {
		final String HighscoreSQLQuery = "INSERT INTO highscores(id,name,score) VALUES(?,?,?)";

		accessDatabase(highscoreUrl, highscoresTable);
		try (Connection conn = DriverManager.getConnection(highscoreUrl); PreparedStatement pstmt = conn.prepareStatement(HighscoreSQLQuery)) {
			pstmt.setInt(1, ID);
			pstmt.setString(2, name);
			pstmt.setInt(3, score);
			pstmt.executeUpdate();
			System.out.println("Successfully inserted score to database");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/* Insert problem sets into problemsetDatabase */
	public static synchronized void createorPopulateProblemSetDatabase()
	{
		// Check to see if database exists and if not create it
		accessDatabase(problemSetsUrl, problemQuestions);
		final String problemSetQuery = "INSERT INTO problemSets(id, Questions, Answers) VALUES(?,?,?)";
		
		// Loop through the questions in the list and add them to the database
		for(int i = 0; i < questionList.length; i++)
		{
			try (Connection conn = DriverManager.getConnection(problemSetsUrl); PreparedStatement pstmt = conn.prepareStatement(problemSetQuery)) {
				pstmt.setInt(1, i);
				pstmt.setString(2, questionList[i]);
				pstmt.setString(3, answerList[i]);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/* Will return a problem string from the list of problem questions  */
	public static synchronized void printProblemSet()
	{
		final String problem = "SELECT id, Questions, Answers FROM problemSets";
		
		try (Connection conn = DriverManager.getConnection(problemSetsUrl);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(problem)) {

			// loop through the result set and prints the Questions to the console
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("Questions")+ "\t" + rs.getString("Answers"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	

}
