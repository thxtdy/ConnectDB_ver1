package com.tenco.quiz.ver2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tenco.quiz.DBConnectionManager;

import lombok.Data;
@Data
public class QuizRepositoryLImpl implements QuizRepository{
	
	
	public static final String ADD_QUIZ = " INSERT INTO quiz(question, answer) values( ?, ? ) "; 
	public static final String VIEW_QUIZ = " SELECT * FROM quiz "; 
	public static final String RANDOM_QUIZ = " SELECT distinct * FROM quiz order by rand() limit 1 "; 
	
	@Override
	public int addQuizQuestion(String question, String answer) throws SQLException{
		
		// 함수 안에서 변수 선언 시 초기화 해주는 습관
		int resultRowCount = 0;
		
		try (Connection conn1 = DBConnectionManager.getConnection()){
			
			PreparedStatement psmt =  conn1.prepareStatement(ADD_QUIZ);
			// 트랜잭션 처리 가능 - 수동모드 커밋 사용 가능
			psmt.setString(1, question);
			psmt.setString(2, answer);
			
			psmt.executeUpdate();
			resultRowCount++;
			
		} 
		
		return resultRowCount;
	}

	@Override
	public List<QuizDTO> viewQuizQuestion() throws SQLException {
		
		List<QuizDTO> quizlist = new ArrayList<>();
		
		try (Connection conn2= DBConnectionManager.getConnection()) {
			
			PreparedStatement psmt = conn2.prepareStatement(VIEW_QUIZ);
			ResultSet resultSet = psmt.executeQuery();
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String question = resultSet.getString("question");
				String answer = resultSet.getString("answer");
				
				quizlist.add(new QuizDTO(id, question, answer)); 
				
			}
		} catch (Exception e) {
			
		}
		
		return quizlist;
	}

	@Override
	public QuizDTO playQuizGame() throws SQLException{
		
		QuizDTO quizDTO = null;
		
		try (Connection conn = DBConnectionManager.getConnection()){
			
			PreparedStatement psmt = conn.prepareStatement(RANDOM_QUIZ);
			ResultSet resultSet = psmt.executeQuery();
			
			if(resultSet.next()){
				
				int id = resultSet.getInt("id");
				String question = resultSet.getString("question");
				String answer = resultSet.getString("answer");
				quizDTO = new QuizDTO(id, question, answer);
				
			}
			
		} 
		return quizDTO;
	}
	
}
