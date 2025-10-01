package com.shop.dto;

/**
 * Sale 테이블의 데이터를 담는 DTO(Data Transfer Object) 클래스
 */
public class Sale {

	private int saleNo;
	private int custNo; 
	private int pCost; 
	private int amount; 
	private int price; 
	private String pCode;

	public Sale(int custNo, int pCost, int amount, int price, String pCode) {
		this.custNo = custNo;
		this.pCost = pCost;
		this.amount = amount;
		this.price = price;
		this.pCode = pCode;
	}

	public Sale(int saleNo, int custNo, int pCost, int amount, int price, String pCode) {
		this.saleNo = saleNo;
		this.custNo = custNo;
		this.pCost = pCost;
		this.amount = amount;
		this.price = price;
		this.pCode = pCode;
	}

	// 각 필드의 값을 외부에서 읽을 수 있도록 하는 Getter 메소드들
	public int getSaleNo() {
		return saleNo;
	}

	public int getCustNo() {
		return custNo;
	}

	public int getPCost() {
		return pCost;
	}

	public int getAmount() {
		return amount;
	}

	public int getPrice() {
		return price;
	}

	public String getPCode() {
		return pCode;
	}

	// 객체를 쉽게 출력하여 확인하기 위한 toString() 메소드
	@Override
	public String toString() {
		return "Sale{" + "saleNo=" + saleNo + ", custNo=" + custNo + ", pCost=" + pCost + ", amount=" + amount
				+ ", price=" + price + ", pCode='" + pCode + '\'' + '}';
	}
}
