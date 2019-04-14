**User Stories 1:**

As a teacher, I want to help students to improve their Problem-solving skills with a Sudoku game, so that they can learn a new language in a more interactive way.

**TDD examples:**

1. **[Complete]**
   - Given: that listening comprehension mode is enabled
     - Or normal text mode is enabled
   - When: the student press **refresh button**
     - Or each time initials the app
   - Then: the program generates a new puzzle filled with different words pairs from the default or chosen word list accordingly 
2. **[Complete]**
   - Given: that listening comprehension mode is enabled
     - Or normal text mode is enabled
   - When: the student clicks on one of the **empty cells**
   - Then: the program highlights the row and column of that empty cell by showing a crosshair in colour
3. **[Complete]**
   - Given: that normal text mode is enabled
   - When: the student clicks on one of the **prefilled cells**
   - Then: the program pops up a tranlastion of the word which the student clicked
4. **[Complete]**
   - Given: that listening comprehension mode is enabled
     - Or normal text mode is enabled
   - When: the student first clicks on the cells which has been entered in an answer
     - and then clicks on the **erase** button
   - Then: the cell will be erased and be empty
5. **[Complete]**
   - Given: on the setting of the welcome page
   - When: the student changes **language**
   - Then: the program will switch the languages it displays in the prefilled cell
6. **[Complete]**
   - Given: that listening comprehension mode is enabled
     - Or normal text mode is enabled
   - When: the student look at the **clock counter** at the middle top of the screen
   - Then: studentcan know how much time has past
7. **[Complete]**
   - Given: that normal text mode is enabled
   - When: the student entered a repeat word
   - Then:  a instant caution pops up
     - and the program will prevent the student enter that answer
8. **[Complete]**
   - Given: that normal text mode is enabled
   - When: the student press an **empty cell**
   - Then:  a word list will be shown
     - Student can choose one of the words to enter into the empty grid
     - if the chosen answer is not repeated, then it'll be shown in the cell

------

**User stories 2:**

As a student, I want to listen to the words I'm learning, so that I can practice my understanding of spoken words in the language that I am learning.

**TDD Examples:**

1. **[Complete]** 
   - Given: on the setting of the welcome page
   - When: the student changes **game mode** to listening mode
        - and click start button
   - Then: the student sees a standard Sudoku grid with some prefilled cells showing digits in the range 1..9/1…4/1…6/1…12 accordingly and all other cells empty
2. **[Complete]** 
   - Given: on the setting of the welcome page
   - When: the student changes **language**
   - Then: the program switch the languages it speaks
3. **[Complete]**
   - Given: that listening comprehension mode is enabled
   - When: the student clicks **empty cells**
   - Then: the program will pops up a list answer for the student to enter into the cell
4. **[Complete]** 
   - Given: that the student is filling in the grid in listening comprehension mode,
     - and that the grid includes a cell with the prefilled digit 4
     - and that word pair 4 is (green, 绿)
   - When: the student presses the **prefilled cell** having the digit 4
   - Then: the student hears the word "绿" read out and pronounced in Chinese.
5. **[Complete]** 
   - Given: that the student is filling in the grid in listening comprehension mode,
   - When: the student presses a cell and hears the word "绿"
   - Then: the student does not see the word "绿" anywhere on the game grid.
6. **[Complete]** 
   - Given: that the student is filling in the grid in listening comprehension mode,
     - and that the grid includes a cell with the prefilled digit 4
     - and that word pair 4 is (green, 绿)
   - When: the student selects a **empty cell** to enter the word "green"
   - Then: the word "green" appears in the list of words that may be selected, but not in the fourth position

------

**User Stories 3:**

As a teacher, I want to specify a list of word pairs for my students, so that they can practice different sets of words each week.

**TDD Examples:**

1. **[Complete]**
   - Given: on the welcome page
   - When: the student click the **import button**
     - and  choose a **csv file** from his phone 
   - Then:  the app will import a list of words from the chosen csv file into the word list of app
