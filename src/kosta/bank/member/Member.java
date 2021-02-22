package kosta.bank.member;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import kosta.bank.account.Account;
import kosta.bank.account.Minus;
import kosta.bank.account.Savings;
import kosta.bank.account.TransactionInfo;
import kosta.bank.scan.Scan;

public class Member implements Serializable {
	private String name; // 회원이름
	private String id; // 회원아이디
	private String pw; // 회원비밀번호
	private String phoneNo; // 회원전화번호
	private String birth; // 회원생년월일
	private ArrayList<Account> accountList = new ArrayList<Account>(); // 회원계좌정보들

	public Member() {

	}

	public Member(String name, String id, String pw, String phoneNo, String birth) {
		super();
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.phoneNo = phoneNo;
		this.birth = birth;
	}

	public Member(String name, String id, String pw, String phoneNo, String birth, ArrayList<Account> accountList) {
		super();
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.phoneNo = phoneNo;
		this.birth = birth;
		this.accountList = accountList;
	}

	@Override
	public String toString() {
		return "이름: " + name + "\nid: " + id + "\n핸드폰번호: " + phoneNo + "\n생일: " + birth + "\n계좌: " + accountList;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public ArrayList<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(ArrayList<Account> accountList) {
		this.accountList = accountList;
	}

	// 기능 구현 함수들

	// 회원 정보 보기
	public void showInfo() {
		System.out.println(this);
	}

	// 계좌개설
	public void openAccount() {

		System.out.println("1.입출금 계좌  2.마이너스 계좌  3.예금 계좌 ");
		System.out.print("개설하실 통장 종류를 선택:");
		String menu = Scan.scan().next();
		String accountPw;
		while (true) {
			System.out.print("계좌 비밀번호(숫자 4자리):");
			accountPw = Scan.scan().next();
			if (accountPw.matches("[0-9]{4}")) {
				break;
			} else {
				System.out.println("####숫자 4자리로 비밀번호를 입력해주세요.####");
				continue;
			}
		}
		int balance;
		Random rand = new Random();
		String accountNo = "";
		for (int j = 0; j < 10; j++) {
			accountNo += rand.nextInt(9);
		}

		Account account;
		switch (menu) {
		case "1":
			System.out.print("입금액: ");
			balance = Integer.parseInt(Scan.scan().next());
			account = new Account(accountNo, accountPw, balance);
			accountList.add(account);
			break;
		case "2":
			balance = 0;
			System.out.print("한도 금액: ");
			int limit = Scan.scan().nextInt();
			account = new Minus(accountNo, accountPw, balance, limit);
			accountList.add(account);
			break;
		case "3":
			System.out.print("입금액: ");
			balance = Integer.parseInt(Scan.scan().next());
			System.out.print("적금 기간(년): ");
			int term = Scan.scan().nextInt();
			double rate;
			if (balance <= 1000000)
				rate = 0.1;
			else if (balance > 1000000 && balance <= 5000000)
				rate = 0.6;
			else
				rate = 0.8;
			account = new Savings(accountNo, accountPw, balance, rate, term);
			accountList.add(account);
			break;

		default:
			break;
		}
		System.out.println("계좌번호:" + accountNo);
	}

	// 계좌 목록, 잔액 출력
	 public void balanceInquiry() {
	      //입출금, 마이너스, 예금
	      int i = 0;
	      System.out.println("*보유하고 있는 계좌 목록*");
	      for (Account account : accountList) {
	         if(account instanceof Minus) {
	            System.out.print(i++ + "번 마이너스 통장 " );
	         }else if(account instanceof Savings) {
	            System.out.print(i++ + "번 예금 통장 " );
	         }else {
	            System.out.print(i++ + "번 입출금 통장 " );
	         }
	            
	         System.out.println("- 계좌번호:" + account.getAccountNo() + ", 잔액: " + account.getBalance() + "원");
	      }
	      System.out.print("입출금 내역 조회(필요없으면 -1): ");
	      int n = Scan.scan().nextInt();
	      if(n==-1)
	         return;
	      accountList.get(n).showTrx();
	   }

	// 대출
	public int loan() {

		Account account1 = null; // 마이너스 통장
		Account account2 = null; // 입출금 통장
		for (Account acc : accountList) {
			if (acc instanceof Minus) {
				account1 = acc;
			}
			if (!(acc instanceof Minus || acc instanceof Savings)) {
				account2 = acc;
			}
		}
		Minus mAccount = null;
		if (account1 == null) {
			System.out.println("Minus 통장을 개설하셔야 대출할 수 있습니다.");
			return 0;
		} else if (account2 == null) {
			System.out.println("입출금 통장을 개설하셔야 대출할 수 있습니다.");
			return 0;
		} else {
			mAccount = (Minus) account1;
			System.out.print("대출 금액 입력(한도:" + mAccount.getLimit() + ")");
			int amount = Integer.parseInt(Scan.scan().next());
			if (mAccount.getBalance() + mAccount.getLimit() < amount) {
				System.out.println("한도초과 실패");
				return 0;
			} else {
				System.out.println("대출 전 minus 통장 잔액 " + mAccount.getBalance());
				mAccount.setBalance(mAccount.getBalance() - amount);
				System.out.println("대출 후 minus 통장 잔액 " + mAccount.getBalance());
				System.out.println("대출 전 입출금 통장 잔액" + account2.getBalance());
				account2.setBalance(account2.getBalance() + amount);
				System.out.println("대출 후 입출금 통장 잔액" + account2.getBalance());
				account2.getTrxList().add(new TransactionInfo(getName() + "대출", amount, "입금"));
				mAccount.getTrxList().add(new TransactionInfo(getName() + "대출", amount, "출금"));
				return amount;
			}

		}
	}

	// 환전
	public String exchange() {

		Account account = null;
		for (Account acc : accountList) {
			if (!(acc instanceof Minus || acc instanceof Savings)) {
				account = acc;
			}
		}
		String exMoney = "0";
		if (account == null) {
			System.out.println("입출금 통장을 개설하셔야 환전을 이용하실 수 있습니다.");
		} else {
			System.out.println("환전할 통화 선택");
			System.out.println("1.달러($), 2.유로(€), 3.엔(￥)");
			String n1 = Scan.scan().next();
			System.out.println("환전할 금액(￦) 입력:");
			double n2 = Double.parseDouble(Scan.scan().next());
			System.out.println("환전 전 잔액" + account.getBalance());
			account.setBalance((int) (account.getBalance() - n2));
			System.out.println("환전 후 잔액" + account.getBalance());
			account.getTrxList().add(new TransactionInfo(getName() + "환전", (int) n2, "출금"));
			double rate;
			switch (n1) {
			case "1":
				rate = 0.91;
				n2 = n2 / 1000 * rate;
				System.out.println(NumberFormat.getCurrencyInstance(Locale.US).format(n2));
				exMoney = NumberFormat.getCurrencyInstance(Locale.US).format(n2);
				break;
			case "2":
				rate = 0.75;
				n2 = n2 / 1000 * rate;
				System.out.println(NumberFormat.getCurrencyInstance(Locale.FRANCE).format(n2));
				exMoney = NumberFormat.getCurrencyInstance(Locale.FRANCE).format(n2);
				break;
			case "3":
				rate = 95.54;
				n2 = n2 / 1000 * rate;
				System.out.println(NumberFormat.getCurrencyInstance(Locale.JAPAN).format(n2));
				exMoney = NumberFormat.getCurrencyInstance(Locale.JAPAN).format(n2);
				break;
			}

		}
		return exMoney;

	}

}