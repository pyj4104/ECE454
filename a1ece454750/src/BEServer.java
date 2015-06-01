//package ece454750s15a1;
import ece454750s15a1.*;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

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
			TNonblockingServerTransport trans;
			Args args;
			TServer server;
		
			trans = new TNonblockingServerSocket(portNumber);
			args = new TThreadedSelectorServer.Args(trans);
			args.transportFactory(new TFramedTransport.Factory());
			args.protocolFactory(new TBinaryProtocol.Factory());
			args.processor(proc);
			args.selectorThreads(4);
			args.workerThreads(32);
			server = new TThreadedSelectorServer(args);

			System.out.println("Starting the server...");
			server.serve();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}
}

