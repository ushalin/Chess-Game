// ICS4U1
// Chess - ISU Project
// Written by : Shalin Upadhyay, Cameron Frizado, Kalan Dowrich
// Written for : Ms.Ganesan
// Due : June 13, 2014
// Submitted : June 13, 2014
//
// Program Purpose: Purpose of the program is to recreate the well known game, Chess.
//
// Class Purpose: The purpose of the class is to create a user friendly graphical user interface using swings
//
// Variables & Objects Used :
//
// fw - FileWriter used to write settings chosen by user into file
// contentPane - Main content panel into which all other swing components are added
// start - JButton used to take the program to the screen where users enter their names
// settings - JButton used to take the program to the settings screen
// instructions - JButton used to take the program to the instructions screen
// credits - JButton used to take the program to the credits screen
// exit - JButton used to exit the program
// back - JButton used in several pages in the menu to return the user back to the main menu
// pieceinstr - JButton used to take the program to the piece instruction screen
// pieceback - JButton used to take the program back to the main menu
// board1 - JCheckBox that can be seleceted to represent the first board
// board2 - JCheckBox that can be seleceted to represent the second board
// board3 - JCheckBox that can be seleceted to represent the third board
// board4 - JCheckBox that can be seleceted to represent the fourth board
// board5 - JCheckBox that can be seleceted to represent the fifth board
// piece1 - JCheckBox that can be seleceted to represent the first piece set
// piece2 - JCheckBox that can be seleceted to represent the second piece set
// groupboards - ButtonGroup for JCheckBox's for boards to allow only one to be chosen at one time
// grouppieces - ButtonGroup for JCheckBox's for pieces to allow only one to be chosen at one time
// textField - TextField to allow player1 to enter their name
// textField2 - TextField to allow player2 to enter their name
// startScreen - Reads in "ChessNameSelect.jpg"
// startImg - Assigns the "ChessNameSelect.jpg" image
// mainScreen - Reads in "ChessMENUBack.jpg"
// mainImg - Assigns the "ChessMENUBack.jpg" image
// instructionScreen - Reads in "Instructions.jpg"
// instructionImg - Assigns the "Instructions.jpg" image
// settingsScreen - Reads in "settings.jpg"
// settingsImg - Assigns the "settings.jpg" image
// creditScreen - Reads in "Credits Screen.jpg"
// creditImg - Assigns the "Credits Screen.jpg" image
// pieceScreen - Reads in "Piece Movements.jpg"
// pieceImg - Assigns the "Piece Movements.jpg" image
// b1 - Reads in "board1.jpg"
// b1Screen - Assigns the "board1.jpg" image
// b2 - Reads in "board2.jpg"
// b2Screen - Assigns the "board2.jpg" image
// b3 - Reads in "board3.jpg"
// b3Screen - Assigns the "board3.jpg" image
// b4 - Reads in "board4.jpg"
// b4Screen - Assigns the "board4.jpg" image
// b5 - Reads in "board5.jpg"
// b5Screen - Assigns the "board5.jpg" image
// font - Custom font used when the users enter their names into the JTextFields
// drawCheck - int checker used to check for which image should be drawn
// visibleCheck - int checker used to check which swing components(ie. JButtons, JCheckBoxes, JTextFields) should be visible
// instrcheck - int checker used to check which instructions screen is present and which image & swing components should be visible
// board - int variable used to represent choice of board by user, it is then written into "settings.txt" which is used by ChessEngine() to draw the board
// piece - int variable used to represent choice of pieces by user, it is then written into "settings.txt" which is used by ChessEngine() to draw the pieces
// player1 - String variable to represent name of a user
// player2 - String variable to represent name of the other player
// playerName1 - Vector that holds the name of player1
// playerName2 - Vector that holds the name of player2
// t - Declares a thread, which is used to constantly repaint an image
// stopThread - boolean variable used to stop thread from running
// command - Object used to get the source of the action performed
//
// Methods Used:
//
// main - Calls on the "MainMenu" constructor
// MainMenu - Constructor that initializes all swing components and creates the user friendly graphical user interface
// run - Thread method which constantly redraws an image depending on the value of drawCheck
// paint - Paints all imported images depending on the value of drawCheck
// setVisible - Sets the visibilty of JButtons, JCheckBoxes, and JTextFields depending on the value of visibleCheck
// actionPerformed - Invoked to check if an action occurs, and depending on the type of action causes something to occur
// MouseReleased - Invoked to check if a mouse button is let up. Based on this action occuring in a certain area, it causes something to occur
//

