package test;

import org.springframework.stereotype.Service;

import board.testMemberDTO;

@Service
public interface testMemberService {
	
	public testMemberDTO login(String id, String pass);
}
