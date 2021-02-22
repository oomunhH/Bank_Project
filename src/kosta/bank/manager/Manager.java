package kosta.bank.manager;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import kosta.bank.account.Account;
import kosta.bank.account.TransactionInfo;
import kosta.bank.member.Member;
import kosta.bank.scan.Scan;

public class Manager implements Serializable {
	private ArrayList<Member> memberList; // 회원정보리스트
	private HashMap<String, String> fAQ; // FAQ 질문,답

	public Manager() {
		memberList = new ArrayList<Member>();
		memberList.add(new Member("A", "admin", "admin", "010-3921-1129", "1129"));
		fAQ = new HashMap<String, String>();
		String str1 = "예금 금리";
		String str2 = "기본금리(연) : 0.10\n 100만원 초과~500만원 이하 : 0.60\n 500만원초과 1000만원이하 : 0.80\n";
		fAQ.put(str1, str2);
		str1 = "이용 가능 시간";
		str2 = "0:00~24:00 이용 가능\n(서버 점검 시간 23:40~0:10 제외)\n";
		fAQ.put(str1, str2);
		str1 = "사고신고 전화 접수";
		str2 = "일반 : 1588-5000 \n 해외 : 82-2-2006-5000\n";
		fAQ.put(str1, str2);
	}

	public ArrayList<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(ArrayList<Member> memberList) {
		this.memberList = memberList;
	}

	public HashMap<String, String> getfAQ() {
		return fAQ;
	}

	public void setfAQ(HashMap<String, String> fAQ) {
		this.fAQ = fAQ;
	}

	// 기능 구현 함수들
	// 회원가입
	public void signup() {

		WhileLoop: while (true) {
			System.out.println("<<회원가입>>");
			System.out.print("이름 : ");
			String name = Scan.scan().nextLine();
			System.out.print("id : ");
			String id = Scan.scan().nextLine();
			System.out.print("pw : ");
			String pw = Scan.scan().nextLine();
			System.out.print("phoneNo(010-xxxx-xxxx): ");
			String phoneNo = Scan.scan().nextLine();
			System.out.print("birth(월/일) : ");
			String birth = Scan.scan().nextLine();
			if (!(phoneNo.matches("^(010)-[0-9]{4}-[0-9]{4}"))) {
				System.out.println("########## '010-xxxx-xxxx' 형식으로 번호를 입력해주세요 ##########");
				continue;
			} else if (!(birth.matches("[0-9]+/[0-9]+"))) {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat dateF = new SimpleDateFormat("MM/dd");
				String str = dateF.format(cal.getTime());
				System.out.println("########## '" + str + "' 형식으로 입력해주세요 ##########");

				continue;
			}
			for (Member m : memberList) {
				if (m.getId().equals(id)) {
					System.out.println("##########중복된 아이디 입니다.##########");
				} else {
					memberList.add(new Member(name, id, pw, phoneNo, birth));
					System.out.println("'" + id + "' 회원가입 성공!!!");
					break WhileLoop;
				}
			}
		}
	}

	// 로그인 : 로그인 회원이면 회원이름, 관리자이면 관리자 return
	public Member login() {

		Member mem = null;
		WhileLoop: while (true) {
			System.out.println("<<로그인>>");
			System.out.print("id : ");
			String id = Scan.scan().nextLine();
			System.out.print("pw : ");
			String pw = Scan.scan().nextLine();
			for (Member m : memberList) {
				if (id.equals(m.getId()) && pw.equals(m.getPw())) {
					System.out.println("'" + id + "'로그인 성공");
					mem = m;
					break WhileLoop;
				}
			}
			if (mem == null) {
				System.out.println("id와 pw를 확인해 주세요");
			}
		}
		return mem;
	}

	// FAQ 전체 출력
	public void showFaq() {
		System.out.println("<<자주하는 질문(FAQ)>>");
		Set<String> keys = fAQ.keySet();
		int i = 0;
		System.out.println("-------------------------");
		for (String key : keys) {
			i++;
			System.out.println(i + "." + key);
			System.out.printf(fAQ.get(key));
			System.out.println("-------------------------");
		}

	}

	// FAQ 관리
	public void manageFaq() {
		System.out.print("질문 : ");
		String str1 = Scan.scan().nextLine();
		System.out.print("답변 : ");
		String str2 = Scan.scan().nextLine();
		fAQ.put(str1, str2);
	}

	// 모든 회원 정보 조회
	public void showMember() {
		for (Member m : memberList)
			System.out.println(m);
	}

	// 계좌 이체
	public void accountTransfer(Member m) {
		m.balanceInquiry();
		System.out.print("내 계좌 선택: ");
		int n = Scan.scan().nextInt();
		System.out.print("보낼 계좌번호: ");
		String no = Scan.scan().nextLine();
		System.out.print("보낼 금액: ");
		int money = Scan.scan().nextInt();

		WhileLoop: for (Member mem : memberList) {
			for (Account a : mem.getAccountList())

				if (m.getAccountList().get(n).getBalance() < money) {
					System.out.println("잔액이 부족합니다.");
					break WhileLoop;
				} else {
					if (a.getAccountNo().equals(no)) {
						System.out.println(mem.getName() + "님에게 " + money + "원 송금하시겠습니까?");
						System.out.print("(1.예, 2.아니요)");
						int check = Integer.parseInt(Scan.scan().nextLine());
						if (check == 1) {
							System.out.print("비밀번호를 입력해주세요");
							String pw = Scan.scan().next();
							if (a.getAccountPw().equals(pw)) {
								a.setBalance(a.getBalance() + money);
								m.getAccountList().get(n).setBalance(m.getAccountList().get(n).getBalance() - money);
								a.getTrxList().add(new TransactionInfo(m.getName(), money, "입금"));
								m.getAccountList().get(n).getTrxList()
										.add(new TransactionInfo(mem.getName(), money, "출금"));

								System.out.println("===이체완료===");
								System.out.println(m.getAccountList().get(n));
							} else {
								System.out.println("비밀번호가 틀렸습니다!");
							}
						}
						break WhileLoop;
					}

				}

		}

	}

}