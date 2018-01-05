


import java.awt.Component;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

import javax.swing.JLabel;

import com.example.himanshu.myapplication.Preamble;


public class FIleServer {

	 static String path="C:\\Users\\Himanshu\\DTNMessages\\";
	/*
    public static void main(String[] args) throws IOException {
        
    	
        long start = System.currentTimeMillis();
        int bytesRead;
        int current = 0;
        FIleServer f=new FIleServer();
        
        // create socket
        ServerSocket servsock = new ServerSocket(1149);
        while (true) {
          System.out.println("Waiting...");

          Socket sock = servsock.accept();
          InputStream is = sock.getInputStream();
          DataInputStream dis=new DataInputStream(sock.getInputStream());
          System.out.println("Accepted connection : " + sock);

        	  byte[] noOfMessagesByte=new byte[4];
        	  
        	  readByteArray(is,noOfMessagesByte);
              
              ByteBuffer noOfMessagesByteBuffer=ByteBuffer.wrap(noOfMessagesByte);
              int noOfMessages=noOfMessagesByteBuffer.getInt();
              System.out.println("Value of number of messages received FileServer:"+noOfMessages);
            //get preamble Strings
              for(int i=0;i<noOfMessages;i++)
              {
            	  
            	  //Read preamble size
                  byte[] preambleSizeBytes=new byte[4];
                  readByteArray(is,preambleSizeBytes);
                  ByteBuffer PreambleByteByffer=ByteBuffer.wrap(preambleSizeBytes);
                  int preambleSize=PreambleByteByffer.getInt();
                  
            	  
                  //Read preamble
                  byte[] PreambleByteArray=new byte[preambleSize];
            	  readByteArray(is,PreambleByteArray);
            	  Preamble preamble=bytesToPreamble(PreambleByteArray);
                  System.out.println("Size and name of the message are:"+preamble.dataSize+" and "+preamble.dataFileName);
            	 

                  //Create separate folder
                  new File(path+preamble.dataFileName.split("\\.")[0]).mkdir();
                  String newPath=path+preamble.dataFileName.split("\\.")[0]+"\\";
                  
                  ///Read file
            	  System.out.println("Reading bytes for the image");
                  byte messageByteArray[]=new byte[preamble.dataSize];
            	  readByteArray(is,messageByteArray);
            	  FileOutputStream fos = new FileOutputStream(newPath+preamble.dataFileName); // destination path and name of file
                  BufferedOutputStream bos = new BufferedOutputStream(fos);
                  bos.write(messageByteArray, 0 , messageByteArray.length);
                  bos.flush();  fos.flush();
                  bos.close(); fos.close();
                  
                  
                  //Read size of the metadata string
                  byte[] metadataSizeBytes=new byte[4];
                  readByteArray(is,metadataSizeBytes);
                  ByteBuffer MetadataByteByffer=ByteBuffer.wrap(metadataSizeBytes);
                  int metadataSize=MetadataByteByffer.getInt();
                  System.out.println("Metadata size:"+metadataSize);
                  
                  //Read the metadata string
                  byte[] MetadataByteArray=new byte[metadataSize];
                  readByteArray(is,MetadataByteArray);
                  String Metadata=MetadataByteArray.toString();
                  System.out.println(Metadata);
                  //Save it into a file
                  FileOutputStream fos1 = new FileOutputStream(newPath+preamble.dataFileName.split("\\.")[0]+"-metadata.txt"); // destination path and name of file
                  BufferedOutputStream bos1 = new BufferedOutputStream(fos1);
                  bos1.write(MetadataByteArray, 0 , MetadataByteArray.length);
                  bos1.flush(); fos1.flush(); bos1.close();fos1.close();
                      
              }
              
             
              
              sock.close();
          
          
       }
    }
  */
    public void start(String path1,JLabel jl) throws IOException
    {
    	path=path1;
    	long start = System.currentTimeMillis();
        int bytesRead;
        int current = 0;
        FIleServer f=new FIleServer();
        
        // create socket
        ServerSocket servsock = new ServerSocket(1149);
        int countLoop=0;
        int noOfFilesReceived=0;
        while (true) {
        	
          System.out.println("Waiting...");
          if(countLoop==0) 
          jl.setText("File server started!Waiting for a connection..");
          else
          {
        	  jl.setText(noOfFilesReceived+" new files received , waiting for more connections!");
          }
          noOfFilesReceived=0;
          Socket sock = servsock.accept();
          InputStream is = sock.getInputStream();
          DataInputStream dis=new DataInputStream(sock.getInputStream());
          String remoteAddr=sock.toString().split("\\/")[1].split("\\,")[0];
          System.out.println("Accepted connection : " + sock);
          jl.setText("Accepted connection from "+remoteAddr);
          
          
          JLabel jl1=(JLabel)(ServerGUI.window.frame.getContentPane().getComponent(5));
          jl1.setText("Connections from:"+remoteAddr);
         //Component[] cs=ServerGUI.window.frame.getComponents();
          Component cs[]=ServerGUI.window.frame.getContentPane().getComponents();
          for(int i=0;i<cs.length;i++)
          {
          Class a=cs[i].getClass();
          System.out.println("Component"+a.getName());
          }
        	  byte[] noOfMessagesByte=new byte[4];
        	  
        	  readByteArray(is,noOfMessagesByte);
        	  
              ByteBuffer noOfMessagesByteBuffer=ByteBuffer.wrap(noOfMessagesByte);
              int noOfMessages=noOfMessagesByteBuffer.getInt();
              System.out.println("Value of number of messages received:"+noOfMessages);
              jl.setText(remoteAddr+" has "+noOfMessages+" messages");
            //get preamble Strings
              for(int i=0;i<noOfMessages;i++)
              {
            	  
            	  //Read preamble size
                  byte[] preambleSizeBytes=new byte[4];
                  readByteArray(is,preambleSizeBytes);
                  ByteBuffer PreambleByteByffer=ByteBuffer.wrap(preambleSizeBytes);
                  int preambleSize=PreambleByteByffer.getInt();
                  
            	  
                  //Read preamble
                  byte[] PreambleByteArray=new byte[preambleSize];
            	  readByteArray(is,PreambleByteArray);
            	  Preamble preamble=bytesToPreamble(PreambleByteArray);
                  //System.out.println("Size and name of the message are:"+preamble.dataSize+" and "+preamble.dataFileName);
            	 
            	  
            	  //Check if the folder with the name UUID already exists in the folder-- send 1 if file does not exist and required
            	  //Else send 1
            	  
            	  boolean ifExists=new File(path+preamble.UUID.split("\\.")[0]).exists();
            	  
            	  int flag=(ifExists==true?0:1);
            	  
            	  //Send flag to android
            	  byte flagBytes[] = ByteBuffer.allocate(4).putInt(flag).array();
            	  writeByteArray(flagBytes,sock);
            	  
            	  if(flag==1) {
            		  
            		  
            	//Create separate folder
                  new File(path+preamble.UUID.split("\\.")[0]).mkdir();
                  String newPath=path+preamble.UUID.split("\\.")[0]+"\\";
                  
                  ///Read file
            	  System.out.println("Reading bytes for the image");
                  byte messageByteArray[]=new byte[preamble.dataSize];
            	  readByteArray(is,messageByteArray);
            	  FileOutputStream fos = new FileOutputStream(newPath+preamble.dataFileName); // destination path and name of file
                  BufferedOutputStream bos = new BufferedOutputStream(fos);
                  bos.write(messageByteArray, 0 , messageByteArray.length);
                  bos.flush();  fos.flush();
                  bos.close(); fos.close();
                  
                  
                  //Read size of the metadata string
                  byte[] metadataSizeBytes=new byte[4];
                  readByteArray(is,metadataSizeBytes);
                  ByteBuffer MetadataByteByffer=ByteBuffer.wrap(metadataSizeBytes);
                  int metadataSize=MetadataByteByffer.getInt();
                  System.out.println("Metadata size:"+metadataSize);
                  
                  //Read the metadata string
                  byte[] MetadataByteArray=new byte[metadataSize];
                  readByteArray(is,MetadataByteArray);
                  String Metadata=MetadataByteArray.toString();
                  System.out.println(Metadata);
                  //Save it into a file
                  FileOutputStream fos1 = new FileOutputStream(newPath+preamble.dataFileName.split("\\.")[0]+"-metadata.txt"); // destination path and name of file
                  BufferedOutputStream bos1 = new BufferedOutputStream(fos1);
                  bos1.write(MetadataByteArray, 0 , MetadataByteArray.length);
                  bos1.flush(); fos1.flush(); bos1.close();fos1.close();
                  noOfFilesReceived++;
              }
              }
              
            
             
              
              sock.close();
              jl1.setText("Connections:");
              countLoop++;
          
       }
    }
    public static void readByteArray(InputStream is,byte byteArray[])
    {
    	int bytesRead;
        int current = 0;
        
        try{
    	bytesRead = is.read(byteArray,0,byteArray.length);
        current= bytesRead;
        
       
        while(current!=byteArray.length) {
            bytesRead =is.read(byteArray, current, (byteArray.length-current));
            if(bytesRead >= 0) current += bytesRead;
         } 
        
        }
        catch(Exception e)
        {
        	System.out.println("Exception:"+e);
        }
       // System.out.println("Done reading "+byteArray.length+" bytes for the image" );
    }
    
