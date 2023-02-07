package board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import board.util.BoardService;

@Controller
public class BoardController {
	
	BoardService service = null;
	
	@Autowired
	ListExecute listExecute;
}
