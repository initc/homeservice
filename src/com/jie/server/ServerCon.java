package com.jie.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerCon {

	private static String parseIn = "LognIn";

	public static void main(String[] args) {
		try {
			int count = 0 ;
			ServerSocket server = new ServerSocket(8900);
			System.out.println("------server is on running ");
			
		
			while (true) {
				
				Socket socket = server.accept();
				System.out.println("------get "+(++count) +" socket---");
				BufferedReader read = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String str = read.readLine();
				switch (str) {
				case "service":new Thread(new PoiService(socket, read)).start();
				break;
				case "LognIn":
					new Thread(new LognServer(socket, read)).start();
					break;
				case "BindIn":new Thread(new BindServer(socket, read)).start();
					break;
				case "GetPoi": new Thread(new MakePoi(socket, read)).start();break;
				default:
					System.out.println("nothing to thread");
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
