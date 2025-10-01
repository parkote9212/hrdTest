package com.shop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.main.DBConnection;
import com.shop.dto.CustomerMaxSaleDto;
import com.shop.dto.CustomerSaleDto;
import com.shop.dto.Sale;
import com.shop.dto.ShopMember;

public class ShopDao {

	// --- 테이블 생성 및 삭제 ---
	// 이 메소드들은 주로 개발/설정 단계에서 사용되므로 기존 예외 처리 방식을 유지해도 괜찮습니다.
	public void createTables() {
	    String sqlShopMember = """
	            CREATE TABLE ShopMember (
	                CustNo INT PRIMARY KEY AUTO_INCREMENT,
	                CustName VARCHAR(30) NOT NULL,
	                Phone VARCHAR(13) UNIQUE,
	                Address VARCHAR(50),
	                JoinDate DATE NOT NULL,
	                Grade CHAR(1) CHECK(Grade IN ('A', 'B', 'C')),
	                City CHAR(2)
	            )
	            """;
	    
	    String sqlSale = """
	            CREATE TABLE Sale (
	                SaleNo INT PRIMARY KEY AUTO_INCREMENT,
	                CustNo INT,
	                PCost INT,
	                Amount INT,
	                Price INT,
	                PCode CHAR(3),
	                FOREIGN KEY (CustNo) REFERENCES ShopMember(CustNo)
	            )
	            """;

	    try (Connection conn = DBConnection.getConnection(); 
	         Statement stmt = conn.createStatement()) {
	        // ShopMember 테이블 먼저 생성
	        stmt.execute(sqlShopMember);
	        // Sale 테이블 생성
	        stmt.execute(sqlSale);
	        System.out.println("ShopMember 및 Sale 테이블 생성 성공");
	    } catch (SQLException e) {
	        System.out.println("테이블 생성 실패: " + e.getMessage());
	    }
	}
	
	
	    public void dropTable(String tblName) {
	        String sql = "DROP TABLE IF EXISTS " + tblName;
	        try (Connection conn = DBConnection.getConnection(); 
	             Statement stmt = conn.createStatement()) {
	            stmt.execute(sql);
	            System.out.println(tblName + " 테이블 제거 성공");
	        } catch (SQLException e) {
	            System.out.println(tblName + " 테이블 제거 실패: " + e.getMessage());
	        }
	    }

