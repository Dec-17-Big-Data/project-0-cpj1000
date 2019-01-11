package com.revature.bank_assignment.models;

import java.io.Serializable;

public class Login implements Serializable {
	private static final long serialVersionUID = 923220888185168418L;

	private int userNumber;
	private String loginID;
	private String loginPassword;

	public Login() {
	}

	public Login(int userNumber, String loginID, String loginPassword) {
		super();
		this.userNumber = userNumber;
		this.loginID = loginID;
		this.loginPassword = loginPassword;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loginID == null) ? 0 : loginID.hashCode());
		result = prime * result + ((loginPassword == null) ? 0 : loginPassword.hashCode());
		result = prime * result + userNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Login other = (Login) obj;
		if (loginID == null) {
			if (other.loginID != null)
				return false;
		} else if (!loginID.equals(other.loginID))
			return false;
		if (loginPassword == null) {
			if (other.loginPassword != null)
				return false;
		} else if (!loginPassword.equals(other.loginPassword))
			return false;
		if (userNumber != other.userNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Login [userNumber=" + userNumber + ", loginID=" + loginID + ", loginPassword=" + loginPassword + "]";
	}

}