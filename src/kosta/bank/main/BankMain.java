package kosta.bank.main;

import kosta.bank.manager.Manager;
import kosta.bank.member.Member;
import kosta.bank.scan.Scan;
import kosta.bank.serial.SerialService;

public class BankMain {

	public static void main(String[] args) {

		SerialService service = new SerialService();
		Manager mgr = service.read();
		while (true) {
			System.out.println("======Kosta_Bank에 오신 것을 환영합니다======");
			System.out.println("1.회원가입, 2.로그인, 그 외.나가기");
			System.out.print(":");
			int n1 = Integer.parseInt(Scan.scan().nextLine());
			Member m = null;
			if (n1 == 1) {
				mgr.signup();
				// 아이디 생성
			} else if (n1 == 2) {
				m = mgr.login();
				// 로그인 제대로 될 때까지 로그인 화면 뜨도록
				int n2;
				WhileLoop: while (true) {
					if (m.getName().equals("A")) {
						System.out.println("관리자 페이지입니다.");
						System.out.println("1.회원 정보 보기, 2.FAQ관리, 3.모든정보 불러오기, 4.모든정보 저장하기, 5.로그아웃");
						n2 = Integer.parseInt(Scan.scan().nextLine());
						switch (n2) {
						case 1:
							// 1. 회원정보보기
							mgr.showMember();
							break;
						case 2:
							// 2. FAQ관리
							mgr.manageFaq();
							break;
						case 3:
							// 3. 모든정보 불러오기
							mgr = service.read();
							break;
						case 4:
							// 4. 모든정보 저장하기
							service.write(mgr);
							break;
						case 5:
							// 5. 로그아웃
							System.out.println("로그인 페이지로 이동>>");
							break WhileLoop;
						default:
							break;
						}
					} else {
						System.out.println(m.getName() + "님 무엇을 도와드릴까요??");
						System.out.println("1.내 정보 조회, 2.계좌 개설, 3.계좌 조회, 4.계좌 이체, 5.대출, 6.환전, 7.FAQ, 8.로그아웃");
						n2 = Integer.parseInt(Scan.scan().nextLine());
						switch (n2) {
						case 1:
							// 1. 정보 조회
							m.showInfo();
							break;
						case 2:
							// 2. 계좌 개설
							m.openAccount();
							break;
						case 3:
							// 3. 계좌 조회
							m.balanceInquiry();
							break;
						case 4:
							// 4. 계좌 이체
							mgr.accountTransfer(m);
							break;
						case 5:
							// 5. 대출
							m.loan();
							break;
						case 6:
							// 6. 환전
							m.exchange();
							break;
						case 7:
							// 7. FAQ 조회
							mgr.showFaq();
							break;
						case 8:
							// 8. 로그아웃
							System.out.println("메인 페이지로 이동>>");
							break WhileLoop;
						default:
							break;
						}
					}
				}
				m = null;
			} else {
				service.write(mgr);
				System.out.println("프로그램 종료");
				return;
			}
		}
	}

}