	    public List<ShopMember> findAllShopMembers() {
	        List<ShopMember> members = new ArrayList<>();
	        String sql = "SELECT * FROM ShopMember"; // 모든 컬럼을 조회하는 SQL

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                // ResultSet의 각 행(row)을 ShopMember 객체로 변환
	                ShopMember member = new ShopMember(
	                    rs.getInt("CustNo"),
	                    rs.getString("CustName"),
	                    rs.getString("Phone"),
	                    rs.getString("Address"),
	                    rs.getDate("JoinDate").toLocalDate(),
	                    rs.getString("Grade").charAt(0),
	                    rs.getString("City")
	                );
	                // 리스트에 추가
	                members.add(member);
	            }
	        } catch (SQLException e) {
	            // 예외가 발생하면 RuntimeException으로 감싸서 호출한 쪽에 알림
	            throw new RuntimeException("전체 회원 조회 중 DB 오류 발생", e);
	        }
	        
	        // 조회된 데이터가 담긴 리스트를 반환
	        return members;
	    }
	    
	    public List<Sale> findAllSales() {
	        List<Sale> sales = new ArrayList<>();
	        String sql = "SELECT * FROM Sale"; // 모든 컬럼을 조회하는 SQL

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                // ResultSet의 각 행(row)을 Sale 객체로 변환
	                Sale sale = new Sale(
	                    rs.getInt("SaleNo"),
	                    rs.getInt("CustNo"),
	                    rs.getInt("PCost"),
	                    rs.getInt("Amount"),
	                    rs.getInt("Price"),
	                    rs.getString("PCode")
	                );
	                // 리스트에 추가
	                sales.add(sale);
	            }
	        } catch (SQLException e) {
	            // 예외가 발생하면 RuntimeException으로 감싸서 호출한 쪽에 알림
	            throw new RuntimeException("전체 판매 내역 조회 중 DB 오류 발생", e);
	        }
	        
	        // 조회된 데이터가 담긴 리스트를 반환
	        return sales;
	    }
	    
	    
	// --- 1. 회원 등록 ---
	public void insertMember(ShopMember member) {
		String sql = "INSERT INTO ShopMember(CustName, Phone, Address, JoinDate, Grade, City) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); 
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, member.getCustName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getAddress());
			pstmt.setDate(4, Date.valueOf(member.getJoinDate()));
			pstmt.setString(5, String.valueOf(member.getGrade()));
			pstmt.setString(6, member.getCity());
			pstmt.executeUpdate();
			System.out.println(member.getCustName() + " 회원 정보가 등록되었습니다.");
		} catch (SQLException e) {
			throw new RuntimeException("회원 등록 중 DB 오류 발생", e);
		}
	}

	// --- 2. 회원 조회 ---
	public List<ShopMember> findMembersByGrade(char grade) {
		List<ShopMember> members = new ArrayList<>();
		String sql = "SELECT * FROM ShopMember WHERE Grade = ?";
		try (Connection conn = DBConnection.getConnection(); 
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, String.valueOf(grade));
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					members.add(new ShopMember(rs.getInt("CustNo"), rs.getString("CustName"), rs.getString("Phone"),
							rs.getString("Address"), rs.getDate("JoinDate").toLocalDate(),
							rs.getString("Grade").charAt(0), rs.getString("City")));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("등급별 회원 조회 중 DB 오류 발생", e);
		}
		return members;
	}

	public List<ShopMember> findMembersAfterDate(LocalDate date) {
		List<ShopMember> members = new ArrayList<>();
		String sql = "SELECT * FROM ShopMember WHERE JoinDate >= ?";
		try (Connection conn = DBConnection.getConnection(); 
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setDate(1, Date.valueOf(date));
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					members.add(new ShopMember(rs.getInt("CustNo"), rs.getString("CustName"), rs.getString("Phone"),
							rs.getString("Address"), rs.getDate("JoinDate").toLocalDate(),
							rs.getString("Grade").charAt(0), rs.getString("City")));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("가입일자별 회원 조회 중 DB 오류 발생", e);
		}
		return members;
	}

	// --- 3. 판매 등록 ---
	public void insertSale(Sale sale) {
		String sql = "INSERT INTO Sale(CustNo, PCost, Amount, Price, PCode) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); 
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, sale.getCustNo());
			pstmt.setInt(2, sale.getPCost());
			pstmt.setInt(3, sale.getAmount());
			pstmt.setInt(4, sale.getPrice());
			pstmt.setString(5, sale.getPCode());
			pstmt.executeUpdate();
			System.out.println("판매 정보가 등록되었습니다.");
		} catch (SQLException e) {
			throw new RuntimeException("판매 등록 중 DB 오류 발생", e);
		}
	}

	// --- 4. 판매 조회 ---
	public List<CustomerSaleDto> findAllCustomerSales() {
		List<CustomerSaleDto> list = new ArrayList<>();
		String sql = "SELECT M.CustNo, M.CustName, S.Price FROM ShopMember M JOIN Sale S ON M.CustNo = S.CustNo";
		try (Connection conn = DBConnection.getConnection(); 
			 PreparedStatement pstmt = conn.prepareStatement(sql); 
			 ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(new CustomerSaleDto(rs.getInt("CustNo"), rs.getString("CustName"), rs.getInt("Price")));
			}
		} catch (SQLException e) {
			throw new RuntimeException("전체 판매 내역 조회 중 DB 오류 발생", e);
		}
		return list;
	}

	public List<CustomerMaxSaleDto> findMaxPricePerCustomer() {
		List<CustomerMaxSaleDto> list = new ArrayList<>();
		String sql = "SELECT M.CustNo, M.CustName, MAX(S.Price) AS MaxPrice FROM ShopMember M JOIN Sale S ON M.CustNo = S.CustNo GROUP BY M.CustNo, M.CustName";
		try (Connection conn = DBConnection.getConnection(); 
			 PreparedStatement pstmt = conn.prepareStatement(sql); 
			 ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(new CustomerMaxSaleDto(rs.getInt("CustNo"), rs.getString("CustName"), rs.getInt("MaxPrice")));
			}
		} catch (SQLException e) {
			throw new RuntimeException("회원별 최고 구매액 조회 중 DB 오류 발생", e);
		}
		return list;
	}

	// --- 5. 데이터 수정 ---
	public void updateMemberGrade(String custName, char newGrade) {
		String sql = "UPDATE ShopMember SET Grade = ? WHERE CustName = ?";
		try (Connection conn = DBConnection.getConnection(); 
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, String.valueOf(newGrade));
			pstmt.setString(2, custName);
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				System.out.println(custName + " 회원의 등급이 " + newGrade + "로 수정되었습니다.");
			} else {
				System.out.println(custName + " 회원을 찾을 수 없습니다.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("회원 등급 수정 중 DB 오류 발생", e);
		}
	}

	// --- 6. 데이터 삭제 (Transaction 처리 및 RuntimeException) GPT99% ---
	public void deleteMember(int custNo) {
		String saleSql = "UPDATE Sale SET CustNo = NULL WHERE CustNo = ?";
		String memberSql = "DELETE FROM ShopMember WHERE CustNo = ?";
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 트랜잭션 시작

			try (PreparedStatement pstmt1 = conn.prepareStatement(saleSql)) {
				pstmt1.setInt(1, custNo);
				pstmt1.executeUpdate();
			}

			try (PreparedStatement pstmt2 = conn.prepareStatement(memberSql)) {
				pstmt2.setInt(1, custNo);
				int rows = pstmt2.executeUpdate();
				if (rows > 0) {
					System.out.println(custNo + "번 회원 정보가 삭제되었습니다.");
				} else {
					System.out.println("삭제할 회원을 찾을 수 없습니다.");
				}
			}

			conn.commit(); // 모든 작업 성공 시 커밋

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback(); // 오류 발생 시 롤백
				} catch (SQLException ex) {
					// 롤백 실패는 심각한 문제이므로, 원본 예외와 함께 로그를 남기는 것이 좋음
					throw new RuntimeException("롤백 실패", ex);
				}
			}
			// 원본 예외를 던져서 호출한 쪽에 실패했음을 알림
			throw new RuntimeException("회원 삭제 트랜잭션 중 DB 오류 발생", e);
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true); // 커넥션 상태를 원래대로 복구
					conn.close(); // 커넥션 반납
				} catch (SQLException e) {
					e.printStackTrace(); // 이 예외는 보통 무시되지만, 로그를 남기는 것이 좋음
				}
			}
		}
	}
}