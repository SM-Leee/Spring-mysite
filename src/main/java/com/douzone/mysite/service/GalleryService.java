package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryDao;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galleryDao;
	
	public int upload(GalleryVo galleryVo) {
		return galleryDao.upload(galleryVo);
	}
	
	public List<GalleryVo> getGalleryList(){
		return galleryDao.getList();
	}
	
	public int delete(long no) {
		System.out.println(no);
		return galleryDao.delete(no);
	}
}
