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

	public String hashPassword(String password, int logRounds) throws ServiceUnavailableException
	{
		CommandStructure command;
		ReturnStructure result;

		command = new CommandStructure("hash", password, logRounds);

		localReqRec.addAndGet(1);
		result = ProcessRequest(command);
		if(result.error == true)
		{
			throw new ServiceUnavailableException(result.errMsg);
		}
		localReqCom.addAndGet(1);
		return result.hash;
	}

	public boolean checkPassword(String candidate, String hash) throws ServiceUnavailableException
	{
		CommandStructure command;
		ReturnStructure result;

		command = new CommandStructure("check", hash, candidate);

		localReqRec.addAndGet(1);
		result = ProcessRequest(command);
		localReqCom.addAndGet(1);

		if(result.error == true)
		{
			throw new ServiceUnavailableException(result.errMsg);
		}

		return result.check;
	}

	private ReturnStructure ProcessRequest(CommandStructure com) throws ServiceUnavailableException
	{
		ReturnStructure retVal;
		String key;
		int bEUsed;

		bEUsed = 0;
		key = "";

		if (activeBEs.isEmpty())
		{
			retVal = new ReturnStructure(true, "All back end server is down. Service is unavailable.");
			return retVal;
		}

		try
		{
			while(!activeBEs.isEmpty())
			{
				bEUsed = new Random().nextInt(activeBEs.size());
				key = super.generateKeyString(activeBEs.get(bEUsed));

				//Choose which BE it will use
				if(deadBEs.containsKey(key))
				{
					activeBEs.remove(bEUsed);
				}
				else
				{
					break;
				}
			}

			retVal = Execute(com, activeBEs.get(bEUsed).host, activeBEs.get(bEUsed).pportNum);
		}
		catch(TTransportException X)
		{
			synchronized(activeBEs)
			{
				if(!deadBEs.containsKey(key))
				{
					deadBEs.put(key, activeBEs.get(bEUsed));
				}

				activeBEs.remove(bEUsed);
			}

			retVal = ProcessRequest(com);
		}
		catch(TException ex)
		{
			retVal = new ReturnStructure(true, "Error!");
		}

		return retVal;
	}

	private ReturnStructure Execute(CommandStructure com, String host, int pport) throws TTransportException, ServiceUnavailableException
	{
		TTransport transport;
		TProtocol protocol;
		A1Password.Client client;
		ReturnStructure retVal;

		try
		{
			transport = new TFramedTransport(new TSocket(host, pport));
			transport.open();
			protocol = new TBinaryProtocol(transport);
			client = new A1Password.Client(protocol);

			if (com.command.equals("hash"))
			{
				retVal = new ReturnStructure(client.hashPassword(com.password, com.logRounds));
			}
			else if (com.command.equals("check"))
			{
				retVal = new ReturnStructure(client.checkPassword(com.candidate, com.hash));
			}
			else
			{
				retVal = new ReturnStructure(true, "Error!");
			}
			
			transport.close();

			return retVal;
		}
		catch(TTransportException X)
		{
			throw new TTransportException("Bad!!!");
		}
		catch(TException X)
		{
			return new ReturnStructure(true, "Error!");
		}
	}

	class CommandStructure
	{
		protected String command;
		protected String password;
		protected String candidate;
		protected String hash;
		protected int logRounds;

		public CommandStructure(String com, String pass, int log)
		{
			command = com;
			password = pass;
			logRounds = log;
		}

		public CommandStructure(String com, String cand, String has)
		{
			command = com;
			candidate = cand;
			hash = has;
		}
	}

	class ReturnStructure
	{
		protected boolean error;
		protected boolean check;
		protected String hash;
		protected String errMsg;
		
		public ReturnStructure(String has)
		{
			hash = has;
		}

		public ReturnStructure(boolean che)
		{
			check = che;
		}

		public ReturnStructure(boolean err, String msg)
		{
			error = err;
			errMsg = msg;
		}
	}
}
