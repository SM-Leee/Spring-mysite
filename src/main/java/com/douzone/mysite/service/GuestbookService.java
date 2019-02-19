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
	
	public void write(GuestbookVo guestbookVo) {
		guestbookDao.insert(guestbookVo);
	}
	
	public void delete(GuestbookVo guestbookVo) {
		guestbookDao.delete(guestbookVo);
	}
}
