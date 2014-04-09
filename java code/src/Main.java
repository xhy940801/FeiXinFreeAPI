import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import com.xiao.Feixin.FreeAPI.Connection.InitServer;
import com.xiao.Socket.WebClient.WebCilentException;


public class Main
{

	public static void main(String[] args) throws UnknownHostException, WebCilentException
	{
		InitServer initServer = new InitServer();
		initServer.init();
		Thread thread = new Thread(initServer);
		thread.start();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			String commend = null;
			int errCount = 0;
			try
			{
				commend = br.readLine();
			}
			catch (IOException e)
			{
				++errCount;
				e.printStackTrace();
				if(errCount > 5)
					System.exit(-1);
			}
			
			if(commend != null && commend.equals("stop"))
			{
				initServer.stop();
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				System.exit(0);
			}
		}
	}

}
