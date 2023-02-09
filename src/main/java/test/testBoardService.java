package test;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import board.BoardDTO;
import board.ParameterDTO;

@Service
public interface testBoardService {

	//게시물 갯수를 카운트
	public int getTotalCount();
	//리스트
	public ArrayList<BoardDTO> listPage(int s, int e);
	//글쓰기 (DB에 not null로 줬던것들 다 넣음 없어서 에러 났었음)
	public int write(@Param("_name") String name,
			@Param("_content") String content,
			@Param("_title")String title,
			@Param("_id")String id);
	
	public BoardDTO view(ParameterDTO parameterDTO);
	public int modify(BoardDTO boardDTO);
	public int delete(String idx, String id);
}
