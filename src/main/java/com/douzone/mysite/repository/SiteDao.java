package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int update(SiteVo siteVo) {
		return sqlSession.update("site.update",siteVo);
	}
	
	public SiteVo get() {
		return sqlSession.selectOne("site.select");
	}
}
