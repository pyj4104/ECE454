import ece454750s15a1.*;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
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
		
		try
		{
			TTransport transport;
			transport = new TSocket("localhost", 24264);
			transport.open();

			TProtocol protocol = new  TBinaryProtocol(transport);
			A1Password.Client client = new A1Password.Client(protocol);

			perform(client);

			transport.close();
		}
		catch (Exception X)
		{
			X.printStackTrace();
		}
	}
	
	private static void perform(A1Password.Client client) throws TException
	{
		String password;
		String hashed;
		int saltGenLogRounds;

		password = "David is so good that";
		saltGenLogRounds = 10;

		hashed = client.hashPassword(password, saltGenLogRounds);

		System.out.println(hashed);
	}
}

