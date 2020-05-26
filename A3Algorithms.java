/* A3Algorithms.java
   CSC 225 - Summer 2019


   B. Bird - 04/28/2019
   (Add your name/studentID/date here)
*/ 

import java.awt.Color;
import java.io.*;
import java.util.*;
/*
int v
boolean visited[];

for int i = 0, i<n,i++
	if G[V][i] == 1 and visited[i] == false
*/

public class A3Algorithms{

	/* FloodFillDFS(v, writer, fillColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void FloodFillDFS(PixelVertex v, PixelWriter writer, Color fillColour){
		/* Your code here */
		Stack<PixelVertex> stack = new Stack<PixelVertex>();
		v.visited = true;
		stack.add(v);

		while(!stack.isEmpty()){
			PixelVertex z = stack.pop();

			writer.setPixel(z.getX(),z.getY(),fillColour);

			for(PixelVertex temp : z.getNeighbours()){
				if(!temp.visited){
					temp.visited = true;
					stack.add(temp);
				}
			}

		}
	}
	
	/* FloodFillBFS(v, writer, fillColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void FloodFillBFS(PixelVertex v, PixelWriter writer, Color fillColour){
		/* Your code here */
		Queue<PixelVertex> queue = new LinkedList<PixelVertex>();
		v.visited = true;
		queue.add(v);

		while(!queue.isEmpty()){
			PixelVertex z = queue.poll();

			writer.setPixel(z.getX(),z.getY(),fillColour);

			for(PixelVertex temp : z.getNeighbours()){
				if(!temp.visited){
					temp.visited = true;
					queue.add(temp);
				}
			}
		}
	}
	
	/* OutlineRegionDFS(v, writer, outlineColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void OutlineRegionDFS(PixelVertex v, PixelWriter writer, Color outlineColour){
		/* Your code here */
		Stack<PixelVertex> stack = new Stack<PixelVertex>();
		v.visited = true;
		stack.add(v);

		while(!stack.isEmpty()){
			PixelVertex z = stack.pop();
			if(z.getDegree()< 4){
				writer.setPixel(z.getX(),z.getY(),outlineColour);}

			for(PixelVertex temp : z.getNeighbours()){
				if(!temp.visited){
					temp.visited = true;
					stack.add(temp);
				}
			}

		}
	}
	
	/* OutlineRegionBFS(v, writer, outlineColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void OutlineRegionBFS(PixelVertex v, PixelWriter writer, Color outlineColour){
		/* Your code here */
		Queue<PixelVertex> queue = new LinkedList<PixelVertex>();
		v.visited = true;
		queue.add(v);

		while(!queue.isEmpty()){
			PixelVertex z = queue.poll();
			if(z.getDegree()< 4){
				writer.setPixel(z.getX(),z.getY(),outlineColour);}
			for(PixelVertex temp : z.getNeighbours()){
				if(!temp.visited){
					temp.visited = true;
					queue.add(temp);
				}
			}
		}
	}

	/* CountComponents(G)
	   Count the number of connected components in the provided PixelGraph 
	   object.
	*/
	public static int CountComponents(PixelGraph G){
		/* Your code here */
		int number = 0;
		for(int i = 0; i < G.the_graph.length; i++){
			for(int j = 0; j < G.the_graph[0].length; j++){
				PixelVertex v = G.the_graph[i][j];
				if(!v.visited){
					number++;
					Stack<PixelVertex> stack = new Stack<PixelVertex>();
					v.visited = true;
					stack.add(v);

					while(!stack.isEmpty()){
						PixelVertex z = stack.pop();
						for(PixelVertex temp : z.getNeighbours()){
							if(!temp.visited){
								temp.visited = true;
								stack.add(temp);
							}
						}
					}

				}
			}
		}

		return number;
	}
}