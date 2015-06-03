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

public class ManagementHandlerCommon implements A1Management.Iface
{
	private Date _startTime;
	private int[] localReqRec;
	private int[] localReqCom;
	private List<BEJoinProtocol> activeBEs;
	private ConcurrentHashMap<BEJoinProtocol, Boolean> deadBEs;
	
	public PerfCounters getPerfCounters()
	{
		PerfCounters returnStruct;
		
		returnStruct = new PerfCounters();
		
		returnStruct.numSecondsUp = (int)((new Date().getTime() - this._startTime.getTime())) / 1000;
		returnStruct.numRequestsReceived = localReqRec[0];
		returnStruct.numRequestsCompleted = localReqCom[0];
		return returnStruct;
	}
	
	public List<String> getGroupMembers()
	{
		List<String> QID;
		
		QID = new ArrayList<String>();
		
		QID.add("y27park");
		QID.add("h53huang");
		
		return QID;
	}
	
	
	public void join(BEJoinProtocol joinProto)
	{
	}
	
	public void gossip(List<BEJoinProtocol> listOfBE)
	{
	}
}

