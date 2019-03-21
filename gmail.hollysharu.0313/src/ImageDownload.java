import java.io.BufferedInputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

//Thread 클래스를 이용해서 Thread를 생성 
class ImageThread extends Thread {
		// 이미지 파일의 주소를 문자열로 저장
		String addr = "https://lumiere-a.akamaihd.net/v1/images/uk_lio-mus_showcase_hero-breaker_r_f13afa33.jpeg";

	public void run() {
		// 파일명 부분만 잘라내기
		// 문자열 분할
		// 1. 특정 기호나 패턴으로 분할: split 함수 이용 - 문자열 배열로 리턴
		// 2. 위치를 기준으로 분할 : subString 함수 이용 - 문자열로 리턴
		// lastindexOf와 subString을 이용해서 파일명만 추출
		int idx = addr.lastIndexOf('/');
		String filename = addr.substring(idx + 1); // idx만 하면 / 포함해서 출력됨 그 이후부터 출력하려면 +1해서 다음 번째 출력
		// System.out.printf("%s\n", filename);

		// 파일 경로 생성
		String filepath = String.format("/Users/mac/Documents/%s", filename);
		// File 객체 생성
		File f = new File(filepath);
		
		// 파일이 존재하면 대화상자 출력하고 함수 종료
		// 두 번째 실행 때부터 출력
		if (f.exists() == true) {
			JOptionPane.showMessageDialog(null, "파일이 존재합니다");
			return;
		}
		
		// 웹 주소와 연결하기 위한 객체
		HttpURLConnection con = null;
		// 데이터를 읽어오기 위한 스트림
		BufferedInputStream bis = null;
		//파일에 바이트 기록할 스트림 
		PrintStream pw = null;

		try {
			// 다운로드 받을 주소 생성
			URL url = new URL(addr);
			// 연결 객체 생성
			con = (HttpURLConnection) url.openConnection();
			// 옵션을 설정
			con.setConnectTimeout(30000);
			con.setUseCaches(false);

			// 읽기 위한 스트림 생성
			bis = new BufferedInputStream(con.getInputStream());
			//데이터를 기록하기 위한 스트림 생성 
			pw = new PrintStream(filepath);

			// 데이터 읽기
			while (true) {
				byte[] b = new byte[512];
				int r = bis.read(b);
				if (r <= 0) {
					break;
				}
				//읽은 내용을 기록 - 배열의 모든 내용을 기록하지 않도록 주의 
				//항상 '배열에서 읽은 만큼'을 기록해야 함 
				//write(byte[] buf, int off, int len)
				pw.write(b, 0, r);
			}
			pw.flush();
		} catch (Exception e) {
			System.out.printf("1:%s\n", e.getMessage());
			e.getStackTrace();
		} finally {
			try {
				// 나중에 만든 걸 먼저 해제 해야 함 - 계란 노른자/흰자
				pw.close();
				bis.close();
				con.disconnect();
			} catch (Exception e) {
				System.out.printf("2:%s\n", e.getMessage());
				e.printStackTrace();
			}
		}
	}
}

public class ImageDownload {

	public static void main(String[] args) {
		// 스레드 인스턴스를 생성하고 스레드 시작
		Thread thread = new ImageThread();
		thread.start();

	}

}
