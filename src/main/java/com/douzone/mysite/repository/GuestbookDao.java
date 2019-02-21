package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	@Autowired
	private SqlSession sqlSession;

	public GuestbookVo get(long no) {
		return null;
	}

	public List<GuestbookVo> getList(int page) {
		page = (page-1)*5;
		List<GuestbookVo> list = sqlSession.selectList("guestbook.getListAjax", page);
		return list;
	}

	public int delete(GuestbookVo guestbookVo){
		return sqlSession.delete("guestbook.delete", guestbookVo);
	}

	public long insert(GuestbookVo guestbookVo){
		sqlSession.insert("guestbook.insert", guestbookVo);
		long no = guestbookVo.getNo();
		return no;
	}

	public List<GuestbookVo> getList(){
		List<GuestbookVo> list = sqlSession.selectList("guestbook.getList");
		return list;
	}

}
