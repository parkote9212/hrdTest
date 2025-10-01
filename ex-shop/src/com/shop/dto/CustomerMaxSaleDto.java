package com.shop.dto;

/**
 * 회원의 최고 구매 금액 정보를 담는 DTO (shopmember, sale JOIN 및 Max() 집계 결과)
 */
public class CustomerMaxSaleDto {

    private int custNo;       // 회원번호
    private String custName;  // 회원성명
    private int maxPrice;     // 최고 구매 금액

    // 생성자
    public CustomerMaxSaleDto(int custNo, String custName, int maxPrice) {
        this.custNo = custNo;
        this.custName = custName;
        this.maxPrice = maxPrice;
    }

    // Getter 메소드
    public int getCustNo() {
        return custNo;
    }

    public String getCustName() {
        return custName;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    // 객체 출력을 위한 toString() 메소드
    @Override
    public String toString() {
        return "CustomerMaxSaleDto{" +
                "custNo=" + custNo +
                ", custName='" + custName + '\'' +
                ", maxPrice=" + maxPrice +
                '}';
    }
}