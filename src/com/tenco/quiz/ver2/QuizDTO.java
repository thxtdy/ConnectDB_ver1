package com.tenco.quiz.ver2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {
	
	private int id;
	private String question;
	private String answer;
	@Override
	public String toString() {
		return "QUIZ ID : " + id + " || Question : " + question + " || Answer : " + answer + "\n";
	}
	
	
}
