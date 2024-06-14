package com.tenco.quiz.ver2;

import java.sql.SQLException;
import java.util.List;

public class MainTest1 {
	
	public static void main(String[] args) {
		
		QuizRepositoryLImpl quizRepositoryLImpl = new QuizRepositoryLImpl();
		
		// 인터페이스에서 예외 처리를 안했기 때문에 내가 직접 예외처리를 해야 함
//		try {
//			List<QuizDTO> quizDTOs = quizRepositoryLImpl.viewQuizQuestion();
//			
//			for (QuizDTO quizDTO : quizDTOs) {
//				System.out.println(quizDTO.toString());
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		try {
			
			QuizDTO dto = quizRepositoryLImpl.playQuizGame();
			
			System.out.println(dto);
			
			System.out.println(dto.getQuestion());
			String userInput = "4월 9일";
			
			if(dto.getAnswer().equalsIgnoreCase(userInput)) {
				System.out.println("정답");
			} else {
				System.out.println("ㅋㅋ");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
