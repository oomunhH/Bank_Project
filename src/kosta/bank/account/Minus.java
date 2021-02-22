package kosta.bank.account;

public class Minus extends Account{
	private int limit;	//대출한도

	public Minus() {
	}

	public Minus(String accountNo, String accountPw, int balance) {
		super(accountNo, accountPw, balance);
	}

	public Minus(String accountNo, String accountPw, int balance, int limit) {
		super(accountNo, accountPw, balance);
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
}
