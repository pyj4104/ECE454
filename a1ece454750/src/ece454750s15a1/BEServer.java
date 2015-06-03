//package ece454750s15a1;
package ece454750s15a1;
import java.util.concurrent.atomic.*;
import java.util.*;
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
	public static BEPasswordHandler phandler;
	public static BEManagementHandler mhandler;
	public static A1Password.Processor pproc;
	public static A1Management.Processor mproc;
	private static int pportNumber;
	private static int mportNumber;
	private static int numCore;
	private static String hostString;
	private static ArrayList<String> seedHosts;
	private static ArrayList<Integer> seedPorts;
	private volatile static int[] numReqRec;
	private volatile static int[] numReqCom;
	
	public static void main(String[] args)
	{
		try
		{
			for(int i = 0; i < (args.length ); i++)
			{
				System.out.println("Argument " + i + " is " + args[i]);
				if(args[i].equals("-host")){
					hostString = args[i+1];
				}else if(args[i].equals("-pport")){
					pportNumber = Integer.parseInt(args[i+1]);
				}else if(args[i].equals("-mport")){
					mportNumber = Integer.parseInt(args[i+1]);
				}else if(args[i].equals("-ncores")){
					numCore = Integer.parseInt(args[i+1]);
				}else if(args[i].equals("-seeds")){
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
			System.out.println(Arrays.toString(seedHosts.toArray(new String[seedHosts.size()])));
			
			numReqRec = new int[1];
			numReqCom = new int[1];
			
			numReqRec[0] = 0;
			numReqCom[0] = 0;
			
			Runnable password = new Runnable()
			{
				public void run()
				{
					phandler = new BEPasswordHandler(numReqRec, numReqCom);
					pproc = new A1Password.Processor(phandler);
					psimple(pproc, pportNumber, numCore);
				}
			};
			
			Runnable management = new Runnable()
			{
				public void run()
				{
					mhandler = new BEManagementHandler(numReqRec, numReqCom);
					mproc = new A1Management.Processor(mhandler);
					msimple(mproc, mportNumber, numCore);
				}
			};

			new Thread(password).start();
			new Thread(management).start();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}
	
	public static void psimple(A1Password.Processor proc, int portNum, int coreNum)
	{
		try
		{
			TNonblockingServerTransport trans;
			Args args;
			TServer server;
		
			trans = new TNonblockingServerSocket(portNum);
			args = new TThreadedSelectorServer.Args(trans);
			args.transportFactory(new TFramedTransport.Factory());
			args.protocolFactory(new TBinaryProtocol.Factory());
			args.processor(proc);
			args.selectorThreads(coreNum);
			args.workerThreads(coreNum * 8);
			server = new TThreadedSelectorServer(args);

			System.out.println("Starting the password server...");
			server.serve();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}
	
	public static void msimple(A1Management.Processor proc, int portNum, int coreNum)
	{
		try
		{
			TNonblockingServerTransport trans;
			Args args;
			TServer server;
		
			trans = new TNonblockingServerSocket(portNum);
			
			args = new TThreadedSelectorServer.Args(trans);
			args.selectorThreads(coreNum);
			args.workerThreads(coreNum * 8);
			
			server = new TThreadedSelectorServer(args.processor(proc));

			System.out.println("Starting the management server...");
			server.serve();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}
}

