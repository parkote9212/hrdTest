package com.main;

import com.shop.dao.ShopDao;
import com.shop.dto.ShopMember;
import java.time.LocalDate; // LocalDate를 사용하기 위해 import

public class DBmain {

    public static void main(String[] args) {
        ShopDao dao = new ShopDao();

        try {
        	
//        	매개변수값이 많을경우 객체 사용하여 매개변수로 넘긴다.
        	
            // 1. 등록할 회원 정보를 담은 ShopMember 객체 생성
//        	1.이름, 2.전화번호,3.주소 4.가입일자 5.등급 6.도시
            ShopMember newMember1 = new ShopMember(
                "김유신",                   // custName
                "010-5555-6666",          // phone
                "경주시 충효동",             // address
                LocalDate.of(2023, 5, 1), // joinDate
                'B',                      // grade
                "04"                      // city
            );
            
            //  2. DAO의 insertMember 메소드 호출 
                      dao.insertMember(newMember1);

        } catch (RuntimeException e) {
            System.err.println("데이터베이스 작업 중 오류가 발생했습니다.");
            System.err.println("원인: " + e.getMessage());
        }
    }
}