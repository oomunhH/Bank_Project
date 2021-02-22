package kosta.bank.serial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import kosta.bank.manager.Manager;

public class SerialService {

	// 객체를 직렬화하는 매서드
	public void write(Manager mgr) {

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("object.ser")); // object.ser에 전달하겠다 필터스트림임을 인지
			oos.writeObject(mgr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 객체를 역직렬화하는 매서드
	public Manager read() {
		ObjectInputStream ois = null;
		Manager m = null;
		try {
			File file = new File("object.ser");
			if (file.exists()) {
				ois = new ObjectInputStream(new FileInputStream("object.ser"));
				m = (Manager) ois.readObject();
			} else {
				m = new Manager();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return m;
	}

}
