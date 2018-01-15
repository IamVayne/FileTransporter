package com.server;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer
{
	public static void main(String[] args)
	{
		new FileServer().start();
		
	}
	public void start()
	{
		try
		{
			ServerSocket socket = new ServerSocket(6464);
			trans(socket);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void trans(ServerSocket socket)
	{
		File file = new File("d://2.txt");
		String name = file.getAbsolutePath();
		int length = 0;
		long sum=0;
		try
		{
			Socket socket2 = socket.accept();
			PrintWriter printWriter = new PrintWriter(socket2.getOutputStream());
			printWriter.write(name+"\n");
			printWriter.flush();
			BufferedOutputStream outputStream = new BufferedOutputStream(socket2.getOutputStream());
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
			byte[] bs = new byte[2];
			while((length=randomAccessFile.read(bs, 0, bs.length))!=-1)
			{
//				sum+=length;
				
				outputStream.write(bs,0,length);
				outputStream.flush();
				sum++;
				if (sum%64==0)
				{
					System.out.print("#");
				}
			}
			System.err.println("传输成功！");
			randomAccessFile.close();
			socket.close();
			socket2.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
		}
	}

}
