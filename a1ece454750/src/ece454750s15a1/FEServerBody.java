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
		synchronized(eventLogger)
		{
			for(Iterator<GossippingProto> iter = eventLogger.iterator(); iter.hasNext();)
			{
				GossippingProto gossip = iter.next();
				if(gossip.eventLife != 0)
				{
					gossip.eventLife--;
					msg.add(gossip);
				}
				else
				{
					iter.remove();
				}
			}
		}

		talk(msg);
	}

	private void talk(ArrayList<GossippingProto> msg)
	{
		Iterator<Map.Entry<String, JoinProtocol>> entries = aliveFEs.entrySet().iterator();
		//for(Map.Entry<String, JoinProtocol> entry : aliveFEs.entrySet())
		while(entries.hasNext())
		{
			Map.Entry<String, JoinProtocol> entry = entries.next();
			Random rng = new Random();
			if(!entry.getKey().equals(super.id))
			{
				if(rng.nextInt(100)<10)
					System.out.println(entry.getKey());
				try
				{
					TTransport transport;
					TProtocol protocol;
					A1Management.Client client;

					transport = new TFramedTransport(new TSocket(entry.getValue().host, entry.getValue().mportNum));
					transport.open();
					protocol = new TBinaryProtocol(transport);
					client = new A1Management.Client(protocol);
					client.gossip(msg);
					transport.close();
				}
				catch(TTransportException refused)
				{
					GossippingProto event;
					System.out.println("refused: " + refused.getMessage());
					event = new GossippingProto();
					event.isDead = true;
					event.key = entry.getKey();
					event.eventServer = aliveFEs.get(entry.getKey());
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
}
