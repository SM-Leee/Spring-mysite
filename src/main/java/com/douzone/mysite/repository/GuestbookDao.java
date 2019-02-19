package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	public List<GuestbookVo> getList(int page) {
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();

 		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

 		try {
			conn = getConnection();

 			// SQL문 준비
			String sql =
				"   select no," + 
				"          name," + 
				"	       message," + 
				"     	   date_format(reg_date, '%Y-%m-%d %h:%i:%s')" + 
				"     from guestbook" + 
				" order by reg_date desc" +
				"	  limit ?, 5";

 			// Statement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (page-1)*5);

 			rs = pstmt.executeQuery();

 			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String message = rs.getString(3);
				String reg_date = rs.getString(4);

 				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setMessage( message );
				vo.setReg_date(reg_date);

 				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

 		return list;
	}
	
	public int delete(GuestbookVo guestbookVo) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql ="delete from guestbook where no=? and password=password(?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, guestbookVo.getNo());
			pstmt.setString(2, guestbookVo.getPassword());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Error : "+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public long insert(GuestbookVo guestbookVo) {
		long result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql ="insert into guestbook values(null, ?,password(?),?,now())";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, guestbookVo.getName());
			pstmt.setString(2, guestbookVo.getPassword());
			pstmt.setString(3, guestbookVo.getMessage());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			
			sql = "select last_insert_id()";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getLong(1);		
			}
			
						
		} catch (SQLException e) {
			System.out.println("Error : "+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<GuestbookVo> getList(){
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select no, name, password, message, reg_date from guestbook order by no desc";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String reg_date = rs.getString(5);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setMessage(message);
				vo.setReg_date(reg_date);
				
				list.add(vo);
			}
			
			
			
		} catch (SQLException e) {
			System.out.println("Error : "+e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		try {
			// 1. JDBC Driver(MYSQL) 로딩
			Class.forName("com.mysql.jdbc.Driver");
			//pripertiy -> build path를 등록해줘야된다.

			// 2. 연결하기 ( jdbc:연결할database://ip:port/database이름 ) port번호는 생략가능하다.
			// url과 id와 password를 같이 입력해준다. (Connection 객체 얻어오기)
			String url = "jdbc:mysql://localhost:3306/webdb";
			conn = DriverManager.getConnection(url,"webdb","webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : "+e);
		}
		return conn;
	}
	
}
