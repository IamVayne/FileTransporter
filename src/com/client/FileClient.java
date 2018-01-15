package com.client;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.Socket;

public class FileClient
{
	// 建立连接
	// 获取输入流
	// 首先传输的meta数据：文件名称和文件大小。
	//
	// 开始传输
	//
	public static void main(String[] args)
	{
		new FileClient().start();
		
	}
	public void start()
	{
		String ip = "127.0.0.1";
		try
		{
			Socket socket = new Socket(ip, 6464);
			receiveFile(socket);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("传输失败！");
		}
	}

	public static void receiveFile(Socket socket)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String fileName = reader.readLine();
			System.out.println("服务器端传送过来的文件的原始文件名： "+fileName);
//			File file = new File(fileName);
//			if (file.exists())
//			{
//				long length = file.length();
//				System.out.println("原有文件大小： " + length);
//				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//				printWriter.write(String.valueOf(length));
//				printWriter.flush();
//			} else
//			{
//				long length = 0L;
//				System.out.println("没有源文件");
//				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//				printWriter.write(String.valueOf(length));
//				printWriter.flush();
//			}
			// 协商完毕，开始传输数据
			BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream(), 2);
			File file2 = new File("d://1.txt");
			RandomAccessFile randomAccessFile = new RandomAccessFile(file2, "rw");
			randomAccessFile.seek(0);
			byte[] bs = new byte[2];
			int i = 0;
			while ((i = bufferedInputStream.read(bs, 0, bs.length)) != -1)
			{
				randomAccessFile.write(bs, 0, i);
			}
			System.out.println("传输成功！");
			randomAccessFile.close();
			socket.close();
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}finally {
		
		}

	}

}
