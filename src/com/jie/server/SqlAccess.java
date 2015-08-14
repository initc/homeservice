package com.jie.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlAccess {
	Statement stmt;
	private Connection  con;
	private String  connectDB;
	private String username=null;
	private String password=null;
	private static SqlAccess sql;
	public static  SqlAccess getDefault(){
		if (sql==null)
			sql= new SqlAccess();
		return sql;
	}
	public   boolean  connectDB(){
		return connectDB(username, password);
	}
	public   boolean  connectDB(String username,String password){
		/*String user="qsj";//你自己创建的用户名字和密码！！！！！！！！！！！！
		String password="QQQqqq"; */
	  try {
		  this.username=username;
		  this.password=password;
		  if(con==null||con.isClosed())
		  con=DriverManager.getConnection(connectDB,username,password);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("连接数据库失败");
		return false;
	}//连接数据库对象
	  System.out.println("连接数据库成功");  
	  return true;
		
	}
	public boolean close (){			 
			try {
				if(!stmt.isClosed())
				stmt.close();//关闭命令对象连接
				if(!con.isClosed())
				con.close();//关闭数据库连接 }
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		    
	}
	public int exeDelete(String exesql){
		
	return	exeSqlUpdate( exesql);
		
		
	}
	public int exeInsert(String exesql){
		
		return exeSqlUpdate(exesql);
		
	}
	public int exeSqlUpdate(String exesql){		
			try {
				stmt=con.createStatement();
				return stmt.executeUpdate(exesql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
	}
	public ResultSet exeSqlQuery(String exesql){
		 
		try {
			stmt = con.createStatement();
			  System.out.println("---------查询");  
			  System.out.println("---------开始读取数据"); 
			 ResultSet rs=stmt.executeQuery(exesql);//返回SQL语句查询结果集(集合) //循环输出每一条记录 
		    return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("----------执行sql错误");
			return null;
		}//创建SQL命令对象 //创建表 
	}
	private SqlAccess(){
		String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL数据库引擎 
	    connectDB="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=home_data";//数据源注意IP地址和端口号，数据库名字！！！ 
		try {  
			Class.forName(JDriver);//加载数据库引擎，返回给定字符串名的类 
		}catch(ClassNotFoundException e) {  //
			e.printStackTrace();  
			System.out.println("加载数据库引擎失败");
			System.exit(0); 
			}   
		 
	}
	/*public static void pssmain(String[] args)  
	{  
		SqlAccess sql = SqlAccess.getDefault();
		if(sql.connectDB("qsj","QQQqqq"))
			sql.exeSqlQuery("SELECT * FROM course where credit=4 ");
}*/
}

