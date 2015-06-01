//package ece454750s15a1;
import ece454750s15a1.*;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

public class BEServer
{
	public static BEPasswordHandler handler;
	public static A1Password.Processor proc;
	public static int portNumber;

	public static void main(String[] args)
	{
		try
		{
			System.out.println("BE starting");
			for(int i = 0; i < args.length; i++){
				System.out.println("Argument " + i + " is " + args[i]);
			}
			if(args.length == 10){
				portNumber = Integer.parseInt(args[3]);
				String[] hostAndPort = args[9].split(",");
				for(String s : hostAndPort){
					String host = s.split(":")[0];
					String port = s.split(":")[1];
					System.out.println("host: " + host + "  port: " + port);
				}
			}
			handler = new BEPasswordHandler();
			proc = new A1Password.Processor(handler);

			Runnable simple = new Runnable()
			{
				public void run()
				{
					simple(proc);
				}
			};

			new Thread(simple).start();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}
	
	public static void simple(A1Password.Processor proc)
	{
		try
		{
			TServerTransport serverTransport = new TServerSocket(portNumber);
			TServer server = new TSimpleServer(new Args(serverTransport).processor(proc));

			System.out.println("Starting the server...");
			server.serve();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}
}

