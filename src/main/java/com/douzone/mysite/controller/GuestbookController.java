package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String read(Model model) {
		model.addAttribute("list", guestbookService.read());
		return "guestbook/list";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@ModelAttribute GuestbookVo guestbookVo) {
		guestbookService.write(guestbookVo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(@RequestParam(value="no", required=true) Long no) {
		return "guestbook/deleteform";
	}
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo guestbookVo) {
		guestbookService.delete(guestbookVo);
		return "redirect:/guestbook/list";
	}
	
	
	

}
