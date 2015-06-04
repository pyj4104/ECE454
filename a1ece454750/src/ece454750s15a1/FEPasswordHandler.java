/* This is password implementation of the assignment1
*/
//package ece454750s15a1;
package ece454750s15a1;
import java.util.Random;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.TException;
import org.mindrot.jbcrypt.BCrypt;

public class FEPasswordHandler extends PasswordHandlerCommon
{
	private List<JoinProtocol> activeBEs;
	private ConcurrentHashMap<String, JoinProtocol> deadBEs;

	public FEPasswordHandler(AtomicInteger numReqRec, AtomicInteger numReqCom,
		List<JoinProtocol> alive, 
		ConcurrentHashMap<String, JoinProtocol> dead)
	{
		super(numReqRec, numReqCom);
		activeBEs = alive;
		deadBEs = dead;
	}

	public String hashPassword(String password, int logRounds)
	{
		TTransport transport;
		TProtocol protocol;
		A1Password.Client client;
		String hashed;
		int bEUsed;

		localReqRec.addAndGet(1);

		//Choose which BE it will use
		bEUsed = new Random().nextInt(activeBEs.size());
		System.out.println(bEUsed);

		try
		{
			transport = new TFramedTransport(new TSocket(activeBEs.get(bEUsed).host, activeBEs.get(bEUsed).pportNum));
			transport.open();
			protocol = new TBinaryProtocol(transport);
			client = new A1Password.Client(protocol);

			hashed = client.hashPassword(password, logRounds);
			
			transport.close();

			localReqCom.addAndGet(1);

			return hashed;
		}
		catch(TTransportException refused)
		{

		}
		catch(Exception X)
		{
			X.printStackTrace();
		}

		return null;
	}

	public boolean checkPassword(String candidate, String hash)
	{
		TTransport transport;
		TProtocol protocol;
		A1Password.Client client;
		boolean check;

		localReqRec.addAndGet(1);
		
		try
		{
			transport = new TFramedTransport(new TSocket("localhost", 14264));
			transport.open();
			protocol = new TBinaryProtocol(transport);
			client = new A1Password.Client(protocol);

			check = client.checkPassword(candidate, hash);
			
			transport.close();

			localReqCom.addAndGet(1);

			return check;
		}
		catch(TTransportException refused)
		{

		}
		catch(Exception X)
		{
			X.printStackTrace();
		}

		return false;
	}

	class CommandStructure
	{
		protected String command;
		protected String host;
		protected String password;
		protected int port;

		public CommandStructure(String com, String hos, String pass, int por)
		{
			command = com;
			host = hos;
			password = pass;
			port = por;
		}
	}

	class ReturnStructure
	{
		protected String command;
		protected boolean check;
		protected String hash;
		
		public ReturnStructure(String com, boolean che, String has)
		{

		}
	}
}
