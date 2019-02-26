package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public Map<String, Object> list(int page, String kwd){
		page = (page==0)? 1 : page;
		//화면에 띄울 게시글의 수
		int board_count = 10;
		//화면에 보여줄 페이지
		int size_page = 5;

		List<BoardVo> list = boardDao.getList(page, board_count, kwd);
		int count = boardDao.count(kwd);

		int start_page = 0;
		int end_page = 0;
		//총 페이지
		int total_page = ((count-1)/board_count)+1;

		//시작 페이지
		start_page = ((page-1)/size_page)*size_page+1;
		//끝 페이지
		end_page = start_page+size_page -1;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list",	list);
		map.put("page", page);
		map.put("end_page", end_page);
		map.put("start_page", start_page);
		map.put("size_page", size_page);
		map.put("total_page", total_page);
		map.put("count", count);
		map.put("board_count", board_count);
		map.put("kwd",kwd);

		return map;
	}

	public void write(BoardVo boardVo) {
		boardVo.setMax_no(boardDao.get());
		System.out.println(boardVo);
		boardDao.insert(boardVo);
	}

	public void delete(BoardVo boardVo) {
		boardDao.delete(boardVo);
	}

	public Map<String, Object> view(BoardVo boardVo) {
		//hit update
		boardDao.hitUpdate(boardVo);
		
		boardVo = boardDao.get(boardVo);
				
		List<CommentVo> cl = boardDao.getListComment(boardVo);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vo", boardVo);
		map.put("cl", cl);

		return map;
	}

	public void reply(BoardVo boardVo) {
		boardVo.setOrder_no(boardVo.getOrder_no()+1);
		boardVo.setDepth(boardVo.getDepth()+1);
		
		boardDao.OrderNoUpdate(boardVo);
		boardDao.insert(boardVo);
	}

	public BoardVo getAll(BoardVo boardVo) {
		return boardDao.getAll(boardVo);
	}
	
	public void modify(BoardVo boardVo) {
		boardDao.update(boardVo);
	}

	public void writeComment(CommentVo commentVo) {
		boardDao.insert(commentVo);
	}

	public void deleteComment(CommentVo commentVo) {
		boardDao.delete(commentVo);
	}
}
