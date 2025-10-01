package com.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class DBmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (Connection conn = DBConnection.getConnection()) {
			System.out.println("==DB 연결 성공 ==");
		} catch (SQLException e) {
			System.err.println("==DB 연결 실패 ==" + e.getMessage());
		}
	
	}

}
