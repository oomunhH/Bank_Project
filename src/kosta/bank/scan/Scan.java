package kosta.bank.scan;

import java.io.Serializable;
import java.util.Scanner;

public class Scan implements Serializable {

	public static Scanner scan() {
		Scanner scan = new Scanner(System.in);
		return scan;
	}
}
