import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class MyClient {
public static void main(String[] args) {
try {
	Socket s=new Socket("localhost",50000);
	DataInputStream dis = new DataInputStream(s.getInputStream());
    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
    BufferedInputStream bis = new BufferedInputStream(dis);
    BufferedOutputStream bos = new BufferedOutputStream(dos);
		
	
	bos.write(("HELO\n").getBytes());
	bos.flush();
	
	
	String str=(String)dis.readLine();
	System.out.println(str);
	
	bos.write(("AUTH Adam\n").getBytes());
	bos.flush();
	
	
	str=dis.readLine();
	System.out.println(str);
	bos.write(("REDY\n").getBytes());
	bos.flush();
	

	str=dis.readLine();
	System.out.println(str);
	
	String[] job = str.split(" ", 10);
	bos.write(("GETS All\n").getBytes());
	bos.flush();
	

	str = dis.readLine();
	System.out.println(str);
	
	int nRecs  = Integer.parseInt(String.valueOf(str.charAt(5)));
	System.out.println(nRecs);
	
	bos.write(("OK\n").getBytes());
	bos.flush();
	
	int j = 0;
	String[] large = new String[10];
	for(int i = 0; i < nRecs; i++){
		str = dis.readLine();
		System.out.println(str);
	
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

