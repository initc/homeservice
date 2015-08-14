package com.jie.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PoiService implements Runnable{
	private  Socket socket =null ;
	private  BufferedReader   read = null ;
	public PoiService(Socket socket,BufferedReader   read) {
		
		this.socket=socket;
		this.read=read;
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
		int result=1;
		
		String PoiName = read.readLine();
		String x= read.readLine();
		String y = read.readLine();
		if(x==null||y==null)
			return ;
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ); 
		String time = sdf.format(new Date());
		System.out.println(time);
		System.out.println("---------------------------");
		if (PoiName == null)
			return ;
		SqlAccess sql = SqlAccess.getDefault();
		sql.connectDB("qsj", "QQQqqq");
		ResultSet  re =sql.exeSqlQuery("select * from postion where userName='"+PoiName+"'");
		if(!re.next()){
			
			sql.exeInsert("insert into postion (userName)values ('"+PoiName+"')");
		}
		sql.exeSqlUpdate("update  postion set poiTime='"+time+"'"
				+ ",positionX='"+x+"',positionY='"+y+"' where userName='"+PoiName+"'");
		sql.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("在创建postion数据时出现错误");
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
