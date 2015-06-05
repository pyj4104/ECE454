package ece454750s15a1;
import java.util.concurrent.atomic.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.*;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

public class FEServerBody extends ServerCommon
{
	public static FEPasswordHandler phandler;
	public static FEManagementHandler mhandler;
	private volatile static List<String> activeBEs; //must ensure thread safety
	private volatile static ConcurrentHashMap<String, JoinProtocol> aliveBEs;
	private volatile static ConcurrentHashMap<String, JoinProtocol> aliveFEs;
	
	public FEServerBody(String[] args)
	{
		super(args);

		try
		{
			FEJoinResponse initInfo;

			activeBEs = Collections.synchronizedList(new ArrayList<String>());
			aliveBEs = new ConcurrentHashMap<String, JoinProtocol>();
			aliveFEs = new ConcurrentHashMap<String, JoinProtocol>();

			initInfo = super.FEReport();
			System.out.println(initInfo.activeBEs);
			System.out.println(initInfo.aliveBEs);

			if(initInfo.activeBEs != null)
			{
				activeBEs.addAll(initInfo.activeBEs);
			}
			if(!initInfo.aliveBEs.isEmpty())
			{
				aliveBEs.putAll(initInfo.aliveBEs);
			}
			aliveFEs.putAll(initInfo.aliveFEs);

			Runnable password = new Runnable()
			{
				public void run()
				{
					phandler = new FEPasswordHandler(numReqRec, numReqCom, activeBEs, aliveBEs);
					pproc = new A1Password.Processor(phandler);
					psimple(pproc);
				}
			};
			
			Runnable management = new Runnable()
			{
				public void run()
				{
					mhandler = new FEManagementHandler(numReqRec, numReqCom, activeBEs, aliveBEs, aliveFEs);
					mproc = new A1Management.Processor(mhandler);
					msimple(mproc);
				}
			};

			Runnable unstoppingMouth = new Runnable()
			{
				public void run()
				{
					
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

	private void gossipWithOthers()
	{

	}
}
