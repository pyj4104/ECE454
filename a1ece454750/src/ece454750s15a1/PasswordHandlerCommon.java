/* This is password implementation of the assignment1
*/
//package ece454750s15a1;
package ece454750s15a1;
import java.util.concurrent.atomic.*;
import org.apache.thrift.TException;
import org.mindrot.jbcrypt.BCrypt;

public abstract class PasswordHandlerCommon implements A1Password.Iface
{
	protected AtomicInteger localReqRec;
	protected AtomicInteger localReqCom;

	protected PasswordHandlerCommon(AtomicInteger numReqRec, AtomicInteger numReqCom)
	{
		localReqRec = numReqRec;
		localReqCom = numReqCom;
	}

	public abstract String hashPassword(String password, int logRounds) throws ServiceUnavailableException;
	public abstract boolean checkPassword(String candidate, String hash) throws ServiceUnavailableException;

	public static String generateKeyString(JoinProtocol proto)
	{
		String key;

		key = proto.host + ":" + proto.pportNum + ":" + proto.mportNum;

		return key;
	}
}

