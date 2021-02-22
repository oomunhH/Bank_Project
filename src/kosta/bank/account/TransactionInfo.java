package kosta.bank.account;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransactionInfo implements Serializable {
	private String date; // 거래 날짜
	private String senderName; // 보내는 사람 or 받는사람
	private int money; // 입출금 금액
	private String deOrWi; // 입출금 여부(출금, 입금)

	public TransactionInfo() {
	}

	public TransactionInfo(String senderName, int money, String deOrWi) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		String str = dateF.format(cal.getTime());
		this.date = str;
		this.senderName = senderName;
		this.money = money;
		this.deOrWi = deOrWi;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getDeOrWi() {
		return deOrWi;
	}

	public void setDeOrWi(String deOrWi) {
		this.deOrWi = deOrWi;
	}

	@Override
	public String toString() {
		if (deOrWi.equals("입금")) {
			System.out.print("입금 - ");
			return "날짜: " + date + ", 보낸사람: " + senderName + ", 입금액: " + money;
		} else {
			System.out.print("출금 - ");
			return "날짜: " + date + ", 받는사람: " + senderName + ", 출금액: " + money;
		}

	}

}