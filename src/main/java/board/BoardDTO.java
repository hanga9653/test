package board;

import lombok.Data;

@Data
public class BoardDTO {
	private int idx;
	private String atype;
	private String id;
	private String name;
	private String phone;
	private String email;
	private String title;
	private String content;
	private java.sql.Date postdate;
}
