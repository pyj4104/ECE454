/**
 * ECE 454/750: Distributed Computing
 *
 * Code written by Wojciech Golab, University of Waterloo, 2015
 *
 * IMPLEMENT YOUR SOLUTION IN THIS FILE
 *
 */

package ece454750s15a2;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class TriangleCountImpl {
    private byte[] input;
    private int numCores;

    public TriangleCountImpl(byte[] input, int numCores) {
	this.input = input;
	this.numCores = numCores;
    }

    public List<String> getGroupMembers() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add("h53huang");
		ret.add("y27park");
		ret.add("njbenann");
		return ret;
    }

    public List<Triangle> enumerateTriangles() throws IOException, InterruptedException, ExecutionException {
		if(numCores == 1){
			return singleThread();
		}else{
			return multiThread(numCores);
		}
    }
    public List<Triangle> singleThread() throws IOException {
		// this code is single-threaded and ignores numCores
		ArrayList<Triangle> ret = new ArrayList<Triangle>();
		
		//Edge iterator algorithm
		long startTime = System.currentTimeMillis();
		ArrayList<HashSet<Integer>> adjacencyList = getAdjacencyListSet(input);
		System.out.println("io:" + String.valueOf(System.currentTimeMillis() - startTime));
		int numVertices = adjacencyList.size();
		for(int i = 0; i < numVertices; i++){
			HashSet<Integer> neighbours = adjacencyList.get(i);
			Iterator<Integer> it = neighbours.iterator();
			while(it.hasNext()){
				Integer j = it.next();
				if(i<j){
					boolean set1Larger = adjacencyList.get(j).size()>neighbours.size() ;
					List<Integer> common = new ArrayList<Integer>(set1Larger ? neighbours:adjacencyList.get(j));
					common.retainAll(set1Larger ? adjacencyList.get(j):neighbours);
					for(Integer k : common){
						if(k>j){
							ret.add(new Triangle(i,j,k));
						}
					}
				}
			}
		}
		
		return ret;
    }

    public List<Triangle> multiThread(int numCores) throws IOException, InterruptedException, ExecutionException {
		// this code is single-threaded and ignores numCores
		final List<Triangle> ret = new ArrayList<Triangle>();
		
		//Edge iterator algorithm
		long startTime = System.currentTimeMillis();
		final ArrayList<HashSet<Integer>> adjacencyList;
		if(numCores < 4)
		{
			adjacencyList = getAdjacencyListSet(input);
		}
		else
		{
			adjacencyList = getAdjacencyListSetMulti(input);
		}
		System.out.println("io:" + String.valueOf(System.currentTimeMillis() - startTime));
		final Integer chunkSize = 50;
		ExecutorService threadPool = Executors.newFixedThreadPool(numCores);
		List<Future<ArrayList<Triangle>>> list = new ArrayList<Future<ArrayList<Triangle>>>();

		int numVertices = adjacencyList.size();
		for(int z = 0; z < numVertices/chunkSize; z++)
		{
			final int startingPoint = z*chunkSize;
			final int endPoint = (z+1)*chunkSize;

			Callable<ArrayList<Triangle>> callable = new Callable<ArrayList<Triangle>>()
			{
				@Override
				public ArrayList<Triangle> call()
				{
					ArrayList<Triangle> subSet = new ArrayList<Triangle>();
					for(int i = startingPoint; i < endPoint; i++)
					{
						HashSet<Integer> neighbours = adjacencyList.get(i);
						Iterator<Integer> it = neighbours.iterator();
						while(it.hasNext()){
							Integer j = it.next();
							if(i<j){
								boolean set1Larger = adjacencyList.get(j).size()>neighbours.size() ;
								List<Integer> common = new ArrayList<Integer>(set1Larger ? neighbours:adjacencyList.get(j));
								common.retainAll(set1Larger ? adjacencyList.get(j):neighbours);
								for(Integer k : common){
									if(k>j){
										subSet.add(new Triangle(i, j, k));
									}
								}
							}
						}
					}
					return subSet;
				}
			};
			Future<ArrayList<Triangle>> future = threadPool.submit(callable);
			list.add(future);
		}

		for(Future<ArrayList<Triangle>> fut: list)
		{
			ret.addAll(fut.get());
		}
		
		threadPool.shutdown();
		threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

		return ret;
    }
	
	public ArrayList<HashSet<Integer>> getAdjacencyListSet(byte[] data) throws IOException {
		InputStream istream = new ByteArrayInputStream(data);
		BufferedReader br = new BufferedReader(new InputStreamReader(istream));
		String strLine = br.readLine();
		if (!strLine.contains("vertices") || !strLine.contains("edges")) {
		    System.err.println("Invalid graph file format. Offending line: " + strLine);
		    System.exit(-1);	    
		}
		String parts[] = strLine.split(", ");
		int numVertices = Integer.parseInt(parts[0].split(" ")[0]);
		int numEdges = Integer.parseInt(parts[1].split(" ")[0]);
		System.out.println("Found graph with " + numVertices + " vertices and " + numEdges + " edges");
	 
		ArrayList<HashSet<Integer>> adjacencyListSet = new ArrayList<HashSet<Integer>>(numVertices);
		for (int i = 0; i < numVertices; i++) {
			//adjacencyListSet.add(new HashSet<Integer>());
			adjacencyListSet.add(new HashSet<Integer>((int)((1.3- ((double)i/numVertices))*numEdges/numVertices)));
		}
		
		//long startTime = System.currentTimeMillis();
		while ((strLine = br.readLine()) != null && !strLine.equals(""))   {
			
			int previous = 0;
			int current = strLine.indexOf(':', 0);
			int vertex = Integer.parseInt(strLine.substring(0,current));
			current = strLine.indexOf(' ',previous+1);
			while(current < strLine.length() - 1){
				previous = current;
				current = strLine.indexOf(' ',previous+1);
				int part = Integer.parseInt(strLine.substring(previous+1,current));
				if(part > vertex)
					adjacencyListSet.get(vertex).add(part);
			}
		}
		//System.out.println("I/O single " + String.valueOf(System.currentTimeMillis() - startTime));
		return adjacencyListSet;
    }

	public ArrayList<HashSet<Integer>> getAdjacencyListSetMulti(byte[] data) throws IOException, InterruptedException,
	ExecutionException {
		String inputString = new String(data);
		//System.out.println("Input is " + inputString);
		final String[] inputLines = inputString.split("[\\r\\n]+");
		if (!inputLines[0].contains("vertices") || !inputLines[0].contains("edges")) {
		    System.err.println("Invalid graph file format. Offending line: " + inputLines[0]);
		    System.exit(-1);	    
		}
		String parts[] = inputLines[0].split(", ");
		int numVertices = Integer.parseInt(parts[0].split(" ")[0]);
		int numEdges = Integer.parseInt(parts[1].split(" ")[0]);
		System.out.println("Found graph with " + numVertices + " vertices and " + numEdges + " edges");
		ArrayList<Future<ArrayList<HashSet<Integer>>>> future = new ArrayList<Future<ArrayList<HashSet<Integer>>>>();
		
		ArrayList<HashSet<Integer>> adjacencyListSet = new ArrayList<HashSet<Integer>>(numVertices);
		ExecutorService executor = Executors.newFixedThreadPool(numCores);

		//long startTime = System.currentTimeMillis();
		for(int j = 0; j < numCores; j++)
		{
			final int startingPoint = j*numVertices/numCores+1;
			final int endPoint = (j+1)*numVertices/numCores+1;

			Callable<ArrayList<HashSet<Integer>>> callable = new Callable<ArrayList<HashSet<Integer>>>()
			{
				@Override
				public ArrayList<HashSet<Integer>> call()
				{
					//System.out.println(startingPoint + " ~ " + endPoint);
					ArrayList<HashSet<Integer>> retVal = new ArrayList<HashSet<Integer>>(endPoint - startingPoint);
					for(int i = 0; i < endPoint - startingPoint; i++){
						retVal.add(new HashSet<Integer>());
					}
					for(int i = startingPoint; i < endPoint; i++)   {
						int index = i-startingPoint;
						int previous = 0;
						int current = inputLines[i].indexOf(':', 0);
						int vertex = Integer.parseInt(inputLines[i].substring(0,current));
						current = inputLines[i].indexOf(' ',previous+1);
						while(current < inputLines[i].length() - 1){
							previous = current;
							current = inputLines[i].indexOf(' ',previous+1);
							//System.out.printf("vertex %s Current %s, previous %s \n",vertex,current,previous);
							int part = Integer.parseInt(inputLines[i].substring(previous+1,current));
							if(part > vertex)
								retVal.get(index).add(part);
						}
					}
					//System.out.printf("retval from %d to %d is %s\n",startingPoint,endPoint,retVal);
					return retVal;
				}
			};
			future.add(executor.submit(callable));
		}

		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

		for(int i = 0; i < numCores; i++)
		{
			//System.out.println(future.get(i).size());
			adjacencyListSet.addAll(future.get(i).get());
		}

		//System.out.println("set: " + adjacencyListSet);

		//System.out.println("I/O single " + String.valueOf(System.currentTimeMillis() - startTime));
		return adjacencyListSet;
    }
}
