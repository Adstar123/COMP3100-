import java.io.*;
import java.net.*;
public class MyClient {

  // the code below defines my constants. This makes the code look cleaner and it removes some errors I was getting in relation to \n 
  public static String AUTH = "AUTH " + System.getProperty("Adam") + "\n";
  public static String REDY = "REDY\n";
  public static String OK = "OK\n";
  public static String HELO = "HELO\n";
  public static String GETSALL ="GETS All\n";
  public static String QUIT = "QUIT\n";

public static void main(String[] args) {
try {
    // creates a socket connection to the server side simulator at 'localhost' and port 50000
    Socket s=new Socket("localhost",50000);
    // creates a bufferedreader to read data from the server side simulator
    BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
    // Create a DataOutputStream to write data to the server-side simulator
    DataOutputStream dout=new DataOutputStream(s.getOutputStream());

    // Send HELO command and read server response and print it
    dout.write(HELO.getBytes());
    dout.flush();
    String str=(String)dis.readLine();
    System.out.println(str);

   // send AUTH command and read server response and print it
    dout.write(AUTH.getBytes());
    dout.flush();
    str=dis.readLine();
    System.out.println(str);

    // send REDY command and read server response and print it
    dout.write(REDY.getBytes());
    dout.flush();
    str=dis.readLine();
    System.out.println(str);

    // Split the response into a job array 
    String[] job = str.split(" ", 10);

    // Send GETSALL command and read server response and print it
    dout.write(GETSALL.getBytes());
    dout.flush();
    str = dis.readLine();
    System.out.println(str);

    // extract the number of records (nrecs) from the server response
    int nRecs  = Integer.parseInt(String.valueOf(str.charAt(5)));
    System.out.println(nRecs);

    // send the OK command
    dout.write(OK.getBytes());
    dout.flush();

    // Initialise J and large array. 
    int j = 0;
    String[] large = new String[10];
  
   // Loop through nRecs, reading the respoonse and printing it.
    for(int i = 0; i < nRecs; i++){
        str = dis.readLine();
        System.out.println(str);

        // Split the response into an array, extracting the number of CPU cores (corenum) from arr[4]
        String[] arr = str.split(" ", 9);
        int coreNum = Integer.parseInt(String.valueOf(arr[4]));

        // if j is smaller than coreNum, update j and large array with the current server informatiuon
        if(j < coreNum){
            j = coreNum;
            large = arr;
        }
    }

    // send OK command
    dout.write(OK.getBytes());
    dout.flush();

    // schedule the job by sending a SCHD command with the job information and the largest tye found.
    dout.write(("SCHD " + job[2] + " " + large[0] + " " + large[1] + "\n").getBytes());
    dout.flush();

    // send OK command
    dout.write(OK.getBytes());
    dout.flush();

    // send Quit command and close the dataoutput stream and the socket connection
    dout.write(QUIT.getBytes());
    dout.flush();
    dout.close();
    s.close();
}
catch(Exception e){
    System.out.println(e);
}
}
}

// The code overall schedules 1 job with a server and the server performs the job. It does not perfrom the Largest Round Robin algorithim unforuantely.