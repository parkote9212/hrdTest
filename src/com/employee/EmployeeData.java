package com.employee;

import java.util.Arrays;
import java.util.List;

public class EmployeeData {

	public static List<Employee> getEmployees() {
		List<Employee> employees = Arrays.asList(new Employee("김인사", "인사부", "2022-01-10", 3500000),
				new Employee("박개발", "개발부", "2021-03-15", 4200000), new Employee("이영업", "영업부", "2023-05-20", 3800000),
				new Employee("최기획", "기획부", "2020-11-01", 5000000), new Employee("정디자인", "디자인부", "2022-08-12", 3200000),
				new Employee("강인사", "인사부", "2023-02-28", 2800000), new Employee("조개발", "개발부", "2022-06-05", 6000000),
				new Employee("윤영업", "영업부", "2021-09-18", 4500000), new Employee("장기획", "기획부", "2023-01-15", 4100000),
				new Employee("임디자인", "디자인부", "2020-07-22", 3900000), new Employee("한개발", "개발부", "2023-07-07", 7500000),
				new Employee("오인사", "인사부", "2021-12-30", 3100000), new Employee("서영업", "영업부", "2022-04-11", 5200000),
				new Employee("신개발", "개발부", "2020-02-14", 8000000), new Employee("권기획", "기획부", "2023-10-02", 4800000),
				new Employee("황디자인", "디자인부", "2021-05-25", 3600000), new Employee("안영업", "영업부", "2023-08-19", 4000000),
				new Employee("송개발", "개발부", "2022-10-31", 5500000), new Employee("전인사", "인사부", "2020-09-09", 3300000),
				new Employee("홍기획", "기획부", "2021-01-20", 6200000));
		return employees;
	}
}