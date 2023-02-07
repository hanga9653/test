package board.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import board.BoardDTO;
import board.util.PagingUtil;
import test.testBoardService;

@Controller
public class BoardController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping("/testboard/list.do")
	public String list(Model model, HttpServletRequest req) {
	
		int totalRecordCount = 
				sqlSession.getMapper(testBoardService.class).getTotalCount();
		
		int pageSize = 4;
		int blockPage = 2;
		int totalPage = (int)Math.ceil((double)totalRecordCount/pageSize);
		int nowPage = (req.getParameter("nowPage")==null 
				|| req.getParameter("nowPage").equals(""))
					? 1 : Integer.parseInt(req.getParameter("nowPage"));
		
		int start = (nowPage-1) * pageSize + 1;
		int end = nowPage * pageSize;
		
		ArrayList<BoardDTO> lists =
				sqlSession.getMapper(testBoardService.class).listPage(start, end);
		
		String pagingImg = 
				PagingUtil.pagingImg(totalRecordCount, pageSize, blockPage, nowPage, req.getContextPath()+"/testboard/list.do?");
		model.addAttribute("pagingImg", pagingImg);
		
		for(BoardDTO dto : lists) {
			String temp = dto.getContent().replace("\r\n", "<br>");
			dto.setContent(temp);
		}
		
		model.addAttribute("lists", lists);
		return "07Board/list";
	}
	
	
}
