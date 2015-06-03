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
	protected Date startTime;
	protected AtomicInteger localReqRec;
	protected AtomicInteger localReqCom;

	public ManagementHandlerCommon(AtomicInteger numReqRec, AtomicInteger numReqCom)
	{
		startTime = new Date();
		localReqRec = numReqRec;
		localReqCom = numReqCom;
	}

	public PerfCounters getPerfCounters()
	{
		PerfCounters returnStruct;
		
		returnStruct = new PerfCounters();
		
		returnStruct.numSecondsUp = (int)((new Date().getTime() - this.startTime.getTime())) / 1000;
		returnStruct.numRequestsReceived = localReqRec.get();
		returnStruct.numRequestsCompleted = localReqCom.get();
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

