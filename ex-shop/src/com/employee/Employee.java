package com.employee;

import java.time.LocalDate;

/**
 * Employee 테이블의 데이터를 담는 DTO(Data Transfer Object) 클래스
 */
public class Employee {

	private int empNo; // 직원 번호 (INT)
	private String empName; // 직원 이름 (VARCHAR)
	private String dept; // 부서 (VARCHAR)
	private LocalDate hireDate; // 입사일 (DATE)
	private int salary; // 급여 (INT)

	// 모든 필드를 초기화하는 생성자
	public Employee(int empNo, String empName, String dept, LocalDate hireDate, int salary) {
		this.empNo = empNo;
		this.empName = empName;
		this.dept = dept;
		this.hireDate = hireDate;
		this.salary = salary;
	}

	public Employee(String empName, String dept, String hireDate, int salary) {
		this.empName = empName;
		this.dept = dept;
		// hireDate가 문자열로 들어올 경우를 대비해 parse 로직 추가
		this.hireDate = LocalDate.parse(hireDate);
		this.salary = salary;
	}

	// 각 필드의 값을 외부에서 읽을 수 있도록 하는 Getter 메소드들
	public int getEmpNo() {
		return empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public String getDept() {
		return dept;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public int getSalary() {
		return salary;
	}

	// 객체를 쉽게 출력하여 확인하기 위한 toString() 메소드
	@Override
	public String toString() {
		return "Employee{" + "empNo=" + empNo + ", empName='" + empName + '\'' + ", dept='" + dept + '\''
				+ ", hireDate=" + hireDate + ", salary=" + salary + '}';
	}
}