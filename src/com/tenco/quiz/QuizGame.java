package com.tenco.quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class QuizGame {
	
	private static final String ADD_QUIZ = " INSERT INTO quiz(question, answer) values( ?, ? ) "; 
	private static final String VIEW_QUIZ = " SELECT * FROM quiz "; 
	private static final String RANDOM_QUIZ = " SELECT distinct * FROM quiz order by rand() limit 1 "; 
	
	private static int score = 0;
	
	public static void main(String[] args) {
		
		try(Connection conn = DBConnectionManager.getConnection();
			 Scanner sc = new Scanner(System.in)) {
			
			while(true) {
				printMenu();
				
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

	private static void printMenu() {
		System.out.println();
		System.out.println("================================");
		System.out.println("1. 퀴즈 문제 추가");
		System.out.println("2. 퀴즈 문제 조회");
		System.out.println("3. 퀴즈 게임 시작");
		System.out.println("4. 종료");
		System.out.print("옵션을 선택하세요 : ");
	}

	// 1. 퀴즈 문제 추가
	private static void addQuizQuesion(Connection conn, Scanner sc) {

		try (PreparedStatement psmt = conn.prepareStatement(ADD_QUIZ)){
			
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

		try (PreparedStatement psmt = conn.prepareStatement(VIEW_QUIZ)){
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
		
		try (PreparedStatement psmt = conn.prepareStatement(RANDOM_QUIZ);){
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
