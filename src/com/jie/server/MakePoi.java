package com.jie.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MakePoi implements Runnable {

	
	private  Socket socket =null ;
	private  BufferedReader   read = null ;
	public MakePoi(Socket socket,BufferedReader   read) {
		
		this.socket=socket;
		this.read=read;
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			int result=1;
			String srcName = read.readLine();
			String PoiName = read.readLine();
			System.out.println("------------添加用户中---------------");
			System.out.println("------------name : "+srcName +"  passsword "+PoiName);
			if (srcName==null||PoiName == null)
				return ;
			SqlAccess sql = SqlAccess.getDefault();
			sql.connectDB("qsj", "QQQqqq");
			ResultSet  setName=sql.exeSqlQuery("select * from postion where userName='"+PoiName+"'");
			String   time=null;
			int x=0;
			int y=0;
			int Lresult =-1;
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(setName.next()){
			  time =sim.format(setName.getDate(2));
				 x = setName.getInt(3);
				 y=setName.getInt(4);
				Lresult = 1;
			}
			
			sql.close();
			
			BufferedWriter write =  new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			if(Lresult==-1){
			write.write(Lresult+"\r\n");
			
			write.flush();
			write.close();
			}else {
				
				write.write(Lresult+"\r\n");
				write.write(time+"\r\n");
				write.write(x+"\r\n");
				write.write(y+"\r\n");
				write.flush();
				write.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try {
				if(read!=null)
				read.close();
				if (!socket.isClosed())
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
	}

}
