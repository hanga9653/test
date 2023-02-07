package test;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import board.BoardDTO;

@Service
public interface testBoardService {

	public int getTotalCount();
	public ArrayList<BoardDTO> listPage(int s, int e);
	
}
