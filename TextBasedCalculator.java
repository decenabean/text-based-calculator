import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.Math; // is this cheating?

public class TextBasedCalculator {

	// Method to determine operation type and output result to screen
	public static void performOperation(String operator, double num1, double num2) {
		// switch case based on operator type
		switch (operator) {
			// Add
			case ("add"):
				System.out.println("Result: " + (num1 + num2));
				break;
			// Subtract
			case ("subtract"):
				System.out.println("Result: " + (num1 - num2));
				break;
			// Multiply
			case ("multiply"):
				System.out.println("Result: " + (num1 * num2));
			break;
			// Divide
			case ("divide"):
				if (num2 == 0 ) { // Checks for division by 0
					System.out.println("Error: Cannot divide by zero");
					break;
				}
				else {
					System.out.println("Result: " + (num1 / num2));
					break;
				}
			// Power
			case ("power"):
				System.out.println("Result: " + Math.pow(num1, num2));
				break;
			// Square Root, apologies if using Math library doesn't count 
			case ("sqrt"):
				if (num1 < 0 ) { // Handles negatives
					System.out.println("Error: Cannot compute square root of negatives.");
					break;
				}
				else {
					System.out.println("Result: " + Math.sqrt(num1));
					break;
				}
			// Handles non valid operators 
			default:
				System.out.println("Error: Invalid or nonexistent operator");
				break;
		}
	}
	
	// Method to read input from console
	public static void readInput(String input) {
		// Splits input string into separate parts
		String[] parts = input.split(" ");
	
		if (parts.length == 3) {
			String operator = parts[0];
			double num1 = Double.parseDouble(parts[1]); // had to look up type conversion ngl
			double num2 = Double.parseDouble(parts[2]);
			
			performOperation(operator, num1, num2);
		}
		else if (parts.length == 2 && parts[0].equals("sqrt")) { // Handles sqrt operator
			String operator = parts[0];
			double num1 = Double.parseDouble(parts[1]);
			performOperation(operator, num1, 1); // 1 is just a placeholder, not actually used
		}
		else if (parts.length % 3 == 0) { // Handles multiple non-sqrt operations on one line
			for (int i = 0; i < parts.length; i+=3) { 
				String operator = parts[i];
				double num1 = Double.parseDouble(parts[i+1]);
				double num2 = Double.parseDouble(parts[i+2]);
				performOperation(operator, num1, num2);
			}
		}
		else {
			System.out.println("Error: Invalid input format. Enter operator and then numbers.");
		}
	}
	
	// Method to read input from a txt file
    public static void readFromFile(String fileName) throws IOException {
    	// Message for user
    	System.out.println("Reading from file...\n");    	
    	
    	FileInputStream fileStream = new FileInputStream(fileName);
        Scanner fileScanner = new Scanner(fileStream);
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        
        // Reads and prints each line of the file
        while (fileScanner.hasNext()) {
        	System.out.println(fileScanner.nextLine()); // For testing purposes
        	String input = reader.readLine();
        	readInput(input);
        	
        }
        // Closing message
        System.out.println("\nEnd of file " + fileName); 
        
        // Closes scanner, reader, and file
        reader.close();
        fileStream.close(); 
        fileScanner.close(); 
        }

	// Main
	public static void main(String[] args) throws IOException {
		// Initializes global variables and scanner
		Scanner scnr = new Scanner(System.in);
		
		// Opening message and instructions
		System.out.println("Welcome to my Text-Based Calculator!");
		System.out.println("Type operations and numbers to begin, type 'file' \nto read from a file,"
				+ " or type 'exit' to quit.");
		
		// Bad loop I know
		while (true) {
			// Prompts and gets user input
			String input = scnr.nextLine();
			
            // If input is 'exit', break the loop
            if (input.equals("exit")) {
                break;
            }
            else if (input.equals("file")) { // If input is 'file', tries to read input from file
            	// Prompts user to enter file name
            	System.out.print("Enter the file directory: ");
        		String fileName = scnr.nextLine();
                
            	try {
                	readFromFile(fileName);
            	} catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input");
                    scnr.next();
                } catch (FileNotFoundException e) {
                    System.out.println("Error: File '" + fileName + "' not found");
                } catch (IOException e) {
                    System.out.println("An error occurred while reading the file.");
                }
            }
            else { // Reads input from console
            	try {
            		readInput(input);
            	} catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Enter operator and then numbers.");
                    scnr.next();
                }    
            }
		}
		// Ending message
		System.out.println("Thanks for your input, goodbye! (with grace)");
		
		// Closes scanner
		scnr.close();

	}

}
