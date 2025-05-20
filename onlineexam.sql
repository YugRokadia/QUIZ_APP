drop database online_exam;
CREATE DATABASE online_exam;
USE online_exam;



CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

INSERT INTO users (username, password) VALUES 
('student1', 'pass123'),
('student2', 'pass456');

CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_text TEXT NOT NULL,
    option_a VARCHAR(255),
    option_b VARCHAR(255),
    option_c VARCHAR(255),
    option_d VARCHAR(255),
    correct_option CHAR(1)
);

INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option) VALUES 
('What is the capital of France?', 'Berlin', 'London', 'Paris', 'Rome', 'C'),
('2 + 2 = ?', '3', '4', '5', '6', 'B'),
('Java is ___?', 'a snake', 'a drink', 'a language', 'a dance', 'C');



DROP DATABASE IF EXISTS online_exam;
CREATE DATABASE online_exam;
USE online_exam;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL,
    option_a VARCHAR(255),
    option_b VARCHAR(255),
    option_c VARCHAR(255),
    option_d VARCHAR(255),
    correct_option CHAR(1),
    category VARCHAR(50)  -- 'GK' or 'Java'
);

INSERT INTO users (username, password) VALUES
('admin', 'admin123'),
('student1', 'password1'),
('jack', 'spiderman');

INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option, category) VALUES
-- GK (2)
('Which planet is known as the Red Planet?', 'Earth', 'Mars', 'Jupiter', 'Saturn', 'B', 'GK'),
('Who is known as the Father of the Nation in India?', 'Subhash Chandra Bose', 'Bhagat Singh', 'Mahatma Gandhi', 'Jawaharlal Nehru', 'C', 'GK'),

-- JAVA (8)
('Which keyword is used to inherit a class in Java?', 'this', 'super', 'extends', 'implements', 'C', 'Java'),
('Which of the following is not a Java primitive type?', 'int', 'boolean', 'String', 'char', 'C', 'Java'),
('What is the size of an int in Java?', '2 bytes', '4 bytes', '8 bytes', 'Depends on system', 'B', 'Java'),
('Which method is the entry point of a Java program?', 'start()', 'run()', 'main()', 'execute()', 'C', 'Java'),
('Which of these is not a feature of Java?', 'Object-Oriented', 'Platform Dependent', 'Robust', 'Secure', 'B', 'Java'),
('What is the default value of a boolean variable in Java?', 'true', 'false', '0', 'null', 'B', 'Java'),
('Which interface must be implemented by a Java class to support multi-threading?', 'Runnable', 'Serializable', 'Comparable', 'Cloneable', 'A', 'Java'),
('Which exception is thrown when an array is accessed with an illegal index?', 'NullPointerException', 'ArrayIndexOutOfBoundsException', 'ArithmeticException', 'NumberFormatException', 'B', 'Java');

-- GK Questions (25)

INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option, category) VALUES
('What is the capital of Australia?', 'Sydney', 'Melbourne', 'Canberra', 'Brisbane', 'C', 'GK'),
('Which is the largest ocean in the world?', 'Atlantic', 'Indian', 'Arctic', 'Pacific', 'D', 'GK'),
('Who wrote the play "Hamlet"?', 'Charles Dickens', 'William Shakespeare', 'Jane Austen', 'Mark Twain', 'B', 'GK'),
('What is the chemical symbol for gold?', 'Au', 'Ag', 'Fe', 'Cu', 'A', 'GK'),
('Which planet is known as the "Morning Star" or "Evening Star"?', 'Mars', 'Venus', 'Jupiter', 'Saturn', 'B', 'GK'),
('What is the largest desert in the world?', 'Sahara', 'Arabian', 'Gobi', 'Antarctic', 'D', 'GK'),
('Who painted the "Mona Lisa"?', 'Vincent van Gogh', 'Leonardo da Vinci', 'Pablo Picasso', 'Michelangelo', 'B', 'GK'),
('What is the largest mammal in the world?', 'Elephant', 'Blue Whale', 'Giraffe', 'Hippopotamus', 'B', 'GK'),
('Which country is known as the "Land of the Rising Sun"?', 'China', 'Korea', 'Vietnam', 'Japan', 'D', 'GK'),
('What is the boiling point of water in Celsius?', '100', '0', '50', '212', 'A', 'GK'),
('Who discovered penicillin?', 'Marie Curie', 'Alexander Fleming', 'Albert Einstein', 'Isaac Newton', 'B', 'GK'),
('What is the currency of Japan?', 'Yuan', 'Won', 'Ringgit', 'Yen', 'D', 'GK'),
('Which gas do plants absorb from the atmosphere?', 'Oxygen', 'Nitrogen', 'Carbon Dioxide', 'Hydrogen', 'C', 'GK'),
('What is the largest river in South America?', 'Amazon', 'Nile', 'Mississippi', 'Yangtze', 'A', 'GK'),
('Who was the first president of the United States?', 'Thomas Jefferson', 'George Washington', 'John Adams', 'James Madison', 'B', 'GK'),
('What is the capital of Canada?', 'Toronto', 'Vancouver', 'Montreal', 'Ottawa', 'D', 'GK'),
('Which element is essential for human bones?', 'Iron', 'Calcium', 'Potassium', 'Sodium', 'B', 'GK'),
('What is the speed of light in a vacuum?', '300,000 km/s', '150,000 km/s', '500,000 km/s', '100,000 km/s', 'A', 'GK'),
('Who invented the telephone?', 'Thomas Edison', 'Alexander Graham Bell', 'Nikola Tesla', 'Benjamin Franklin', 'B', 'GK'),
('What is the largest organ in the human body?', 'Heart', 'Liver', 'Skin', 'Brain', 'C', 'GK'),
('Which continent is known as the "Dark Continent"?', 'Asia', 'Europe', 'Africa', 'Australia', 'C', 'GK'),
('What is the chemical formula for water?', 'CO2', 'NaCl', 'H2O', 'O2', 'C', 'GK'),
('Who developed the theory of relativity?', 'Isaac Newton', 'Albert Einstein', 'Galileo Galilei', 'Stephen Hawking', 'B', 'GK'),
('What is the tallest mountain in the world?', 'K2', 'Mount Everest', 'Kangchenjunga', 'Makalu', 'B', 'GK'),
('Which animal is known as the "King of the Jungle"?', 'Lion', 'Tiger', 'Elephant', 'Giraffe', 'A', 'GK');

