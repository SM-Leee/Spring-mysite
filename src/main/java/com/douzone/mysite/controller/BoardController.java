package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Controller
@Auth
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd,
			Model model) {
		
		Map<String, Object> map = boardService.list(page, kwd);
		model.addAllAttributes(map);

		return "board/list";

	}
	
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public String searchList(
			@RequestParam(value="page", required=true, defaultValue="0") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			Model model) { 
		Map<String,Object> map = boardService.list(page, kwd);
		model.addAllAttributes(map);
		
		return "/board/list";
	}
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd,
			Model model) {
		
		model.addAttribute("page", page);
		model.addAttribute("kwd", kwd);		
		return "board/write";
	}

	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo boardVo,
			@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd
			) {
		
		boardService.write(boardVo);
		return "redirect:/board/list?page="+page+"&kwd="+kwd;
	}
	
	@Auth
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute BoardVo boardVo,
			@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd
			) {
		boardService.delete(boardVo);
		return "redirect:/board/list?page="+page+"&kwd="+kwd;
	}

	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String view(
			@PathVariable("no") long no,
			@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd,
			Model model) {
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(no);
		
		model.addAllAttributes(boardService.view(boardVo));
		model.addAttribute("page", page);
		model.addAttribute("kwd", kwd);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value = "/reply/{no}", method = RequestMethod.GET)
	public String reply(@PathVariable("no") long no,
			@AuthUser UserVo authuser,
			@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd,
			Model model) {
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(no);
		
		boardVo = boardService.getAll(boardVo);
		boardVo.setUser_no(authuser.getNo());
		
		model.addAttribute("vo", boardVo);
		model.addAttribute("page", page);
		model.addAttribute("kwd", kwd);		
		return "board/reply";
	}

	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(@ModelAttribute BoardVo boardVo,
			@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd
			) {

		boardService.reply(boardVo);
		return "redirect:/board/list?page="+page+"&kwd="+kwd;
	}
	@Auth
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String modify(
			@PathVariable("no") long no,
			@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd,
			Model model) {
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(no);
		
		model.addAttribute("vo", boardService.getAll(boardVo));
		model.addAttribute("page", page);
		model.addAttribute("kwd", kwd);		
		return "board/modify";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo boardVo,
			@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd) {
		boardService.modify(boardVo);
		return "redirect:/board/list?page="+page+"&kwd="+kwd;
	}
	
	@Auth
	@RequestMapping(value = "/writeComment", method = RequestMethod.POST)
	public String writeComment(
			@ModelAttribute CommentVo commentVo,
			@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd) {
		
		boardService.writeComment(commentVo);
		
		//model.addAttribute("no", commentVo.getBoard_no());		
		
		return "redirect:/board/view/" + commentVo.getBoard_no()+"?page="+page+"&kwd="+kwd;
	}
	
	@RequestMapping(value = "/deleteComment", method = RequestMethod.GET)
	public String deleteComment(
			@ModelAttribute CommentVo commentVo,
			@RequestParam(value = "page", required = true, defaultValue = "0") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd) {
		boardService.deleteComment(commentVo);

		return "redirect:/board/view/"+commentVo.getBoard_no()+"?page="+page+"&kwd="+kwd;
	}
}
