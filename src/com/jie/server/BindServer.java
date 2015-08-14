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

public class BindServer implements Runnable {

	private Socket socket = null;
	private BufferedReader read = null;

	public BindServer(Socket socket, BufferedReader read) {

		this.socket = socket;
		this.read = read;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			int result = 1;
			String srcName = read.readLine();
			String dstName = read.readLine();
			System.out.println("------------���û���---------------");
			System.out.println("----------srcName : " + srcName + "  detName "
					+ dstName);
			if (srcName == null || dstName == null)
				return;
			SqlAccess sql = SqlAccess.getDefault();
			sql.connectDB("qsj", "QQQqqq");
			ResultSet isSave = sql            //�Д������Ƿ��ѽ��]����
					.exeSqlQuery("select * from homeUser where userName='"
							+dstName+"'");
			if (isSave==null || ! isSave.next())// �����ѯ�����ݶ���һ�о���Ϊ���ظ��İ�
			{
				result =2;
				isSave.close();
				
				
			}else {
			ResultSet setName = sql
					.exeSqlQuery("select * from userBind where srcName='"
							+srcName + "'and dstName='"+dstName+"'");
			if (setName == null) {

				result = -1;
			} else {
				if (setName.next())  // �����ѯ�����ݶ���һ�о���Ϊ���ظ��İ�
				{
					result = 0;
					setName.close();
				}
				else {
					// ������в���
					SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
					String bindTime = format.format(new Date());
					System.out.println("------ʱ��------"+bindTime +"---------------");
					result = sql.exeInsert("insert into userBind (srcName ,dstName,bindTime) values ('"
							+ srcName + "','" + dstName + "','"+bindTime+"')");
				}
			}
			
			
			}
			// result
			// =sql.exeInsert("insert into homeUser values ('"+name+"','"+password+"')");
			if (result == 1) {

				System.out.println("------------���û��ɹ�---------------");

			} else {

				System.out.println("------------���û�ʧ��---------------");
			}
			sql.close();
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			write.write(result + "\r\n");// Ϊ1�ǳɹ� 0 Ϊʧ�� -1 Ϊ�������ݿ�ʧ��
			write.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (read != null)
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
