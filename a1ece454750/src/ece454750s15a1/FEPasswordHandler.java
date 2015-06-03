/* This is password implementation of the assignment1
*/
//package ece454750s15a1;
package ece454750s15a1;
import java.util.concurrent.atomic.*;
import org.apache.thrift.TException;
import org.mindrot.jbcrypt.BCrypt;

public class FEPasswordHandler extends PasswordHandlerCommon
{
	public FEPasswordHandler(int[] numReqRec, int[] numReqCom)
	{
		super(numReqRec, numReqCom);
	}

	public String hashPassword(String password, int logRounds)
	{
		return null;
	}

	public boolean checkPassword(String candidate, String hash)
	{
		return true;
	}
}

