package com.revature.bank_assignment.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.revature.bank_assignment.dao.LoginDao;
import com.revature.bank_assignment.dao.LoginOracle;
import com.revature.bank_assignment.exceptions.LoginCreateFailException;
import com.revature.bank_assignment.exceptions.LoginDeletionFailException;
import com.revature.bank_assignment.exceptions.LoginFailException;
import com.revature.bank_assignment.models.Login;

public class LoginService {

	private static LoginService login_Service;
	final static LoginDao login_Dao = LoginOracle.getDao();

	private LoginService() {

	}

	public static LoginService getService() {
		if (login_Service == null) {
			login_Service = new LoginService();
		}
		return login_Service;
	}

	public Login getLogin(String userID) {
		try {
			Optional<Login> loginQuery = login_Dao.getLogin(userID);
			if (loginQuery.isPresent()) {
				return loginQuery.get();
			} else {
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public boolean Authenticate(String userid_in, String password_in) {
		try {
			Login loginQuery = getLogin(userid_in);
			if (loginQuery == null) {
				throw new LoginFailException();
			} else if (loginQuery.getLoginPassword().compareTo(password_in) != 0) {
				throw new LoginFailException();
			} else {
				return true;
			}
		} catch (LoginFailException e) {
			return false;
		}
	}

	public boolean addNewUser(String userid_in, String password_in) {
		try {
			Optional<Login> loginQuery = login_Dao.getLogin(userid_in);
			if (!(loginQuery.isPresent())) {
				login_Dao.addNewLogin(userid_in, password_in);
				return true;
			}
			throw new LoginCreateFailException();
		} catch (LoginCreateFailException e) {
			return false;
		}
	}

	// superuser only
	public boolean deleteUser(int userid_in) {
		try {
			Optional<Integer> result = login_Dao.purgeMember(userid_in);
			if (result.isPresent()) {
				if (result.get() == 1)
					return true;
				else
					return false;
			} else
				throw new LoginDeletionFailException();
		} catch (LoginDeletionFailException e) {
			return false;
		}
	}

	public boolean updatePassword(int userNumber, String password) {
		try {
			login_Dao.updatePassword(userNumber, password);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// superuser only
	public List<Login> getAllLogins() {
		try {
			Optional<List<Login>> loginList = login_Dao.getAllLogins();
			if (loginList.isPresent()) {
				return loginList.get();
			} else {
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			return null;
		}
	}

}