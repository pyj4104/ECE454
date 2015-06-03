/* This is password implementation of the assignment1
*/
//package ece454750s15a1;
package ece454750s15a1;
import java.util.concurrent.atomic.*;
import org.apache.thrift.TException;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHandlerCommon implements A1Password.Iface
{
	protected int[] localReqRec;
	protected int[] localReqCom;

	protected PasswordHandlerCommon(int[] numReqRec, int[] numReqCom)
	{
		localReqRec = numReqRec;
		localReqCom = numReqCom;
	}

	public String hashPassword(String password, int logRounds)
	{
		return "dummy";
	}

	public boolean checkPassword(String candidate, String hash)
	{
		return true;
	}
}