//Imports all necessary packages
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

public class MainMenu extends JFrame implements ActionListener, MouseListener, Runnable //Implements all required interfaces
{
    //All global variables and objects used throughout the MainMenu class
    FileWriter fw = null;
    JPanel contentPane;
    JButton start, settings, instructions, credits, exit, back, pieceinstr, pieceback;
    JCheckBox board1, board2, board3, board4, board5, piece1, piece2;
    ButtonGroup groupboards, grouppieces;
    JTextField textField, textField2;
    Vector playerName1, playerName2;
    Thread t;
    Font font;
    ImageIcon startScreen, mainScreen, instructionScreen, settingsScreen, creditScreen, pieceScreen, b1, b2, b3, b4, b5;
    Image startImg, mainImg, instructionImg, settingsImg, creditImg, pieceImg, b1Screen, b2Screen, b3Screen, b4Screen, b5Screen;

    int drawCheck = 0;
    int visibleCheck = 0;
    int instrcheck = 0;
    int board = 1;
    int piece = 1;
    String player1, player2;
    boolean stopThread = false;

    //Creates a new JFrame and calls on MainMenu constructor
    public static void main (String args[])
    {
	new MainMenu ();
    }


    //Constructor for the class
    public MainMenu ()
    {
	//Sets output panel size of 800 x 800
	this.setSize (800, 800);
	//Will completely stop program from running if "Close" button is pressed
	this.setDefaultCloseOperation (EXIT_ON_CLOSE);
	//Sets the location of the output window to the center of the screen
	this.setLocationRelativeTo (null);
	//Prevents user from resizing the output screen
	this.setResizable (false);
	//Gets root content pane
	contentPane = (JPanel) this.getContentPane ();
	//Layout of contentPane is set to null, to allow canvas style layout
	contentPane.setLayout (null);

	//Creates vectors to store the name of both players
	playerName1 = new Vector ();
	playerName2 = new Vector ();

	//Creates the font object with specific parameters
	font = new Font ("Comic Sans MS", Font.BOLD, 20);

	//Creates image objects with required images
	startScreen = new ImageIcon ("NameSelect.jpg");
	mainScreen = new ImageIcon ("Menu.jpg");
	instructionScreen = new ImageIcon ("Instructions.jpg");
	settingsScreen = new ImageIcon ("settings.jpg");
	creditScreen = new ImageIcon ("Credits Screen.jpg");
	pieceScreen = new ImageIcon ("Piece Movements.jpg");
	b1 = new ImageIcon ("board1.jpg");
	b2 = new ImageIcon ("board2.jpg");
	b3 = new ImageIcon ("board3.jpg");
	b4 = new ImageIcon ("board4.jpg");
	b5 = new ImageIcon ("board5.jpg");

	//Initializes objects to hold respective images
	startImg = startScreen.getImage ();
	mainImg = mainScreen.getImage ();
	instructionImg = instructionScreen.getImage ();
	settingsImg = settingsScreen.getImage ();
	creditImg = creditScreen.getImage ();
	pieceImg = pieceScreen.getImage ();
	b1Screen = b1.getImage ();
	b2Screen = b2.getImage ();
	b3Screen = b3.getImage ();
	b4Screen = b4.getImage ();
	b5Screen = b5.getImage ();

	//Creates all JButtons for the menu
	start = new JButton ("Start");
	settings = new JButton ("Settings");
	instructions = new JButton ("Instructions");
	credits = new JButton ("Credits");
	exit = new JButton ("Exit");
	back = new JButton ("Back");
	pieceinstr = new JButton ("Piece Instructions");
	pieceback = new JButton ("Back to Menu");

	//Creates all JCheckBoxes for the boards
	board1 = new JCheckBox ("board1");
	board1.setSelected (true); //Sets board1 as default board
	board2 = new JCheckBox ("board2");
	board3 = new JCheckBox ("board3");
	board4 = new JCheckBox ("board4");
	board5 = new JCheckBox ("board5");

	//Creates all JCheckBoxes for the piece types
	piece1 = new JCheckBox ("piece1");
	piece1.setSelected (true); //Sets piece1 as default piece type set
	piece2 = new JCheckBox ("piece2");

	//Creates JTextField for player1 and player2 name
	textField = new JTextField ("", 20);
	textField2 = new JTextField ("", 20);

	//Creates the ButtonGroup for the boards and the piece sets
	groupboards = new ButtonGroup ();
	grouppieces = new ButtonGroup ();

	//Initializes the thread object
	t = new Thread (this);

	//Adds the JCheckBoxes for board1, board2, board3, board4, board5 to the groupboards ButtonGroup
	groupboards.add (board1);
	groupboards.add (board2);
	groupboards.add (board3);
	groupboards.add (board4);
	groupboards.add (board5);

	//Adds the JCheckBoxes for piece1, piece2 to the grouppieces ButtonGroup
	grouppieces.add (piece1);
	grouppieces.add (piece2);

	//Sets appropriate bounds for all JTextFields, JButtons & JCheckBoxes
	textField.setBounds (243, 58, 300, 57);
	textField2.setBounds (243, 293, 300, 57);
	start.setBounds (71, 179, 65, 20);
	settings.setBounds (71, 255, 105, 22);
	instructions.setBounds (67, 342, 155, 22);
	credits.setBounds (71, 428, 92, 22);
	exit.setBounds (85, 544, 45, 22);
	back.setBounds (24, 732, 100, 20);
	pieceinstr.setBounds (530, 130, 174, 69);
	pieceback.setBounds (624, 581, 113, 75);
	board1.setBounds (90, 245, 50, 20);
	board2.setBounds (175, 245, 50, 20);
	board3.setBounds (275, 245, 50, 20);
	board4.setBounds (357, 245, 50, 20);
	board5.setBounds (458, 245, 50, 20);
	piece1.setBounds (80, 465, 50, 20);
	piece2.setBounds (175, 465, 50, 20);

	//Sets appropriate visibility for all JTextFields, JButtons & JCheckBoxes
	textField.setVisible (false);
	textField2.setVisible (false);
	start.setVisible (true);
	settings.setVisible (true);
	instructions.setVisible (true);
	credits.setVisible (true);
	exit.setVisible (true);
	back.setVisible (false);
	pieceinstr.setVisible (false);
	pieceback.setVisible (false);
	board1.setVisible (false);
	board2.setVisible (false);
	board3.setVisible (false);
	board4.setVisible (false);
	board5.setVisible (false);
	piece1.setVisible (false);
	piece2.setVisible (false);

	//Adds all JTextFields, JButtons & JCheckBoxes to the JPanel
	contentPane.add (textField);
	contentPane.add (textField2);
	contentPane.add (start);
	contentPane.add (settings);
	contentPane.add (instructions);
	contentPane.add (credits);
	contentPane.add (exit);
	contentPane.add (back);
	contentPane.add (pieceinstr);
	contentPane.add (pieceback);
	contentPane.add (board1);
	contentPane.add (board2);
	contentPane.add (board3);
	contentPane.add (board4);
	contentPane.add (board5);
	contentPane.add (piece1);
	contentPane.add (piece2);

	//Adds Action Listener for all JButtons & JCheckBoxes
	start.addActionListener (this);
	settings.addActionListener (this);
	instructions.addActionListener (this);
	credits.addActionListener (this);
	exit.addActionListener (this);
	back.addActionListener (this);
	pieceinstr.addActionListener (this);
	pieceback.addActionListener (this);
	board1.addActionListener (this);
	board2.addActionListener (this);
	board3.addActionListener (this);
	board4.addActionListener (this);
	board5.addActionListener (this);
	piece1.addActionListener (this);
	piece2.addActionListener (this);

	//Creates mouse listener, for when an action occurs involving the mouse
	addMouseListener (this);

	//Shows the JFrame and displays the output screen
	this.show ();

	//Begins the thread
	t.start ();
    }


