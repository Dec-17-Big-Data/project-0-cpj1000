package com.revature.bank_assignment;

import java.util.Scanner;

public class App {

	private static void main(String args[]) {
		String temp = "";
		String userin_ID = "";
		String userin_pass = "";
		Boolean user_verified = false;

		Scanner scan = new Scanner(System.in);
		System.out.println("Hello, Welcome to the Banking Application.");
		System.out.println("Do you have a banking account with us? (Yes | No | Quit):");
		temp = scan.nextLine();
		if (temp.compareToIgnoreCase("yes") == 0) {
			System.out.println("Please Enter your User ID:");
		} else if (temp.compareToIgnoreCase("no") == 0) {
			System.out.println("Would you like to create an ID? (Yes | No | Quit): ");
			if (temp.compareToIgnoreCase("yes") == 0) {

			} else {
				System.out.println("OK, have a good day.");
			}
		} else if (temp.compareToIgnoreCase("quit") == 0) {

		} else {
			System.out.println("Invalid input.");
			scan.close();
			System.exit(0);
		}
		scan.close();
	}

}