    public static void readByteArray(InputStream is,byte byteArray[],int flag)
    {
    	
    	int bytesRead;
        int current = 0;
        try{
    	bytesRead = is.read(byteArray,0,byteArray.length);
        current= bytesRead;
        System.out.println("Current:"+current);
       
        while(current!=byteArray.length) {
            bytesRead =is.read(byteArray, current, (byteArray.length-current));
            if(bytesRead >= 0) 
            {
            current += bytesRead;
            System.out.println("Current:"+current);}
            else 
            {	
            	System.out.println("Nothing more to read");
            	break;
            }
         } 
        
        }
        catch(Exception e)
        {
        	System.out.println("Exception:"+e);
        }
        
    }

    public static void readByteArrayImage(InputStream is,byte byteArray[])
    {
    	
    	int current1=0;
        
        int noOfBytes=8192;
        int bytesRead1=0;
        byte[] tempBytes;
        while (current1 != byteArray.length) 
        {
        
         if((byteArray.length-current1<noOfBytes))
          noOfBytes=byteArray.length-current1;
          tempBytes=new byte[noOfBytes];
          try{
          bytesRead1=is.read(tempBytes);
          }
          catch(Exception e)
          {
        	  System.out.println("Exception "+e);
          }
          //System.out.println("Before copying, value of current,messagesize and bytes read is:"+current1+","+byteArray.length+" and "+bytesRead1);
          if(bytesRead1!=-1)
          {
          System.arraycopy(tempBytes,0,byteArray,current1,bytesRead1);
          current1+= bytesRead1;
          }
          }
        
        //System.out.println("Done reading all "+byteArray.length+" bytes for the message");
        try{
        if(is.available()!=0)
        {
        	//System.out.println("The stream still has "+is.available()+" number of bytes left to be read 	");
        }
        }
        catch(Exception e)
        {
        	System.out.println("Exception:"+e);
        }
    }
    
    public static Preamble bytesToPreamble(byte preambleBytes[])
    {
    	Preamble preamble=new Preamble();
    	try{
    		preamble=(Preamble)deserialize(preambleBytes);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception:"+e);
    		return new Preamble();
    	}
    		return preamble;
    }
    
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }
    public static void writeByteArray(byte[] bytes,Socket sock)
    {
        try {
            
            OutputStream os = sock.getOutputStream();
            System.out.println("Sending...");
            os.write(bytes, 0, bytes.length);
            os.flush();
        }
        catch(Exception e)
        {
            System.out.println("exception:"+e);
        }
    }
    
}





