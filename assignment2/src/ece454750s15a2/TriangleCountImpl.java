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
    private static Map<Integer,Integer> injectiveMap;

    public TriangleCountImpl(byte[] input, int numCores) {
		this.input = input;
		this.numCores = numCores;
		this.injectiveMap = new LinkedHashMap<Integer,Integer>();
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
			return singleThreadList();
		}else{
			return multiThread();
		}
    }

    public List<Triangle> multiThread() throws IOException, InterruptedException, ExecutionException {
		// this code is single-threaded and ignores numCores
		ArrayList<Triangle> ret = new ArrayList<Triangle>();
		
		// forward
		final Map<Integer,HashSet<Integer>> adjacencyMap = getAdjacencyMap(input);
		final int numVertices = adjacencyMap.size();
		ArrayList<Future<ArrayList<Triangle>>> future;

		final Map<Integer,HashSet<Integer>> dynamicA;

		dynamicA = Collections.synchronizedMap(new HashMap<Integer, HashSet<Integer>>());
		future = new ArrayList<Future<ArrayList<Triangle>>>();

		for(int i = 0; i < numVertices; i++) {
			dynamicA.put(i, new HashSet<Integer>());
		}

		ExecutorService exec = Executors.newFixedThreadPool(numCores);

		for(int k = 0; k < numCores; k++)
		{
			final int g = k;
			Callable<ArrayList<Triangle>> callable = new Callable<ArrayList<Triangle>>()
			{
				@Override
				public ArrayList<Triangle> call()
				{
					ArrayList<Triangle> retVal = new ArrayList<Triangle>();
					for(int i = g*numVertices/numCores; i < (g+1)*numVertices/numCores; i++){
						HashSet<Integer> neighbours = adjacencyMap.get(i);
						Iterator<Integer> itN = neighbours.iterator();
						while(itN.hasNext()){
							Integer j = itN.next();
							if(i < j) {

								// A(s) intersection A(t)
								boolean set1Larger = dynamicA.get(j).size() > dynamicA.get(i).size() ;
								List<Integer> common = new ArrayList<Integer>(set1Larger ? dynamicA.get(i) : dynamicA.get(j));
								common.retainAll(set1Larger ? dynamicA.get(j) : dynamicA.get(i));

								for(Integer k : common){
										retVal.add(new Triangle(k,i,j));
								}
								synchronized(dynamicA)
								{
									(dynamicA.get(j)).add(i);
								}
							}
						}
			    	}
			    	return retVal;
				}
			};
			future.add(exec.submit(callable));
		}

		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		exec.shutdown();

		for(int i = 0; i < numCores; i++)
		{
			ret.addAll(future.get(i).get());
		}

		//Edge iterator algorithm
		// ArrayList<HashSet<Integer>> adjacencyList = getAdjacencyListSet(input);
		// int numVertices = adjacencyList.size();
		// for(int i = 0; i < numVertices; i++){
		// 	HashSet<Integer> neighbours = adjacencyList.get(i);
		// 	Iterator<Integer> it = neighbours.iterator();
		// 	while(it.hasNext()){
		// 		Integer j = it.next();
		// 		if(i<j){
		// 			boolean set1Larger = adjacencyList.get(j).size()>neighbours.size() ;
		// 			List<Integer> common = new ArrayList<Integer>(set1Larger ? neighbours:adjacencyList.get(j));
		// 			common.retainAll(set1Larger ? adjacencyList.get(j):neighbours);
		// 			for(Integer k : common){
		// 				if(k>j){
		// 					ret.add(new Triangle(i,j,k));
		// 				}
		// 			}
		// 		}
		// 	}
		// }
		
		return ret;
    }
	public List<Triangle> singleThreadList() throws IOException {
		ArrayList<Triangle> ret = new ArrayList<Triangle>();
		
		ArrayList<HashSet<Integer>> adjacencyList = getAdjacencyListSet(input);
		//System.out.println("List: " + adjacencyList);
		int numVertices = adjacencyList.size();
		ArrayList<Integer> order = sortList(adjacencyList);
		HashMap <Integer,Integer> injectiveMap = new HashMap<Integer,Integer>(((int)(numVertices*4.0/3.0)) + 1);
		//System.out.println("Order: " + order);
		ArrayList<HashSet<Integer>> subList = new ArrayList<HashSet<Integer>>(adjacencyList.size());
		
		for (int i = 0; i < numVertices; i++) {
			subList.add(new HashSet<Integer>());
			injectiveMap.put(order.get(i),i);
		}
		//System.out.println("Injective Mapping: " + injectiveMap);
		for(int i = 0; i < numVertices; i++){
			int current = order.get(i);
			HashSet<Integer> neighbours = adjacencyList.get(current);
			for(Integer j : neighbours){
				//degree of neighbour < current, or injective neighbour > current
				//System.out.printf("current: %s, neighbour: %s\n", current,j);
				if(injectiveMap.get(current)<injectiveMap.get(j)){
					boolean set1Larger = subList.get(j).size()>subList.get(current).size() ;
					List<Integer> common = new ArrayList<Integer>(set1Larger ? subList.get(current):subList.get(j));
					common.retainAll(set1Larger ? subList.get(j):subList.get(current));
					for(Integer k : common){
						// Store in sorted order
						int min, max, med;
						if (current > j) {
							if (current > k) {
								max = current;
								if (j > k) {
									med = j;
									min = k;
								} else {
									med = k;
									min = j;
								}
							} else {
								med = current;
								if (j > k) {
									max = j;
									min = k;
								} else {
									max = k;
									min = j;
								}
							}
						} else {
							if (j > k) {
								max = j;
								if (current > k) {
									med = current;
									min = k;
								} else {
									med = k;
									min = current;
								}
							} else {
								med = j;
								max = k;
								min = current;
							}
						}
						ret.add(new Triangle(min,med,max));
					}
					if(!(subList.get(j)).contains(current)){
						(subList.get(j)).add(current);
					}
				}
			}
		}
		return ret;
	}
    public List<Triangle> singleThread() throws IOException {
		// this code is single-threaded and ignores numCores
		ArrayList<Triangle> ret = new ArrayList<Triangle>();
		
		
		// forward
		Map<Integer,HashSet<Integer>> adjacencyMap = getAdjacencyMap(input);
		int numVertices = adjacencyMap.size();

		Map<Integer,HashSet<Integer>> dynamicA = new HashMap<Integer, HashSet<Integer>>();

		for(int i = 0; i < numVertices; i++) {
			dynamicA.put(i, new HashSet<Integer>());
		}
		int count = 0; // count the number of triangles
		// System.out.println(adjacencyMap.toString());
		// System.out.println(injectiveMap.toString());

		Iterator itMap = injectiveMap.entrySet().iterator();
  		while (itMap.hasNext()) {
			Map.Entry pair = (Map.Entry)itMap.next();
			int i = (Integer) pair.getKey();
			HashSet<Integer> neighbours = (HashSet<Integer>) adjacencyMap.get(i);
			int v = injectiveMap.get(i);

			Iterator<Integer> itN = neighbours.iterator();
			while (itN.hasNext()) {
				int j = itN.next();
				int u = injectiveMap.get(j);
				
				// System.out.println("j = " + j + " " + "i = " + i);
				
				if (u > v) {

					// A(s) intersection A(t)
					boolean set1Larger = dynamicA.get(j).size() > dynamicA.get(i).size() ;
					List<Integer> common = new ArrayList<Integer>(set1Larger ? dynamicA.get(i) : dynamicA.get(j));
					common.retainAll(set1Larger ? dynamicA.get(j) : dynamicA.get(i));

					for (Integer k : common) {
						count++;
						
						// Store in sorted order
						int min, max, med;
						if (i > j) {
							if (i > k) {
								max = i;
								if (j > k) {
									med = j;
									min = k;
								} else {
									med = k;
									min = j;
								}
							} else {
								med = i;
								if (j > k) {
									max = j;
									min = k;
								} else {
									max = k;
									min = j;
								}
							}
						} else {
							if (j > k) {
								max = j;
								if (i > k) {
									med = i;
									min = k;
								} else {
									med = k;
									min = i;
								}
							} else {
								med = j;
								max = k;
								min = i;
							}
						}

						ret.add(new Triangle(min,med,max));
					}

					(dynamicA.get(j)).add(i);
					// System.out.println(j + "=" + dynamicA.get(j).toString() + " " + i + "=" + dynamicA.get(i).toString());
				}
			}
    	}
    	System.out.println("count = " + count);
		/*
		Edge iterator algorithm
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
		*/
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
	
	public Map<Integer,HashSet<Integer>> getAdjacencyMap(byte[] data) throws IOException {
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
		
		Map<Integer, HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>(numVertices);
		while ((strLine = br.readLine()) != null && !strLine.equals(""))   {
		    parts = strLine.split(": ");
		    int vertex = Integer.parseInt(parts[0]);
			HashSet<Integer> list = new HashSet<Integer>();
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
		return map;
    }
	
	public Map<Integer,HashSet<Integer>> getAdjacencyMapMulti(byte[] data) throws IOException, InterruptedException {
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
		
		ExecutorService threadPool = Executors.newFixedThreadPool(numCores);

		final Map<Integer, HashSet<Integer>> map = Collections.synchronizedMap(new HashMap<Integer, HashSet<Integer>>(numVertices));
		while ((strLine = br.readLine()) != null && !strLine.equals("")){
			parts = strLine.split(": ");
			final String threadedParts[] = parts;
			threadPool.submit(new Runnable()
			{
				public void run()
				{
				    int vertex = Integer.parseInt(threadedParts[0]);
					HashSet<Integer> list = new HashSet<Integer>();
				    if (threadedParts.length > 1) {
						String threadedPartsParts[] = threadedParts[1].split(" +");
						for (String part: threadedPartsParts) {
							//if(Integer.parseInt(part) < vertex) // added to only include adjacencies greater than current value
								list.add(Integer.parseInt(part));
						}
				    }
				    synchronized(map)
				    {
						map.put(vertex, list);
				    }
				}
			});
		}
		br.close();
		threadPool.shutdown();
		threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		return map;
    }
	
	private static Map<Integer, HashSet<Integer>> sortMap(Map<Integer, HashSet<Integer>> map){
		
		// Convert Map to List
		List<Map.Entry<Integer, HashSet<Integer>>> list = 
			new ArrayList<Map.Entry<Integer, HashSet<Integer>>>(map.entrySet());
 
		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Integer, HashSet<Integer>>>() {
			public int compare(Map.Entry<Integer, HashSet<Integer>> e1, Map.Entry<Integer, HashSet<Integer>> e2) {
				if (e1.getValue().size() < e2.getValue().size()) {
					return 1;
				} else if (e1.getValue().size() > e2.getValue().size()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
 		int i = 0;
		// Convert sorted map back to a Map, LinedHashMap preserves iterative order
		Map<Integer, HashSet<Integer>> sortedMap = new LinkedHashMap<Integer, HashSet<Integer>>();
		for (Iterator<Map.Entry<Integer, HashSet<Integer>>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Integer, HashSet<Integer>> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
			injectiveMap.put(entry.getKey(), i++);
		}
		return sortedMap;
	}
	
	public int partition(ArrayList<Integer> values, ArrayList<Integer> position, int left, int right)
	{
		int i = left, j = right;
		int tmp;
		int pivot = values.get((left + right) / 2);

		while (i <= j) {
			// uses > instead of < for reverse sort, same for next while loop
			while (values.get(i) > pivot)
				i++;
			while (values.get(j)< pivot)
				j--;
			if (i <= j) {
				Collections.swap(values,i,j);
				Collections.swap(position,i,j);
				i++;
				j--;
			}
		};

		return i;
	}
	 
	public void quickSort(ArrayList<Integer> values, ArrayList<Integer> position, int left, int right) {
		int index = partition(values, position, left, right);
		if (left < index - 1)
			quickSort(values, position, left, index - 1);
		if (index < right)
			quickSort(values, position, index, right);
	}
	
	public ArrayList<Integer> sortList(ArrayList<HashSet<Integer>> adjacencyList){
		ArrayList<Integer> values = new ArrayList<Integer>(adjacencyList.size());
		ArrayList<Integer> position = new ArrayList<Integer>(adjacencyList.size());
		for(int i = 0; i < adjacencyList.size(); i++){
			values.add(adjacencyList.get(i).size());
			position.add(i);
		}
		quickSort(values,position,0,values.size()-1);
		return position;
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
			adjacencyListSet.add(new HashSet<Integer>((int)((1.25- ((double)i/numVertices))*numEdges/numVertices*2)));
		}
		
		while ((strLine = br.readLine()) != null && !strLine.equals(""))   {
			
			int previous = 0;
			int current = strLine.indexOf(':', 0);
			int vertex = Integer.parseInt(strLine.substring(0,current));
			current = strLine.indexOf(' ',previous+1);
			while(current < strLine.length() - 1){
				previous = current;
				current = strLine.indexOf(' ',previous+1);
				int part = Integer.parseInt(strLine.substring(previous+1,current));
				//if(part > vertex)
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