2. **[Complete]**
   - Given: on import file page
   - When: the student imports the **csv file**
   - Then:  the app will ask the student to enter a name for the word list

------

**User Stories 4:**

As a student, I want to work with a textbook, so that I can practice word from each chapter of the book.

**TDD Examples:**

1. **[Complete]**
   - Given: on the welcome page
   - When: the student click the **words lists button**
     - and choose a **chapter** from words lists page
     - and click the **start button**
   - Then:  the app will generate a new puzzle fill with the words which the user chose
2. **[Complete]**
   - Given: on the setting of the welcome page
   - When: the student click the **words lists button**
   - Then: the wordlist page will be displayed, with preset word lists of each chapter
     - and the imported word list will be displayed by its name

------

**User Stories 5:**

As a student, I want the Sudoku app to keep track of the vocabulary words that I am having difficulty recognizing, so that they will appear more often in my future practice puzzles.

**TDD Examples:**

1. **[Complete]**
   - Given: that normal text mode is enabled
   - When: the student click the **prefilled grids** to get hints
     - and click the **refresh button** to initial a new puzzle
   - Then:  the app will keep track of three prefilled words pairs which has been clicked most times
     - and next puzzle will include these three words, no matter which word list is going to be chosen

------

**User Stories 6:**

As a vocabulary learner, I want to use my phone or tablet(different devices) for Sudoku vocabulary practice, so that I can have a bigger font size to see, and different puzzle size to play with.

**TDD Examples:**

1. **[Complete]**
   - Given: that listening comprehension mode is enabled
     - Or normal text mode is enabled
   - When: the student rotate the **screen** 
   - Then:  the app will increase the font size accordingly, and the width of the grids
2. **[Complete]**
   - Given: on the setting of the welcome page
   - When: the student click the **puzzle size** 
     - and choose one of the 4x4, 6x6, 9x9, 12x12 puzzle sizes
     - and click start button
   - Then:  the app will change the puzzle size accordingly
3. **[Complete]** 
   - Given: normal text mode is enabled
   - When: the student **long click** the prefilled grid
   - Then: the app will pop up the word, in case in some situation that the font size is small

------

**User Stories 7:**

As a vocabulary learner, I want to have a setting for the app, so that the places for the buttons are arranged appropriately.

**TDD Examples:**

1. **[Complete]** **[final iteration]**
   - Given: user launched the app
   - When: the user sees a welcome page with setting button
     - and the user sees import and words lists button
   - Then:  the user can change the settings for the app, select word list, or import file on the welcome page 

------

**User Stories 8:**

As a user, I want to have a dark theme, so that it does not hurt my eyes in either Day time or Night time.

**TDD Example:**

1. **[Complete]** **[final iteration]**
   - Given: on the setting of the welcome page
   - When: the student choose the light or dark **theme**
   - Then: the program switch themes accordingly

------

**User Stories 9:**

As a user, I want to change the puzzle difficulty, so that I won't be pushed away by too difficult or too easy puzzle.

**TDD Example:**

1. **[Complete]** **[final iteration]**
   - Given: on the setting of the welcome page
   - When: the student choose the difficulty of the puzzle by **difficulty button**  
     - and click **start button** to initials the game 
   - Then: the program generates a puzzles with different number of empty cells accordingly

------

**User Stories 10:**

As a user, I want to have a picture for each words, so that I can memorize the word by seeing a picture.

**TDD Example:**

1. **[Complete]** **[final iteration]**
   - Given: on the normal text mode
   - When: the user clicks the **prefilled grid** 
   - Then: on the corner, the program will show a picture which relates to the selected word by getting image online
     - (It will work for the existing wordlist "chapter 2" and other wordlist that the user import)
     - (But for wordlist "chapter 1", since the content of this wordlist is all numbers, it is too abstract for the app to find a picture and then match to the corresponding word)
