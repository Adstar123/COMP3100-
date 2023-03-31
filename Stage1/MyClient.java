import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class MyClient {

public static String AUTH = "AUTH " + System.getProperty("Adam");

public MyClient() {
}

public String readMsg(byte[] b, BufferedInputStream bin) {
    try {
      bin.read(b);
      String str = new String(b, StandardCharsets.UTF_8);
      return str;
    } catch (Exception e) {
      System.out.println(e);
    }
    return "error";
  }

	public static void main(String[] args) {
try {
	Socket s=new Socket("localhost",50000);
	DataInputStream dis = new DataInputStream(s.getInputStream());
    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
    BufferedInputStream bis = new BufferedInputStream(dis);
    BufferedOutputStream bos = new BufferedOutputStream(dos);
		
	MyClient myClient = new MyClient();

	bos.write(("HELO\n").getBytes());
	bos.flush();
	
	
	String serverResponse = myClient.readMsg(new byte[2], bis);
	
	bos.write(AUTH.getBytes());
    bos.flush();
	
	serverResponse = myClient.readMsg(new byte[2], bis);
	
	bos.write(("REDY\n").getBytes());
	bos.flush();
	

	serverResponse = myClient.readMsg(new byte[64], bis);
	
	String[] job = serverResponse.split(" ", 10);
	bos.write(("GETS All\n").getBytes());
	bos.flush();
	
	serverResponse = myClient.readMsg(new byte[32], bis);
	
	int nRecs  = Integer.parseInt(String.valueOf(serverResponse.charAt(5)));
	System.out.println(nRecs);
	
	bos.write(("OK\n").getBytes());
	bos.flush();

    String str = new String();
	
	int j = 0;
	String[] large = new String[10];
	for(int i = 0; i < nRecs; i++){
	
	String[] arr = str.split(" ", 9);
	int coreNum = Integer.parseInt(String.valueOf(arr[4]));
	
	if(j < coreNum){
	j = coreNum;
	large = arr;
	}
	}
	
	bos.write(("OK\n").getBytes());
	bos.flush();
	
	bos.write(("SCHD " + job[2] + " " + large[0] + " " + large[1] + "\n").getBytes());
	bos.flush();
	
	bos.write(("OK\n").getBytes());
	bos.flush();
	
	bos.write(("QUIT\n").getBytes());
	bos.flush();
	bos.close();
	s.close();
}catch(Exception e)
	{System.out.println(e);
}
}
}

