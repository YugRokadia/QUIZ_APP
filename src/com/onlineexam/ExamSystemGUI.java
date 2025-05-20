package com.onlineexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExamSystemGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel examPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel questionLabel;
    private JLabel feedbackLabel;
    private JButton[] optionButtons;
    private JButton nextButton;
    private JButton submitButton;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    // Updated color scheme
    private final Color WHITE = new Color(255, 255, 255);
    private final Color LIGHT_BLUE = new Color(230, 240, 250);
    private final Color BLUE = new Color(0, 120, 215);
    private final Color DARK_BLUE = new Color(0, 90, 160);
    private final Color TEXT_COLOR = new Color(51, 51, 51);
    private final Color BORDER_COLOR = new Color(200, 200, 200);

    private JPanel progressPanel;
    private JLabel[] questionStatusLabels;
    private Color[] answerColors; // To store the color of each answer

    private JLabel timerLabel;
    private Timer examTimer;
    private int timeRemaining = 300; // 5 minutes in seconds

    public ExamSystemGUI() {
        setTitle("Online Exam System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new CardLayout());
        createLoginPanel();
        createExamPanel();

        mainPanel.add(loginPanel, "login");
        mainPanel.add(examPanel, "exam");

        add(mainPanel);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Online Exam System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(25);
        styleTextField(usernameField);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(25);
        styleTextField(passwordField);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);
    }

    private void createExamPanel() {
        examPanel = new JPanel(new BorderLayout(20, 20));
        examPanel.setBackground(WHITE);
        examPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Timer panel
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        timerPanel.setBackground(WHITE);
        timerLabel = new JLabel("05:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        timerLabel.setForeground(BLUE);
        timerLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BLUE, 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        timerPanel.add(timerLabel);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(WHITE);

        // Question panel
        JPanel questionPanel = new JPanel(new BorderLayout(10, 10));
        questionPanel.setBackground(WHITE);
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        questionLabel.setForeground(TEXT_COLOR);
        questionPanel.add(questionLabel, BorderLayout.NORTH);

        // Options panel - now using GridLayout(2,2)
        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        optionsPanel.setBackground(WHITE);
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            styleOptionButton(optionButtons[i]);
            final int index = i;
            optionButtons[i].addActionListener(e -> handleOptionSelection(index));
            optionsPanel.add(optionButtons[i]);
        }
        questionPanel.add(optionsPanel, BorderLayout.CENTER);

        // Feedback panel
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        feedbackPanel.setBackground(WHITE);
        feedbackPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        feedbackLabel.setForeground(TEXT_COLOR);
        feedbackPanel.add(feedbackLabel, BorderLayout.CENTER);
        questionPanel.add(feedbackPanel, BorderLayout.SOUTH);

        // Progress panel
        progressPanel = new JPanel(new GridLayout(10, 1, 5, 5));
        progressPanel.setBackground(WHITE);
        progressPanel.setPreferredSize(new Dimension(200, 0));
        progressPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        // Initialize question status labels
        questionStatusLabels = new JLabel[10];
        answerColors = new Color[10];
        for (int i = 0; i < 10; i++) {
            JPanel statusPanel = new JPanel(new BorderLayout(10, 0));
            statusPanel.setBackground(WHITE);
            statusPanel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));

            JLabel numberLabel = new JLabel("Q" + (i + 1), SwingConstants.CENTER);
            numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            numberLabel.setForeground(TEXT_COLOR);
            numberLabel.setPreferredSize(new Dimension(40, 0));

            questionStatusLabels[i] = new JLabel("", SwingConstants.CENTER);
            questionStatusLabels[i].setFont(new Font("Segoe UI", Font.PLAIN, 12));
            questionStatusLabels[i].setForeground(TEXT_COLOR);
            questionStatusLabels[i].setOpaque(true);
            questionStatusLabels[i].setBackground(WHITE);

            statusPanel.add(numberLabel, BorderLayout.WEST);
            statusPanel.add(questionStatusLabels[i], BorderLayout.CENTER);
            progressPanel.add(statusPanel);
            answerColors[i] = WHITE;
        }

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(WHITE);
        nextButton = new JButton("Next");
        styleButton(nextButton);
        nextButton.addActionListener(e -> handleNextQuestion());
        buttonPanel.add(nextButton);

        submitButton = new JButton("Submit");
        styleButton(submitButton);
        submitButton.addActionListener(e -> handleSubmit());
        submitButton.setEnabled(false);
        buttonPanel.add(submitButton);

        contentPanel.add(timerPanel, BorderLayout.NORTH);
        contentPanel.add(questionPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        examPanel.add(contentPanel, BorderLayout.CENTER);
        examPanel.add(progressPanel, BorderLayout.EAST);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(WHITE);
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(BLUE);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 45));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(DARK_BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BLUE);
            }
        });
    }

    private void styleOptionButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(LIGHT_BLUE);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!button.getBackground().equals(new Color(46, 204, 113))) {
                    button.setBackground(DARK_BLUE);
                    button.setForeground(WHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!button.getBackground().equals(new Color(46, 204, 113))) {
                    button.setBackground(LIGHT_BLUE);
                    button.setForeground(TEXT_COLOR);
                }
            }
        });
    }

    private void handleOptionSelection(int index) {
        // Reset all buttons to default state
        for (JButton button : optionButtons) {
            button.setBackground(LIGHT_BLUE);
            button.setForeground(TEXT_COLOR);
        }
        
        // Highlight selected button
        optionButtons[index].setBackground(new Color(46, 204, 113)); // Green color for selected
        optionButtons[index].setForeground(WHITE);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (ExamSystem.authenticateUser(username, password)) {
            questions = ExamSystem.getQuestions();
            currentQuestionIndex = 0;
            score = 0;
            timeRemaining = 300; // Reset timer
            displayQuestion();
            startTimer(); // Start the timer
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "exam");
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid credentials. Please try again.",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkAnswer() {
        Question question = questions.get(currentQuestionIndex);
        boolean answered = false;
        
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].getBackground().equals(new Color(46, 204, 113))) {
                answered = true;
                String userAnswer = String.valueOf((char)('A' + i));
                if (userAnswer.equals(question.getCorrectOption())) {
                    score++;
                    feedbackLabel.setText("Correct! Well done!");
                    feedbackLabel.setForeground(new Color(46, 204, 113)); // Green
                    answerColors[currentQuestionIndex] = new Color(46, 204, 113); // Green for correct
                    questionStatusLabels[currentQuestionIndex].setText("Correct");
                } else {
                    feedbackLabel.setText("Incorrect! The correct answer was: " + question.getCorrectOption());
                    feedbackLabel.setForeground(new Color(231, 76, 60)); // Red
                    answerColors[currentQuestionIndex] = new Color(231, 76, 60); // Red for incorrect
                    questionStatusLabels[currentQuestionIndex].setText("Incorrect");
                }
                break;
            }
        }
        
        if (!answered) {
            feedbackLabel.setText("Please select an answer!");
            feedbackLabel.setForeground(new Color(241, 196, 15)); // Yellow
            answerColors[currentQuestionIndex] = new Color(241, 196, 15); // Yellow for unanswered
            questionStatusLabels[currentQuestionIndex].setText("Unanswered");
        }

        // Update the status label color
        questionStatusLabels[currentQuestionIndex].setBackground(answerColors[currentQuestionIndex]);
        questionStatusLabels[currentQuestionIndex].setForeground(
            answerColors[currentQuestionIndex].equals(WHITE) ? TEXT_COLOR : WHITE
        );
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionLabel.setText(question.getQuestionText());
            optionButtons[0].setText("A. " + question.getOptionA());
            optionButtons[1].setText("B. " + question.getOptionB());
            optionButtons[2].setText("C. " + question.getOptionC());
            optionButtons[3].setText("D. " + question.getOptionD());
            
            // Reset all buttons to default state
            for (JButton button : optionButtons) {
                button.setBackground(LIGHT_BLUE);
                button.setForeground(TEXT_COLOR);
            }
            
            feedbackLabel.setText("");
            nextButton.setEnabled(currentQuestionIndex < questions.size() - 1);
            submitButton.setEnabled(currentQuestionIndex == questions.size() - 1);
        }
    }

    private void handleNextQuestion() {
        checkAnswer();
        
        // Disable all buttons while showing feedback
        for (JButton button : optionButtons) {
            button.setEnabled(false);
        }
        nextButton.setEnabled(false);
        
        // Show feedback for 2 seconds
        javax.swing.Timer feedbackTimer = new javax.swing.Timer(2000, e -> {
            // Re-enable buttons
            for (JButton button : optionButtons) {
                button.setEnabled(true);
            }
            nextButton.setEnabled(currentQuestionIndex < questions.size() - 1);
            
            // Move to next question
            currentQuestionIndex++;
            displayQuestion();
        });
        feedbackTimer.setRepeats(false);
        feedbackTimer.start();
    }

    private void startTimer() {
        examTimer = new Timer();
        examTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    int minutes = timeRemaining / 60;
                    int seconds = timeRemaining % 60;
                    SwingUtilities.invokeLater(() -> {
                        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                        // Change color to red when less than 1 minute remains
                        if (timeRemaining <= 60) {
                            timerLabel.setForeground(new Color(231, 76, 60));
                        }
                    });
                } else {
                    examTimer.cancel();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(ExamSystemGUI.this,
                            "Time's up! Your exam will be submitted automatically.",
                            "Time Expired",
                            JOptionPane.WARNING_MESSAGE);
                        handleSubmit();
                    });
                }
            }
        }, 1000, 1000); // Update every second
    }

    private void handleSubmit() {
        if (examTimer != null) {
            examTimer.cancel();
        }
        checkAnswer();
        
        // Disable all buttons while showing feedback
        for (JButton button : optionButtons) {
            button.setEnabled(false);
        }
        submitButton.setEnabled(false);
        
        // Show feedback for 2 seconds before showing final score
        javax.swing.Timer feedbackTimer = new javax.swing.Timer(2000, e -> {
            String result = score < 4 ? "FAIL" : "PASS";
            JOptionPane.showMessageDialog(this,
                "Exam completed!\nYour score: " + score + "/" + questions.size() + "\nResult: " + result,
                "Exam Results",
                JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });
        feedbackTimer.setRepeats(false);
        feedbackTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new ExamSystemGUI().setVisible(true);
        });
    }
} 