    //Runnable method that is called when thread is initialized
    public void run ()
    {
	//Continually runs until requested to stop
	while (stopThread == false)
	{
	    //If the start screen is not visible, repaint the screen
	    if (drawCheck != 1)
	    {
		//repaints the screen
		repaint ();
	    }

	    try
	    {
		//If the settings screen is not visible
		if (drawCheck != 4)
		{
		    //Sleep the thread for 0 ms
		    Thread.sleep (0);
		}
		//If its on any other screen besides the settings screen
		else
		{
		    //Sleeps the thread for 0 ms
		    Thread.sleep (0);
		}
	    }

	    //Catch interruption in sleeping thread
	    catch (InterruptedException e)
	    {
	    }
	}
    }


    public void paint (Graphics g)
    {
	//If drawCheck equals 0, draw the main menu screen
	if (drawCheck == 0)
	{
	    g.drawImage (mainImg, 0, 0, null);
	}

	//If drawCheck equals 1, draw the starting screen
	else if (drawCheck == 1)
	{
	    g.drawImage (startImg, 0, 0, null);
	}

	//If drawCheck equals 2, draw the instruction screen
	else if (drawCheck == 2)
	{
	    g.drawImage (instructionImg, 0, 0, null);
	}

	//If drawCheck equals 3, draw the credits screen
	else if (drawCheck == 3)
	{
	    /*
	    int i = 0;
	    do
	    {
		try
		{
		    g.drawImage (creditImg, 0, i, null);
		    Thread.sleep (10);
		    i--;
		    repaint ();

		    if (i == -1250)
		    {
			i = 800;
			g.drawImage (creditImg, 0, i, null);
		    }
		}
		catch (InterruptedException e)
		{
		}
	    }
	    while (i != -1250);
	    */

	    g.drawImage (creditImg, 0, 0, null);
	}

	//If drawCheck equals 4, draw the settings screen
	else if (drawCheck == 4)
	{
	    g.drawImage (settingsImg, 0, 0, null);

	    //If board1 is selected, draw a black rectangle to show that it has been selected
	    if (board1.isSelected ())
	    {
		g.fillRect (104, 273, 18, 18);
	    }

	    //If board2 is selected, draw a black rectangle to show that it has been selected
	    if (board2.isSelected ())
	    {
		g.fillRect (194, 273, 18, 18);
	    }

	    //If board3 is selected, draw a black rectangle to show that it has been selected
	    if (board3.isSelected ())
	    {
		g.fillRect (294, 273, 18, 18);
	    }

	    //If board4 is selected, draw a black rectangle to show that it has been selected
	    if (board4.isSelected ())
	    {
		g.fillRect (379, 273, 18, 18);
	    }

	    //If board5 is selected, draw a black rectangle to show that it has been selected
	    if (board5.isSelected ())
	    {
		g.fillRect (480, 273, 18, 18);
	    }

	    //If piece1 is selected, draw a black rectangle to show that it has been selected
	    if (piece1.isSelected ())
	    {
		g.fillRect (99, 492, 18, 18);
	    }

	    //If piece2 is selected, draw a black rectangle to show that it has been selected
	    if (piece2.isSelected ())
	    {
		g.fillRect (198, 492, 18, 18);
	    }
	}

	//If drawCheck equals 5, checks which instructions screen is being shown
	else if (drawCheck == 5)
	{
	    //If instrcheck equals 1, draws the image for piece instructions
	    if (instrcheck == 1)
	    {
		g.drawImage (pieceImg, 0, 20, null);
	    }

	    //If instrcheck equals 2, draws the image for instructions on how to play
	    else if (instrcheck == 2)
	    {
		g.drawImage (instructionImg, 0, 0, null);

		//Resets the instructions checker back to 0
		instrcheck = 0;
		drawCheck = 2;
		repaint ();
	    }
	}
    }


