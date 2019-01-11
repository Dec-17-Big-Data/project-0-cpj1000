CREATE TABLE login
  (
     user_number     NUMBER(10),
     user_id         VARCHAR2(32) NOT NULL UNIQUE,
     user_password   VARCHAR2(32) NOT NULL,
     CONSTRAINT pk_login PRIMARY KEY (user_number)
  );

CREATE TABLE bank_account
  (
     bank_account_id NUMBER(10),
     user_number     NUMBER(10) NOT NULL,
     balance         BINARY_FLOAT DEFAULT 0 NOT NULL CHECK (balance >=0),
     CONSTRAINT pk_bank_account_id PRIMARY KEY (bank_account_id),
     CONSTRAINT fk_user_number_on_account FOREIGN KEY (user_number) REFERENCES
     login (user_number) ON DELETE CASCADE
  );
  
CREATE TABLE transactions
(
    transaction_id NUMBER(10),
    bank_account_id NUMBER(10) NOT NULL,
    transaction_type VARCHAR2(32) NOT NULL,
    transaction_amount BINARY_FLOAT NOT NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (transaction_id),
    CONSTRAINT fk_bank_account_id_on_trans FOREIGN KEY (bank_account_id) REFERENCES
    bank_account (bank_account_id) ON DELETE CASCADE
);

-- Create Sequences to track user numbers
CREATE SEQUENCE user_number_seq
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1;

CREATE SEQUENCE bank_account_id_seq
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1;
  
CREATE SEQUENCE trans_id_seq
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1;

-- Procedures
CREATE OR replace PROCEDURE Add_login (account_id       VARCHAR,
                                       account_password VARCHAR)
AS
BEGIN
    INSERT INTO login
    VALUES      (user_number_seq.NEXTVAL,
                 account_id,
                 account_password);
END;

/
CREATE OR replace PROCEDURE Add_account (user_number VARCHAR)
AS
BEGIN
    INSERT INTO bank_account
                (bank_account_id,
                 user_number)
    VALUES      (bank_account_id_seq.NEXTVAL,
                 user_number);
END;

/
CREATE OR replace PROCEDURE Delete_account (bank_id NUMBER, w_success OUT NUMBER)
AS
    accountExist NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO   accountExist
    FROM   bank_account
    WHERE  bank_account_id = bank_id;
    IF accountExist >= 1 THEN
        DELETE FROM bank_account WHERE bank_account_id = bank_id AND balance = 0;
        w_success := 1;
    ELSE
        w_success := -1;
    END IF;
END;

/
CREATE OR replace PROCEDURE Delete_login (del_user NUMBER)
AS
BEGIN
    DELETE FROM login
    WHERE  EXISTS (SELECT *
                   FROM   login
                   WHERE  user_number = del_user);
END;

/
CREATE OR replace PROCEDURE Deposit(deposit_bankID  NUMBER,
                                    amount_deposit BINARY_FLOAT)
AS
  current_balance BINARY_FLOAT;
BEGIN
    SELECT balance
    INTO   current_balance
    FROM   bank_account
    WHERE  deposit_bankID = bank_account_id;

    UPDATE bank_account
    SET    balance = ( current_balance + amount_deposit )
    WHERE  deposit_bankID = bank_account_id;
    
    INSERT INTO transactions VALUES (trans_id_seq.NEXTVAL, deposit_bankID, 'Deposit', amount_deposit);
END;
/

CREATE OR replace PROCEDURE Withdraw(withdraw_bankID     NUMBER,
                                     amount_withdrawal BINARY_FLOAT,
                                     w_success         OUT NUMBER)
AS
  current_balance BINARY_FLOAT;
BEGIN
    SELECT balance
    INTO   current_balance
    FROM   bank_account
    WHERE  withdraw_bankID = bank_account_id;

    IF ( current_balance - amount_withdrawal ) >= 0 THEN
      UPDATE bank_account
      SET    balance = ( current_balance - amount_withdrawal )
      WHERE  withdraw_bankID = bank_account_id;

      INSERT INTO transactions VALUES (trans_id_seq.NEXTVAL, withdraw_bankID, 'Withdrawal', amount_withdrawal);

      w_success := 1;
    ELSE
      w_success := -1;
    END IF;
END;

/
--Adds the superuser onto the table so he can be identified as such in the Java Applications
EXEC add_login('banks_superuser', 'a1s2d3f4');
COMMIT;

EXIT; 