package com.tenco.quiz.ver1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class QuizGame {
	
	// 준비물
	private static final String URL = "jdbc:mysql://localhost:3306/quizdb?serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PASSWORD = "asd123";
	
	private static int score = 0;
	
	public static void main(String[] args) {
		// JDBC 드라이버 로드 <-- 인터페이스 <-- 구현 클래스 필요
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		try(Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
			 Scanner sc = new Scanner(System.in)) {
			
			while(true) {
				System.out.println();
				System.out.println("================================");
				System.out.println("1. 퀴즈 문제 추가");
				System.out.println("2. 퀴즈 문제 조회");
				System.out.println("3. 퀴즈 게임 시작");
				System.out.println("4. 종료");
				System.out.print("옵션을 선택하세요 : ");
				
				int choice = sc.nextInt();
				
				if(choice == 1) {
					// 퀴즈 문제 추가
					// alt + shift + m
					addQuizQuesion(conn, sc);
				} else if (choice == 2) {
					// 퀴즈 문제 조회
					viewQuizQuestion(conn);
				} else if (choice == 3) {
					// 퀴즈 게임 시작
					playQuizGame(conn, sc);
				} else if (choice == 4) {
					// 종료
					System.out.println("프로그램을 종료합니다.");
					break;
				} else {
					System.out.println("잘못된 선택값입니다.");
					return;
					 
				}
				
			}
			
			
		} catch (Exception e) {

		}
		
	} // end of main

	// 1. 퀴즈 문제 추가
	private static void addQuizQuesion(Connection conn, Scanner sc) {

		String addQuery = "INSERT INTO quiz(question, answer) values( ?, ? ) ";
		
		try (PreparedStatement psmt = conn.prepareStatement(addQuery)){
			
			System.out.println("퀴즈 문제를 입력하세요 : " );
			sc.nextLine();
			String question = sc.nextLine();
			System.out.println("퀴즈 정답을 입력하세요 : ");
			String answer = sc.nextLine();
		
			psmt.setString(1, question);
			psmt.setString(2, answer);
			
			int rowsInsertedCount =  psmt.executeUpdate();
			System.out.println("RowCount : " + rowsInsertedCount);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	}
	// 2. 퀴즈 문제 조회
	private static void viewQuizQuestion(Connection conn) {
		String selectQuery = "SELECT * FROM quiz";
		
		try (PreparedStatement psmt = conn.prepareStatement(selectQuery)){
			ResultSet resultset = psmt.executeQuery();
			
			while(resultset.next()) {
				System.out.println("문제 ID : " + resultset.getInt("id"));
				System.out.println("문제 : " + resultset.getString("question"));
				System.out.println("답안 : " + resultset.getString("answer"));
				if(!resultset.isLast()) {
					System.out.println("================================");
					
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	// 3. 퀴즈 게임 시작  + 문제 중복을 해결해보자
	private static void playQuizGame(Connection conn, Scanner sc) {
		String playQuery = "select distinct * from quiz order by rand() limit 1 ";
		
		try (PreparedStatement psmt = conn.prepareStatement(playQuery);){
			ResultSet resultSet = psmt.executeQuery();
			
			if(resultSet.next()) {
				String question = resultSet.getString("question");
				String answer = resultSet.getString("answer");
				
				System.out.println("퀴즈 문제 : " + question);
				// 버그 처리
				sc.nextLine();
				String userAnswer = sc.nextLine();
				System.out.println("당신의 답 : "  + userAnswer);
				
				if(userAnswer.equalsIgnoreCase(answer)) {
					System.out.println("정답! 점수 + 1");
					score++;
					System.out.println("현재 당신의 점수는 " + score);
				} else {
					System.out.println("오답! 점수 - 1 ㅋㅋ");
					score--;
					System.out.println("현재 당신의 점수는 " + score);
					System.out.println("퀴즈 정답은 -> " + answer);
				}
				
			} else {
				System.out.println("아직 만들고 있어요");
			}
			
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	}
	
	// 퀴즈를 추가하는 함수 만들기.
	// 사용자 퀴즈와 답을 입력 받아야 함.
	// Connection 활용해서 Query 날려버리기~
	
} // end of class
