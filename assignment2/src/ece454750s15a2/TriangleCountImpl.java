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
	private volatile List<Triangle> ret;

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

    public List<Triangle> enumerateTriangles() throws IOException, InterruptedException {
		return multiThread();
    }
    public List<Triangle> singleThread() throws IOException {
		// this code is single-threaded and ignores numCores
		ArrayList<Triangle> ret = new ArrayList<Triangle>();
		
		//Edge iterator algorithm
		ArrayList<HashSet<Integer>> adjacencyList = getAdjacencyListSet(input);
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

    public List<Triangle> multiThread() throws IOException, InterruptedException {
		// this code is single-threaded and ignores numCores
		ret = Collections.synchronizedList(new ArrayList<Triangle>());
		ExecutorService threadPool = Executors.newFixedThreadPool(4);

		//Edge iterator algorithm
		final ArrayList<HashSet<Integer>> adjacencyList = getAdjacencyListSet(input);

		int numVertices = adjacencyList.size();
		for(int i = 0; i < numVertices; i++){
			final int iSub = i;
			threadPool.submit(new Runnable(){
				public void run(){
					HashSet<Integer> neighbours = adjacencyList.get(iSub);
					Iterator<Integer> it = neighbours.iterator();
					while(it.hasNext()){
						Integer j = it.next();
						if(iSub<j){
							boolean set1Larger = adjacencyList.get(j).size()>neighbours.size() ;
							List<Integer> common = new ArrayList<Integer>(set1Larger ? neighbours:adjacencyList.get(j));
							common.retainAll(set1Larger ? adjacencyList.get(j):neighbours);
							for(Integer k : common){
								if(k>j){
									synchronized(ret)
									{
										ret.add(new Triangle(iSub,j,k));
									}
								}
							}
						}
					}
				}
     		});
		}

		threadPool.shutdown();
		threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		
		return ret;
    }

	public boolean[][] getAdjacencyMatrix(byte[] data) throws IOException {
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
	 
		boolean[][] adjacencyMatrix = new boolean[numVertices][numVertices];
		while ((strLine = br.readLine()) != null && !strLine.equals(""))   {
		    parts = strLine.split(": ");
		    int vertex = Integer.parseInt(parts[0]);
		    if (parts.length > 1) {
			parts = parts[1].split(" +");
			for (String part: parts) {
				if(Integer.parseInt(part) > vertex) // added to only include adjacencies greater than current value
				adjacencyMatrix[vertex][Integer.parseInt(part)] = true;
			}
		    }
		}
		br.close();
		return adjacencyMatrix;
    }
	
	public Map<Integer,ArrayList<Integer>> getAdjacencyMap(byte[] data) throws IOException {
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
		
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>(numVertices);
		while ((strLine = br.readLine()) != null && !strLine.equals(""))   {
		    parts = strLine.split(": ");
		    int vertex = Integer.parseInt(parts[0]);
			ArrayList<Integer> list = new ArrayList<Integer>();
		    if (parts.length > 1) {
				parts = parts[1].split(" +");
				for (String part: parts) {
					//if(Integer.parseInt(part) < vertex) // added to only include adjacencies greater than current value
						list.add(Integer.parseInt(part));
				}
		    }
			map.put(vertex, list);
		}
		br.close();
		map = sortMap(map);
		return map;
    }
	
	private static Map<Integer, ArrayList<Integer>> sortMap(Map<Integer, ArrayList<Integer>> map){
 
		// Convert Map to List
		List<Map.Entry<Integer, ArrayList<Integer>>> list = 
			new ArrayList<Map.Entry<Integer, ArrayList<Integer>>>(map.entrySet());
 
		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Integer, ArrayList<Integer>>>() {
			public int compare(Map.Entry<Integer, ArrayList<Integer>> e1, Map.Entry<Integer, ArrayList<Integer>> e2) {
				if (e1.getValue().size() < e2.getValue().size()) {
					return 1;
				} else if (e1.getValue().size() > e2.getValue().size()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
 
		// Convert sorted map back to a Map, LinedHashMap preserves iterative order
		Map<Integer, ArrayList<Integer>> sortedMap = new LinkedHashMap<Integer, ArrayList<Integer>>();
		for (Iterator<Map.Entry<Integer, ArrayList<Integer>>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Integer, ArrayList<Integer>> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
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
	
	if(numVertices < 200000){
		//doesn't work for 1 million, java out of memory on heap
		for (int i = 0; i < numVertices; i++) {
			//adjacencyListSet.add(new HashSet<Integer>());
			adjacencyListSet.add(new HashSet<Integer>((numVertices-i)/((numEdges*3)/(numVertices-i))));
		}
	}else{
		for (int i = 0; i < numVertices; i++) {
			adjacencyListSet.add(new HashSet<Integer>());
		}
	}
	
	while ((strLine = br.readLine()) != null && !strLine.equals(""))   {
		
		int previous = 0;
		int current = strLine.indexOf(':', 0);
		int vertex = Integer.parseInt(strLine.substring(0,current));
		current = strLine.indexOf(' ',previous+1);
		while(current < strLine.length() - 1){
			previous = current;
			current = strLine.indexOf(' ',previous+1);
			//System.out.println(strLine.substring(previous+1,current));
			int part = Integer.parseInt(strLine.substring(previous+1,current));
			if(part > vertex)
				adjacencyListSet.get(vertex).add(part);
		}
		
		/*
	    StringTokenizer st1 = new StringTokenizer(strLine,": ");
	    int vertex = Integer.parseInt(st1.nextToken());
		while(st1.hasMoreTokens()){
			int part = Integer.parseInt(st1.nextToken());
			if(part > vertex){
				adjacencyListSet.get(vertex).add(part);
			}
		}
		*/
	}
	br.close();
	return adjacencyListSet;
    }
	
    public ArrayList<ArrayList<Integer>> getAdjacencyList(byte[] data) throws IOException {
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
	 
		ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<ArrayList<Integer>>(numVertices);
		for (int i = 0; i < numVertices; i++) {
		    adjacencyList.add(new ArrayList<Integer>());
		}
		while ((strLine = br.readLine()) != null && !strLine.equals(""))   {
		    parts = strLine.split(": ");
		    int vertex = Integer.parseInt(parts[0]);
		    if (parts.length > 1) {
			parts = parts[1].split(" +");
			for (String part: parts) {
				if(Integer.parseInt(part) > vertex) // added to only include adjacencies greater than current value
					adjacencyList.get(vertex).add(Integer.parseInt(part));
			}
		    }
		}
		br.close();
		return adjacencyList;
	    }
}
