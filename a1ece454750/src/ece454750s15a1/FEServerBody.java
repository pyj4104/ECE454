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
	private volatile static List<JoinProtocol> activeBEs; //must ensure thread safety
	private volatile static ConcurrentHashMap<String, JoinProtocol> deadBEs;
	
	public FEServerBody(String[] args)
	{
		super(args);

		try
		{
			activeBEs = Collections.synchronizedList(new ArrayList<JoinProtocol>());
			deadBEs = new ConcurrentHashMap<String, JoinProtocol>();
			
			Runnable password = new Runnable()
			{
				public void run()
				{
					phandler = new FEPasswordHandler(numReqRec, numReqCom, activeBEs, deadBEs);
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
