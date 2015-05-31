/* This is password implementation of the assignment1
*/
import org.apache.thrift.TException;
import ece454750s15a1.*;
import org.mindrot.jbcrypt.BCrypt;

public class BEPasswordHandler implements A1Password.Iface
{
	public BEPasswordHandler()
	{
	}

	public String hashPassword(String password, int logRounds)
	{
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
		return hashed;
	}

	public boolean checkPassword(String candidate, String hash)
	{
		return BCrypt.checkpw(candidate, hash);
	}
}

