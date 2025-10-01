package com.main;

import java.util.List;
import com.employee.dao.EmployeeDao;
import com.employee.dto.Employee;

public class DBmain {

    public static void main(String[] args) {
        // DAO 객체 생성은 try 블록 바깥에 두어도 괜찮습니다.
        EmployeeDao dao = new EmployeeDao();

        try {
            // 이제 별도의 연결 테스트 없이 바로 실제 로직을 실행합니다.
            // 만약 여기서 DB 연결이 실패하면 catch 블록으로 바로 이동합니다.
            
            System.out.println("== 개발부 직원 조회 ==");
            List<Employee> employees = dao.selectTable("개발부");

            if (employees.isEmpty()) {
                System.out.println("조회된 데이터가 없습니다.");
            } else {
                for (Employee emp : employees) {
                    System.out.println(emp);
                }
            }

            // ... 다른 DAO 메소드 호출 ...

        } catch (RuntimeException e) {
            // 모든 DB 관련 오류는 여기서 처리됩니다.
            System.err.println("데이터베이스 작업 중 오류가 발생했습니다.");
            // e.getMessage()는 구체적인 원인을 보여주므로 디버깅에 유용합니다.
            System.err.println("원인: " + e.getMessage()); 
        }
    }
}