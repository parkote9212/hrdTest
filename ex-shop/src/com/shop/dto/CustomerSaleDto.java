package com.shop.dto;

/**
 * 회원의 개별 구매 내역 정보를 담는 DTO (shopmember, sale JOIN 결과)
 */
public class CustomerSaleDto {

    private int custNo;       
    private String custName;  
    private int price;       

    // 생성자
    public CustomerSaleDto(int custNo, String custName, int price) {
        this.custNo = custNo;
        this.custName = custName;
        this.price = price;
    }

    // Getter 메소드
    public int getCustNo() {
        return custNo;
    }

    public String getCustName() {
        return custName;
    }

    public int getPrice() {
        return price;
    }

    // 객체 출력을 위한 toString() 메소드
    @Override
    public String toString() {
        return "CustomerSaleDto{" +
                "custNo=" + custNo +
                ", custName='" + custName + '\'' +
                ", price=" + price +
                '}';
    }
}