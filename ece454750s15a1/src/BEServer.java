import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

import ece454750s15a1.*;

public class BEServer
{
	public static BEPasswordHandler handler;
	public static A1Password.Processor proc;

	public static void main(String[] args)
	{
		try
		{
			handler = new BEPasswordHandler();
			proc = new A1Password.Processor(handler);

			Runnable simple = new Runnable()
			{
				public void run()
				{
					simple(proc);
				}
			};
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
			TServerTransport serverTransport = new TServerSocket(24264);
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

