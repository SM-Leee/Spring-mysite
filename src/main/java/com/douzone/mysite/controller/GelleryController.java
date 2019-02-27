package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;

@Controller
@RequestMapping("/gallery")
public class GelleryController {
	
	@Autowired
	private FileuploadService fileuploadService;
	
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list = galleryService.getGalleryList();
		model.addAttribute("list", list);
		return "gallery/index";
	}
	
	@Auth(Role.ADMIN)
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(
			@ModelAttribute GalleryVo galleryVo,
			@RequestParam(value="file") MultipartFile multipartFile) {
		
		String file = fileuploadService.restore(multipartFile);
		galleryVo.setImage_url(file);
		
		galleryService.upload(galleryVo);
		
		return "redirect:/gallery";
	}
	
	@Auth(Role.ADMIN)
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(
			@PathVariable("no") long no) {
		System.out.println(no);
		galleryService.delete(no);
		return "redirect:/gallery";
	}

}
