package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
			Model model) {
		Map<String, Object> map = boardService.list(page, kwd);
		model.addAllAttributes(map);

		return "board/list";

	}
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public String searchList(
			@RequestParam(value="page", required=false, defaultValue="0") int page,
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd,
			Model model) { 
		Map<String,Object> map = boardService.list(page, kwd);
		model.addAllAttributes(map);
		
		return "/board/list";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("authuser");
		if (userVo == null) {
			return "redirect:/";
		}
		return "board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write( @ModelAttribute BoardVo boardVo) {
		boardService.write(boardVo);
		return "redirect:/board/list";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute BoardVo boardVo) {
		boardService.delete(boardVo);
		return "redirect:/board/list";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(@ModelAttribute BoardVo boardVo, Model model) {
		model.addAllAttributes(boardService.view(boardVo));
		System.out.println(boardVo);
		return "board/view";
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(HttpSession session, @ModelAttribute BoardVo boardVo) {
		UserVo authuser = (UserVo) session.getAttribute("authuser");
		if (authuser == null) {
			return "redirect:/";
		}
		return "board/reply";
	}

	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(@ModelAttribute BoardVo boardVo) {

		boardService.reply(boardVo);
		return "redirect:/board/list";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(HttpSession session, @ModelAttribute BoardVo boardVo) {
		UserVo authuser = (UserVo) session.getAttribute("authuser");
		if (authuser == null) {
			return "redirect:/";
		}
		return "board/modify";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo boardVo) {
		boardService.modify(boardVo);
		return "redirect:/board/list";
	}

	@RequestMapping(value = "/writeComment", method = RequestMethod.POST)
	public String writeComment(@ModelAttribute CommentVo commentVo, Model model) {
		boardService.writeComment(commentVo);
		model.addAttribute("order_no", commentVo.getOrder_no());
		model.addAttribute("group_no", commentVo.getGroup_no());

		return "redirect:/board/view";
	}

	@RequestMapping(value = "/deleteComment", method = RequestMethod.GET)
	public String deleteComment(@ModelAttribute CommentVo commentVo, Model model) {
		boardService.deleteComment(commentVo);
		model.addAttribute("order_no", commentVo.getOrder_no());
		model.addAttribute("group_no", commentVo.getGroup_no());

		return "redirect:/board/view";
	}
}
