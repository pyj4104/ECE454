/* This is management implementation of the assignment1
*/
//package ece454750s15a1;
import ece454750s15a1.*;
import org.apache.thrift.TException;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.*;
import java.util.concurrent.ConcurrentHashMap;

public class FEManagementHandler extends ManagementHandlerCommon
{
	private Date _startTime;
	private int[] localReqRec;
	private int[] localReqCom;
	private List<BEJoinProtocol> activeBEs;
	private ConcurrentHashMap<BEJoinProtocol, Boolean> deadBEs;
	
	public FEManagementHandler(int[] numReqRec, int[] numReqCom,
		List<BEJoinProtocol> alive, 
		ConcurrentHashMap<BEJoinProtocol, Boolean> dead)
	{
		this._startTime = new Date();
		localReqRec = numReqRec;
		localReqCom = numReqCom;
		activeBEs = alive;
		deadBEs = dead;
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

