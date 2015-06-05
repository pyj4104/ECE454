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
	private ConcurrentHashMap<String, JoinProtocol> aliveBEs;

	public FEManagementHandler(AtomicInteger numReqRec, AtomicInteger numReqCom,
		List<String> active, 
		ConcurrentHashMap<String, JoinProtocol> alive)
	{
		super(numReqRec, numReqCom);
		activeBEs = active;
		aliveBEs = alive;
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
					activeBEs.add(key);
				}
				if(!aliveBEs.containsKey(key))
				{
					aliveBEs.put(key, newNode);
				}
			}

			return true;
		}
		catch(Exception X)
		{
			X.printStackTrace();
		}

		return false;
	}
}

