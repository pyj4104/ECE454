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
	class Edge{
		int v1;
		int v2;
		public Edge(int vertex1, int vertex2){
			v1 = vertex1;
			v2 = vertex2;
		}
		public int hashCode() {
			final int prime = 31;
			int result = 1;
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
			Edge other = (Edge) obj;
			if (v1 != other.v1)
				return false;
			if (v2 != other.v2)
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
		ArrayList<ArrayList<Integer>> adjacencyList = getAdjacencyList(input);
		ArrayList<Triangle> ret = new ArrayList<Triangle>();
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
		/*
		//hash solution, algorithm runs too slow.
		HashSet<Edge> edges = new HashSet<Edge>();
		for(int i = 0; i < numVertices; i++){
			ArrayList<Integer> neighbours = adjacencyList.get(i);
			for(Integer j : neighbours){
				if(j.intValue() > i){
					edges.add(new Edge(i, j.intValue()));
				}
			}
		}
		for(Edge edge : edges){
			if(edge.v1 > edge.v2) continue;
			
			for(int i = 0; i < edge.v1; i++){
				Edge e1 = new Edge(i,edge.v1);
				Edge e2 = new Edge(i,edge.v2);
				if(edges.contains(e1) && edges.contains(e2)){
					ret.add(new Triangle(i,edge.v1, edge.v2));
				}
			}
		}
		*/
		/*
		// matrix solution - doesn't work, takes too much space on heap.
		boolean[][] adjacencyMatrix = new boolean[numVertices][numVertices];
		for(int i = 0; i < numVertices; i++){
			ArrayList<Integer> neighbours = adjacencyList.get(i);
			for(Integer j : neighbours){
				adjacencyMatrix[i][j.intValue()] = true;
			}
		}
		for(int i = 0; i < numVertices; i++){
			for(int j = 0; j < numVertices; j++){
				if(adjacencyMatrix[i][j] == true){
					for(int k = 0; k < numVertices; k++){
						if(adjacencyMatrix[k][i] && adjacencyMatrix[k][j]){
							if( i < j && j < k){
								ret.add(new Triangle(i,j,k));
							}
						}
					}
				}
			}
		}
		*/
		/*
		// naive triangle counting algorithm
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
		    adjacencyList.get(vertex).add(Integer.parseInt(part));
		}
	    }
	}
	br.close();
	return adjacencyList;
    }
}