    public void swingVisible ()
    {
	//If visibleCheck equals 0, sets all main menu buttons to false, except for the back button
	//User has clicked "Start"
	if (visibleCheck == 0)
	{
	    start.setVisible (false);
	    settings.setVisible (false);
	    instructions.setVisible (false);
	    credits.setVisible (false);
	    exit.setVisible (false);
	    pieceinstr.setVisible (false);
	    back.setVisible (true);
	}

	//If visibleCheck equals 1, sets all main menu buttons to true, and all JTextFields & JCheckBoxes to false
	//User is on the Main Menu
	else if (visibleCheck == 1)
	{
	    start.setVisible (true);
	    settings.setVisible (true);
	    instructions.setVisible (true);
	    credits.setVisible (true);
	    exit.setVisible (true);
	    back.setVisible (false);
	    pieceinstr.setVisible (false);
	    pieceback.setVisible (false);
	    board1.setVisible (false);
	    board2.setVisible (false);
	    board3.setVisible (false);
	    board4.setVisible (false);
	    board5.setVisible (false);
	    piece1.setVisible (false);
	    piece2.setVisible (false);
	    textField.setVisible (false);
	    textField2.setVisible (false);
	}

	//If visibleCheck equals 2, sets all JCheckBoxes to true, and sets piece instructions to false
	//User is in the settings page
	else if (visibleCheck == 2)
	{
	    board1.setVisible (true);
	    board2.setVisible (true);
	    board3.setVisible (true);
	    board4.setVisible (true);
	    board5.setVisible (true);
	    piece1.setVisible (true);
	    piece2.setVisible (true);
	    pieceinstr.setVisible (false);

	    //Resets visibleCheck to 0
	    visibleCheck = 0;

	    //Calls on swingVisible method
	    swingVisible ();
	}

	//If visibleCheck equals 3, sets all main menu buttons to false, and sets piece instruction to true
	//User is in second instructions page
	else if (visibleCheck == 3)
	{
	    start.setVisible (false);
	    settings.setVisible (false);
	    instructions.setVisible (false);
	    credits.setVisible (false);
	    exit.setVisible (false);
	    back.setVisible (false);
	    pieceinstr.setVisible (true);
	    pieceback.setVisible (true);
	}
    }


