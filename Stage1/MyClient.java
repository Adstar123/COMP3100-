import java.io.*;
import java.net.*;
public class MyClient {

  public static String AUTH = "AUTH " + System.getProperty("Adam") + "\n";
  public static String REDY = "REDY\n";
  public static String OK = "OK\n";
  public static String HELO = "HELO\n";
  public static String GETSALL ="GETS All\n";
  public static String QUIT = "QUIT\n";

public static void main(String[] args) {
try {
    Socket s=new Socket("localhost",50000);
    BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
    DataOutputStream dout=new DataOutputStream(s.getOutputStream());


    dout.write(HELO.getBytes());
    dout.flush();


    String str=(String)dis.readLine();
    System.out.println(str);

    dout.write(AUTH.getBytes());
    dout.flush();


    str=dis.readLine();
    System.out.println(str);
    dout.write(REDY.getBytes());
    dout.flush();


    str=dis.readLine();
    System.out.println(str);

    String[] job = str.split(" ", 10);
    dout.write(GETSALL.getBytes());
    dout.flush();


    str = dis.readLine();
    System.out.println(str);

    int nRecs  = Integer.parseInt(String.valueOf(str.charAt(5)));
    System.out.println(nRecs);

    dout.write(OK.getBytes());
    dout.flush();

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

    dout.write(OK.getBytes());
    dout.flush();

    dout.write(("SCHD " + job[2] + " " + large[0] + " " + large[1] + "\n").getBytes());
    dout.flush();

    dout.write(OK.getBytes());
    dout.flush();

    dout.write(QUIT.getBytes());
    dout.flush();
    dout.close();
    s.close();
}catch(Exception e)
    {System.out.println(e);
}
}
}