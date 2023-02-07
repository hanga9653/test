package board.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import board.model.JDBCTemplateDAO;
import board.util.BoardService;

@Service
public class ListExecute implements BoardService {
	
	@Autowired
	JDBCTemplateDAO dao;
	
	@Override
	public void execute(Model model) {
		System.out.println("Listexecute 호출");
		
		Map<String, Object> paramMap = model.asMap();
		
		HttpServletRequest req = (HttpServletRequest)paramMap.get("req");
	}
}
