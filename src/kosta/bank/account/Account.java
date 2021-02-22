package kosta.bank.account;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
	private String accountNo; // 계좌번호
	private String accountPw; // 계좌 비밀번호
	private int balance; // 잔액
	private ArrayList<TransactionInfo> trxList = new ArrayList<>();

	public Account() {
	}

	public Account(String accountNo, String accountPw, int balance) {
		this.accountNo = accountNo;
		this.accountPw = accountPw;
		this.balance = balance;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public String getAccountPw() {
		return accountPw;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public ArrayList<TransactionInfo> getTrxList() {
		return trxList;
	}

	public void setTrxList(ArrayList<TransactionInfo> trxList) {
		this.trxList = trxList;
	}

	@Override
	public String toString() {
		return "계좌번호: " + accountNo + ", 잔액: " + balance;
	}

	// 입출금 내역 출력
	public void showTrx() {
		if (trxList.isEmpty())
			System.out.println("입출금내역이 없습니다.");
		else {
			for (TransactionInfo t : trxList)
				System.out.println(t);
		}
	}

}