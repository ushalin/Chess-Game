// ICS4U1
// Chess - ISU Project
// Written by : Shalin Upadhyay, Cameron Frizado, Kalan Dowrich
// Written for : Ms.Ganesan
// Due : June 13, 2014
// Submitted : June 13, 2014
//
// Class Purpose: Creates the chess engine that allows the game to accurately follow the rules of Chess
//                Also handles the GUI of the chess game
//
// Variables & Objects Used :
//
// frame - Creates the JFrame object for the class
// buttonCheck - Remembers last button selected to allow deselecting of pieces
// buttonText - Retrieves the text of the button that was clicked on
// WinCheck - Checks if/who the winner of the game is
// BlackCheck - Checks if the black king is in check
// WhiteCheck - Checks if the white king is in check
// CheckPiece - Records which piece put the enemy in check to be displayed to the players
// ColourCheck - Records the color of the piece's tile, so it can be repainted when selected
// tiles - Creates the 8 x 8 grid of JButtons that is able to represent each chess piece and the chess board
// x - x coordinate of the chess piece on the board
// y - y coordinate of the chess piece on the board
// turn - Sets the correct turn of the user depending on its int value
// piece - Reads in the piece set chose by the user from "settings.txt"
// board - Reads in the board chose by the user from "settings.txt"
// count - Counter used to colour the chess board
// BlackWin - If the winner of the game is black, an appropriate message will be outputted
// WhiteWin - If the winner of the game is black, an appropriate message will be outputted
// brook - Reads in chess piece image for black rook
// bknight - Reads in chess piece image for black knight
// bbishop - Reads in chess piece image for black bishop
// bqueen - Reads in chess piece image for black queen
// bking - Reads in chess piece image for black king
// bpawn - Reads in chess piece image for black pawn
// wrook - Reads in chess piece image for white rook
// wknight - Reads in chess piece image for white knight
// wbishop - Reads in chess piece image for white bishop
// wqueen - Reads in chess piece image for white queen
// wking - Reads in chess piece image for white king
// wpawn - Reads in chess piece image for white pawn
// vp1 - Vector that holds the name of player1
// vp2 - Vector that holds the name of player2
// p1Name - Enumerates vp1 to display its contents
// p2Name - Enumeratres vp2 to display its contents
// command - Object used to get the source of the action performed
// row - Represents each row of the chess board which is then coloured appropriately
//
// Methods Used:
//
// Chess - Constructor that initializes swing components, and sets up GUI for chess board and chess pieces
// ChessEngine - Creates the graphical user interface and adds all swing components to the the JFrame
// actionPerformed - Invoked to check if an action occurs, and depending on the type of action causes something to occur
// BKing - Creates the logic & chess rules required to accurately run the Black King
// BQueen - Creates the logic & chess rules required to accurately run the Black Queen
// BBishop - Creates the logic & chess rules required to accurately run the Black Bishop
// BKnight - Creates the logic & chess rules required to accurately run the Black Knight
// BRook - Creates the logic & chess rules required to accurately run the Black Rook
// BPawn - Creates the logic & chess rules required to accurately run the Black Pawn
// WKing - Creates the logic & chess rules required to accurately run the White King
// WQueen - Creates the logic & chess rules required to accurately run the White King
// WBishop - Creates the logic & chess rules required to accurately run the White King
// WKnight - Creates the logic & chess rules required to accurately run the White King
// WRook - Creates the logic & chess rules required to accurately run the White King
// WPawn - Creates the logic & chess rules required to accurately run the White King
// RCollision - Disables invalid movements for the Rook piece
// BCollision - Disables invalid movements for the Bishop piece
// QCollision - Disables invalid movements for the Queen piece
// Check - Checks if any pieces have a king in check, informs player if they are in check
// Move - Switches the image & text of the piece that is moved to a blank spot on the chess board
// boardcolour - Resets the colour of the chess board to its original colour
//

//Imports all necessary packages
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

public class Chess extends JFrame implements ActionListener
{
    //All global variables and objects used throughout the class
    JFrame frame;
    JPanel contentPane;
    String buttonCheck = "", buttonText = "", WinCheck = "", ColourCheck = "", CheckPiece = "";
    private static JButton tiles[] [] = new JButton [8] [8];
    private static int x = 0, y = 0, turn = 1, piece, board, count = 0;
    private static boolean BlackWin = true, WhiteWin = true, BlackCheck = false, WhiteCheck = false;
    private static ImageIcon brook, bknight, bbishop, bqueen, bking, bpawn, wrook, wknight, wbishop, wqueen, wking, wpawn;
    private static String player1Name, player2Name;
    private static Vector vp1, vp2;
    private static Enumeration p1name, p2name;

    //Constructor for the class, that recieves the two vectors
    //The two vectors hold the players names
    public Chess (Vector vp1, Vector vp2)
    {
	//Makes vp1 and vp2 for the class equal vp1 and vp2 coming from parameters
	this.vp1 = vp1;
	this.vp2 = vp2;

	//Has the enumerator for each respective Vector, equal the elements in the Vectors
	this.p1name = this.vp1.elements ();
	this.p2name = this.vp2.elements ();

	//Has the bufferedreader read in the values for the board and piece set chosen from "settings.txt"
	try
	{
	    BufferedReader br = new BufferedReader (new FileReader ("settings.txt"));
	    this.board = Integer.parseInt (br.readLine ());
	    this.piece = Integer.parseInt (br.readLine ());
	}

	//Catches the IOException
	catch (IOException ioe)
	{
	}

	//Reads in all the images for their respective ImageIcon objects
	this.brook = new ImageIcon ("Rook-B" + this.piece + ".png");
	this.bknight = new ImageIcon ("Knight-B" + this.piece + ".png");
	this.bbishop = new ImageIcon ("Bishop-B" + this.piece + ".png");
	this.bqueen = new ImageIcon ("Queen-B" + this.piece + ".png");
	this.bking = new ImageIcon ("King-B" + this.piece + ".png");
	this.bpawn = new ImageIcon ("Pawn-B" + this.piece + ".png");

	this.wrook = new ImageIcon ("Rook-W" + this.piece + ".png");
	this.wknight = new ImageIcon ("Knight-W" + this.piece + ".png");
	this.wbishop = new ImageIcon ("Bishop-W" + this.piece + ".png");
	this.wqueen = new ImageIcon ("Queen-W" + this.piece + ".png");
	this.wking = new ImageIcon ("King-W" + this.piece + ".png");
	this.wpawn = new ImageIcon ("Pawn-W" + this.piece + ".png");

	//Each Jbutton on the board is assigned the appropriate Image, and properties for text on the Jbutton
	for (int i = 0 ; i < 2 ; i++)
	{
	    this.tiles [i] [0] = new JButton ("BRook"); //Sets button text
	    this.tiles [i] [0].setIcon (this.brook); //Sets icon
	    this.tiles [i] [0].setHorizontalTextPosition (AbstractButton.CENTER); //Centers the text horizontally
	    this.tiles [i] [0].setVerticalTextPosition (AbstractButton.BOTTOM); //Sets the text to the bottom vertically

	    this.tiles [i] [1] = new JButton ("BKnight"); //Sets button text
	    this.tiles [i] [1].setIcon (this.bknight); //Sets icon
	    this.tiles [i] [1].setHorizontalTextPosition (AbstractButton.CENTER); //Centers the text horizontally
	    this.tiles [i] [1].setVerticalTextPosition (AbstractButton.BOTTOM); //Sets the text to the bottom vertically

	    this.tiles [i] [2] = new JButton ("BBishop"); //Sets button text
	    this.tiles [i] [2].setIcon (this.bbishop); //Sets icon
	    this.tiles [i] [2].setHorizontalTextPosition (AbstractButton.CENTER); //Centers the text horizontally
	    this.tiles [i] [2].setVerticalTextPosition (AbstractButton.BOTTOM); //Sets the text to the bottom vertically

	    this.tiles [i] [3] = new JButton ("BQueen"); //Sets button text
	    this.tiles [i] [3].setIcon (this.bqueen); //Sets icon
	    this.tiles [i] [3].setHorizontalTextPosition (AbstractButton.CENTER); //Centers the text horizontally
	    this.tiles [i] [3].setVerticalTextPosition (AbstractButton.BOTTOM); //Sets the text to the bottom vertically

	    this.tiles [i] [4] = new JButton ("BKing");  //Sets button text
	    this.tiles [i] [4].setIcon (this.bking);  //Sets icon
	    this.tiles [i] [4].setHorizontalTextPosition (AbstractButton.CENTER); //Centers the text horizontally
	    this.tiles [i] [4].setVerticalTextPosition (AbstractButton.BOTTOM);  //Sets the text to the bottom vertically

	    this.tiles [i] [5] = new JButton ("BBishop");   //Sets button text
	    this.tiles [i] [5].setIcon (this.bbishop);   //Sets icon
	    this.tiles [i] [5].setHorizontalTextPosition (AbstractButton.CENTER);  //Centers the text horizontally
	    this.tiles [i] [5].setVerticalTextPosition (AbstractButton.BOTTOM);  //Sets the text to the bottom vertically

	    this.tiles [i] [6] = new JButton ("BKnight");   //Sets button text
	    this.tiles [i] [6].setIcon (this.bknight);   //Sets icon
	    this.tiles [i] [6].setHorizontalTextPosition (AbstractButton.CENTER);   //Centers the text horizontally
	    this.tiles [i] [6].setVerticalTextPosition (AbstractButton.BOTTOM);   //Sets the text to the bottom vertically

	    this.tiles [i] [7] = new JButton ("BRook");    //Sets button text
	    this.tiles [i] [7].setIcon (this.brook);    //Sets icon
	    this.tiles [i] [7].setHorizontalTextPosition (AbstractButton.CENTER);  //Centers the text horizontally
	    this.tiles [i] [7].setVerticalTextPosition (AbstractButton.BOTTOM);  //Sets the text to the bottom vertically

	    //if statement prevents out of bounds exceptions from tripping
	    if (i > 0)
	    {
		for (int j = 0 ; j < 8 ; j++)
		{
		    this.tiles [i] [j] = new JButton ("BPawn"); //Sets button text
		    this.tiles [i] [j].setIcon (this.bpawn);  //Sets icon
		    this.tiles [i] [j].setHorizontalTextPosition (AbstractButton.CENTER); //Centers the text horizontally
		    this.tiles [i] [j].setVerticalTextPosition (AbstractButton.BOTTOM); //Sets the text to the bottom vertically
		}
	    }
	}

	//Sets all buttons between the two teams as blanks
	for (int i = 2 ; i < 6 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		this.tiles [i] [j] = new JButton (" ");
	    }
	}

