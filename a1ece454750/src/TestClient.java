//package ece454750s15a1;
import ece454750s15a1.*;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class TestClient
{
	public static void main(String[] args)
	{
		if (args.length != 1 || !args[0].contains("simple"))
		{
			System.out.println("Please enter 'simple' ");
			System.exit(0);
		}
		
		simple();
		
		try
		{
			for(int i=0; i<10; i++)
			{
				new Thread("" + i)
				{
					public void run()
					{
						
					}
			  	}.start();
			}
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}
	
	public static void simple()
	{
		try
		{
			TTransport transport;
			TProtocol protocol;
			A1Password.Client client;

			String hashed;
			String password;
			int saltGenLogRounds;
			boolean check;
			
			transport = new TFramedTransport(new TSocket("localhost", 14264));
			transport.open();
			protocol = new TBinaryProtocol(transport);
			client = new A1Password.Client(protocol);

			password = "David is so good that";
			saltGenLogRounds = 12;
			
			hashed = hashPass(client, password, saltGenLogRounds);
			check = checkPass(client, password, hashed);

			transport.close();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}
	
	private static String hashPass(A1Password.Client client, String password, int saltGenLogRounds)
	throws TException
	{
		String hashed = client.hashPassword(password, saltGenLogRounds);
		
		return hashed;
	}
	
	private static boolean checkPass(A1Password.Client client, String password, String hashed)
	throws TException
	{
		boolean check = client.checkPassword(password, hashed);

		return check;
	}
}

