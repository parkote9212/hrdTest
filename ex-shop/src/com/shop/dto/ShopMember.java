package com.shop.dto;

import java.time.LocalDate;

/**
 * ShopMember 테이블의 데이터를 담는 DTO(Data Transfer Object) 클래스
 */
public class ShopMember {
	private int custNo;
	private String custName;
	private String phone;
	private String address;
	private LocalDate joinDate;
	private char grade;
	private String city;

	public ShopMember(int custNo, String custName, String phone, String address, LocalDate joinDate, char grade,
			String city) {
		this.custNo = custNo;
		this.custName = custName;
		this.phone = phone;
		this.address = address;
		this.joinDate = joinDate;
		this.grade = grade;
		this.city = city;
	}

	public ShopMember(String custName, String phone, String address, LocalDate joinDate, char grade, String city) {

		this.custName = custName;
		this.phone = phone;
		this.address = address;
		this.joinDate = joinDate;
		this.grade = grade;
		this.city = city;
	}

	public int getCustNo() {
		return custNo;
	}

	public String getCustName() {
		return custName;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public char getGrade() {
		return grade;
	}

	public String getCity() {
		return city;
	}

	// 객체를 쉽게 출력하여 확인하기 위한 toString() 메소드
	@Override
	public String toString() {
		return "ShopMember{" + "CustNo=" + custNo + ", CustName='" + custName + '\'' + ", Phone='" + phone + '\''
				+ ", Address=" + address + ", JoinDate=" + joinDate + ", grade=" + grade + ", city=" + city + '}';
	}

}
