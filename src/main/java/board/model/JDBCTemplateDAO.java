package board.model;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCTemplateDAO {

	@Autowired
	JdbcTemplate template;
	
	//게시물 카운트
	public int getTotalCount(Map<String, Object> map) {
		String sql = "SELECT COUNT(*) FROM pmp ";
		if(map.get("Word")!= null) {
			sql +=" WHERE "+ map.get("Column")+" "
				+" LIKE '%"+map.get("Word")+"%' ";
		}
		return template.queryForObject(sql, Integer.class);
	}
	
	//게시판 리스트 출력
	public ArrayList<BoardDTO> list(Map<String, Object> map) {
		
		String sql = "SELECT * FROM pmp ";
		if(map.get("Word")!= null) {
			sql += " WHERE "+ map.get("Column")+" "
				+ " LIKE '%"+map.get("Word")+"%' ";
		}
		sql += " ORDER BY bgroup DESC, bstep ASC";
		
		return (ArrayList<BoardDTO>)template.query(sql, 
				new BeanPropertyRowMapper<BoardDTO>(BoardDTO.class));
	}
}
