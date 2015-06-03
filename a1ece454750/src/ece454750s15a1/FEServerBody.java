package ece454750s15a1;
import java.util.concurrent.atomic.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
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
	private volatile static List<BEJoinProtocol> activeBEs; //must ensure thread safety
	private volatile static ConcurrentHashMap<BEJoinProtocol,Boolean> deadBEs;
	
	public FEServerBody(String[] args)
	{
		super(args);

		try
		{
			activeBEs = Collections.synchronizedList(new ArrayList<BEJoinProtocol>());
			deadBEs = new ConcurrentHashMap<BEJoinProtocol, Boolean>();
			
			Runnable password = new Runnable()
			{
				public void run()
				{
					phandler = new FEPasswordHandler(numReqRec, numReqCom);
					pproc = new A1Password.Processor(phandler);
					psimple(pproc);
				}
			};
			
			Runnable management = new Runnable()
			{
				public void run()
				{
					mhandler = new FEManagementHandler(numReqRec, numReqCom, activeBEs, deadBEs);
					mproc = new A1Management.Processor(mhandler);
					msimple(mproc);
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
}
