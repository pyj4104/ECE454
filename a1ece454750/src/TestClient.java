//package ece454750s15a1;
import ece454750s15a1.*;
import java.util.List;
import java.util.Arrays;
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
		
		try
		{
			simple();
			
			A1Management.Client client2;
			TProtocol protocol;
			List<String> QID;
			PerfCounters returnStruct;
			
			TTransport trans = new TFramedTransport(new TSocket("localhost", 24266));
			trans.open();
			protocol = new TBinaryProtocol(trans);
			client2 = new A1Management.Client(protocol);
			QID = client2.getGroupMembers();
			returnStruct = client2.getPerfCounters();
			System.out.println("hi");
			System.out.println(Arrays.toString(QID.toArray()));
			System.out.println(returnStruct.numSecondsUp);
			System.out.println(returnStruct.numRequestsReceived);
			System.out.println(returnStruct.numRequestsCompleted);
			trans.close();
		}
		catch(Exception X)
		{
			X.printStackTrace();
		}
		
		//simple();
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
			
			transport = new TFramedTransport(new TSocket("localhost", 14266));
			transport.open();
			protocol = new TBinaryProtocol(transport);
			client = new A1Password.Client(protocol);

			password = "David is so good that";
			saltGenLogRounds = 12;
			
			hashed = hashPass(client, password, saltGenLogRounds);
			check = checkPass(client, password, hashed);
			
			System.out.println(hashed);
			System.out.println(check);

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

