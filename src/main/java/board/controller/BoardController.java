package board.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import board.BoardDTO;
import board.ParameterDTO;
import board.testMemberDTO;
import board.util.PagingUtil;
import test.testBoardService;
import test.testMemberService;

@Controller
public class BoardController {
	
	@Autowired
	private SqlSession sqlSession;
	
	//리스트
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
	
	//글쓰기
	@RequestMapping("/testboard/write.do")
	public String write(Model model, HttpServletRequest req, HttpSession session) {
		
		if(session.getAttribute("siteUserInfo")==null) {
			model.addAttribute("backUrl", "07Board/write");
			return "redirect:login.do";
		}
		return "07Board/write";
	}
	
	//로그인 
	@RequestMapping("/testboard/login.do")
	public String login(Model model) {
		return "07Board/login";
	}
	
	//로그인 처리
	@RequestMapping("/testboard/loginAction.do")
	public ModelAndView loginAction(HttpServletRequest req, HttpSession session) {
		testMemberDTO dto = sqlSession.getMapper(testMemberService.class).login(req.getParameter("id"),
				req.getParameter("pass"));
		
		ModelAndView mv = new ModelAndView();
		if(dto == null) {
			mv.addObject("Error", "아이디/패스워드가 틀렸음");
			mv.setViewName("07Board/login");
			return mv;
		}
		else {
			session.setAttribute("siteUserInfo", dto);
		}
		String backUrl = req.getParameter("backUrl");
		if(backUrl == null || backUrl.equals("")) {
			mv.setViewName("07Board/login");
		}
		else {
			mv.setViewName(backUrl);
		}
		return mv;
	}
	
	//글쓰기 처리
	@RequestMapping(value="/testboard/writeAction.do",
			method=RequestMethod.POST)
	public String writeAction(Model model, HttpServletRequest req, HttpSession session) {
		if(session.getAttribute("siteUserInfo")==null) {
			return "redirect:login.do";
		}
		//(DB에 not null로 줬던것들 다 넣음 없어서 에러 났었음)
		int applyRow = sqlSession.getMapper(testBoardService.class)
				.write(req.getParameter("name"),
						req.getParameter("content"),
						req.getParameter("title"),
						((testMemberDTO)session.getAttribute("siteUserInfo")).getId()
		);
		System.out.println("입력된행의갯수:"+ applyRow);
		
		return "redirect:list.do";
	}
	
	//수정
	@RequestMapping("/testboard/modify.do")
	public String  modify(Model model, HttpServletRequest req, HttpSession session) {
		if(session.getAttribute("siteUserInfo")==null) {
			return "redirect:login.do";
		}
		ParameterDTO parameterDTO = new ParameterDTO();
		parameterDTO.setBoard_idx(req.getParameter("idx"));
		parameterDTO.setUser_id(((testMemberDTO)session.getAttribute("siteUserInfo")).getId());
		
		BoardDTO dto = sqlSession.getMapper(testBoardService.class).view(parameterDTO);
		
		model.addAttribute("dto", dto);
		return "07Board/modify";
	}
	//수정 처리
	@RequestMapping("/testboard/modifyAction.do")
	public String modifyAction(HttpSession session, 
			BoardDTO boardDTO) {
		if(session.getAttribute("siteUserInfo")==null) {
			return "redirect:login.do";
		}
		int applyRow = sqlSession.getMapper(testBoardService.class).modify(boardDTO);
		System.out.println("수정된행의갯수:"+ applyRow);
		
		return "redirect:list.do";
	}
	
	//삭제
	@RequestMapping("/testboard/delete.do")
	public String delete(HttpServletRequest req,
			HttpSession session) {
		if(session.getAttribute("siteUserInfo")==null) {
			return "redirect:login.do";
		}
		System.out.println("된당");
		int applRow = sqlSession.getMapper(testBoardService.class).delete(req.getParameter("idx"),
				((testMemberDTO)session.getAttribute("siteUserInfo")).getId());
		System.out.println(req.getParameter("idx")+"//"+
		         ((testMemberDTO)session.getAttribute("siteUserInfo")).getId());
		System.out.println("삭제된행의갯수:"+ applRow);
		return "redirect:list.do";
	}
}
