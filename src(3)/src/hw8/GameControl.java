import java.util.Random;
import java.util.Scanner;

public class GameControl {
	
	/**
	 * Computer player
	 */
	Computer computer;

	/**
	 * Human player
	 */
    Human human;

	
	/**
	 * A random number generator to be used for returning random dice result (1-6) for both computer and human player
	 */
	Random random = new Random();
	
	/**
	 * The main method just creates a GameControl object and calls its run method
	 * @param args not used
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		System.out.println("Welcome to Pig!");
		System.out.println();
		GameControl gc = new GameControl();
		while (true) {
			gc.run(sc);
			System.out.println("--------------------");
			System.out.println("Do you want to play again?");
			
			boolean check = askYesNo(sc);
			if (!check) {
				System.out.println("Goodbye!");
				sc.close();
				break;
			}
		}
	}
	
	/**
     * Calls methods to perform each of the following actions:
     * - Ask user to input the name for human player, and print welcome with the name
     * - Create the players (one human and one computer)
     * - Control the play of the game
     * - Print the final results
	 * @param sc to use for getting user input
	 */
	public void run(Scanner sc) {
		System.out.println("Please input your name");
		String name = sc.next();
		System.out.println("Welcome " + name);
		createPlayers(name);
		while (! checkWinningStatus())
		{
			System.out.println("Computer's score in this round: " + computer.move(human,random));
			System.out.println("Computer's total score is:" + computer.getScore());
			System.out.println();
			//if the first player reaches or exceeds 50, the second player gets one additional turn
			if (computer.getScore()>49 & 50> human.getScore())
			{
				System.out.println(human.getName() + "'s score in this round: " + human.move(computer,random,sc));
				System.out.println("Human's total score is:" + human.getScore());
				//if the first player reaches or exceeds 50, the second player gets one additional turn
				while (computer.getScore() == human.getScore())
				{
					System.out.println("Computer's score in this round: " + computer.move(human,random));
					System.out.println("Computer's total score is:" + computer.getScore());
					System.out.println();
					System.out.println(human.getName() + "'s score in this round: " + human.move(computer,random,sc));
					System.out.println("Human's total score is:" + human.getScore());
					System.out.println();
				}
				break;
			}
			System.out.println(human.getName() + "'s score in this round: " + human.move(computer,random,sc));
			System.out.println("Human's total score is:" + human.getScore());
			System.out.println();


		}
		printResults();
		printWinner();

	}
	
	/**
     * Creates one human player with the given humanName, and one computer player with a name.
     * @param humanName for human player
     */
	public void createPlayers(String humanName) {
		this.human = new Human(humanName);
		this.computer = new Computer();
	}
	
	/**
     * Checks if a winning status has been achieved
     * @return true if one player has won the game
     */
	public boolean checkWinningStatus() {
		//if human wins, return true, because in main, I defined another way break the loop
		return human.getScore() > 49 & 50 > computer.getScore();
	}
	
	/**
	 * Prints the final scores of the human player and computer player
	 */
	public void printResults() {
		System.out.println("Human gets:" + human.getScore());
		System.out.println("Computer gets:" + computer.getScore());
	}
	
	/**
     * Determines who won the game, and prints the results
     */
	public void printWinner() {
		if(human.getScore() > computer.getScore() )
		{
			System.out.println("Human wins!");
		}else{
			System.out.println("Computer wins!");
		}

	}

	/**
	 * If the user responds a string starting with "y" or "Y", the function returns True. 
	 * If the  user responds a string starting with "n" or "N", the function returns False. 
	 * Any other response will cause the question to be repeated until the user provides an acceptable response. 
	 * @param sc to use for getting user input
	 * @return true if user responds with "y" or "Y"
	 */
	public static boolean askYesNo(Scanner sc) {
		boolean result = true;
		boolean d = true;
		while (d){
			String in = sc.next();
			if(in.equals("y") | in.equals("Y"))
			{
				d = false;
			}
			else if(in.equals("n") || in.equals("N"))
			{
				d = false;
				result = false;

			}else
			{
				System.out.println("Input again");
			}
		}
		return result;
	}
	
}
