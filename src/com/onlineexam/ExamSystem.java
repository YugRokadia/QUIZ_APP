package com.onlineexam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExamSystem {
    // Make methods static and public for GUI access
    public static boolean authenticateUser(String username, String password) {
        try (Connection conn = DBConnection.connect()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                return rs.next();  // If user exists, return true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        try (Connection conn = DBConnection.connect()) {
            // Get 2 GK questions
            String gkQuery = "SELECT * FROM questions WHERE category = 'GK' ORDER BY RAND() LIMIT 2";
            Statement gkStmt = conn.createStatement();
            ResultSet gkRs = gkStmt.executeQuery(gkQuery);

            while (gkRs.next()) {
                String questionText = gkRs.getString("question_text");
                String optionA = gkRs.getString("option_a");
                String optionB = gkRs.getString("option_b");
                String optionC = gkRs.getString("option_c");
                String optionD = gkRs.getString("option_d");
                String correctOption = gkRs.getString("correct_option");
                String category = gkRs.getString("category");

                Question question = new Question(questionText, optionA, optionB, optionC, optionD, correctOption, category);
                questions.add(question);
            }

            // Get 8 Java questions
            String javaQuery = "SELECT * FROM questions WHERE category = 'Java' ORDER BY RAND() LIMIT 8";
            Statement javaStmt = conn.createStatement();
            ResultSet javaRs = javaStmt.executeQuery(javaQuery);

            while (javaRs.next()) {
                String questionText = javaRs.getString("question_text");
                String optionA = javaRs.getString("option_a");
                String optionB = javaRs.getString("option_b");
                String optionC = javaRs.getString("option_c");
                String optionD = javaRs.getString("option_d");
                String correctOption = javaRs.getString("correct_option");
                String category = javaRs.getString("category");

                Question question = new Question(questionText, optionA, optionB, optionC, optionD, correctOption, category);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    // Keep the console version for backward compatibility
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Login functionality
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (authenticateUser(username, password)) {
            System.out.println("Login successful!");

            // Start the exam
            List<Question> questions = getQuestions();
            startExam(questions);
        } else {
            System.out.println("Invalid credentials. Exiting...");
        }

        scanner.close();
    }

    private static void startExam(List<Question> questions) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        for (Question question : questions) {
            System.out.println("\nQuestion: " + question.getQuestionText());
            System.out.println("A. " + question.getOptionA());
            System.out.println("B. " + question.getOptionB());
            System.out.println("C. " + question.getOptionC());
            System.out.println("D. " + question.getOptionD());

            System.out.print("Your answer (A/B/C/D): ");
            String userAnswer = scanner.nextLine().toUpperCase();

            if (userAnswer.equals(question.getCorrectOption())) {
                System.out.println("Correct! Well done!");
                score++;
            } else {
                System.out.println("Incorrect! The correct answer was: " + question.getCorrectOption());
            }
            
            System.out.println("Correct Answer: " + question.getCorrectOption());
            System.out.println("----------------------------------------");
        }

        System.out.println("\nExam completed!");
        System.out.println("Your final score: " + score + "/" + questions.size());
        scanner.close();
    }
}
