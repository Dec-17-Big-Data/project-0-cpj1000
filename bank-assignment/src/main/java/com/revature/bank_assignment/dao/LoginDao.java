package com.revature.bank_assignment.dao;

import java.util.List;
import java.util.Optional;

import com.revature.bank_assignment.models.*;

public interface LoginDao {

	public Optional<Login> getLogin(String loginID);

	public Optional<Login> getLoginByNum(int userNumber);

	public Optional<Integer> addNewLogin(String login, String password);

	public Optional<Integer> updatePassword(int userNumber, String password);

	// Should be restricted to superuser
	public Optional<List<Login>> getAllLogins();

	// Should be restricted to superuser
	public Optional<Integer> purgeMember(int userNumber);
}