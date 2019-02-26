package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo get(String email){
		return sqlSession.selectOne("user.getByEmail", email);
		
	}
	
	public int update(UserVo userVo){
		return sqlSession.update("user.update", userVo);
	}
	
	public UserVo get(long no){
		return sqlSession.selectOne("user.getByNo", no);
	}
	
	public UserVo get(String email, String password){
		//parameter는 Vo로 받아오는것이 제일 좋다
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		
		UserVo userVo = sqlSession.selectOne("user.getByEmailAndPassword", map);
		
		return userVo;
	}
	
	public int insert(UserVo userVo){
		int count = sqlSession.insert("user.insert", userVo);
		return count;
	}
}
