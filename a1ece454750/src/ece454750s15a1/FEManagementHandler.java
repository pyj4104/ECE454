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
	private List<JoinProtocol> activeBEs;
	private ConcurrentHashMap<String, JoinProtocol> deadBEs;

	public FEManagementHandler(AtomicInteger numReqRec, AtomicInteger numReqCom,
		List<JoinProtocol> alive, 
		ConcurrentHashMap<String, JoinProtocol> dead)
	{
		super(numReqRec, numReqCom);
		activeBEs = alive;
		deadBEs = dead;
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
			String key = generateKeyString(newNode);

			synchronized(activeBEs)
			{
				for(int i = 0; i < newNode.numCore; i++)
				{
					activeBEs.add(newNode);
				}
				if(deadBEs.containsKey(key))
				{
					deadBEs.remove(key);
				}
			}

			return true;
		}
		catch(Exception X)
		{
			X.printStackTrace();
		}
		finally
		{
			return false;
		}
		
	}
	
	/*public void leave(ConcurrentHashMap<BEJoinProtocol, Boolean> dead)
	{
		deadBEs.remove(super.generateKeyString(newNode));
	}*/
}

