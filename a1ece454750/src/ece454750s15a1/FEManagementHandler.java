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
	private List<BEJoinProtocol> activeBEs;
	private ConcurrentHashMap<BEJoinProtocol, Boolean> deadBEs;

	public FEManagementHandler(AtomicInteger numReqRec, AtomicInteger numReqCom,
		List<BEJoinProtocol> alive, 
		ConcurrentHashMap<BEJoinProtocol, Boolean> dead)
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
	
	public void join(List<BEJoinProtocol> alive)
	{
		activeBEs.addAll(alive);
		if(deadBEs.containsKey(alive.get(0)))
		{
			deadBEs.remove(alive.get(0));
		}
	}
	
	public void leave(ConcurrentHashMap<BEJoinProtocol, Boolean> dead)
	{
		deadBEs.putAll(dead);
	}
}

