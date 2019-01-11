package com.revature.bank_assignment.dao;

import java.util.List;
import java.util.Optional;

import com.revature.bank_assignment.models.Account;

public interface AccountDao {

	public Optional<List<Account>> getAccounts(int userNumber);

	// superuser only
	public Optional<List<Account>> getAllAccounts();

	public Optional<Integer> openNewAccount(int userNumber);

	public Optional<Integer> depositMoney(int bankID, double amount);

	public Optional<Integer> withdrawMoney(int bankID, double amount);

	public Optional<Integer> closeAccount(int bankID);
}