    public void actionPerformed (ActionEvent evt)
    {
	Object command = evt.getSource ();

	//Checks if the "start" button is pressed
	if (command == start)
	{
	    //Sets appropriate checkers.
	    drawCheck = 1;
	    visibleCheck = 0;

	    //Calls on the swingVisible method
	    swingVisible ();

	    //Sets properties for both JTextFields
	    textField.setVisible (true);
	    textField2.setVisible (true);
	    textField.setText ("");
	    textField2.setText ("");
	    textField.setFont (font);
	    textField.setForeground (Color.RED);
	    textField.setBackground (Color.GRAY);
	    textField2.setFont (font);
	    textField2.setForeground (Color.RED);
	    textField2.setBackground (Color.GRAY);

	    //Repaints the screen
	    repaint ();
	}

	//Checks if the "settings" button is pressed
	else if (command == settings)
	{
	    //Sets appropriate checkers.
	    drawCheck = 4;
	    visibleCheck = 2;

	    //Calls on the swingVisible method
	    swingVisible ();

	    //Repaints the screen
	    repaint ();
	}

	//Checks if the "instructions" button is pressed
	else if (command == instructions)
	{
	    //Sets appropriate checkers.
	    drawCheck = 2;
	    visibleCheck = 0;

	    //Calls on the swingVisible method
	    swingVisible ();

	    //Sets the piece instruction button visibility to true
	    pieceinstr.setVisible (true);

	    //Repaints the screen
	    repaint ();
	}

	//Checks if the "credits" button is pressed
	else if (command == credits)
	{
	    //Sets appropriate checkers.
	    drawCheck = 3;
	    visibleCheck = 0;

	    //Calls on the swingVisible method
	    swingVisible ();

	    //Repaints the screen
	    repaint ();
	}

	//Checks if the "back" button is pressed
	else if (command == back)
	{
	    //Sets appropriate checkers.
	    drawCheck = 0;
	    visibleCheck = 1;

	    //Calls on the swingVisible method
	    swingVisible ();

	    //Repaints the screen
	    repaint ();
	}

	//Checks if the "piece instructions" button is pressed
	else if (command == pieceinstr)
	{
	    //Sets appropriate checkers.
	    drawCheck = 5;
	    visibleCheck = 3;

	    if (instrcheck == 1)
	    {
		//Sets appropriate checkers.
		instrcheck = 2;
	    }

	    else
	    {
		//Sets appropriate checkers.
		instrcheck = 1;
	    }
	    pieceinstr.setVisible (true);

	    //Calls on the swingVisible method
	    swingVisible ();

	    //Repaints the screen
	    repaint ();
	}

	//Checks if the "piece back" button is pressed
	else if (command == pieceback)
	{
	    //Sets appropriate checkers.
	    drawCheck = 0;
	    visibleCheck = 1;

	    //Calls on the swingVisible method
	    swingVisible ();

	    //Repaints the screen
	    repaint ();
	}

	//Checks if the "exit" button is pressed
	else if (command == exit)
	{
	    //Closes the program
	    System.exit (0);
	}

	//Checks if the "board 1" checkbox is pressed
	else if (command == board1)
	{
	    //Assigns board as 1
	    board = 1;
	}

	//Checks if the "board 2" checkbox is pressed
	else if (command == board2)
	{
	    //Assigns board as 2
	    board = 2;
	}

	//Checks if the "board 3" checkbox is pressed
	else if (command == board3)
	{
	    //Assigns board as 3
	    board = 3;
	}

	//Checks if the "board 4" checkbox is pressed
	else if (command == board4)
	{
	    //Assigns board as 4
	    board = 4;
	}

	//Checks if the "board 5" checkbox is pressed
	else if (command == board5)
	{
	    //Assigns board as 5
	    board = 5;
	}

	//Checks if the "piece 1" checkbox is pressed
	else if (command == piece1)
	{
	    //Assigns piece set as 1
	    piece = 1;
	}

	//Checks if the "piece 2" checkbox is pressed
	else if (command == piece2)
	{
	    //Assigns board set as 2
	    piece = 2;
	}
    }


