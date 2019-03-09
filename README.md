#### **Sudoku Vocabulary Application** 
This is a 9*9 Sudoku application that helps the users to learn a second language.

When the users launch the application, it will automatically generate a number puzzle for the user to play.

This app has been tested on emulator phone "Nexus 5X API 21" and tablet "10.1 WXGA(Tablet) API 24"(As the requirements of this iteration the API supposed to be 21. 
The reason why we use API 24 is to test the sound feature in listening mode , but every features of application works in API 21 except the sound feature in listening mode.). The test environment is MACOS 10.14.2, 10.13 and Windows 10.
This app right now is runnable for portrait screen mode and landscape mode.[when the user rotates the screen the app will save the state.]

**User Instruction:**
- Click on an empty cell, the highlighted reticle will indicates the cell that the user select, also the app will pop out another select screen for the user to select the answers. 

- While playing the sudoku, the user can switch between Chinese and English by tapping on "Switch language" button.

- The application has the ability to check for repetitions instantly.

- This application features a timer on the top of the screen.

- While playing the sudoku, the user can click the preset gird to get a short pop up message as translation hint of that word.

- While playing the sudoku, the user can use the erase button to erase the cells(cells need to be filled )to change their answer.

- While playing the sudoku, the user can click the listening mode button to switch the app to a listening mode. When the user clicks a preset cell, the pronunciation of the word will be played.

- The user can click the "+" sign button to import the CSV files that contain the two language pairs to the current puzzle.
  - When the user wants to import the CSV file, he/she can name the file by the user itself, after choosing the file, click ok to import the file successfully.
  - all the imported file will displayed in the wordlist(another fragment).

- The user can click the refresh(another imageButton) button to generate a new puzzle.
  - Rule of refreshing: 
    Based on how many times the user clicks the preset cell for the hint, 3 highest clicked words (unfamiliar words) will appear in the new puzzle.
  - If user doesn't click any preset grid, then the application thinks the user is familiar with all words, so the next time user generate new puzzle, there are not any unfamiliar word.
    
- The user can click the wordlist button to choose word list.
  - When user choose a word list from wordlist(chapter 1 and chapter 2 are wordlists from textbook, the puzzle generate words from chapter 1 defaultly), user needs to press refresh button to generate new puzzle.
  
- After the user finishes the puzzle, he/she can click the checkResult button to check if he/she gets the correct answer.


**This application was created by group IOTA as the first team iteration in CMPT276**