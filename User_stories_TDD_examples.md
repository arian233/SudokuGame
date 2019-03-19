**User Stories 1:**

As a teacher, I want to help students to improve their Problem-solving skills with a Sudoku game so that they can learn a new language in a more interactive way.

**TDD examples:**

1. Each time that my students launch the app, the program generates a different set of words and puzzles to help them practice variety of words. **[Complete]**
2. Each time that my students click on one of the empty cells in the grid, it highlights the location by showing a crosshair and the corresponding smaller region's boundary in colour. **[Complete]**
3. When my students want a hint of the translations of the corresponding words, the students could short click the preset words. **[Complete]**
4. When my students want to change their answer, they can use the erase button to erase the cells(cells need to be filled ). **[Complete]**
5. When a student wants to change the number of empty grid,  the students can choose difficulty level of the Sudoku game to set the number of empty grids. **[To be implemented]**
6. When a student select switch language button, the student can choose one of the two types of languages he/she wants to learn. **[Complete]**
7. When students want to measure their performances, they can look at the clock counter, which shows how much  time has past. **[Complete]**
8. When student entered a repeat word, a instant feedback pops up and the program will prevent the student enter that answer. **[Complete]**

&nbsp;

**User stories 2:**

As a student, I want to practice my understanding of spoken words in the language that I am learning.

**TDD Examples:**

1. In the Sudoku application, the student could click the listening mode button to get into the listening mode. In the listening mode, the user could click the cell that corresponding the word that he/she wants to practice, the application will play the pronunciation of the word. **[Complete]**

&nbsp;

**User Stories 3:**

As a user, I want to use this app in either Day time or Night time so that it does not hurt my eyes.

**TDD Example:**

1. When the user wants to change the background, the user can click the theme button to change the grid theme between dark mode or light mode **[To be implemented]**

&nbsp;

**User Stories 4:**

As a teacher, I want to specify a list of word pairs for my students to practice this week.

**TDD Examples:**

1. The teacher could specify a group of words or different word chapters that students need to practice by click import button to add those words into a CSV file, later the student could use the wordlists button to import the words pairs from the wordlists CSV files into the current Sudoku Puzzle.**[Complete]**

&nbsp;

**User Stories 5:**

As a student working with a textbook, I want to load pairs of words to practice from each chapter of the book.

**TDD Examples:**

1. Students can extract those words from each chapter to a CSV file by click import button. Then each time that students want to practice those words, they can directly click the wordlists button to import those words to the current Suduko puzzle.**[Complete]**
2. When the student wants to import the CSV file, he/she can name the file by the user itself, after choosing the file, click ok to import the file successfully.**[Complete]**
3. Each time that students click the refresh button, it generates a new puzzle for the students to solve.**[Complete]**

&nbsp;

**User Stories 6:**

As a student, I want the Sudoku app to keep track of the vocabulary words that I am having difficulty recognizing so that they will be used more often in my practice puzzles.

**TDD Examples:**

1. While playing the game, the application keeps track of the words that users are not familiar with, and this based on the number of times that user clicks for the word hint, and with the tracking result, those unfamiliar words will appear more often in next plays than those familiar words.**[Complete]**
2. When the user clicks the refresh button, the puzzle will be regenerated and top 3 unfamiliar words[3 words that have the highest frequency of hint clicking] will appear again in the new puzzle.**[Complete]**

&nbsp;

**User Stories 7:**

As a vocabulary learner, I want to use my phone or tablet(different devices) for Sudoku vocabulary practice, so that I want the words' font size be automatically changed to the size that fits for different devices.

**TDD Examples:**

1. When the users use different devices to playing the Sudoku application, the words changing size depends on the real size of the screen in inches so that the size of the words could automatically be changed to the size that suitable to the current screen.**[Complete]**