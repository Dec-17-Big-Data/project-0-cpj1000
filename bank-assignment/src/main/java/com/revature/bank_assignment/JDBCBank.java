package com.revature.bank_assignment;

import java.util.List;
import java.util.Scanner;

import com.revature.bank_assignment.models.Account;
import com.revature.bank_assignment.models.Login;
import com.revature.bank_assignment.models.Transaction;
import com.revature.bank_assignment.services.AccountService;
import com.revature.bank_assignment.services.LoginService;
import com.revature.bank_assignment.services.TransactionService;

public class JDBCBank {

	public static void main(String args[]) {

		Scanner scan = new Scanner(System.in);
		LoginService loginService = LoginService.getService();
		AccountService accountService = AccountService.getService();
		TransactionService transactionService = TransactionService.getService();
		String menuChoice = "";
		String accountMenuChoice = "";
		String manageMenuChoice = "";
		String accountSelection = "";
		String selectionLast = "";
		boolean superuser = false;
		boolean authorized = false;
		String moneyAmount = "";
		double moneyAmount_doub = 0;

		boolean exit = false;
		System.out.println("Welcome to Big Data Bank.");

		while (!exit) {
			System.out.println("----------Login Account Options----------");
			System.out.println("What would you like to do?:");
			System.out.println("1. Log in to an existing account.");
			System.out.println("2. Create a new account.");
			System.out.println("3. Exit.");
			System.out.println("-----------------------------------------");
			menuChoice = scan.nextLine();

			if (menuChoice.equals("1")) {
				String inputUsername = "";
				String inputPassword = "";
				Login sessionAccount = null;
				System.out.println("You have chosen to Log in.");
				System.out.println("Please enter your username: ");
				inputUsername = scan.nextLine();
				System.out.println("Please enter your password: ");
				inputPassword = scan.nextLine();
				if (loginService.Authenticate(inputUsername, inputPassword)) {
					sessionAccount = loginService.getLogin(inputUsername);

					// superuser flag, better implementation required
					superuser = false;
					if (inputUsername.equals("banks_superuser")) {
						superuser = true;
					}

					// Put account/transaction code here (ongoing)

					boolean accountExit = false;
					boolean manageExit = false;

					while (!accountExit) {
						System.out.println();
						System.out.println("Logged in as: ");
						System.out.println("User Number: " + sessionAccount.getUserNumber() + " LoginID: "
								+ sessionAccount.getLoginID());
						System.out.println();
						System.out.println("----------Bank Account Options----------");
						System.out.println("What would you like to do?:");
						System.out.println("1. See list of your Bank Accounts/Manage them.");
						System.out.println("2. Open a new Account.");
						System.out.println("3. Exit.");
						System.out.println("-----------------------------------------");
						if (superuser) {
							System.out.println("----------Superuser Account Options----------");
							System.out.println("4. Get list of all users.");
						}

						manageExit = false;
						accountMenuChoice = scan.nextLine();
						if (accountMenuChoice.equals("1")) {
							while (!manageExit) {
								System.out.println("----------Bank List----------");
								List<Account> accountList = accountService
										.getUserAccountList(sessionAccount.getUserNumber());
								if (accountList != null) {
									for (Account temp : accountList) {
										System.out.println(temp);
									}
									System.out.println();
									System.out.println("----------Bank Account Management----------");
									System.out.println("What would you like to do?:");
									System.out.println("1. Withdraw money from an account.");
									System.out.println("2. Deposit money from an account.");
									System.out.println("3. See account transaction history.");
									System.out.println("4. Delete account with 0 balance.");
									System.out.println("5. Exit.");
									System.out.println();
									manageMenuChoice = scan.nextLine();
									if (manageMenuChoice.equals("1")) {
										System.out.println("Enter account number to withdraw money from.");
										accountSelection = scan.nextLine();
										authorized = false;
										if (superuser == false) {
											for (Account temp : accountList) {
												if (Integer.toString(temp.getAccountID()).equals(accountSelection)) {
													System.out.println(Integer.toString(temp.getAccountID()));
													authorized = true;
												}
											}
										} else {
											authorized = true;
										}
										if (!authorized) {
											System.out.println("You do not have access to this account.");
										} else {
											System.out.println("How much money to withdraw?");
											moneyAmount = scan.nextLine();
											moneyAmount_doub = Double.parseDouble(moneyAmount);
											if (moneyAmount_doub < 0)
												System.out.println("Canot withdraw negative values.");
											else if (accountService.withdrawMoney(Integer.parseInt(accountSelection),
													moneyAmount_doub)) {
												System.out.println(
														"Withdrawal success. Here's your " + moneyAmount_doub + ".");
											} else
												System.out.println("Not enough money in Account.");
										}

									} else if (manageMenuChoice.equals("2")) {
										System.out.println("Enter account number to deposit money from.");
										accountSelection = scan.nextLine();
										authorized = false;
										if (superuser == false) {
											for (Account temp : accountList) {
												if (Integer.toString(temp.getAccountID()).equals(accountSelection)) {
													authorized = true;
												}
											}
										} else {
											authorized = true;
										}
										if (!authorized) {
											System.out.println("You do not have access to this account.");
										} else {
											System.out.println("How much money to deposit?");
											moneyAmount = scan.nextLine();
											moneyAmount_doub = Double.parseDouble(moneyAmount);
											if (moneyAmount_doub < 0)
												System.out.println("Cannot deposit negative values.");
											else if (accountService.depositMoney(Integer.parseInt(accountSelection),
													moneyAmount_doub)) {
												System.out.println("Deposit success.");
											}
										}
									} else if (manageMenuChoice.equals("3")) {
										System.out.println("Enter account number to check transaction history.");
										accountSelection = scan.nextLine();
										authorized = false;
										if (superuser == false) {
											for (Account temp : accountList) {
												if (Integer.toString(temp.getAccountID()).equals(accountSelection)) {
													authorized = true;
												}
											}
										} else {
											authorized = true;
										}
										if (!authorized) {
											System.out.println("You do not have access to this account.");
										} else {
											List<Transaction> transactionList = transactionService
													.getTransactionList(Integer.parseInt(accountSelection));
											for (Transaction temp : transactionList) {
												System.out.println(temp);
											}
										}

									} else if (manageMenuChoice.equals("4")) {
										System.out.println("Enter account number to delete.");
										accountSelection = scan.nextLine();
										authorized = false;
										if (superuser == false) {
											for (Account temp : accountList) {
												if (Integer.toString(temp.getAccountID()).equals(accountSelection)) {
													authorized = true;
												}
											}
										} else {
											authorized = true;
										}
										if (!authorized) {
											System.out.println("You do not have access to this account.");
										} else {
											if (accountService.closeAccount(Integer.parseInt(accountSelection))) {
												System.out.println(
														"Closed Bank Account " + accountSelection + " successfully.");
											} else
												System.out.println("Could not close bank account");
										}

									} else if (manageMenuChoice.equals("5")) {
										manageExit = true;
									}

								} else {
									System.out.println("No accounts found under user account.");
									manageExit = true;
								}
							}

						} else if (accountMenuChoice.equals("2")) {
							accountService.openNewAccount(sessionAccount.getUserNumber());
							System.out.println("New account created.");

						} else if (accountMenuChoice.equals("3")) {
							System.out.println();
							System.out.println("Logging out...");
							System.out.println();

							accountExit = true;
						} else if (accountMenuChoice.equals("4") && superuser) {
							manageExit = false;
							while (!manageExit) {
								List<Login> loginList = loginService.getAllLogins();
								if (loginList != null) {
									System.out.println();
									System.out.println("List of All Users in the Bank Database:: ");
									for (Login temp : loginList) {
										System.out.println(temp);
									}
									System.out.println();
									System.out.println("----------Superuser Account Management----------");
									System.out.println("What would you like to do?:");
									System.out.println("1. Delete a user and all records associated.");
									System.out.println("2. Change user's password.");
									System.out.println("3. Exit.");
									System.out.println();
									manageMenuChoice = scan.nextLine();

									if (manageMenuChoice.equals("1")) {
										System.out.println(
												"Enter user number to purge(Warning: This purges every info associated with the user.)");
										accountSelection = scan.nextLine();
										loginService.deleteUser(Integer.parseInt(accountSelection));

									} else if (manageMenuChoice.equals("2")) {
										System.out.println("Enter user number of user to change password for: ");
										accountSelection = scan.nextLine();
										System.out.println("Enter new password for user: ");
										selectionLast = scan.nextLine();
										loginService.updatePassword(Integer.parseInt(accountSelection), selectionLast);
									} else if (manageMenuChoice.equals("3")) {
										System.out.println("Back to Account menu.");
										manageExit = true;
									} else {
										System.out.println("Invalid Menu Selection. Please try again.");
									}

								}
							}
						}
					}
				} else {
					System.out.println("Invalid Username/Password. Login Failed.");
				}
				exit = false;

			} else if (menuChoice.equals("2")) {
				String inputUsername = "";
				String inputPassword = "";
				System.out.println("You have chosen to Create an account.");
				System.out.println("Please enter a username: ");
				inputUsername = scan.nextLine();
				System.out.println("Please enter a password: ");
				inputPassword = scan.nextLine();
				if (loginService.addNewUser(inputUsername, inputPassword)) {
					System.out.println("Account creation success:");
					System.out.println("You may now log in as: " + inputUsername);
				} else {
					System.out.println("Account creation failed.");
				}
				exit = false;

			} else if (menuChoice.equals("3"))

			{
				exit = true;
			} else {
				exit = false;
				System.out.println("Invalid Menu Selection. Please try again.");
			}
		}
		System.out.println("You have chosen to exit the program. Have a nice day!");
		scan.close();
		System.exit(0);
	}
}