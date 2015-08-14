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
		/*String user="qsj";//���Լ��������û����ֺ����룡����������������������
		String password="QQQqqq"; */
	  try {
		  this.username=username;
		  this.password=password;
		  if(con==null||con.isClosed())
		  con=DriverManager.getConnection(connectDB,username,password);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("�������ݿ�ʧ��");
		return false;
	}//�������ݿ����
	  System.out.println("�������ݿ�ɹ�");  
	  return true;
		
	}
	public boolean close (){			 
			try {
				if(!stmt.isClosed())
				stmt.close();//�ر������������
				if(!con.isClosed())
				con.close();//�ر����ݿ����� }
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
			  System.out.println("---------��ѯ");  
			  System.out.println("---------��ʼ��ȡ����"); 
			 ResultSet rs=stmt.executeQuery(exesql);//����SQL����ѯ�����(����) //ѭ�����ÿһ����¼ 
		    return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("----------ִ��sql����");
			return null;
		}//����SQL������� //������ 
	}
	private SqlAccess(){
		String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL���ݿ����� 
	    connectDB="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=home_data";//����Դע��IP��ַ�Ͷ˿ںţ����ݿ����֣����� 
		try {  
			Class.forName(JDriver);//�������ݿ����棬���ظ����ַ��������� 
		}catch(ClassNotFoundException e) {  //
			e.printStackTrace();  
			System.out.println("�������ݿ�����ʧ��");
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

