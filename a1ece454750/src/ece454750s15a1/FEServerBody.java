package ece454750s15a1;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.*;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;

public class FEServerBody extends ServerCommon
{
	public static FEPasswordHandler phandler;
	public static FEManagementHandler mhandler;
	private volatile static List<String> activeBEs; //must ensure thread safety
	private volatile static List<GossippingProto> eventLogger;
	private volatile static ConcurrentHashMap<String, JoinProtocol> aliveBEs;
	private volatile static ConcurrentHashMap<String, JoinProtocol> aliveFEs;
	
	public FEServerBody(String[] args)
	{
		super(args);

		try
		{
			FEJoinResponse initInfo;

			eventLogger = Collections.synchronizedList(new ArrayList<GossippingProto>());

			activeBEs = Collections.synchronizedList(new ArrayList<String>());
			aliveBEs = new ConcurrentHashMap<String, JoinProtocol>();
			aliveFEs = new ConcurrentHashMap<String, JoinProtocol>();

			initInfo = super.FEReport();

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
					phandler = new FEPasswordHandler(numReqRec, numReqCom, activeBEs, aliveBEs, eventLogger);
					pproc = new A1Password.Processor(phandler);
					psimple(pproc);
				}
			};
			
			Runnable management = new Runnable()
			{
				public void run()
				{
					mhandler = new FEManagementHandler(numReqRec, numReqCom, activeBEs, aliveBEs, aliveFEs, eventLogger);
					mproc = new A1Management.Processor(mhandler);
					msimple(mproc);
				}
			};

			Runnable unstoppableMouth = new Runnable()
			{
				public void run() 
				{
					while(true)
					{
						try
						{
							Thread.sleep(100);
							gossipWithOthers();
						}
						catch(InterruptedException ex)
						{

						}
					}
				}
			};

			new Thread(password).start();
			new Thread(management).start();
			new Thread(unstoppableMouth).start();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}

	private void gossipWithOthers()
	{
		ArrayList<GossippingProto> msg;

		msg = new ArrayList<GossippingProto>();

		//Prepare message
		for(int i = 0; i < eventLogger.size(); i++)
		{
			if(eventLogger.get(i).eventLife != 0)
			{
				synchronized(eventLogger)
				{
					eventLogger.get(i).eventLife--;
				}
				msg.add(eventLogger.get(i));
			}
			else
			{
				synchronized(eventLogger)
				{
					eventLogger.remove(i);
				}
			}
		}

		talk(msg);
	}

	private void talk(ArrayList<GossippingProto> msg)
	{
		for(Map.Entry<String, JoinProtocol> entry : aliveFEs.entrySet())
		{
			try
			{
				TTransport transport;
				TProtocol protocol;
				A1Management.Client client;

				transport = new TFramedTransport(new TSocket(entry.getValue().host, entry.getValue().mportNum));
				transport.open();
				protocol = new TBinaryProtocol(transport);
				client = new A1Management.Client(protocol);
				//client.
				transport.close();
			}
			catch(TTransportException refused)
			{
				GossippingProto event;

				event = new GossippingProto();
				event.isDead = true;
				event.eventServer.put(entry.getKey(), aliveBEs.get(entry.getKey()));
				event.time = new Date().getTime();
				event.eventLife = 10;
				event.isBE = false;

				aliveFEs.remove(entry.getKey());
			}
			catch (Exception X)
			{
				X.printStackTrace();
			}
		}
	}
}
