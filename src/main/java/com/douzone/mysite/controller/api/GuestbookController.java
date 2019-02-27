package com.douzone.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller("guestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	
	@RequestMapping({"/ajax",""})
	public String main() {
		return "guestbook/index-ajax";
	}
	
	@ResponseBody
	@RequestMapping("/ajax-list")
	public JSONResult getList(
			@RequestParam(value="page", required=true, defaultValue="") int page) {
		
		return JSONResult.success(guestbookService.ajaxRead(page));
		
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax-insert", method=RequestMethod.POST)
	public JSONResult insert(
			@RequestParam(value="name") String name,
			@RequestParam(value="password") String password,
			@RequestParam(value="text") String text) {
		GuestbookVo guestbookVo = new GuestbookVo();
		guestbookVo.setName(name);
		guestbookVo.setPassword(password);
		guestbookVo.setMessage(text);
		
		
		return JSONResult.success(guestbookService.write(guestbookVo));
		
		//return JSONResult.success(guestbookVo);
		
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax-delete", method=RequestMethod.POST)
	public JSONResult delete(
			@RequestParam(value ="no") Long no,
			@RequestParam(value="password") String password) {
		GuestbookVo guestbookVo = new GuestbookVo();
		guestbookVo.setNo(no);
		guestbookVo.setPassword(password);
		
		return JSONResult.success(guestbookService.delete(guestbookVo));
		
	}
}