-- Java Questions (25)

INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option, category) VALUES
('Which keyword is used to define a constant variable in Java?', 'final', 'static', 'const', 'constant', 'A', 'Java'),
('What is the purpose of the "super" keyword in Java?', 'To create a superclass', 'To call a superclass constructor', 'To define a superclass method', 'To declare a superclass variable', 'B', 'Java'),
('Which of the following is an abstract class in Java?', 'String', 'ArrayList', 'Scanner', 'AbstractList', 'D', 'Java'),
('What is the output of "System.out.println(10 + 20 + "Java");"?', '30Java', 'Java30', '1020Java', 'Java1020', 'A', 'Java'),
('Which access modifier makes a variable accessible only within its own class?', 'public', 'private', 'protected', 'default', 'B', 'Java'),
('What is the purpose of the "static" keyword in Java?', 'To create an instance variable', 'To create a class variable', 'To define a constant', 'To inherit a class', 'B', 'Java'),
('Which interface is used to create a thread in Java?', 'Thread', 'Runnable', 'Callable', 'Future', 'B', 'Java'),
('What is the output of "System.out.println(10 / 0);"?', '0', 'Infinity', 'NaN', 'ArithmeticException', 'D', 'Java'),
('Which method is used to compare two strings in Java?', 'equals()', 'compare()', 'match()', 'isEqual()', 'A', 'Java'),
('What is the purpose of the "try-catch" block in Java?', 'To define a loop', 'To handle exceptions', 'To create an object', 'To declare a variable', 'B', 'Java'),
('Which collection class implements the List interface in Java?', 'HashSet', 'HashMap', 'ArrayList', 'TreeSet', 'C', 'Java'),
('What is the output of "System.out.println("Java".substring(1, 3));"?', 'av', 'va', 'jav', 'ja', 'A', 'Java'),
('Which keyword is used to create an object of a class in Java?', 'new', 'create', 'object', 'instance', 'A', 'Java'),
('What is the purpose of the "finally" block in a try-catch statement?', 'To handle exceptions', 'To execute code regardless of exceptions', 'To define a loop', 'To create an object', 'B', 'Java'),
('Which class is used to read input from the console in Java?', 'Scanner', 'BufferedReader', 'InputStreamReader', 'Console', 'A', 'Java'),
('What is the output of "System.out.println(10 % 3);"?', '0', '1', '3', '3.33', 'B', 'Java'),
('Which method is used to convert a string to an integer in Java?', 'parseInt()', 'toString()', 'valueOf()', 'toInt()', 'A', 'Java'),
('What is the purpose of the "this" keyword in Java?', 'To refer to the current object', 'To call a superclass constructor', 'To define a static variable', 'To create an instance of a class', 'A', 'Java'),
('Which data structure uses the LIFO (Last-In-First-Out) principle?', 'Queue', 'Stack', 'LinkedList', 'ArrayList', 'B', 'Java'),
('What is the output of "System.out.println(Math.pow(2, 3));"?', '6.0', '8.0', '9.0', '10.0', 'B', 'Java'),
('Which interface is used to iterate over a collection in Java?', 'Iterable', 'Iterator', 'Enumeration', 'ListIterator', 'B', 'Java'),
('What is the purpose of the "instanceof" operator in Java?', 'To create an instance of a class', 'To check if an object is an instance of a class', 'To define a static variable', 'To call a superclass constructor', 'B', 'Java'),
('Which class is used to format dates in Java?', 'SimpleDateFormat', 'DateFormat', 'CalendarFormat', 'DateFormatter', 'A', 'Java');
