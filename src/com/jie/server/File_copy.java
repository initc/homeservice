package com.jie.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Scanner;

public class File_copy {

	/*public static void main(String[] args) {

		System.out.println(encodingMd5("123456"));
		

	}*/
	
	public static String encodingMd5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  

	
public void   fileCopy(){
	Scanner scan = new Scanner(System.in);
	System.out.println("请输入要进行传输的文件路径");
	String path = scan.next();
	int i = path.lastIndexOf("\\");
	String desFile = path.substring(i + 1, path.length());
	BufferedReader read=null;
	BufferedWriter write=null;
	try {
		 read = new BufferedReader(new FileReader(path));
		 write = new BufferedWriter(new FileWriter("D:\\"
				+ desFile));
        String ins =null;
        while ((ins=read.readLine())!=null){
        	write.write(ins+"\r\n");
        	
        }
        write.flush();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}
}
