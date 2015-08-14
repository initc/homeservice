package com.jie.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LognServer implements Runnable{

	private  Socket socket =null ;
	private  BufferedReader   read = null ;
	public LognServer(Socket socket,BufferedReader   read) {
		
		this.socket=socket;
		this.read=read;
		
	}
	@Override
	public void run() {
	
		try {
			int result=1;
		    
			String name = read.readLine();
			String password = read.readLine();
			System.out.println("------------添加用户中---------------");
			System.out.println("------------name : "+name +"  passsword "+password);
			if (name==null||password == null)
				return ;
			SqlAccess sql = SqlAccess.getDefault();
			sql.connectDB("qsj", "QQQqqq");
			ResultSet  setName=sql.exeSqlQuery("select * from homeUser where username='"+name+"'");
			
			if(setName==null){
				result =-1;
			}else {
				if (setName.next())//如果查询的数据多于一行就认为有重复的用户名 
					result = 0 ;
				else {
					//否则进行插入
					result =sql.exeInsert("insert into homeUser values ('"+name+"','"+password+"')");
					
				}
			}
			//result =sql.exeInsert("insert into homeUser values ('"+name+"','"+password+"')");
			if (result ==1){
				
				System.out.println("------------添加用户成功---------------");
				
			}else {
				
				System.out.println("------------添加用户失败---------------");
			}
			sql.close();
			BufferedWriter write =  new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			write.write(result+"\r\n");//为1是成功  0 为失败  -1 为连接数据库失败
			write.flush();
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
