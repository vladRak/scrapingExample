package websocket;

public class App {

	public static void main(String[] args) throws Exception{
		final DemoListener ogogoroNewsListener = new DemoListener("wss://socketsbay.com/wss/v2/1/demo/");
		ogogoroNewsListener.connect();		
	}
}

