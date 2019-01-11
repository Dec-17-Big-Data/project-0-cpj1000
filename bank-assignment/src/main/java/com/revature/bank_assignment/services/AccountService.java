package com.revature.bank_assignment.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.revature.bank_assignment.dao.AccountDao;
import com.revature.bank_assignment.dao.AccountOracle;
import com.revature.bank_assignment.models.Account;

public class AccountService {

	private static AccountService account_Service;
	final static AccountDao account_Dao = AccountOracle.getDao();

	private AccountService() {

	}

	public static AccountService getService() {
		if (account_Service == null) {
			account_Service = new AccountService();
		}
		return account_Service;
	}

	// Gets list of accounts for a given user number
	public List<Account> getUserAccountList(int userNumber) {
		try {
			Optional<List<Account>> accountList = account_Dao.getAccounts(userNumber);
			if (accountList.isPresent()) {
				return accountList.get();
			} else {
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	// superuser only, gets all accounts
	public List<Account> getAllAccountList() {
		try {
			Optional<List<Account>> accountList = account_Dao.getAllAccounts();
			if (accountList.isPresent()) {
				return accountList.get();
			} else {
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public boolean withdrawMoney(int bankID, double amount) {
		try {
			Optional<Integer> result = account_Dao.withdrawMoney(bankID, amount);
			if (result.isPresent()) {
				if (result.get() == 1)
					return true;
				else
					return false;
			} else
				return false;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean depositMoney(int bankID, double amount) {
		try {
			Optional<Integer> result = account_Dao.depositMoney(bankID, amount);
			if (result.isPresent()) {
				if (result.get() == 1)
					return true;
				else
					return false;
			} else
				return false;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	// TODO needs return (but should never fail)
	public boolean openNewAccount(int userNumber) {
		try {
			account_Dao.openNewAccount(userNumber);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean closeAccount(int bankID) {
		try {
			Optional<Integer> result = account_Dao.closeAccount(bankID);
			if (result.isPresent()) {
				if (result.get() == 1)
					return true;
				else
					return false;
			} else
				return false;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}