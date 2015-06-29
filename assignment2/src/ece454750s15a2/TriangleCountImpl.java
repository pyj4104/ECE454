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

public class TriangleCountImpl {
	class Graph{
		int vertices;
		HashSet<Edge> edges;
		public Graph(int v, HashSet<Edge> e){
			vertices = v;
			edges = e;
		}
	}
	class Edge{
		int v1;
		int v2;
		public Edge(int vertex1, int vertex2){
			v1 = vertex1;
			v2 = vertex2;
		}
		public int hashCode() {
			final int prime = 31;
			int result = 7;
			result = prime * result + v1;
			result = prime * result + v2;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MultiEdge other = (MultiEdge) obj;
			if (v1 != other.v1)
				return false;
			if (v2 != other.v2)
				return false;
			return true;
		}
	}
	class MultiEdge{
		int v1;
		int v2;
		int v3;
		public MultiEdge(int vertex1, int vertex2, int vertex3){
			v1 = vertex1;
			v2 = vertex2;
			v3 = vertex3;
		}
		public int hashCode() {
			final int prime = 17;
			int result = 7;
			result = prime * result + v1;
			result = prime * result + v2;
			result = prime * result + v3;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MultiEdge other = (MultiEdge) obj;
			if (v1 != other.v1)
				return false;
			if (v2 != other.v2)
				return false;
			if (v3 != other.v3)
				return false;
			return true;
		}
	}
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

    public List<Triangle> enumerateTriangles() throws IOException {
		// this code is single-threaded and ignores numCores
		ArrayList<Triangle> ret = new ArrayList<Triangle>();
		
		Set<MultiEdge> retMultiEdge = new HashSet<MultiEdge>();
		Map<Integer, ArrayList<Integer>> adjacencyMap = getAdjacencyMap(input);
		ArrayList<ArrayList<Integer>> subList = new ArrayList<ArrayList<Integer>>(adjacencyMap.size());
		
		for (int i = 0; i < adjacencyMap.size(); i++) {
			subList.add(new ArrayList<Integer>());
		}
		for(Map.Entry<Integer,ArrayList<Integer>> entry : adjacencyMap.entrySet()) {
			//System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
			for(Integer adjacent : entry.getValue()){
				//System.out.println("\tlooking at " + entry.getKey() + " and " + adjacent);
				if(adjacencyMap.get(adjacent).size() <= adjacencyMap.get(entry.getKey()).size()){
					List<Integer> common = new LinkedList<Integer>(subList.get(entry.getKey()));
					common.retainAll(new HashSet<Integer>(subList.get(adjacent)));
					for(Integer k : common){
						
						int min, max, med;//assume values are there for a b c
						if( entry.getKey() > adjacent ){
						 if( entry.getKey() > k ){
						  max = entry.getKey();
						  if( adjacent > k ){
						   med = adjacent;
						   min = k;
						  }else{
						   med = k;
						   min = adjacent;
						  }
						 }else{
						  med = entry.getKey();
						  if( adjacent > k ){
						   max = adjacent;
						   min = k;
						  }else{
						   max = k;
						   min = adjacent;
						  }
						 }
						}else{
						 if( adjacent > k ){
						  max = adjacent;
						  if( entry.getKey() > k ){
						   med = entry.getKey();
						   min = k;
						  }else{
						   med = k;
						   min = entry.getKey();
						  }
						 }else{
						  med = adjacent;
						  max = k;
						  min = entry.getKey();
						 }
						}
						
						if(!retMultiEdge.contains(new MultiEdge(min,med,max))){
							//ret.add(new Triangle(min,med,max));
							//ret.add(new Triangle(adjacent,entry.getKey(),k));
							retMultiEdge.add(new MultiEdge(min,med,max));
							//System.out.println("...triangle: " + k+","+entry.getKey()+","+adjacent);
						}
					}
					if(!(subList.get(adjacent)).contains(entry.getKey())){
						(subList.get(adjacent)).add(entry.getKey());
					}
					//System.out.println("\t\tsubList: " + subList);
				}
			}
		}
		
		
		for(MultiEdge me : retMultiEdge){
			ret.add(new Triangle(me.v1,me.v2,me.v3));
		}
		
		/*
		// The comparator
		Comparator<List<Integer>> comparatorList = new Comparator<List<Integer>>() {
			@Override
			public int compare(List<Integer> l1, List<Integer> l2) {
				if (l1.size() < l2.size()) {
					return -1;
				} else if (l1.size() > l2.size()) {
					return 1;
				} else {
					return 0;
				}
			}

		};
		*/
		
		/*
		ArrayList<ArrayList<Integer>> adjacencyList = getAdjacencyList(input);
		int numVertices = adjacencyList.size();
		for(int i = 0; i < numVertices; i++){
			ArrayList<Integer> neighbours = adjacencyList.get(i);
			for(Integer j : neighbours){
				if(i < j){
					List<Integer> common = new LinkedList<Integer>(neighbours);
					common.retainAll(new HashSet<Integer>(adjacencyList.get(j)));
					for(Integer k : common){
						if(k > j){
							ret.add(new Triangle(i,j,k));
						}
					}
				}
			}
		}
		*/
		/*
		//hash solution, algorithm runs too slow.
		Graph graph = getGraph(input);
		for(Edge edge : graph.edges){			
			for(int i = 0; i < edge.v1; i++){
				Edge e1 = new Edge(i,edge.v1);
				Edge e2 = new Edge(i,edge.v2);
				if(graph.edges.contains(e1) && graph.edges.contains(e2)){
					ret.add(new Triangle(i,edge.v1, edge.v2));
				}
			}
		}
		*/
		/*
		// matrix solution - doesn't work, takes too much space on heap.
		boolean[][] adjacencyMatrix = getAdjacencyMatrix(input);
		int numVertices = adjacencyMatrix[0].length;
		for(int i = 0; i < numVertices; i++){
			for(int j = i; j < numVertices; j++){
				if(adjacencyMatrix[i][j] == true){
					for(int k = j; k < numVertices; k++){
						if(adjacencyMatrix[k][i] && adjacencyMatrix[k][j]){
							ret.add(new Triangle(i,j,k));
						}
					}
				}
			}
		}
		*/
		/*
		// naive triangle counting algorithm
		ArrayList<ArrayList<Integer>> adjacencyList = getAdjacencyList(input);
		int numVertices = adjacencyList.size();
		for (int i = 0; i < numVertices; i++) {
			ArrayList<Integer> n1 = adjacencyList.get(i);
			for (int j: n1) {
			ArrayList<Integer> n2 = adjacencyList.get(j);
			for (int k: n2) {
				ArrayList<Integer> n3 = adjacencyList.get(k);
				for (int l: n3) {
				if (i < j && j < k && l == i) {
					ret.add(new Triangle(i, j, k));
				}
				}
			}
			}
		}
		*/
		return ret;
    }

	public Graph getGraph(byte[] data) throws IOException {
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
	HashSet<Edge> edges = new HashSet<Edge>();
	while ((strLine = br.readLine()) != null && !strLine.equals(""))   {
	    parts = strLine.split(": ");
	    int vertex = Integer.parseInt(parts[0]);
	    if (parts.length > 1) {
		parts = parts[1].split(" +");
		for (String part: parts) {
			if(Integer.parseInt(part) > vertex) // added to only include adjacencies greater than current value
				edges.add(new Edge(vertex,Integer.parseInt(part)));
		}
	    }
	}
	br.close();
	Graph graph = new Graph(numVertices,edges);
	return graph;
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
			new LinkedList<Map.Entry<Integer, ArrayList<Integer>>>(map.entrySet());
 
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
