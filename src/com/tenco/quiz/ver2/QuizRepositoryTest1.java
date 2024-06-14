package com.tenco.quiz.ver2;

import java.sql.Connection;

import com.tenco.quiz.DBConnectionManager;

public class QuizRepositoryTest1 {
	
	public static void main(String[] args) {
		// QUizRepository 구현 클래스 테스트
		
		QuizRepositoryLImpl quizRepositoryLImpl = new QuizRepositoryLImpl();
		
		
		try (Connection conn = DBConnectionManager.getConnection()){
			quizRepositoryLImpl.addQuizQuestion(quizRepositoryLImpl.ADD_QUIZ);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
