/* This is management implementation of the assignment1
*/
//package ece454750s15a1;
package ece454750s15a1;
import org.apache.thrift.TException;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.*;
import java.util.concurrent.ConcurrentHashMap;

public class FEManagementHandler extends ManagementHandlerCommon 
{
	private List<String> activeBEs;
	private List<GossippingProto> logger;
	private ConcurrentHashMap<String, JoinProtocol> aliveBEs;
	private ConcurrentHashMap<String, JoinProtocol> aliveFEs;

	public FEManagementHandler(AtomicInteger numReqRec, AtomicInteger numReqCom,
		List<String> active, 
		ConcurrentHashMap<String, JoinProtocol> aliveBE,
		ConcurrentHashMap<String, JoinProtocol> aliveFE,
		List<GossippingProto> eventLogger)
	{
		super(numReqRec, numReqCom);
		activeBEs = active;
		aliveBEs = aliveBE;
		aliveFEs = aliveFE;
		logger = eventLogger;
	}
	
	public PerfCounters getPerfCounters()
	{
		return super.getPerfCounters();
	}
	
	public List<String> getGroupMembers()
	{
		return super.getGroupMembers();
	}
	
	public boolean join(JoinProtocol newNode)
	{
		try
		{
			GossippingProto event;
			String key;
			
			event = new GossippingProto();
			key = generateKeyString(newNode);
			newNode.upTime = new Date().getTime();

			synchronized(activeBEs)
			{
				for(int i = 0; i < newNode.numCore; i++)
				{
					activeBEs.add(key);
				}
				if(!aliveBEs.containsKey(key))
				{
					aliveBEs.put(key, newNode);
				}
			}

			event.isDead = false;
			event.key = key;
			event.eventServer = aliveBEs.get(key);
			event.time = new Date().getTime();
			event.eventLife = 10;
			event.isBE = true;

			synchronized(logger)
			{
				logger.add(event);
			}

			return true;
		}
		catch(Exception X)
		{
			X.printStackTrace();
		}

		return false;
	}

	public FEJoinResponse feJoin(JoinProtocol newNode)
	{
		try
		{
			GossippingProto event;
			FEJoinResponse retVal;
			String key;

			event = new GossippingProto();
			key = generateKeyString(newNode);
			newNode.upTime = new Date().getTime();

			synchronized(aliveFEs)
			{
				if(!aliveFEs.containsKey(key))
				{
					aliveFEs.put(key, newNode);
				}
			}

			event.isDead = false;
			event.key = key;
			event.eventServer = aliveBEs.get(key);
			event.time = new Date().getTime();
			event.eventLife = 10;
			event.isBE = false;

			synchronized(logger)
			{
				logger.add(event);
			}

			retVal = new FEJoinResponse();

			retVal.activeBEs = activeBEs;
			retVal.aliveBEs = aliveBEs;
			retVal.aliveFEs = aliveFEs;

			return retVal;
		}
		catch(Exception X)
		{
			X.printStackTrace();
		}

		return null;
	}

	public void gossip(List<GossippingProto> message)
	{
		System.out.println(message);
		GossippingProto event;
		ArrayList<GossippingProto> msg;

		msg = new ArrayList<GossippingProto>(message);

		for(int j = 0; j < msg.size(); j++)
		{
			event = msg.get(j);
			System.out.println(event.key);
			if(event.isBE)
			{
				if(event.isDead)
				{
					if(event.time > aliveBEs.get(event.key).upTime)
					{
						if (aliveBEs.containsKey(event.key))
						{
							aliveBEs.remove(event.key);
						}
					}
				}
				else
				{
					if(!aliveBEs.containsKey(event.key))
					{
						aliveBEs.put(event.key, event.eventServer);
						for(int i = 0; i < event.eventServer.numCore; i++)
						{
							synchronized(activeBEs)
							{
								activeBEs.add(event.key);
							}
						}
					}
				}
			}
			else
			{
				if(event.isDead)
				{
					if(event.time > aliveFEs.get(event.key).upTime)
					{
						aliveFEs.remove(event.key);
					}
				}
				else
				{
					if(!aliveFEs.containsKey(event.key))
					{
						aliveFEs.put(event.key, event.eventServer);
					}
				}
			}
		}
	}
}

