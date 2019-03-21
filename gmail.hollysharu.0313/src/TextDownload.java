import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//Thread 클래스를 이용해서 Thread를 생성 
class TextThread extends Thread{
	public void run() {
		//연결을 반드시 해제하는 URLConnection 변수 생성
		HttpURLConnection con = null;
		//문자열을 읽어올 BufferedReader 변수 생성 
		BufferedReader br = null;;
		
		try {
			//1. URL은 해제할 필요가 없는 클래스라서 try구문 안에서 생성 
			URL url = new URL("http://www.hani.co.kr/rss/");
			//2. URL과 통신할 수 있는 연결 객체 생성 
			//URLConnection은 추상 클래스라서 사용할 클래스로 강제 형 변환 해서 사용 
			con = (HttpURLConnection)url.openConnection();
			//3. 연결 옵션 설정 : 메소드 전송 방식, 연결 제한 시간, 캐시 사용 여부 등 
			con.setConnectTimeout(30000); //연결제한 시간 30초 
			con.setUseCaches(false); //캐시사용을 하지 않음 
			con.setRequestMethod("GET"); //GET방식으로 전송 
			//4. 문자열을 읽어올 스트림을 생성 - 반드시 연결 해제 
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			//5. 문자열을 읽어오기 - 대용량의 문자열을 읽기 
			//데이터를 읽어올 때는 줄 단위로 읽어오므로 편집 가능한 문자열 객체에 저장하는 것이 좋음 
			StringBuilder sb = new StringBuilder();
			//크기를 알 수 없으므로 무한반복 
			while(true) {
				//한 줄씩 읽기 
				String line = br.readLine();
				//읽은 문자열이 없으면 문자열 종료 
				if(line == null) {
					break;
				}
				sb.append(line);
			}
			//문자열을 전부 저장했으면 스트링빌더에 있는 여분의 공간이 필요없기 때문에 String으로 변환 
			String content = sb.toString();
			System.out.printf("%s", content);
		}
		catch(Exception e) {
			System.out.printf("%s\n", e.getMessage());
			e.getStackTrace();
		}
		finally {
			
			//나중에 만든 걸 먼저 해제 해야 함 - 계란 노른자/흰자 
			try {
				br.close();
				con.disconnect();
			} catch (IOException e) {
				System.out.printf("%s\n", e.getMessage());
				e.printStackTrace();
			}
		}
		
		
	}
}

public class TextDownload {

	public static void main(String[] args) {
		//스레드 인스턴스를 생성하고 스레드 시작 
		Thread thread = new Thread();
		thread.start();
		
	}

}
