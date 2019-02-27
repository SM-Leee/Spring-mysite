package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookDao;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookDao guestbookDao;
	
	public List<GuestbookVo> read() {
		return guestbookDao.getList();
	}
	
	public GuestbookVo write(GuestbookVo guestbookVo) {
		return guestbookDao.get(guestbookDao.insert(guestbookVo));
	}
	
	public boolean delete(GuestbookVo guestbookVo) {
		return guestbookDao.delete(guestbookVo);
	}
	
	public List<GuestbookVo> ajaxRead(int page){
		List<GuestbookVo> list = guestbookDao.getList(page); 
		return list;
	}
}
