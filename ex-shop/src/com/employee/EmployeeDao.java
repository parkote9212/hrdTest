package com.employee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.main.DBConnection;

public class EmployeeDao {

	public void createTables() {
		String sql = """
				CREATE TABLE Employee (
					EmpNo INT PRIMARY KEY AUTO_INCREMENT,
					EmpName VARCHAR(30) NOT NULL UNIQUE,
					Dept VARCHAR(20) NOT NULL,
					HireDate DATE NOT NULL,
					Salary INT CHECK(Salary >= 2000000)
					)
					""";

		try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			System.out.println("테이블 생성 성공");
		} catch (SQLException e) {
			System.out.println("테이블 생성 실패");
		}
	}

	public void dropTable(String tblName) {
		String sql = "DROP TABLE IF EXISTS " + tblName;
		try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			System.out.println("테이블 제거 성공");
		} catch (SQLException e) {
			System.out.println("테이블 제거 실패");
		}

	}

//	부서입력시 조회
	public List<Employee> selectTable(String dept) {
//		 Employee 객체만 담을수 있는 ArrayList를 생성하여 List 타입의 employeeList 변수에 저장하라
		List<Employee> employeeList = new ArrayList<>();
		String sql = "SELECT EmpNo, EmpName, Dept, HireDate, Salary FROM Employee WHERE Dept = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 1. PreparedStatement의 ?에 부서명(dept) 값을 설정
			pstmt.setString(1, dept);

			// 2. executeQuery()로 쿼리를 실행하고 결과를 ResultSet으로 받음
			try (ResultSet rs = pstmt.executeQuery()) {

				// 3. ResultSet의 각 행(row)을 반복하면서 Employee 객체로 변환
				while (rs.next()) {
					Employee employee = new Employee(rs.getInt("EmpNo"), rs.getString("EmpName"), rs.getString("Dept"),
							rs.getDate("HireDate").toLocalDate(), // sql.Date -> time.LocalDate
							rs.getInt("Salary"));
					employeeList.add(employee); // 리스트에 추가
				}
				System.out.println("리스트 생성 성공");
			}
		} catch (SQLException e) {
			System.err.println("부서별 조회 실패: " + e.getMessage());
		}

		// 4. 조회된 데이터가 담긴 리스트를 반환
		return employeeList;
	}

//월급 조건식 조회	
	public List<Employee> selectTable(int salary) {
//		 Employee 객체만 담을수 있는 ArrayList를 생성하여 List 타입의 employeeList 변수에 저장하라
		List<Employee> employeeList = new ArrayList<>();
		// Employee 테이블의 모든 컬럼을 조회하도록 SQL 수정
		String sql = "SELECT EmpNo, EmpName, Dept, HireDate, Salary FROM Employee WHERE (Salary >= ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 1. PreparedStatement의 ?에 부서명(dept) 값을 설정
			pstmt.setInt(1, salary);

			// 2. executeQuery()로 쿼리를 실행하고 결과를 ResultSet으로 받음
			try (ResultSet rs = pstmt.executeQuery()) {

				// 3. ResultSet의 각 행(row)을 반복하면서 Employee 객체로 변환
				while (rs.next()) {
					Employee employee = new Employee(rs.getInt("EmpNo"), rs.getString("EmpName"), rs.getString("Dept"),
							rs.getDate("HireDate").toLocalDate(), // sql.Date -> time.LocalDate
							rs.getInt("Salary"));
					employeeList.add(employee); // 리스트에 추가
				}
				System.out.println("리스트 생성 성공");
			}
		} catch (SQLException e) {
			System.err.println("조건식 조회 실패: " + e.getMessage());
		}

		// 4. 조회된 데이터가 담긴 리스트를 반환
		return employeeList;
	}

	public void insertEmployeesBatch(List<Employee> employees) {
		String sql = "INSERT INTO Employee (EmpName, Dept, HireDate, Salary) VALUES (?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

//			 각 데이터를 배치(batch)에 추가
			for (Employee emp : employees) {
				pstmt.setString(1, emp.getEmpName());
				pstmt.setString(2, emp.getDept());
//				자바의 LocalDate를 JDBC용 java.sql.Date로 변환
				pstmt.setDate(3, Date.valueOf(emp.getHireDate()));
				pstmt.setInt(4, emp.getSalary());
				pstmt.addBatch();
			}

			int[] result = pstmt.executeBatch();

			System.out.println("배치 작업 성공! 총 " + result.length + "건의 데이터가 처리되었습니다.");

		} catch (SQLException e) {
			System.err.println("배치 작업 실패: " + e.getMessage());
		}
	}

	public boolean insertEmployee(String empName, String dept, String hireDate, int salary) {
		String sql = "INSERT INTO Employee (EmpName, Dept, HireDate, Salary) VALUES (?, ?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, empName);
			pstmt.setString(2, dept);
			pstmt.setDate(3, Date.valueOf(hireDate));
			pstmt.setInt(4, salary);

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("데이터 삽입 성공");
				System.out.println("이름: " + empName + " 부서명: " + dept + " 입사일자: " + hireDate + " 월급: " + salary);
				return true;
			} else {
				System.out.println("데이터 삽입 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("데이터 삽입 실패: " + e.getMessage());
			return false;
		}
	}

//	이름을 통해 조회하고 월급을 변경하는 메소드
	public boolean updateEmployee(String empName, int salary) {
		String sql = "UPDATE Employee SET Salary = ? WHERE EmpName = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salary);
			pstmt.setString(2, empName);
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("데이터 수정 성공");
				System.out.println("이름: " + empName + " 월급: " + salary);
				return true;
			} else {
				System.out.println("데이터 찾기 실패");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("데이터 수정 실패: " + e.getMessage());
			return false;
		}

	}

//	id로 조회 및 삭제
	public boolean deleteEmployee(int id) {
		String sql = """
				DELETE FROM Employee
				WHERE EmpNo = ?
				""";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("데이터 삭제 성공");
				return true;
			} else {
				System.out.println("데이터 찾기 실패");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("데이터 삭제 실패: " + e.getMessage());
			return false;
		}
	}
}
