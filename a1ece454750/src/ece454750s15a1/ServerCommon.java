package ece454750s15a1;
import java.util.*;
import java.util.concurrent.atomic.*;
import javax.naming.ServiceUnavailableException;
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
				System.out.print("Reporting to " + seedHosts.get(i % seedHosts.size()) + ":");
				System.out.println(seedPorts.get(i % seedHosts.size()) +
					" Failed. Attempting the report procedure again within 1 second.");
				System.out.println("3");
				Thread.sleep(1000);
				System.out.println("2");
				Thread.sleep(1000);
				System.out.println("1");
				Thread.sleep(1000);
				System.out.println("Reporting......");
			}
		}

		if(isSuccess)
		{
			System.out.println("Successfully connected to a FE seed node.");
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

	protected FEJoinResponse FEReport() throws InterruptedException
	{
		JoinProtocol selfInfo;
		FEJoinResponse initInfo;
		boolean isSeed;
		boolean reported;
		String seedKey;
		String myKey;
		
		selfInfo = new JoinProtocol();
		selfInfo.host = hostString;
		selfInfo.pportNum = pportNumber;
		selfInfo.mportNum = mportNumber;
		selfInfo.isBackEnd = false;
		selfInfo.numCore = numCore;

		initInfo = new FEJoinResponse();
		initInfo.activeBEs = new ArrayList<String>();
		initInfo.aliveBEs = new HashMap<String, JoinProtocol>();
		initInfo.aliveFEs = new HashMap<String, JoinProtocol>();

		isSeed = false;
		reported = false;

		if (seedHosts.isEmpty())
		{
			System.out.println("No seed is given. The program will exit");
			System.exit(0);
		}

		for(int i = 0; i < seedHosts.size(); i++)
		{
			seedKey = seedHosts.get(i) + ":" + seedPorts.get(i);
			myKey = selfInfo.host + ":" + selfInfo.mportNum;

			if(myKey.equals(seedKey))
			{
				isSeed = true;
			}
			else
			{
				try
				{
					initInfo = FETryConnection(selfInfo, i % seedHosts.size());
					reported = true;
					break;
				}
				catch (ServiceUnavailableException X)
				{
					System.out.print("Reporting to " + seedHosts.get(i % seedHosts.size()) + ":");
					System.out.println(seedPorts.get(i % seedHosts.size()) +
						" Failed. Attempting the report procedure again within 1 second.");
					System.out.println("3");
					Thread.sleep(1000);
					System.out.println("2");
					Thread.sleep(1000);
					System.out.println("1");
					Thread.sleep(1000);
					System.out.println("Reporting......");
				}
			}				
		}

		if(reported)
		{
			System.out.println("Successfully connected to a FE seed node.");
		}
		else
		{
			System.out.println("No other seeds have been found.");
			if(isSeed)
			{
				System.out.println("This is the first seed.");
				selfInfo.upTime = new Date().getTime();
				initInfo.aliveFEs.put(selfInfo.host + ":" + selfInfo.pportNum + ":" + selfInfo.mportNum, selfInfo);
			}
			else
			{
				System.out.println("This is not a seed FE server. The program will be terminated.");
				System.exit(0);
			}
		}

		return initInfo;		
	}

	private static FEJoinResponse FETryConnection(JoinProtocol selfInfo, int index) throws ServiceUnavailableException
	{
		TTransport transport;
		TProtocol protocol;
		A1Management.Client client;
		FEJoinResponse initInfo;

		try
		{
			transport = new TFramedTransport(new TSocket(seedHosts.get(index), seedPorts.get(index)));
			transport.open();
			protocol = new TBinaryProtocol(transport);
			client = new A1Management.Client(protocol);

			initInfo = client.feJoin(selfInfo);

			transport.close();

			return initInfo;
		}
		catch(TTransportException refused)
		{
			throw new ServiceUnavailableException("Failed to connect.");
		}
		catch(Exception X)
		{
			X.printStackTrace();
			System.exit(0);
		}
		
		return null;
	}
}

