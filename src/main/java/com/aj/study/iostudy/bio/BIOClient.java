package com.aj.study.iostudy.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class BIOClient {

	public static void main(String[] args) throws IOException {
		//生成一个随机的ID
		String message = UUID.randomUUID().toString();
		sendSms(message);
	}

	private static void sendSms(String message) throws IOException {
		//要和谁进行通信，服务器IP、服务器的端口
		//一台机器的端口号是有限
		try (Socket client = new Socket("localhost", 8080);
			 //不管是客户端还是服务端，都有可能write和read
			 OutputStream os = client.getOutputStream()) {
			System.out.println("客户端发送数据：" + message);
			//传说中的101011010
			os.write(message.getBytes());
		}
	}

}
