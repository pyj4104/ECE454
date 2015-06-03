/* This is password implementation of the assignment1
*/
//package ece454750s15a1;
package ece454750s15a1;
import java.util.concurrent.atomic.*;
import org.apache.thrift.TException;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHandlerCommon implements A1Password.Iface
{
	private int[] localReqRec;
	private int[] localReqCom;
	
	public String hashPassword(String password, int logRounds)
	{
		localReqRec[0]++;
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
		localReqCom[0]++;
		return hashed;
	}

	public boolean checkPassword(String candidate, String hash)
	{
		boolean retVal;
		
		localReqRec[0]++;
		retVal = BCrypt.checkpw(candidate, hash);
		localReqCom[0]++;
		
		return retVal;
	}
}

