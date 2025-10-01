package com.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.employee.Employee;
import com.employee.EmployeeDao;

public class DBmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (Connection conn = DBConnection.getConnection()) {
			System.out.println("==DB 연결 성공 ==");
		} catch (SQLException e) {
			System.err.println("==DB 연결 실패 ==" + e.getMessage());
		}
		EmployeeDao dao = new EmployeeDao();
//		create
//		dao.createTables();

//		drop
//		dao.dropTable("employee");

//		대규모 데이터 삽입(배치사용)
//		배치로 삽입할 데이터 가져오기
		/*
		 * List<Employee> employees = EmployeeData.getEmployees(); // 배치 실행
		 * dao.insertEmployeesBatch(employees);
		 */

//		select
//		부서가 개발부

		List<Employee> employees = dao.selectTable("개발부");
		for (Employee emp : employees) {
			System.out.println(emp);
		}

//		급여 조건부 검색
		/*
		 * List<Employee> employees = dao.selectTable(3000000); for (Employee emp :
		 * employees) { System.out.println(emp); }
		 */

//		insert
//		직원명,부서,입사일자,월급
		/*
		 * dao.insertEmployee("홍길동", "영업부", "2020-03-01", 2500000);
		 * dao.insertEmployee("이순신", "인사부", "2019-07-15", 3200000);
		 * dao.insertEmployee("강감찬", "개발부", "2021-01-10", 2800000);
		 */

// 		update(이름,월급)
//		emp.updateEmployee("이순신", 3500000);

//		delete(id)
//		dao.deleteEmployee(1);
	}

}
