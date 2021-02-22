package kosta.bank.account;

public class Savings extends Account {
	private double rate;
	private int term;

	public Savings() {
	}

	public Savings(String accountNo, String accountPw, int balance) {
		super(accountNo, accountPw, balance);
	}

	public Savings(String accountNo, String accountPw, int balance, double rate, int term) {
		super(accountNo, accountPw, balance);
		this.rate = rate;
		this.term = term;
		System.out.println("만기시: "+calRate());
		
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}
	
	public long calRate() {
		return (long) (getBalance()*rate/100*term)+getBalance();
	}

}
