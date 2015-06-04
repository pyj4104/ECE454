package ece454750s15a1;
import java.util.concurrent.atomic.*;
import java.util.*;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TSocket;

public class ServerCommon
{
	protected static A1Password.Processor pproc;
	protected static A1Management.Processor mproc;
	protected static int pportNumber;
	protected static int mportNumber;
	protected static int numCore;
	protected static String hostString;
	protected static ArrayList<String> seedHosts;
	protected static ArrayList<Integer> seedPorts;
	protected static AtomicInteger numReqRec;
	protected static AtomicInteger numReqCom;

	public ServerCommon(String[] args)
	{
		numReqRec = new AtomicInteger();
		numReqCom = new AtomicInteger();

		ParseArg(args);
	}
	
	public void psimple(A1Password.Processor proc)
	{
		try
		{
			TNonblockingServerTransport trans;
			TServer server;
		
			trans = new TNonblockingServerSocket(pportNumber);

			server = new TThreadedSelectorServer(ArgGen(trans).processor(proc));

			System.out.println("Starting the password server...");
			server.serve();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}
	
	public void msimple(A1Management.Processor proc)
	{
		try
		{
			TNonblockingServerTransport trans;
			TServer server;
		
			trans = new TNonblockingServerSocket(mportNumber);

			server = new TThreadedSelectorServer(ArgGen(trans).processor(proc));

			System.out.println("Starting the management server...");
			server.serve();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}

	private TThreadedSelectorServer.Args ArgGen(TNonblockingServerTransport trans)
	{
		Args args;

		args = new TThreadedSelectorServer.Args(trans);
		args.transportFactory(new TFramedTransport.Factory());
		args.protocolFactory(new TBinaryProtocol.Factory());
		args.selectorThreads(numCore);
		args.workerThreads(numCore * 8);

		return args;
	}

	private static void ParseArg(String[] args)
	{
		for(int i = 0; i < (args.length ); i++)
		{
			System.out.println("Argument " + i + " is " + args[i]);
			if(args[i].equals("-host"))
			{
				hostString = args[i+1];
			}
			else if(args[i].equals("-pport"))
			{
				pportNumber = Integer.parseInt(args[i+1]);
			}
			else if(args[i].equals("-mport"))
			{
				mportNumber = Integer.parseInt(args[i+1]);
			}
			else if(args[i].equals("-ncores"))
			{
				numCore = Integer.parseInt(args[i+1]);
			}
			else if(args[i].equals("-seeds"))
			{
				String[] hostAndPort = args[i+1].split(",");
				seedHosts = new ArrayList<String>();
				seedPorts = new ArrayList<Integer>();
				for(String s : hostAndPort)
				{
					seedHosts.add(s.split(":")[0]);
					seedPorts.add(Integer.parseInt(s.split(":")[1]));
				}
			}
		}
	}

	protected static boolean Report(boolean isBE) throws InterruptedException
	{
		JoinProtocol selfInfo;
		boolean isSuccess;

		isSuccess = false;

		selfInfo = new JoinProtocol();

		selfInfo.host = hostString;
		selfInfo.pportNum = pportNumber;
		selfInfo.mportNum = mportNumber;
		selfInfo.isBackEnd = isBE;
		selfInfo.numCore = numCore;

		if (seedHosts.isEmpty())
		{
			System.out.println("No seed is given. The program will exit");
			System.exit(0);
		}

		for(int i = 0; (i < 60 / seedHosts.size()) || isSuccess; i++)
		{
			isSuccess = isSuccess || TryConnection(selfInfo, i % seedHosts.size());
			if(!isSuccess)
			{
				System.out.println("Failed to connect. Attempting the report procedure again within 1 second.");
				System.out.println("3");
				Thread.sleep(1000);
				System.out.println("2");
				Thread.sleep(1000);
				System.out.println("1");
				Thread.sleep(1000);
				System.out.println("Reporting......");
			}
		}

		return isSuccess;
	}

	private static boolean TryConnection(JoinProtocol selfInfo, int index)
	{
		TTransport transport;
		TProtocol protocol;
		A1Management.Client client;
		boolean isSuccess;

		try
		{
			transport = new TFramedTransport(new TSocket(seedHosts.get(index), seedPorts.get(index)));
			transport.open();
			protocol = new TBinaryProtocol(transport);
			client = new A1Management.Client(protocol);

			isSuccess = client.join(selfInfo);
		
			transport.close();

			return isSuccess;
		}
		catch(TTransportException refused)
		{
		}
		catch(Exception X)
		{
			X.printStackTrace();
			System.exit(0);
		}
		
		return false;
	}
}