    // Method required to be implemented due to MouseListener interface
    public void mousePressed (MouseEvent e)
    {
    }


    //Checks for clicking back button within a certain area of the screen and returns to the menu
    //Checks if the user clicks in the area of the "GO" text to start the game, and assign player names
    public void mouseReleased (MouseEvent e)
    {
	//Checks upper and lower x and y coordinates of rectangle to see if click has occurred inside "Back" text
	if (e.getButton () == 1 && e.getX () > 15 && e.getX () < 100 && e.getY () > 755 && this.drawCheck == 1)
	{
	    //Sets appropriate checkers.
	    drawCheck = 0;
	    visibleCheck = 1;

	    //Calls on swingVisible method
	    swingVisible ();
	}

	//Checks upper and lower x and y coordinates of rectangle to see if click has occurred inside "GO" text
	else if (e.getButton () == 1 && e.getX () > 175 && e.getX () < 500 && e.getY () > 470 & e.getY () < 600 && this.drawCheck == 1)
	{
	    //Gets user input from textfield and textfield2 and assigns it to player1 and player2 respectfully.
	    player1 = textField.getText ();
	    player2 = textField2.getText ();

	    //If player1 enters no text, give them a default name.
	    if (player1.length () == 0)
	    {
		player1 = "Anon1";
	    }

	    //If player1's name is greater than 10 spots, cut off the name to 10 places.
	    else if (player1.length () > 10)
	    {
		player1 = player1.substring (0, 10);
	    }

	    //If player2's name is blank and player1's name is blank give player2 default name.
	    if (player2.length () == 0 && player1.equals ("Anon1"))
	    {
		player2 = "Anon2";
	    }

	    //if player2 enters no text, give them a default name.
	    else if (player2.length () == 0)
	    {
		player2 = "Anon1";
	    }

	    //If player2's name is greater than 10 spots, cut off the name to 10 places.
	    else if (player2.length () > 10)
	    {
		player2 = player2.substring (0, 10);
	    }

	    //Checks if the two user names are equal.
	    if (player1.equals (player2))
	    {
		player2 += "2";
	    }

	    //Adds name of player1 and player2 to their respective Vectors
	    playerName1.addElement (player1);
	    playerName2.addElement (player2);

	    //writes the board & piece set to file and check for possible errors.
	    try
	    {
		fw = new FileWriter ("settings.txt");
		fw.write (board + "\r\n");
		fw.write (piece + "\r\n");
		fw.close ();
	    }

	    //If an exception occurs, displays a message then exits from the program
	    catch (IOException ioe)
	    {
		System.out.println ("Error reading from file. Program is exiting");
		System.exit (0);
	    }

	    //Sets stopThread to true so that the thread stops running on next iteration
	    stopThread = true;

	    //Disposes of the JPanel
	    this.dispose ();

	    //Calls on the ChessTesting constructor
	    JFrame frame = new Chess (playerName1, playerName2);
	}
    }


    // Method required to be implemented due to MouseListener interface
    public void mouseClicked (MouseEvent e)
    {
    }


    // Method required to be implemented due to MouseListener interface
    public void mouseEntered (MouseEvent e)
    {
    }


    // Method required to be implemented due to MouseListener interface
    public void mouseExited (MouseEvent e)
    {
    }
}
