package com.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String DB_URL = "jdbc:mariadb://localhost:3306/hrdtest";
	private static final String USER = "root";
	private static final String PASS = "7305";

	// DB Connection을 얻는 메소드
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}
}
