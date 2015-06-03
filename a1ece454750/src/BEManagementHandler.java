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

public class BEManagementHandler extends ManagementHandlerCommon
{
	private Date _startTime;
	private int[] localReqRec;
	private int[] localReqCom;
	
	public BEManagementHandler(int[] numReqRec, int[] numReqCom)
	{
		this._startTime = new Date();
		localReqRec = numReqRec;
		localReqCom = numReqCom;
	}
}

