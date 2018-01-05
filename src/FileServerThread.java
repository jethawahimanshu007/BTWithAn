


import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.example.himanshu.myapplication.Preamble;


public class FileServerThread extends Thread{

	 
	public Thread t;
	String path;
	JLabel jt;
	FileServerThread(String path,JLabel jt)
	{
		this.path=path;
		this.jt=jt;
		
	}
    public void run()
    {
    	
    	try{
    	jt.setText("Started File server");
    	new FIleServer().start(path,jt);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception:"+e);
    	}
    }
    
    public void start() 
    {
    	try{
    	Thread.sleep(50);
    	t=new Thread(this,"Thread 1");
    	//System.out.println("New thread started");
    	t.start();
    	}catch(Exception e)
    	{
    		System.out.println("Exception occured");
    	}
    	
    }
    
    
}






