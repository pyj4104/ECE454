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

public class BEServerBody extends ServerCommon
{
	public static BEPasswordHandler phandler;
	public static BEManagementHandler mhandler;
	
	public BEServerBody(String[] args)
	{
		super(args);

		try
		{
			Runnable password = new Runnable()
			{
				public void run()
				{
					phandler = new BEPasswordHandler(numReqRec, numReqCom);
					pproc = new A1Password.Processor(phandler);
					psimple(pproc);
				}
			};
			
			Runnable management = new Runnable()
			{
				public void run()
				{
					mhandler = new BEManagementHandler(numReqRec, numReqCom);
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
