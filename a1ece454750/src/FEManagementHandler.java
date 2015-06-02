/* This is management implementation of the assignment1
*/
//package ece454750s15a1;
import ece454750s15a1.*;
import org.apache.thrift.TException;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class FEManagementHandler implements A1Management.Iface
{
	private Date _startTime;
	
	public void BEPasswordHandler()
	{
		this._startTime = new Date();
	}

	public PerfCounters getPerfCounters()
	{
		PerfCounters returnStruct;
		
		returnStruct = new PerfCounters();
		
		returnStruct.numSecondsUp = (int)((new Date().getTime() - this._startTime.getTime())) * 1000;

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
	
}

