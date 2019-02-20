package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.BoardDaoException;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;

@Repository
public class BoardDao {
	
	public boolean delete(CommentVo commentVo) throws BoardDaoException{
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql ="delete from comment where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, commentVo.getNo());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			throw new BoardDaoException("댓글 삭제 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("댓글 삭제 오류");
			}
		}
		
		return result;
	}
	
	public boolean update(BoardVo boardVo) throws BoardDaoException{
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board set title=?, contents=? where no="+boardVo.getNo();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			throw new BoardDaoException("글 수정 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("글 수정 오류");
			}
		}
		
		return result;
	}
	
	public boolean updateInsert(BoardVo boardVo) throws BoardDaoException{
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "update board set order_no = (order_no+1) where group_no="+boardVo.getGroup_no()+" and order_no>="+ boardVo.getOrder_no();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			pstmt.close();
			
			sql ="insert into board values(null,?,?, now(),0,?,?,?,"+boardVo.getUser_no()+")";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setInt(3, boardVo.getGroup_no());
			pstmt.setInt(4, boardVo.getOrder_no());
			pstmt.setInt(5, boardVo.getDepth());
			
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			throw new BoardDaoException("답글 달기 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("답글 달기 오류");
			}
		}
		
		return result;
	}
	
	public boolean delete(BoardVo boardVo) throws BoardDaoException{
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql ="delete from board where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, boardVo.getNo());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			throw new BoardDaoException("글 삭제 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("글 삭제 오류");
			}
		}
		
		return result;
	}
	
	public boolean insert(BoardVo boardVo) throws BoardDaoException{
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select max(group_no)+1 from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long max_no = rs.getLong(1);
				
				boardVo.setMax_no(max_no);				
			}
			pstmt.close();
			
			sql ="insert into board values(null,?,?, now(),0,"+boardVo.getMax_no()+",1,0,"+boardVo.getUser_no()+")";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			throw new BoardDaoException("글 쓰기 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("글 쓰기 오류");
			}
		}
		
		return result;
	}
	public boolean insert(CommentVo commentVo) throws BoardDaoException{
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select no from board where order_no="+commentVo.getOrder_no()+" and group_no="+commentVo.getGroup_no();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long board_no = rs.getLong(1);
				
				commentVo.setBoard_no(board_no);				
			}
			pstmt.close();
			
			sql ="insert into comment values(null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, commentVo.getUser_no());
			pstmt.setString(2, commentVo.getComment());
			pstmt.setLong(3, commentVo.getBoard_no());
						
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			throw new BoardDaoException("댓글달기 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("댓글달기 오류");
			}
		}
		
		return result;
	}
	public List<CommentVo> getListComment(int group_no, int order_no) throws BoardDaoException{
		List<CommentVo> list = new ArrayList<CommentVo>();
				
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			
			String sql = "select a.user_no, c.name, a.comment, a.no  " + 
					"		from comment a, board b, user c    " + 
					"			where a.board_no=b.no and a.user_no=c.no   " + 
					"			and b.group_no = "+ group_no+" and b.order_no = "+order_no+ 
					"			order by a.no asc";

			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Long user_no = rs.getLong(1);
				String user_name = rs.getString(2);
				String comment = rs.getString(3);
				Long no = rs.getLong(4);
				
				CommentVo vo = new CommentVo();
				vo.setUser_no(user_no);
				vo.setUser_name(user_name);
				vo.setComment(comment);
				vo.setNo(no);
								
				list.add(vo);
				
			}
			
			
		} catch (SQLException e) {
			throw new BoardDaoException("댓글 리스트 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("댓글 리스트 오류");
			}
		}
		return list;
	}
	public BoardVo get(int group_no, int order_no) throws BoardDaoException{
		BoardVo boardVo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "update board set hit = (hit+1) where group_no="+group_no+" and order_no="+order_no;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			pstmt.close();
			
			sql ="select no, title, contents, user_no, depth from board where group_no="+group_no+" and order_no="+ order_no;
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);	
				Long user_no = rs.getLong(4);
				int depth = rs.getInt(5);
				
				
				boardVo = new BoardVo();
				boardVo.setNo(no);
				boardVo.setTitle(title);
				boardVo.setContents(contents);
				boardVo.setUser_no(user_no);
				boardVo.setGroup_no(group_no);
				boardVo.setOrder_no(order_no);
				boardVo.setDepth(depth);
				
						
			}
			
			
		} catch (SQLException e) {
			throw new BoardDaoException("글 목록에 있는 글 읽기 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("글 목록에 있는 글 읽기 오류");
			}
		}
		return boardVo;
	}
	
	public int count() throws BoardDaoException{
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new BoardDaoException("글 갯수 카운드 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("글 갯수 카운드 오류");
			}
		}
		
		return count;
	}
	public int count(String kwd) throws BoardDaoException{
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select count(*) from board where title like '%"+kwd+"%'";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new BoardDaoException("글 갯수 카운드 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("글 갯수 카운드 오류");
			}
		}
		
		return count;
	}
	public List<BoardVo> getList(int page, int board_count) throws BoardDaoException{
		List<BoardVo> list = new ArrayList<BoardVo>();
				
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			page = page - 1;
			page = page * board_count;
			
			String sql = "select a.no, a.title, a.contents, a.write_date, a.hit, a.group_no, a.order_no, a.depth,a.user_no ,b.name " + 
					"			from board a, user b " + 
					"				where a.user_no = b.no order by group_no desc, order_no asc "+
					"				limit "+page+", "+board_count;

			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String write_date = rs.getString(4);
				int hit = rs.getInt(5);
				int group_no = rs.getInt(6);
				int order_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				String user_name = rs.getString(10);
				
				
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setWrite_date(write_date);
				vo.setHit(hit);
				vo.setGroup_no(group_no);
				vo.setOrder_no(order_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				vo.setUser_name(user_name);
				
				list.add(vo);
				
			}
			
			
		} catch (SQLException e) {
			throw new BoardDaoException("페이징 및 리스트 불러오기 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("페이징 및 리스트 불러오기 오류");
			}
		}
		return list;
	}
	public List<BoardVo> getList(int page, int board_count, String kwd) throws BoardDaoException{
		List<BoardVo> list = new ArrayList<BoardVo>();
				
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			page = page - 1;
			page = page * board_count;
			
			String sql = "select a.no, a.title, a.contents, a.write_date, a.hit, a.group_no, a.order_no, a.depth,a.user_no ,b.name  " + 
					"			from board a, user b   " + 
					"				where a.user_no = b.no and title like '%"+kwd+"%'  " + 
					"				order by group_no desc, order_no asc  " + 
					"                limit "+page+", "+board_count;

			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String write_date = rs.getString(4);
				int hit = rs.getInt(5);
				int group_no = rs.getInt(6);
				int order_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				String user_name = rs.getString(10);
				
				
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setWrite_date(write_date);
				vo.setHit(hit);
				vo.setGroup_no(group_no);
				vo.setOrder_no(order_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				vo.setUser_name(user_name);
				
				list.add(vo);
				
			}
			
			
		} catch (SQLException e) {
			throw new BoardDaoException("찾기 했을때 페이징 및 리스트 불러오기 오류");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BoardDaoException("찾기 했을때 페이징 및 리스트 불러오기 오류");
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