	//Sets the text for the entire white team
	for (int i = 6 ; i < 8 ; i++)
	{
	    //Sets the text for the pawns in the white team
	    if (i == 6)
	    {
		for (int j = 0 ; j < 8 ; j++)
		{
		    this.tiles [6] [j] = new JButton ("WPawn");  //Sets button text
		    this.tiles [i] [j].setIcon (this.wpawn);  //Sets icon
		    this.tiles [i] [j].setHorizontalTextPosition (AbstractButton.CENTER);  //Centers the text horizontally
		    this.tiles [i] [j].setVerticalTextPosition (AbstractButton.BOTTOM);   //Sets the text to the bottom vertically
		}
	    }

	    //Sets the text for the all the pieces in the white teams except the pawns
	    else
	    {
		this.tiles [i] [0] = new JButton ("WRook");  //Sets button text
		this.tiles [i] [0].setIcon (this.wrook); //Sets icon
		this.tiles [i] [0].setHorizontalTextPosition (AbstractButton.CENTER);  //Centers the text horizontally
		this.tiles [i] [0].setVerticalTextPosition (AbstractButton.BOTTOM); //Sets the text to the bottom vertically

		this.tiles [i] [1] = new JButton ("WKnight");  //Sets button text
		this.tiles [i] [1].setIcon (this.wknight);  //Sets icon
		this.tiles [i] [1].setHorizontalTextPosition (AbstractButton.CENTER);  //Centers the text horizontally
		this.tiles [i] [1].setVerticalTextPosition (AbstractButton.BOTTOM);  //Sets the text to the bottom vertically

		this.tiles [i] [2] = new JButton ("WBishop");  //Sets button text
		this.tiles [i] [2].setIcon (this.wbishop);  //Sets icon
		this.tiles [i] [2].setHorizontalTextPosition (AbstractButton.CENTER);  //Centers the text horizontally
		this.tiles [i] [2].setVerticalTextPosition (AbstractButton.BOTTOM);  //Sets the text to the bottom vertically

		this.tiles [i] [3] = new JButton ("WKing");  //Sets button text
		this.tiles [i] [3].setIcon (this.wking);  //Sets icon
		this.tiles [i] [3].setHorizontalTextPosition (AbstractButton.CENTER);  //Centers the text horizontally
		this.tiles [i] [3].setVerticalTextPosition (AbstractButton.BOTTOM);  //Sets the text to the bottom vertically

		this.tiles [i] [4] = new JButton ("WQueen");  //Sets button text
		this.tiles [i] [4].setIcon (this.wqueen);  //Sets icon
		this.tiles [i] [4].setHorizontalTextPosition (AbstractButton.CENTER);  //Centers the text horizontally
		this.tiles [i] [4].setVerticalTextPosition (AbstractButton.BOTTOM);  //Sets the text to the bottom vertically

		this.tiles [i] [5] = new JButton ("WBishop");    //Sets button text
		this.tiles [i] [5].setIcon (this.wbishop);   //Sets icon
		this.tiles [i] [5].setHorizontalTextPosition (AbstractButton.CENTER);   //Centers the text horizontally
		this.tiles [i] [5].setVerticalTextPosition (AbstractButton.BOTTOM);  //Sets the text to the bottom vertically

		this.tiles [i] [6] = new JButton ("WKnight");   //Sets button text
		this.tiles [i] [6].setIcon (this.wknight);  //Sets icon
		this.tiles [i] [6].setHorizontalTextPosition (AbstractButton.CENTER);  //Centers the text horizontally
		this.tiles [i] [6].setVerticalTextPosition (AbstractButton.BOTTOM);  //Sets the text to the bottom vertically

		this.tiles [i] [7] = new JButton ("WRook");  //Sets button text
		this.tiles [i] [7].setIcon (this.wrook); //Sets icon
		this.tiles [i] [7].setHorizontalTextPosition (AbstractButton.CENTER); //Centers the text horizontally
		this.tiles [i] [7].setVerticalTextPosition (AbstractButton.BOTTOM);  //Sets the text to the bottom vertically
	    }
	}

	//Calls on boardcolour method to colour the board according
	boardcolour ();

