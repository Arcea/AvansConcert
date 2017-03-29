package Controller;

import java.sql.*;

public class DatabaseController {

	private void initialize() {
	}

	public static ResultSet DBConnect(String table) {
		try {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/avansconcert", "root", "root");
			} catch (Exception e) {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
				Statement st = conn.createStatement();
				st.executeUpdate("CREATE DATABASE IF NOT EXISTS `avansconcert`;");

				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/avansconcert", "root", "root");
				st = conn.createStatement();
				st.executeUpdate(
						"CREATE TABLE IF NOT EXISTS `artists` (`id` INT(11) NOT NULL AUTO_INCREMENT, `artist` VARCHAR(45) DEFAULT NULL, `description` VARCHAR(45) DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=myisam AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;");
				st.executeUpdate(
						"INSERT IGNORE INTO `artists` (`id`, `artist`, `description`) VALUES(1,'Avenged Sevenfold','Metal band'), ( 2, 'Hollywood Undead', 'Rapcore band'), ( 3, 'Felkonne', 'Makes metal covers');");
				st.executeUpdate(
						"CREATE TABLE IF NOT EXISTS `stages` (`id` INT(11) NOT NULL AUTO_INCREMENT, `stage` VARCHAR(255) DEFAULT '0', `description` VARCHAR(255) DEFAULT '0', PRIMARY KEY (`id`)) ENGINE=myisam AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;");
				st.executeUpdate(
						"INSERT IGNORE INTO `stages`(`id`,`stage`,`description`) VALUES(1,'Stage 1','Stage for metal'), (2,'Podium 2','Stage for rap'),(3,'Stage 3','Stage for unkown artists'),( 4,'Stage 4','Extra stage');");
				st.executeUpdate(
						"CREATE TABLE IF NOT EXISTS `planning` (`id` INT(11) NOT NULL AUTO_INCREMENT, `artist_id` INT(11) DEFAULT '0', `stage_id` INT(11) DEFAULT '0', `start_time` DATETIME DEFAULT NULL, `end_time` DATETIME DEFAULT NULL, PRIMARY KEY (`id`), FOREIGN KEY `artist_id` (`artist_id`) REFERENCES artists.id, FOREIGN KEY `stage_id` (`stage_id`) REFERENCES stages.id) ENGINE=myisam AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;");
				st.executeUpdate(
						"INSERT IGNORE INTO `planning` (`id`, `artist_id`, `stage_id`, start_time, end_time) VALUES(1, 1, 1, '2017-03-28 22:00:00', '2017-03-28 23:00:00'), (2, 2, 2, '2017-03-28 22:00:00', '2017-03-28 23:00:00'), (3, 3, 3, '2017-03-28 22:00:00', '2017-03-28 23:00:00');");

			}
			Statement st = conn.createStatement();
			ResultSet rs;
			if (table != null) {
				rs = st.executeQuery("SELECT * FROM " + table);
			} else {
				rs = st.executeQuery("SELECT * FROM planning" + " LEFT JOIN artists ON planning.artist_id = artists.id"
						+ " LEFT JOIN stages on planning.stage_id = stages.id;");
			}
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void Delete(String table, int id) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/avansconcert", "root", "");
			Statement st = conn.createStatement();
			String sql = "";
			if (table == "planning") {
				sql = "DELETE FROM " + table + " WHERE id = " + id;
				st.executeUpdate(sql);
			} else {
				if (table == "artists") {
					sql = "DELETE FROM " + table + " WHERE id = " + id;
					st.executeUpdate(sql);
					sql = "DELETE FROM planning WHERE artist_id = " + id;
					st.executeUpdate(sql);
				} else if (table == "stages") {
					sql = "DELETE FROM " + table + " WHERE id = " + id;
					st.executeUpdate(sql);
					sql = "DELETE FROM planning WHERE stage_id = " + id;
					st.executeUpdate(sql);
				}
			}
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void EditArtist(int id, String artist, String description) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/avansconcert", "root", "");
			Statement st = conn.createStatement();
			String sql = "UPDATE artists SET artist='" + artist + "', description='" + description + "' WHERE id = "
					+ id;
			st.executeUpdate(sql);
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int AddArtist(String artist, String description) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/avansconcert", "root", "");
			Statement st = conn.createStatement();
			String sql = "INSERT INTO artists (artist, description) VALUES ('" + artist + "', '" + description + "')";
			st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			int key = 0;
			if (rs.next()) {
				key = rs.getInt(1);
				rs.close();
				st.close();
			}

			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void EditStage(int id, String stage, String description) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/avansconcert", "root", "");
			Statement st = conn.createStatement();
			String sql = "UPDATE stages SET stage='" + stage + "', description='" + description + "' WHERE id = " + id;
			st.executeUpdate(sql);
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int AddStage(String stage, String description) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/avansconcert", "root", "");
			Statement st = conn.createStatement();
			String sql = "INSERT INTO stages (stage, description) VALUES ('" + stage + "', '" + description + "')";
			st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			int key = 0;
			if (rs.next()) {
				key = rs.getInt(1);
				rs.close();
				st.close();
			}

			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