	//If it is white players turn, all of their pieces are enabled
	if (this.turn == 1)
	{
	    for (int ii = 0 ; ii < 6 ; ii++)
	    {
		for (int jj = 0 ; jj < 8 ; jj++)
		{
		    this.tiles [ii] [jj].setEnabled (false);
		}
	    }
	}
	//Calls on the ChessEngine method
	ChessEngine ();
    }


    public void ChessEngine ()
    {
	frame = new JFrame ();  //declare the JFrame
	frame.setSize (800, 800);  //set the size of the JFrame
	frame.setLocation (150, 50);    //set location of the JFrame when it appears
	frame.setResizable (false);     //disables the ability for the user to resize the JFrame
	frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE); //sets program to exit on closing
	contentPane = (JPanel) frame.getContentPane ();  //Declares content pane, assigns it to the JFrame
	contentPane.setLayout (new GridLayout (8, 8));  //sets the layout of the content pane to an 8 by 8 grid

	//nested for loops add the JButton tiles to the grid layout
	for (int x = 0 ; x < 8 ; x++)
	{
	    for (int y = 0 ; y < 8 ; y++)
	    {
		contentPane.add (this.tiles [x] [y]);  //adds tiles into grid
	    }
	}

	//nested for loops add action listeners to the buttons
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		this.tiles [i] [j].addActionListener (this); //creates action listeners for the buttons
	    }
	}
	frame.show ();  //create the frame
    }


    //
    //Method used when a button is pressed
    //
    public void actionPerformed (ActionEvent e)
    {
	Object command = e.getSource ();    //object records which button was pressed
	this.BlackWin = true;    //reset the boolean to true
	this.WhiteWin = true;    //reset the boolean to true

	//nested for loops scan the board for the button that was selected
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		//if checks if the button is the one that was selected
		if (command == this.tiles [i] [j])
		{
		    //assigns the text of the selected button to a variable
		    buttonText = this.tiles [i] [j].getText ();

		    //Checks if it is the black team's turn and they clicked a white piece
		    if (this.turn % 2 == 0 && this.buttonText.charAt (0) == 'W')
		    {
			Move (i, j, this.x, this.y);  //calls move method to move the piece
			Check ();   //calls check method to see if either player is in check
			this.turn++;     //increases turn variable to an odd number to indicate white team's turn

			//nested for loops disable all tiles
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				this.tiles [ii] [jj].setEnabled (false); //disables tiles
			    }
			}

			//nested for loops enable the other team's pieces, because it is now their turn
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				//checks if the tile belongs to the white team
				if (this.tiles [ii] [jj].getText ().charAt (0) == 'W')
				{
				    this.tiles [ii] [jj].setEnabled (true); //enables the tiles
				}
			    }
			}
			this.buttonCheck = " "; //clears buttoncheck variable
			boardcolour (); //recolors the board
		    }

		    //Checks if it is the white team's turn and they clicked a black piece
		    else if (this.turn % 2 != 0 && this.buttonText.charAt (0) == 'B')
		    {
			Move (i, j, this.x, this.y); //calls move method to move the piece
			Check ();       //calls check method to see if either player is in check
			this.turn++;         //increases turn variable to an even number to indicate black team's turn

			//nested for loops disable all tiles
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				this.tiles [ii] [jj].setEnabled (false); //disables tiles
			    }
			}

			//nested for loops enable the other team's pieces, because it is now their turn
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				//checks if the tile belongs to the black team
				if (this.tiles [ii] [jj].getText ().charAt (0) == 'B')
				{
				    this.tiles [ii] [jj].setEnabled (true); //enables the tiles
				}
			    }
			}
			this.buttonCheck = " "; //clears the buttonCheck variable
			boardcolour (); //recolors the board
		    }

		    //checks if the button selected is tha same button as the last button selected, this indicates deselecting a piece
		    else if (this.buttonText == this.buttonCheck)
		    {
			//nested for loops disable all tiles
			for (int iii = 0 ; iii < 8 ; iii++)
			{
			    for (int jjj = 0 ; jjj < 8 ; jjj++)
			    {
				this.tiles [iii] [jjj].setEnabled (false); //disables the tiles
			    }
			}

			//checks if it the black team's turn
			if (this.turn % 2 == 0)
			{
			    //nested for loops scan the board for black pieces
			    for (int iii = 0 ; iii < 8 ; iii++)
			    {
				for (int jjj = 0 ; jjj < 8 ; jjj++)
				{
				    //checks if the tile is a black piece
				    if (this.tiles [iii] [jjj].getText ().charAt (0) == 'B')
				    {
					this.tiles [iii] [jjj].setEnabled (true); //Re-enables the black tiles
				    }
				}
			    }
			}

			//if it's not the black team's turn, it is the white team's turn
			else
			{
			    //nested for loops scan the board for white pieces
			    for (int iii = 0 ; iii < 8 ; iii++)
			    {
				for (int jjj = 0 ; jjj < 8 ; jjj++)
				{
				    //checks if the tile is a black piece
				    if (this.tiles [iii] [jjj].getText ().charAt (0) == 'W')
				    {
					this.tiles [iii] [jjj].setEnabled (true); //Re-enables the white tiles
				    }
				}
			    }
			}
			this.buttonCheck = " "; //clears the buttoncheck variable
			boardcolour (); //recolors the board
		    }

		    //checks if the black king was selected
		    else if (this.buttonText == "BKing")
		    {
			this.buttonCheck = this.buttonText; //sets buttoncheck variable to allow deselection
			BKing (i, j); //calls BKing method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the black queen was selected
		    else if (buttonText == "BQueen")
		    {
			this.buttonCheck = this.buttonText; //sets buttoncheck variable to allow deselection
			BQueen (i, j); //calls BQueen method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the black bishop was selected
		    else if (this.buttonText == "BBishop")
		    {
			this.buttonCheck = this.buttonText; //sets buttoncheck variable to allow deselection
			BBishop (i, j); //calls BBishop method, sends i and j as the position of the current piece
			BCollision (i, j); //calls BCollision method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the black knight was selected
		    else if (this.buttonText == "BKnight")
		    {
			this.buttonCheck = this.buttonText; //sets buttoncheck variable to allow deselection
			//nested for loops disable all tiles on the board
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				this.tiles [ii] [jj].setEnabled (false); //disables the tiles
			    }
			}
			BKnight (i, j); //calls BKnight method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the black rook was selected
		    else if (this.buttonText == "BRook")
		    {
			this.buttonCheck = this.buttonText; //sets buttoncheck variable to allow deselection
			//nested for loops disable all tiles on the board
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				this.tiles [ii] [jj].setEnabled (false); //disables the tiles
			    }
			}
			BRook (i, j); //calls BRook method, sends i and j as the position of the current piece
			RCollision (i, j); //calls RCollision method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the black pawn was selected
		    else if (this.buttonText == "BPawn")
		    {
			this.buttonCheck = this.buttonText; //sets buttoncheck variable to allow deselection
			BPawn (i, j); //calls BPawn method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the white king was selected
		    else if (this.buttonText == "WKing")
		    {
			this.buttonCheck = this.buttonText; //sets buttoncheck variable to allow deselection
			WKing (i, j); //calls WKing method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the white queen was selected
		    else if (this.buttonText == "WQueen")
		    {
			this.buttonCheck = this.buttonText; //sets buttoncheck variable to allow deselection
			WQueen (i, j); //calls WQueen method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the white bishop was selected
		    else if (this.buttonText == "WBishop")
		    {
			this.buttonCheck = this.buttonText; //sets buttoncheck variable to allow deselection
			WBishop (i, j); //calls WBishop method, sends i and j as the position of the current piece
			BCollision (i, j); //calls BCollision method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the white knight was selected
		    else if (this.buttonText == "WKnight")
		    {
			this.buttonCheck = this.buttonText;  //sets buttoncheck variable to allow deselection
			//nested for loops disable all tiles on the board
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				this.tiles [ii] [jj].setEnabled (false); //disables the tiles
			    }
			}

			WKnight (i, j); //calls WKnight method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the white rook was selected
		    else if (this.buttonText == "WRook")
		    {
			this.buttonCheck = this.buttonText; //sets buttoncheck variable to allow deselection
			//nested for loops disable all tiles on the board
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				this.tiles [ii] [jj].setEnabled (false); //disables the tiles
			    }
			}
			WRook (i, j); //calls WRook method, sends i and j as the position of the current piece
			RCollision (i, j); //calls RCollision method, sends i and j as the position of the current piece
			this.x = i; //records vertical grid position for use in movement
			this.y = j; //records horizontal grid position for use in movement
		    }

		    //checks if the white pawn was selected
		    else if (this.buttonText == "WPawn")
		    {
			buttonCheck = buttonText; //sets buttoncheck variable to allow deselection
			this.WPawn (i, j); //calls WPawn method, sends i and j as the position of the current piece
			this.x = i;  //records vertical grid position for use in movement
			this.y = j;  //records horizontal grid position for use in movement
		    }

		    //checks if a blank tile was selected, indicating moving a piece
		    else if (this.buttonText == " ")
		    {
			Move (i, j, this.x, this.y); //calls move class, sends i and j as the tile to be moved to, x and y as the position to be moved from
			Check (); //calls check method to see if either player is in check
			this.turn++; //increases turn variable to an even number to indicate black team's turn

			//nested for loops disable all tiles on the board
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				this.tiles [ii] [jj].setEnabled (false); //disables the tiles
			    }
			}

			//checks if it is the black team's turn
			if (this.turn % 2 == 0)
			{
			    //nested for loops scan board for black pieces
			    for (int ii = 0 ; ii < 8 ; ii++)
			    {
				for (int jj = 0 ; jj < 8 ; jj++)
				{
				    //checks if tile is a black piece
				    if (this.tiles [ii] [jj].getText ().charAt (0) == 'B')
				    {
					this.tiles [ii] [jj].setEnabled (true); //enables black pieces
				    }
				}
			    }
			}

			//if it isn't black team's turn, it's white team's turn
			else
			{
			    //nested for loops scan board for white tiles
			    for (int ii = 0 ; ii < 8 ; ii++)
			    {
				for (int jj = 0 ; jj < 8 ; jj++)
				{
				    //checks if tile is a white piece
				    if (this.tiles [ii] [jj].getText ().charAt (0) == 'W')
				    {
					this.tiles [ii] [jj].setEnabled (true); //enables white pieces
				    }
				}
			    }
			}
			boardcolour (); //recolors the board
		    }
		}
	    }
	}

	//nested for loops scan board for the king pieces
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		//checks if tile is the black king
		if (this.tiles [i] [j].getText () == "BKing")
		{
		    this.WhiteWin = false; //if black king is found, white team has not won yet
		}

		//checks if tile is the white king
		if (this.tiles [i] [j].getText () == "WKing")
		{
		    this.BlackWin = false; //if white king is found, black team has not won yet
		}
	    }
	}

	//checks if the white king was not found on the board, indicating it has been captured
	if (this.BlackWin == true)
	{
	    //Checks vector vp2 for more elements
	    while (this.p2name.hasMoreElements ())
	    {
		System.out.println (this.p2name.nextElement () + " Wins!"); //tells players who won
	    }

	    try //pauses program for 2 second to allow players to read victory message
	    {
		Thread.sleep (2000); //pause for 2000 milliseconds (2 seconds)
	    }

	    catch (InterruptedException s)
	    {
	    }
	    System.exit (0); //shuts down program
	}

	//checks if the black king was not found on the board, indicating it has been captured
	if (this.WhiteWin == true)
	{
	    //check vector vp1 for more elements
	    while (this.p1name.hasMoreElements ())
	    {
		System.out.println (this.p1name.nextElement () + " Wins!"); //tells players who won
	    }

	    try //pauses program for 2 second to allow players to read victory message
	    {
		Thread.sleep (2000); //pause for 2000 milliseconds (2 seconds)
	    }

	    catch (InterruptedException s)
	    {
	    }
	    System.exit (0); //shuts down program
	}
    }


    //
    //Method used to move the black king piece
    //
    public void BKing (int x, int y)  //imports x and y as the position of the curent piece
    {
	//nested for loops disable all tiles on the board
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		this.tiles [i] [j].setEnabled (false); //disables the tiles
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x < 7 && y < 7)
	{
	    this.tiles [x + 1] [y + 1].setEnabled (true);   //enables the tile down and to the right

	    //checks if the tile down and to the right is a teammate
	    if (this.tiles [x + 1] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 1] [y + 1].setEnabled (false); //disables the tile
	    }

	    //checks if the tile down and to the right is blank
	    if (this.tiles [x + 1] [y + 1].getText () == " ")
	    {
		this.tiles [x + 1] [y + 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if the tile down and to the right is an enemy
	    if (this.tiles [x + 1] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x < 7 && y > 0)
	{
	    this.tiles [x + 1] [y - 1].setEnabled (true); //enables the tile down and to the left

	    //checks if the tile down and to the left is a teammate
	    if (this.tiles [x + 1] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 1] [y - 1].setEnabled (false); //disables the tile
	    }

	    //checks if the tile down and to the left is blank
	    if (this.tiles [x + 1] [y - 1].getText () == " ")
	    {
		this.tiles [x + 1] [y - 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if the tile down and to the left is an enemy
	    if (this.tiles [x + 1] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x > 0 && y < 7)
	{
	    this.tiles [x - 1] [y + 1].setEnabled (true); //enables the tile up and to the right

	    //checks if the tile up and to the right is a teammate
	    if (this.tiles [x - 1] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y + 1].setEnabled (false); //disables the tile
	    }

	    //checks if the tile up and to the right is blank
	    if (this.tiles [x - 1] [y + 1].getText () == " ")
	    {
		this.tiles [x - 1] [y + 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if the tile up and to the right is an enemy
	    if (this.tiles [x - 1] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 1] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x > 0 && y > 0)
	{
	    this.tiles [x - 1] [y - 1].setEnabled (true); //enables the tile up and to the left

	    //checks if the tile down and to the left is a teammate
	    if (this.tiles [x - 1] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y - 1].setEnabled (false); //disables the tile
	    }

	    //checks if the tile down and to the left is blank
	    if (this.tiles [x - 1] [y - 1].getText () == " ")
	    {
		this.tiles [x - 1] [y - 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if the tile down and to the left is an enemy
	    if (this.tiles [x - 1] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 1] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x < 7)
	{
	    this.tiles [x + 1] [y].setEnabled (true); //enables the tile down from current position

	    //checks if the tile down from current position is a teammate
	    if (this.tiles [x + 1] [y].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 1] [y].setEnabled (false); //disables the tile
	    }

	    //checks if the tile down from current position is blank
	    if (this.tiles [x + 1] [y].getText () == " ")
	    {
		this.tiles [x + 1] [y].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if the tile down from current position is an enemy
	    if (this.tiles [x + 1] [y].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x > 0)
	{
	    this.tiles [x - 1] [y].setEnabled (true); //enables the tile up from current position

	    //checks if the tile up from current position is a teammate
	    if (this.tiles [x - 1] [y].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y].setEnabled (false); //disables the tile
	    }

	    //checks if the tile up from current position is blank
	    if (this.tiles [x - 1] [y].getText () == " ")
	    {
		this.tiles [x - 1] [y].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if the tile up from current position is an enemy
	    if (this.tiles [x - 1] [y].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 1] [y].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (y < 7)
	{
	    this.tiles [x] [y + 1].setEnabled (true); //enables the tile to the right of current position

	    //checks if the tile to the right of current position is a teammate
	    if (this.tiles [x] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x] [y + 1].setEnabled (false); //disables the tile
	    }

	    //checks if the tile to the right of current position is blank
	    if (this.tiles [x] [y + 1].getText () == " ")
	    {
		this.tiles [x] [y + 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if the tile to the right of current position is an enemy
	    if (this.tiles [x] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (y > 0)
	{
	    this.tiles [x] [y - 1].setEnabled (true); //enables the tile to the left of current position

	    //checks if the tile to the right of current position is a teammate
	    if (this.tiles [x] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x] [y - 1].setEnabled (false); //disables the tile
	    }

	    //checks if the tile to the right of current position is blank
	    if (this.tiles [x] [y - 1].getText () == " ")
	    {
		this.tiles [x] [y - 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if the tile to the right of current position is an enemy
	    if (this.tiles [x] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}
	this.tiles [x] [y].setEnabled (true); //enables tile at current position
    }


    //
    //Method used to move the black queen piece
    //
    public void BQueen (int x, int y)  //imports x and y as position of current piece
    {
	BBishop (x, y); //calls BBishop method, sending x and y as position of current piece
	BRook (x, y); //calls BRook method, sending x and y as position of current piece
	QCollision (x, y); //calls QCollision method, sending x and y as position of current piece
    }


    //
    //Method used to move the black bishop piece
    //
    public void BBishop (int x, int y)  //imports x and y as position of current piece
    {
	//nested for loops disable all tiles on the board
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		this.tiles [i] [j].setEnabled (false); //disables the tiles
	    }
	}

	//for loop enables all tiles down and to the right
	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x + i] [y + i].setEnabled (true); //enables the tiles

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x + i) == 7 || (y + i) == 7)
	    {
		break; //breeaks loop before it goes out of bounds
	    }
	}

	//for loop enables all tiles down and to the left
	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x + i] [y - i].setEnabled (true); //enables the tiles

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x + i) == 7 || (y - i) == 0)
	    {
		break; //breeaks loop before it goes out of bounds
	    }
	}

	//for loop enables all tiles up and to the right
	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x - i] [y + i].setEnabled (true); //enables the tiles

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x - i) == 0 || (y + i) == 7)
	    {
		break; //breeaks loop before it goes out of bounds
	    }
	}

	//for loop enables all tiles up and to the right
	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x - i] [y - i].setEnabled (true); //enables the tiles

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x - i) == 0 || (y - i) == 0)
	    {
		break; //breeaks loop before it goes out of bounds
	    }
	}
	this.tiles [x] [y].setEnabled (true); //enables tile at current position
    }


    //
    //Method used to move the black knight piece
    //
    public void BKnight (int x, int y)  //imports x and y as position of current piece
    {

	//if statement prevents out of bounds exceptions from tripping
	if ((x + 2) < 8 && (y + 1) < 8)
	{
	    this.tiles [x + 2] [y + 1].setEnabled (true); //enables tile at move point

	    //checks if tile at move point is a teammate
	    if (this.tiles [x + 2] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 2] [y + 1].setEnabled (false); //disables the tile
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x + 2] [y + 1].getText () == " ")
	    {
		this.tiles [x + 2] [y + 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is an enemy
	    if (this.tiles [x + 2] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 2] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x + 2) < 8 && (y - 1) >= 0)
	{
	    this.tiles [x + 2] [y - 1].setEnabled (true); //enables tile at move point

	    //checks if tile at move point is a teammate
	    if (this.tiles [x + 2] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 2] [y - 1].setEnabled (false); //disables the tile
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x + 2] [y - 1].getText () == " ")
	    {
		this.tiles [x + 2] [y - 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is an enemy
	    if (this.tiles [x + 2] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 2] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x - 2) >= 0 && (y + 1) < 8)
	{
	    this.tiles [x - 2] [y + 1].setEnabled (true); //enables tile at move point

	    //checks if tile at move point is a teammate
	    if (this.tiles [x - 2] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 2] [y + 1].setEnabled (false); //disables the tile
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 2] [y + 1].getText () == " ")
	    {
		this.tiles [x - 2] [y + 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is an enemy
	    if (this.tiles [x - 2] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 2] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x - 2) >= 0 && (y - 1) >= 0)
	{
	    this.tiles [x - 2] [y - 1].setEnabled (true); //enables tile at move point

	    //checks if tile at move point is a teammate
	    if (this.tiles [x - 2] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 2] [y - 1].setEnabled (false); //disables the tile
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 2] [y - 1].getText () == " ")
	    {
		this.tiles [x - 2] [y - 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is an enemy
	    if (this.tiles [x - 2] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 2] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x + 1) < 8 && (y + 2) < 8)
	{
	    this.tiles [x + 1] [y + 2].setEnabled (true); //enables tile at move point

	    //checks if tile at move point is a teammate
	    if (this.tiles [x + 1] [y + 2].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 1] [y + 2].setEnabled (false); //disables the tile
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x + 1] [y + 2].getText () == " ")
	    {
		this.tiles [x + 1] [y + 2].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is an enemy
	    if (this.tiles [x + 1] [y + 2].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y + 2].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x + 1) < 8 && (y - 2) >= 0)
	{
	    this.tiles [x + 1] [y - 2].setEnabled (true); //enables tile at move point

	    //checks if tile at move point is a teammate
	    if (this.tiles [x + 1] [y - 2].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 1] [y - 2].setEnabled (false); //disables the tile
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x + 1] [y - 2].getText () == " ")
	    {
		this.tiles [x + 1] [y - 2].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is an enemy
	    if (this.tiles [x + 1] [y - 2].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y - 2].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x - 1) >= 0 && (y + 2) < 8)
	{
	    this.tiles [x - 1] [y + 2].setEnabled (true); //enables tile at move point

	    //checks if tile at move point is a teammate
	    if (this.tiles [x - 1] [y + 2].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y + 2].setEnabled (false); //disables the tile
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 1] [y + 2].getText () == " ")
	    {
		this.tiles [x - 1] [y + 2].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is an enemy
	    if (this.tiles [x - 1] [y + 2].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 1] [y + 2].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x - 1) >= 0 && (y - 2) >= 0)
	{
	    this.tiles [x - 1] [y - 2].setEnabled (true); //enables tile at move point

	    //checks if tile at move point is a teammate
	    if (this.tiles [x - 1] [y - 2].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y - 2].setEnabled (false); //disables the tile
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 1] [y - 2].getText () == " ")
	    {
		this.tiles [x - 1] [y - 2].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is an enemy
	    if (this.tiles [x + 1] [y - 2].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y - 2].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}
	this.tiles [x] [y].setEnabled (true); //enables tile at current position
    }


    //
    //Method used to move the black rook piece
    //
    public void BRook (int x, int y)  //imports x and y as position of current piece
    {
	//for loop enables all tiles down from current position
	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x + i] [y].setEnabled (true); //enables tiles

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x + i) == 7)
	    {
		break; //breeaks loop before it goes out of bounds
	    }
	}

	//for loop enables all tiles up from current position
	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x - i] [y].setEnabled (true); //enables tiles

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x - i) == 0)
	    {
		break; //breeaks loop before it goes out of bounds
	    }
	}

	//for loop enables all tiles to the right of current position
	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x] [y + i].setEnabled (true); //enables tiles

	    //if statement prevents out of bounds exceptions from tripping
	    if ((y + i) == 7)
	    {
		break; //breeaks loop before it goes out of bounds
	    }
	}

	//for loop enables all tiles to the left of current position
	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x] [y - i].setEnabled (true); //enables tiles

	    //if statement prevents out of bounds exceptions from tripping
	    if ((y - i) == 0)
	    {
		break; //breeaks loop before it goes out of bounds
	    }
	}
	this.tiles [x] [y].setEnabled (true); //enables tile at current position
    }


    //
    //Method used to move the black pawn piece
    //
    public void BPawn (int x, int y)  //imports x and y as position of current piece
    {
	//nested for loops disable all tiles on the board
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		this.tiles [i] [j].setEnabled (false); //disables the tiles
	    }
	}

	//checks if pawn is in its original position to allow it to jump two squares
	if (x == 1)
	{
	    this.tiles [x + 2] [y].setEnabled (true); //enables tile two squares down from current position

	    //checks if tile two squares down from current position is blank
	    if (this.tiles [x + 2] [y].getText () == " ")
	    {
		this.tiles [x + 2] [y].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile two squares down from current position is not blank
	    if (this.tiles [x + 2] [y].getText ().charAt (0) != ' ')
	    {
		this.tiles [x + 2] [y].setEnabled (false); //disables tile two squares down from current position
	    }

	    this.tiles [x + 1] [y].setEnabled (true); //enables tile one square down from current position

	    //checks if tile one square down from current position is blank
	    if (this.tiles [x + 1] [y].getText () == " ")
	    {
		this.tiles [x + 1] [y].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile one square down from current position is not blank
	    if (this.tiles [x + 1] [y].getText ().charAt (0) != ' ')
	    {
		this.tiles [x + 1] [y].setEnabled (false); //disables tile one square down from current position
		this.tiles [x + 2] [y].setEnabled (false); //disables tile two squares down from current position
	    }
	}

	//if pawn has moved already
	else
	{
	    this.tiles [x + 1] [y].setEnabled (true); //enables tile one square down from current position

	    //checks if tile one square down from current position is blank
	    if (this.tiles [x + 1] [y].getText () == " ")
	    {
		this.tiles [x + 1] [y].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile one square down from current position is not blank
	    if (this.tiles [x + 1] [y].getText ().charAt (0) != ' ')
	    {
		this.tiles [x + 1] [y].setEnabled (false); //disables tile one square down from current position
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((y - 1) > -1)
	{
	    //checks if piece diagonal to pawn is an enemy
	    if (this.tiles [x + 1] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y - 1].setEnabled (true); //enables the tile
		this.tiles [x + 1] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((y + 1) < 8)
	{
	    //checks if piece diagonal to pawn is an enemy
	    if (this.tiles [x + 1] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y + 1].setEnabled (true); //enables the tile
		this.tiles [x + 1] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//checks if tile one square down from current position is an enemy
	if (this.tiles [x + 1] [y].getText ().charAt (0) == 'W')
	{
	    this.tiles [x + 1] [y].setEnabled (false); //disables the tiles at move point
	}
	this.tiles [x] [y].setEnabled (true); //enables tile at current position
    }


    //
    //Method used to move the white king piece
    //
    public void WKing (int x, int y)  //imports x and y as position of current piece
    {
	//nested for loops disable all tiles on the board
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		this.tiles [i] [j].setEnabled (false); //disables the tiles at move point
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x < 7 && y < 7)
	{
	    this.tiles [x + 1] [y + 1].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x + 1] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y + 1].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x + 1] [y + 1].getText () == " ")
	    {
		this.tiles [x + 1] [y + 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x + 1] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 1] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x < 7 && y > 0)
	{
	    this.tiles [x + 1] [y - 1].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x + 1] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y - 1].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x + 1] [y - 1].getText () == " ")
	    {
		this.tiles [x + 1] [y - 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x + 1] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 1] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x > 0 && y < 7)
	{
	    this.tiles [x - 1] [y + 1].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x - 1] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 1] [y + 1].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 1] [y + 1].getText () == " ")
	    {
		this.tiles [x - 1] [y + 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x - 1] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x > 0 && y > 0)
	{
	    this.tiles [x - 1] [y - 1].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x - 1] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 1] [y - 1].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 1] [y - 1].getText () == " ")
	    {
		this.tiles [x - 1] [y - 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x - 1] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x < 7)
	{
	    this.tiles [x + 1] [y].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x + 1] [y].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x + 1] [y].getText () == " ")
	    {
		this.tiles [x + 1] [y].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x + 1] [y].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 1] [y].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x > 0)
	{
	    this.tiles [x - 1] [y].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x - 1] [y].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 1] [y].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 1] [y].getText () == " ")
	    {
		this.tiles [x - 1] [y].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x - 1] [y].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (y < 7)
	{
	    this.tiles [x] [y + 1].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x] [y + 1].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x] [y + 1].getText () == " ")
	    {
		this.tiles [x] [y + 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (y > 0)
	{
	    this.tiles [x] [y - 1].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x] [y - 1].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x] [y - 1].getText () == " ")
	    {
		this.tiles [x] [y - 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}
	this.tiles [x] [y].setEnabled (true);
    }


    //
    //Method used to move the white queen piece
    //
    public void WQueen (int x, int y)  //imports x and y as position of current piece
    {
	//calls WBishop method, sending x and y as position of current piece
	WBishop (x, y);

	//calls WBishop method, sending x and y as position of current piece
	WRook (x, y);

	//calls QCollision method, sending x and y as position of current piece
	QCollision (x, y);
    }


    //
    //Method used to move the white bishop piece
    //
    public void WBishop (int x, int y)  //imports x and y as position of current piece
    {
	//nested for loops disable all tiles on the board
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		this.tiles [i] [j].setEnabled (false); //disables the tiles
	    }
	}

	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x + i] [y + i].setEnabled (true); //enables tile at move point

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x + i) == 7 || (y + i) == 7)
	    {
		break;
	    }
	}

	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x + i] [y - i].setEnabled (true); //enables tile at move point

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x + i) == 7 || (y - i) == 0)
	    {
		break;
	    }
	}

	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x - i] [y + i].setEnabled (true); //enables tile at move point

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x - i) == 0 || (y + i) == 7)
	    {
		break;
	    }
	}

	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x - i] [y - i].setEnabled (true); //enables tile at move point

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x - i) == 0 || (y - i) == 0)
	    {
		break;
	    }
	}
	this.tiles [x] [y].setEnabled (true); //enables tile at move point
    }


    //
    //Method used to move the white knight piece
    //
    public void WKnight (int x, int y)  //imports x and y as position of current piece
    {
	//if statement prevents out of bounds exceptions from tripping
	if ((x + 2) < 8 && (y + 1) < 8)
	{
	    this.tiles [x + 2] [y + 1].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x + 2] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 2] [y + 1].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x + 2] [y + 1].getText () == " ")
	    {
		this.tiles [x + 2] [y + 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x + 2] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 2] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x + 2) < 8 && (y - 1) >= 0)
	{
	    this.tiles [x + 2] [y - 1].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x + 2] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 2] [y - 1].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x + 2] [y - 1].getText () == " ")
	    {
		this.tiles [x + 2] [y - 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x + 2] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 2] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x - 2) >= 0 && (y + 1) < 8)
	{
	    this.tiles [x - 2] [y + 1].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x - 2] [y + 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 2] [y + 1].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 2] [y + 1].getText () == " ")
	    {
		this.tiles [x - 2] [y + 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x - 2] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 2] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x - 2) >= 0 && (y - 1) >= 0)
	{
	    this.tiles [x - 2] [y - 1].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x - 2] [y - 1].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 2] [y - 1].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 2] [y - 1].getText () == " ")
	    {
		this.tiles [x - 2] [y - 1].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x - 2] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 2] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x + 1) < 8 && (y + 2) < 8)
	{
	    this.tiles [x + 1] [y + 2].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x + 1] [y + 2].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y + 2].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x + 1] [y + 2].getText () == " ")
	    {
		this.tiles [x + 1] [y + 2].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x + 1] [y + 2].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 1] [y + 2].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x + 1) < 8 && (y - 2) >= 0)
	{
	    this.tiles [x + 1] [y - 2].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x + 1] [y - 2].getText ().charAt (0) == 'W')
	    {
		this.tiles [x + 1] [y - 2].setEnabled (false); //disables the tiles at move point
	    }

	    if (this.tiles [x + 1] [y - 2].getText () == " ")
	    {
		this.tiles [x + 1] [y - 2].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x + 1] [y - 2].getText ().charAt (0) == 'B')
	    {
		this.tiles [x + 1] [y - 2].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x - 1) >= 0 && (y + 2) < 8)
	{
	    this.tiles [x - 1] [y + 2].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x - 1] [y + 2].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 1] [y + 2].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 1] [y + 2].getText () == " ")
	    {
		this.tiles [x - 1] [y + 2].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x - 1] [y + 2].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y + 2].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((x - 1) >= 0 && (y - 2) >= 0)
	{
	    this.tiles [x - 1] [y - 2].setEnabled (true); //enables tile at move point

	    //checks if a white piece is at that position
	    if (this.tiles [x - 1] [y - 2].getText ().charAt (0) == 'W')
	    {
		this.tiles [x - 1] [y - 2].setEnabled (false); //disables the tiles at move point
	    }

	    //checks if tile at move point is blank
	    if (this.tiles [x - 1] [y - 2].getText () == " ")
	    {
		this.tiles [x - 1] [y - 2].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is black
	    if (this.tiles [x - 1] [y - 2].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y - 2].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}
	this.tiles [x] [y].setEnabled (true);
    }


    //
    //Method used to move the white rook piece
    //
    public void WRook (int x, int y)  //imports x and y as position of current piece
    {
	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x + i] [y].setEnabled (true); //enables tile at move point

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x + i) == 7)
	    {
		break;
	    }
	}

	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x - i] [y].setEnabled (true); //enables tile at move point

	    //if statement prevents out of bounds exceptions from tripping
	    if ((x - i) == 0)
	    {
		break;  //Breaks to ensure value is not negative
	    }
	}

	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x] [y + i].setEnabled (true); //enables tile at move point

	    //if statement prevents out of bounds exceptions from tripping
	    if ((y + i) == 7)
	    {
		break;
	    }
	}

	for (int i = 0 ; i < 8 ; i++)
	{
	    this.tiles [x] [y - i].setEnabled (true); //enables tile at move point

	    //if statement prevents out of bounds exceptions from tripping
	    if ((y - i) == 0)
	    {
		break;  //Breaks to ensure value is not negative
	    }
	}
	this.tiles [x] [y].setEnabled (true); //enables tile at move point
    }


    //
    //Method used to move the white pawn piece
    //
    public void WPawn (int x, int y)  //imports x and y as position of current piece
    {
	//nested for loops disable all tiles on the board
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		this.tiles [i] [j].setEnabled (false); //disables the tiles
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if (x == 6)
	{
	    this.tiles [x - 2] [y].setEnabled (true); //enables tile at move point

	    //checks if tile at move point is blank
	    if (this.tiles [x - 2] [y].getText () == " ")
	    {
		this.tiles [x - 2] [y].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is blank
	    if (tiles [x - 2] [y].getText ().charAt (0) != ' ')
	    {
		this.tiles [x - 2] [y].setEnabled (false); //disables the tiles at move point
	    }

	    this.tiles [x - 1] [y].setEnabled (true);

	    //checks if tile at move point is blank
	    if (this.tiles [x - 1] [y].getText () == " ")
	    {
		this.tiles [x - 1] [y].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is blank
	    if (tiles [x - 1] [y].getText ().charAt (0) != ' ')
	    {
		this.tiles [x - 1] [y].setEnabled (false); //disables the tiles at move point
		this.tiles [x - 2] [y].setEnabled (false); //disables the tiles at move point
	    }
	}

	else
	{
	    this.tiles [x - 1] [y].setEnabled (true); //enables tile at move point

	    //checks if tile at move point is blank
	    if (this.tiles [x - 1] [y].getText () == " ")
	    {
		this.tiles [x - 1] [y].setBackground (Color.yellow); //colors tile yellow to indicate a legal move
	    }

	    //checks if tile at move point is blank
	    if (tiles [x - 1] [y].getText ().charAt (0) != ' ')
	    {
		this.tiles [x - 1] [y].setEnabled (false); //disables the tiles at move point
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((y - 1) > -1)
	{
	    //checks if tile at move point is black
	    if (this.tiles [x - 1] [y - 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y - 1].setEnabled (true); //enables tile at move point
		this.tiles [x - 1] [y - 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}

	//if statement prevents out of bounds exceptions from tripping
	if ((y + 1) < 8)
	{
	    //checks if tile at move point is black
	    if (this.tiles [x - 1] [y + 1].getText ().charAt (0) == 'B')
	    {
		this.tiles [x - 1] [y + 1].setEnabled (true); //enables tile at move point
		this.tiles [x - 1] [y + 1].setBackground (Color.orange); //colors tile orange to indicate a legal capture move
	    }
	}
	//checks if tile at move point is black
	if (this.tiles [x - 1] [y].getText ().charAt (0) == 'B')
	{
	    this.tiles [x - 1] [y].setEnabled (false); //disables the tiles at move point
	}
	this.tiles [x] [y].setEnabled (true); //enables tile at move point
    }


    //
    //Method used to detect collisions for the Rook piece
    //
    public void RCollision (int x, int y)   //imports x and y as the position of the rook selected
    {
	if (this.tiles [x] [y].getBackground () == Color.CYAN) //Checks if the color of the current tile is cyan, for later recoloring
	{
	    this.ColourCheck = "CYAN"; //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.WHITE) //Checks if the color of the current tile is white, for later recoloring
	{
	    this.ColourCheck = "WHITE"; //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.BLUE) //Checks if the color of the current tile is blue, for later recoloring
	{
	    this.ColourCheck = "BLUE"; //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.RED) //Checks if the color of the current tile is red, for later recoloring
	{
	    this.ColourCheck = "RED"; //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.BLACK) //Checks if the color of the current tile is black, for later recoloring
	{
	    this.ColourCheck = "BLACK"; //Records the tile color so it can be recolored later
	}

	//for loop checks for collisions going down from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure not out of bounds statements are tripped
	    if ((x + i) < 8)
	    {
		//checks if the tile is not blank
		if (this.tiles [x + i] [y].getText ().charAt (0) != ' ')
		{
		    //for loop disables the colliding tile and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			this.tiles [x + ii] [y].setEnabled (false); //disables the tiles

			//if statement makes sure not out of bounds statements are tripped
			if (x + ii == 7)
			{
			    break; //breaks the loop before it goes out of bounds
			}
		    }

		    //checks if it's the white team's turn
		    if (this.turn % 2 != 0)
		    {
			//checks if the collided tile is an enemy piece
			if (this.tiles [x + i] [y].getText ().charAt (0) == 'B')
			{
			    this.tiles [x + i] [y].setEnabled (true); //Re-enables the tile
			    this.tiles [x + i] [y].setBackground (Color.orange); //sets the enemy's tile to orange to indicate it can be captured
			    break;
			}
		    }

		    //checks if it's the black team's turn
		    else if (this.turn % 2 == 0)
		    {
			//checks if the collided tile is an enemy piece
			if (this.tiles [x + i] [y].getText ().charAt (0) == 'W')
			{
			    this.tiles [x + i] [y].setEnabled (true); //Re-enables the tile
			    this.tiles [x + i] [y].setBackground (Color.orange); //sets the enemy's tile to orange to indicate it can be captured
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//for loop checks for collisions going up from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure not out of bounds statements are tripped
	    if ((x - i) > -1)
	    {
		//checks if the tile is not blank
		if (this.tiles [x - i] [y].getText ().charAt (0) != ' ')
		{
		    //for loop disables the colliding tile and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			this.tiles [x - ii] [y].setEnabled (false); //disables the tiles

			//if statement makes sure not out of bounds statements are tripped
			if (x - ii == 0)
			{
			    break; //breaks the loop before it goes out of bounds
			}
		    }

		    //checks if it's the white team's turn
		    if (turn % 2 != 0)
		    {
			//checks if the collided tile is an enemy piece
			if (this.tiles [x - i] [y].getText ().charAt (0) == 'B')
			{
			    this.tiles [x - i] [y].setEnabled (true); //Re-enables the tile
			    this.tiles [x - i] [y].setBackground (Color.orange); //sets the enemy's tile to orange to indicate it can be captured
			    break;
			}
		    }

		    //checks if it's the black team's turn
		    else if (turn % 2 == 0)
		    {
			//checks if the collided tile is an enemy piece
			if (this.tiles [x - i] [y].getText ().charAt (0) == 'W')
			{
			    this.tiles [x - i] [y].setEnabled (true); //Re-enables the tile
			    this.tiles [x - i] [y].setBackground (Color.orange); //sets the enemy's tile to orange to indicate it can be captured
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//for loop checks for collisions going right from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure not out of bounds statements are tripped
	    if ((y + i) < 8)
	    {
		//checks if the tile is not blank
		if (this.tiles [x] [y + i].getText ().charAt (0) != ' ')
		{
		    //for loop disables the colliding tile and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			this.tiles [x] [y + ii].setEnabled (false); //disables the tiles

			//if statement makes sure not out of bounds statements are tripped
			if (y + ii == 7)
			{
			    break; //breaks the loop before it goes out of bounds
			}
		    }

		    //checks if it's the white team's turn
		    if (this.turn % 2 != 0)
		    {
			//checks if the collided tile is an enemy piece
			if (this.tiles [x] [y + i].getText ().charAt (0) == 'B')
			{
			    this.tiles [x] [y + i].setEnabled (true); //Re-enables the tile
			    this.tiles [x] [y + i].setBackground (Color.orange); //sets the enemy's tile to orange to indicate it can be captured
			    break;
			}
		    }

		    //checks if it's the black team's turn
		    else if (this.turn % 2 == 0)
		    {
			//checks if the collided tile is an enemy piece
			if (this.tiles [x] [y + i].getText ().charAt (0) == 'W')
			{
			    this.tiles [x] [y + i].setEnabled (true); //Re-enables the tile
			    this.tiles [x] [y + i].setBackground (Color.orange); //sets the enemy's tile to orange to indicate it can be captured
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//for loop checks for collisions going left from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure not out of bounds statements are tripped
	    if ((y - i) > -1)
	    {
		//checks if the tile is not blank
		if (this.tiles [x] [y - i].getText ().charAt (0) != ' ')
		{
		    //for loop disables the colliding tile and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			this.tiles [x] [y - ii].setEnabled (false); //disables the tiles

			//if statement makes sure not out of bounds statements are tripped
			if (y - ii == 0)
			{
			    break; //breaks the loop before it goes out of bounds
			}
		    }

		    //checks if it's the white team's turn
		    if (this.turn % 2 != 0)
		    {
			//checks if the collided tile is an enemy piece
			if (this.tiles [x] [y - i].getText ().charAt (0) == 'B')
			{
			    this.tiles [x] [y - i].setEnabled (true); //Re-enables the tile
			    this.tiles [x] [y - i].setBackground (Color.orange); //sets the enemy's tile to orange to indicate it can be captured
			    break;
			}
		    }

		    //checks if it's the black team's turn
		    else if (this.turn % 2 == 0)
		    {
			//checks if the collided tile is an enemy piece
			if (this.tiles [x] [y - i].getText ().charAt (0) == 'W')
			{
			    this.tiles [x] [y - i].setEnabled (true); //Re-enables the tile
			    this.tiles [x] [y - i].setBackground (Color.orange); //sets the enemy's tile to orange to indicate it can be captured
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//nested for loops scan board for enabled tiles
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int ii = 0 ; ii < 8 ; ii++)
	    {
		//checks if tile is enabled
		if (this.tiles [i] [ii].isEnabled () == true)
		{
		    this.tiles [i] [ii].setBackground (Color.yellow); //colors enabled tiles yellow to indicate a legal move

		    //checks if enabled tile is an enemy piece
		    if (this.turn % 2 != 0 && this.tiles [i] [ii].getText ().charAt (0) == 'B')
		    {
			this.tiles [i] [ii].setBackground (Color.orange); //sets the enemy's tile to orange to indicate it can be captured
		    }

		    //checks if enabled tile is an enemy piece
		    if (this.turn % 2 == 0 && this.tiles [i] [ii].getText ().charAt (0) == 'W')
		    {
			this.tiles [i] [ii].setBackground (Color.orange); //sets the enemy's tile to orange to indicate it can be captured
		    }
		}
	    }
	}
	if (this.ColourCheck == "WHITE") //checks if the color of the original tile was white
	{
	    this.tiles [x] [y].setBackground (Color.WHITE); //sets the color of the tile to white
	}
	else if (this.ColourCheck == "CYAN") //checks if the color of the original tile was cyan
	{
	    this.tiles [x] [y].setBackground (Color.CYAN); //sets the color of the tile to cyan
	}
	else if (this.ColourCheck == "BLUE") //checks if the color of the original tile was blue
	{
	    this.tiles [x] [y].setBackground (Color.BLUE); //sets the color of the tile to blue
	}
	else if (this.ColourCheck == "BLACK") //checks if the color of the original tile was black
	{
	    this.tiles [x] [y].setBackground (Color.BLACK); //sets the color of the tile to black
	}
	else if (this.ColourCheck == "RED") //checks if the color of the original tile was red
	{
	    this.tiles [x] [y].setBackground (Color.RED); //sets the color of the tile to red
	}
    }


    //
    //Method used to detect collisions for the Bishop piece
    //
    public void BCollision (int x, int y)   //imports x and y as the position of the bishop selected
    {
	if (this.tiles [x] [y].getBackground () == Color.CYAN) //Checks if the color of the current tile is cyan, for later recoloring
	{
	    this.ColourCheck = "CYAN"; //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.WHITE) //Checks if the color of the current tile is white, for later recoloring
	{
	    this.ColourCheck = "WHITE"; //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.BLUE) //Checks if the color of the current tile is blue, for later recoloring
	{
	    this.ColourCheck = "BLUE"; //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.RED) //Checks if the color of the current tile is red, for later recoloring
	{
	    this.ColourCheck = "RED"; //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.BLACK) //Checks if the color of the current tile is black, for later recoloring
	{
	    this.ColourCheck = "BLACK"; //Records the tile color so it can be recolored later
	}

	//for loop checks for collisions going down and right from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure no out of bounds exceptions are tripped
	    if ((x + i) < 8 && (y + i) < 8)
	    {
		//Checks if the tile is not empty
		if (this.tiles [x + i] [y + i].getText ().charAt (0) != ' ')
		{
		    //if the tile was not blank, disables it and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			//if statement makes sure no out of bounds exceptions are tripped
			if ((x + ii) < 8 && (y + ii) < 8)
			{
			    this.tiles [x + ii] [y + ii].setEnabled (false); //disables the tiles
			}
		    }

		    //Checks if it is the white team's turn
		    if (turn % 2 != 0)
		    {
			//Checks if the tile that was collided with was black
			if (this.tiles [x + i] [y + i].getText ().charAt (0) == 'B')
			{
			    this.tiles [x + i] [y + i].setEnabled (true); //Re-enables the tile
			    break;
			}
		    }

		    //Checks if it is the black team's turn
		    else if (turn % 2 == 0)
		    {
			//Checks if the tile that was collided with was white
			if (this.tiles [x + i] [y + i].getText ().charAt (0) == 'W')
			{
			    this.tiles [x + i] [y + i].setEnabled (true); //Re-enables the tile
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//for loop checks for collisions going down and left from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure no out of bounds exceptions are tripped
	    if ((x + i) < 8 && (y - i) > -1)
	    {
		//Checks if the tile is not empty
		if (this.tiles [x + i] [y - i].getText ().charAt (0) != ' ')
		{
		    //if the tile was not blank, disables it and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			//if statement makes sure no out of bounds exceptions are tripped
			if ((x + ii) < 8 && (y - ii) > -1)
			{
			    this.tiles [x + ii] [y - ii].setEnabled (false); //disables the tiles
			}
		    }

		    //Checks if it is the white team's turn
		    if (this.turn % 2 != 0)
		    {
			//Checks if the tile that was collided with was black
			if (this.tiles [x + i] [y - i].getText ().charAt (0) == 'B')
			{
			    this.tiles [x + i] [y - i].setEnabled (true); //Re-enables the tile
			    break;
			}
		    }

		    //Checks if it is the black team's turn
		    else if (this.turn % 2 == 0)
		    {
			//Checks if the tile that was collided with was white
			if (this.tiles [x + i] [y - i].getText ().charAt (0) == 'W')
			{
			    this.tiles [x + i] [y - i].setEnabled (true); //Re-enables the tile
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//for loop checks for collisions going up and right from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure no out of bounds exceptions are tripped
	    if ((x - i) > -1 && (y + i) < 8)
	    {
		//Checks if the tile is not empty
		if (this.tiles [x - i] [y + i].getText ().charAt (0) != ' ')
		{
		    //if the tile was not blank, disables it and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			//if statement makes sure no out of bounds exceptions are tripped
			if ((x - ii) > -1 && (y + ii) < 8)
			{
			    this.tiles [x - ii] [y + ii].setEnabled (false); //disables the tiles
			}
		    }

		    //Checks if it is the white team's turn
		    if (this.turn % 2 != 0)
		    {
			//Checks if the tile that was collided with was black
			if (this.tiles [x - i] [y + i].getText ().charAt (0) == 'B')
			{
			    this.tiles [x - i] [y + i].setEnabled (true); //Re-enables the tile
			    break;
			}
		    }

		    //Checks if it is the black team's turn
		    else if (this.turn % 2 == 0)
		    {
			//Checks if the tile that was collided with was white
			if (this.tiles [x - i] [y + i].getText ().charAt (0) == 'W')
			{
			    this.tiles [x - i] [y + i].setEnabled (true); //Re-enables the tile
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//for loop checks for collisions going up and left from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure no out of bounds exceptions are tripped
	    if ((x - i) > -1 && (y - i) > -1)
	    {
		//Checks if the tile is not empty
		if (this.tiles [x - i] [y - i].getText ().charAt (0) != ' ')
		{
		    //if the tile was not blank, disables it and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			//if statement makes sure no out of bounds exceptions are tripped
			if ((x - ii) > -1 && (y - ii) > -1)
			{
			    this.tiles [x - ii] [y - ii].setEnabled (false); //disables the tiles
			}
		    }

		    //Checks if it is the white team's turn
		    if (this.turn % 2 != 0)
		    {
			//Checks if the tile that was collided with was black
			if (this.tiles [x - i] [y - i].getText ().charAt (0) == 'B')
			{
			    this.tiles [x - i] [y - i].setEnabled (true); //Re-enables the tile
			    break;
			}
		    }

		    //Checks if it is the black team's turn
		    else if (this.turn % 2 == 0)
		    {
			//Checks if the tile that was collided with was white
			if (this.tiles [x - i] [y - i].getText ().charAt (0) == 'W')
			{
			    this.tiles [x - i] [y - i].setEnabled (true); //Re-enables the tile
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//Nested for loops check for activated tiles indicating legal moves
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int ii = 0 ; ii < 8 ; ii++)
	    {
		//checks if tile is enabled
		if (this.tiles [i] [ii].isEnabled () == true)
		{
		    this.tiles [i] [ii].setBackground (Color.yellow); //colors enabled tiles yellow to show it's a legal move

		    //checks if it is the white teams turn, and if the enabled button is a black piece
		    if (turn % 2 != 0 && this.tiles [i] [ii].getText ().charAt (0) == 'B')
		    {
			this.tiles [i] [ii].setBackground (Color.orange);   //colors enemy piece orange to indicate a legal capture move
		    }

		    //checks if it is the black teams turn, and if the enabled button is a white piece
		    if (turn % 2 == 0 && this.tiles [i] [ii].getText ().charAt (0) == 'W')
		    {
			this.tiles [i] [ii].setBackground (Color.orange); //colors enemy piece orange to indicate a legal capture move
		    }
		}
	    }
	}
	if (this.ColourCheck == "WHITE") //checks if the color of the original tile was white
	{
	    this.tiles [x] [y].setBackground (Color.WHITE); //sets the color of the tile to white
	}
	else if (this.ColourCheck == "CYAN") //checks if the color of the original tile was cyan
	{
	    this.tiles [x] [y].setBackground (Color.CYAN); //sets the color of the tile to cyan
	}
	else if (this.ColourCheck == "BLUE") //checks if the color of the original tile was blue
	{
	    this.tiles [x] [y].setBackground (Color.BLUE); //sets the color of the tile to blue
	}
	else if (this.ColourCheck == "BLACK") //checks if the color of the original tile was black
	{
	    this.tiles [x] [y].setBackground (Color.BLACK); //sets the color of the tile to black
	}
	else if (this.ColourCheck == "RED") //checks if the color of the original tile was red
	{
	    this.tiles [x] [y].setBackground (Color.RED); //sets the color of the tile to red
	}
    }


    //
    //Method used to detect collisions for the queen piece
    //
    public void QCollision (int x, int y)  //imports x and y as the position of the queen being selected
    {
	if (this.tiles [x] [y].getBackground () == Color.CYAN) //Checks if the color of the current tile is cyan, for later recoloring
	{
	    this.ColourCheck = "CYAN";   //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.WHITE) //Checks if the color of the current tile is white, for later recoloring
	{
	    this.ColourCheck = "WHITE";    //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.BLUE) //Checks if the color of the current tile is blue, for later recoloring
	{
	    this.ColourCheck = "BLUE";   //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.RED) //Checks if the color of the current tile is red, for later recoloring
	{
	    this.ColourCheck = "RED";    //Records the tile color so it can be recolored later
	}
	else if (this.tiles [x] [y].getBackground () == Color.BLACK) //Checks if the color of the current tile is black, for later recoloring
	{
	    this.ColourCheck = "BLACK";    //Records the tile color so it can be recolored later
	}

	//for loop checks for collisions going down from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure no out of bounds exceptions are tripped
	    if ((x + i) < 8)
	    {
		//Checks if the tile is not empty
		if (this.tiles [x + i] [y].getText ().charAt (0) != ' ')
		{
		    //if the tile was not blank, disables it and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			this.tiles [x + ii] [y].setEnabled (false); //disables the tiles
			//If statement makes sure no out of bounds exceptions are tripped
			if (x + ii == 7)
			{
			    break; //breaks the loop before it goes out of bounds
			}
		    }

		    //Checks if it is the white team's turn
		    if (this.turn % 2 != 0)
		    {
			//Checks if the tile that was collided with was black
			if (this.tiles [x + i] [y].getText ().charAt (0) == 'B')
			{
			    this.tiles [x + i] [y].setEnabled (true);   //Re-enables the tile
			    this.tiles [x + i] [y].setBackground (Color.orange);    //sets the enemy's tile to orange

			    break;
			}
		    }

		    //Checks if it is the black team's turn
		    else if (this.turn % 2 == 0)
		    {
			//Checks if the tile that was collided with was white
			if (this.tiles [x + i] [y].getText ().charAt (0) == 'W')
			{
			    this.tiles [x + i] [y].setEnabled (true);   //Re-enables the tile
			    this.tiles [x + i] [y].setBackground (Color.orange); //sets the enemy's tile to orange
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//for loop checks for collisions going up from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure no out of bounds exceptions are tripped
	    if ((x - i) > -1)
	    {
		//Checks if the tile is not empty
		if (this.tiles [x - i] [y].getText ().charAt (0) != ' ')
		{
		    //if the tile was not blank, disables it and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			this.tiles [x - ii] [y].setEnabled (false); //disables the tiles
			//If statement makes sure no out of bounds exceptions are tripped
			if (x - ii == 0)
			{
			    break; //breaks the loop before it goes out of bounds
			}
		    }

		    //Checks if it is the white team's turn
		    if (this.turn % 2 != 0)
		    {
			//Checks if the tile that was collided with was black
			if (this.tiles [x - i] [y].getText ().charAt (0) == 'B')
			{
			    this.tiles [x - i] [y].setEnabled (true); //Re-enables the tile
			    this.tiles [x - i] [y].setBackground (Color.orange); //sets the enemy's tile to orange
			    break;
			}
		    }

		    //Checks if it is the black team's turn
		    else if (this.turn % 2 == 0)
		    {
			//Checks if the tile that was collided with was white
			if (this.tiles [x - i] [y].getText ().charAt (0) == 'W')
			{
			    this.tiles [x - i] [y].setEnabled (true); //Re-enables the tile
			    this.tiles [x - i] [y].setBackground (Color.orange); //sets the enemy's tile to orange
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//for loop checks for collisions going right from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure no out of bounds exceptions are tripped
	    if ((y + i) < 8)
	    {
		//Checks if the tile is not empty
		if (this.tiles [x] [y + i].getText ().charAt (0) != ' ')
		{
		    //if the tile was not blank, disables it and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			this.tiles [x] [y + ii].setEnabled (false); //disables the tiles
			//If statement makes sure no out of bounds exceptions are tripped
			if (y + ii == 7)
			{
			    break; //breaks the loop before it goes out of bounds
			}
		    }

		    //Checks if it is the white team's turn
		    if (this.turn % 2 != 0)
		    {
			//Checks if the tile that was collided with was black
			if (this.tiles [x] [y + i].getText ().charAt (0) == 'B')
			{
			    this.tiles [x] [y + i].setEnabled (true); //Re-enables the tile
			    this.tiles [x] [y + i].setBackground (Color.orange); //sets the enemy's tile to orange
			    break;
			}
		    }

		    //Checks if it is the black team's turn
		    else if (this.turn % 2 == 0)
		    {
			//Checks if the tile that was collided with was white
			if (this.tiles [x] [y + i].getText ().charAt (0) == 'W')
			{
			    this.tiles [x] [y + i].setEnabled (true); //Re-enables the tile
			    this.tiles [x] [y + i].setBackground (Color.orange); //sets the enemy's tile to orange
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//for loop checks for collisions going left from the current position
	for (int i = 1 ; i < 8 ; i++)
	{
	    //if statement makes sure no out of bounds exceptions are tripped
	    if ((y - i) > -1)
	    {
		//Checks if the tile is not empty
		if (this.tiles [x] [y - i].getText ().charAt (0) != ' ')
		{
		    //if the tile was not blank, disables it and everything past it
		    for (int ii = i ; ii < 8 ; ii++)
		    {
			this.tiles [x] [y - ii].setEnabled (false); //disables the tiles
			//If statement makes sure no out of bounds exceptions are tripped
			if (y - ii == 0)
			{
			    break; //breaks the loop before it goes out of bounds
			}
		    }

		    //Checks if it is the white team's turn
		    if (this.turn % 2 != 0)
		    {
			//Checks if the tile that was collided with was black
			if (this.tiles [x] [y - i].getText ().charAt (0) == 'B')
			{
			    this.tiles [x] [y - i].setEnabled (true); //Re-enables the tile
			    this.tiles [x] [y - i].setBackground (Color.orange); //sets the enemy's tile to orange
			    break;
			}
		    }

		    //Checks if it is the black team's turn
		    else if (this.turn % 2 == 0)
		    {
			//Checks if the tile that was collided with was white
			if (this.tiles [x] [y - i].getText ().charAt (0) == 'W')
			{
			    this.tiles [x] [y - i].setEnabled (true); //Re-enables the tile
			    this.tiles [x] [y - i].setBackground (Color.orange); //sets the enemy's tile to orange
			    break;
			}
		    }
		    break;
		}
	    }
	}

	//calls the Bcollision method to handle diagonal collision
	BCollision (x, y);  //passes the current position of the queen

	this.tiles [x] [y].setEnabled (true);   //enables current tiles to allow deselection
	if (this.ColourCheck == "WHITE")     //checks if the color of the original tile was white
	{
	    this.tiles [x] [y].setBackground (Color.WHITE); //sets the color of the tile to white
	}
	else if (this.ColourCheck == "CYAN") //checks if the color of the original tile was cyan
	{
	    this.tiles [x] [y].setBackground (Color.CYAN); //sets the color of the tile to white
	}
	else if (this.ColourCheck == "BLUE") //checks if the color of the original tile was blue
	{
	    this.tiles [x] [y].setBackground (Color.BLUE); //sets the color of the tile to white
	}
	else if (this.ColourCheck == "BLACK") //checks if the color of the original tile was black
	{
	    this.tiles [x] [y].setBackground (Color.BLACK); //sets the color of the tile to white
	}
	else if (this.ColourCheck == "RED") //checks if the color of the original tile was red
	{
	    this.tiles [x] [y].setBackground (Color.RED); //sets the color of the tile to white
	}
    }


    //
    //Method used to move chess pieces
    //
    public void Move (int i, int j, int x, int y)  //Imports i and j as the position of the tile to be moved to, and x and y as the position of the tile to be moved from
    {
	this.tiles [i] [j].setText (this.tiles [x] [y].getText ()); //sets the text of the button at the new position to the text of the old position
	this.tiles [x] [y].setText (" ");   //clears the text of the button at the old position
	this.tiles [i] [j].setIcon (this.tiles [x] [y].getIcon ()); //sets the picture of the button at the new position to the text of the old position
	this.tiles [i] [j].setHorizontalTextPosition (AbstractButton.CENTER);   //positions text properly on the new button position
	this.tiles [i] [j].setVerticalTextPosition (AbstractButton.BOTTOM);   //positions text properly on the button position
	this.tiles [x] [y].setIcon (null);  //clears the picture of the button at the old position

	// for loop scans the board for white pawns at the black end of the board
	for (int ii = 0 ; ii < 8 ; ii++)
	{
	    if (this.tiles [0] [ii].getText () == "WPawn")  //If a white pawn is found at the black end of the board
	    {
		this.tiles [0] [ii].setText ("WQueen"); //changes the text of the pawn at the end of the board to queen
		this.tiles [0] [ii].setIcon (wqueen); //changes the picture of the pawn at the end of the board to queen
		this.tiles [0] [ii].setHorizontalTextPosition (AbstractButton.CENTER); //positions text properly on the button
		this.tiles [0] [ii].setVerticalTextPosition (AbstractButton.BOTTOM); //positions text properly on the button
	    }
	}

	// for loop scans the board for black pawns at the white end of the board
	for (int ii = 0 ; ii < 8 ; ii++)
	{
	    if (this.tiles [7] [ii].getText () == "BPawn") //If a black pawn is found at the white end of the board
	    {
		this.tiles [7] [ii].setText ("BQueen"); //changes the text of the pawn at the end of the board to queen
		this.tiles [7] [ii].setIcon (bqueen); //changes the picture of the pawn at the end of the board to queen
		this.tiles [7] [ii].setHorizontalTextPosition (AbstractButton.CENTER); //positions text properly on the button
		this.tiles [7] [ii].setVerticalTextPosition (AbstractButton.BOTTOM); //positions text properly on the button
	    }
	}
    }


    public void boardcolour ()
    {
	this.count = 0;

	for (int ii = 0 ; ii < 8 ; ii++)
	{
	    for (int jj = 0 ; jj < 8 ; jj++)
	    {
		if (this.count >= 0)
		{
		    int row = (this.count / 8) % 2;

		    if (row == 0)
		    {
			if (this.board == 1)
			{
			    this.tiles [ii] [jj].setBackground (this.count % 2 == 0 ? Color.cyan:
			    Color.white);
			}

			if (this.board == 2)
			{
			    this.tiles [ii] [jj].setBackground (this.count % 2 == 0 ? Color.red:
			    Color.white);
			}

			if (this.board == 3)
			{
			    this.tiles [ii] [jj].setBackground (this.count % 2 == 0 ? Color.cyan:
			    Color.red);
			}

			if (this.board == 4)
			{
			    this.tiles [ii] [jj].setBackground (this.count % 2 == 0 ? Color.black:
			    Color.white);
			}

			if (this.board == 5)
			{
			    this.tiles [ii] [jj].setBackground (this.count % 2 == 0 ? Color.blue:
			    Color.white);
			}
		    }

		    else
		    {
			if (this.board == 1)
			{
			    this.tiles [ii] [jj].setBackground (this.count % 2 == 0 ? Color.white:
			    Color.cyan);
			}

			if (this.board == 2)
			{
			    this.tiles [ii] [jj].setBackground (this.count % 2 == 0 ? Color.white:
			    Color.red);
			}

			if (this.board == 3)
			{
			    this.tiles [ii] [jj].setBackground (this.count % 2 == 0 ? Color.red:
			    Color.cyan);
			}

			if (this.board == 4)
			{
			    this.tiles [ii] [jj].setBackground (this.count % 2 == 0 ? Color.white:
			    Color.black);
			}

			if (this.board == 5)
			{
			    this.tiles [ii] [jj].setBackground (this.count % 2 == 0 ? Color.white:
			    Color.blue);
			}
		    }
		}
		this.count++;
	    }
	}
    }


    //
    //Method checks if either king is in check, and if so displays which piece put them in check
    //
    public void Check ()
    {
	this.WhiteCheck = false;     //Reset variable to make sure it does not repeat itself
	this.BlackCheck = false;     //Reset variable to make sure it does not repeat itself
	this.CheckPiece = "";        //Reset variable to make sure it does not repeat itself

	if (this.turn % 2 != 0)      //If it is Player 1's turn
	{
	    //Nested for loops scan the board for enemy pieces
	    for (int i = 0 ; i < 8 ; i++)
	    {
		for (int j = 0 ; j < 8 ; j++)
		{
		    //Checks if the tiles is an enemy queen
		    if (this.tiles [i] [j].getText () == "WQueen")
		    {
			WQueen (i, j);      //Calling Queen method to check if it can capture the enemy king
			QCollision (i, j);  //Calling Queen collision method to check if it can capture the enemy king

			//If Queen was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "BKing") //If the tile is the black king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange)  //Checks if the black king has been highlighted as capturable
				    {
					this.BlackCheck = true;   //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.BlackCheck == true)     //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "WQueen";  //Records the piece that put the king in check
			break;  //breaks out of the for loop because the check has succeeded
		    }
		    //Checks if the tiles is an enemy king
		    if (this.tiles [i] [j].getText () == "WKing")
		    {
			WKing (i, j); //Calling King method to check if it can capture the enemy king

			//If King was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "BKing") //If the tile is the black king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the black king has been highlighted as capturable
				    {
					this.BlackCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.BlackCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "WKing"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		    //Checks if the tiles is an enemy bishop
		    if (this.tiles [i] [j].getText () == "WBishop")
		    {
			WBishop (i, j); //Calling Bishop method to check if it can capture the enemy king
			BCollision (i, j); //Calling Bishop collision method to check if it can capture the enemy king

			//If Bishop was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "BKing") //If the tile is the black king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the black king has been highlighted as capturable
				    {
					this.BlackCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.BlackCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "WBishop"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		    //Checks if the tiles is an enemy rook
		    if (this.tiles [i] [j].getText () == "WRook")
		    {
			WRook (i, j); //Calling Rook method to check if it can capture the enemy king
			RCollision (i, j); //Calling Rook collision method to check if it can capture the enemy king

			//If Rook was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "BKing") //If the tile is the black king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the black king has been highlighted as capturable
				    {
					this.BlackCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.BlackCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "WRook"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		    //Checks if the tiles is an enemy pawn
		    if (this.tiles [i] [j].getText () == "WPawn")
		    {
			WPawn (i, j); //Calling Pawn method to check if it can capture the enemy king

			//If Pawn was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "BKing") //If the tile is the black king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the black king has been highlighted as capturable
				    {
					this.BlackCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.BlackCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "WPawn"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		    //Checks if the tiles is an enemy knight
		    if (this.tiles [i] [j].getText () == "WKnight")
		    {
			WKnight (i, j); //Calling Knight method to check if it can capture the enemy king

			//If Knight was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "BKing") //If the tile is the black king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the black king has been highlighted as capturable
				    {
					this.BlackCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.BlackCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "WKnight"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		}
		if (this.BlackCheck == true) //Checks if the boolean has been tripped
		{
		    break; //breaks out of the for loop because the check has succeeded
		}
	    }
	    if (this.BlackCheck == true) //Checks if the boolean has been tripped
	    {
		System.out.println (CheckPiece + " has the black King in check."); //If the player is in check, prints out who is in check and what piece put them in check
	    }
	}
	else if (this.turn % 2 == 0) //If it is Player 1's turn
	{
	    for (int i = 0 ; i < 8 ; i++)
	    {
		for (int j = 0 ; j < 8 ; j++)
		{
		    //Checks if the tiles is an enemy queen
		    if (this.tiles [i] [j].getText () == "BQueen")
		    {
			BQueen (i, j); //Calling Queen method to check if it can capture the enemy king
			QCollision (i, j); //Calling Queen collision method to check if it can capture the enemy king

			//If Queen was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "WKing") //If the tile is the white king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the white king has been highlighted as capturable
				    {
					this.WhiteCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.WhiteCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "BQueen"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		    //Checks if the tiles is an enemy king
		    if (this.tiles [i] [j].getText () == "BKing")
		    {
			BKing (i, j); //Calling King method to check if it can capture the enemy king

			//If King was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "WKing") //If the tile is the white king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the white king has been highlighted as capturable
				    {
					this.WhiteCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.WhiteCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "BKing"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		    //Checks if the tiles is an enemy Bishop
		    if (this.tiles [i] [j].getText () == "BBishop")
		    {
			BBishop (i, j); //Calling Bishop method to check if it can capture the enemy king
			BCollision (i, j); //Calling Bishop collision method to check if it can capture the enemy king

			//If Bishop was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "WKing") //If the tile is the white king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the white king has been highlighted as capturable
				    {
					this.WhiteCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.WhiteCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "BBishop"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		    //Checks if the tiles is an enemy rook
		    if (this.tiles [i] [j].getText () == "BRook")
		    {
			BRook (i, j); //Calling Rook method to check if it can capture the enemy king
			RCollision (i, j); //Calling Rook collision method to check if it can capture the enemy king

			//If Rook was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "WKing") //If the tile is the white king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the white king has been highlighted as capturable
				    {
					this.WhiteCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.WhiteCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "BRook"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		    //Checks if the tiles is an enemy pawn
		    if (this.tiles [i] [j].getText () == "BPawn")
		    {
			BPawn (i, j); //Calling Pawn method to check if it can capture the enemy king

			//If Pawn was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "WKing") //If the tile is the white king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the white king has been highlighted as capturable
				    {
					this.WhiteCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.WhiteCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "BPawn"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		    //Checks if the tiles is an enemy knight
		    if (this.tiles [i] [j].getText () == "BKnight")
		    {
			BKnight (i, j); //Calling Knight method to check if it can capture the enemy king

			//If Knight was found, Nested for loops scan for the black king
			for (int ii = 0 ; ii < 8 ; ii++)
			{
			    for (int jj = 0 ; jj < 8 ; jj++)
			    {
				if (this.tiles [ii] [jj].getText () == "WKing") //If the tile is the white king
				{
				    if (this.tiles [ii] [jj].getBackground () == Color.orange) //Checks if the white king has been highlighted as capturable
				    {
					this.WhiteCheck = true; //Boolean is tripped to notify player they are in check
				    }
				}
			    }
			}
		    }
		    if (this.WhiteCheck == true) //Checks if the boolean has been tripped
		    {
			this.CheckPiece = "BKnight"; //Records the piece that put the king in check
			break; //breaks out of the for loop because the check has succeeded
		    }
		}
		if (this.WhiteCheck == true) //Checks if the boolean has been tripped
		{
		    break; //breaks out of the for loop because the check has succeeded
		}
	    }
	    if (this.WhiteCheck == true) //Checks if the boolean has been tripped
	    {
		System.out.println (this.CheckPiece + " has the white King in check."); //If the player is in check, prints out who is in check and what piece put them in check
	    }
	}
    }
}
