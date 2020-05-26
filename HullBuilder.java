/* HullBuilder.java
   CSC 225 - Summer 2019

   Starter code for Convex Hull Builder. Do not change the signatures
   of any of the methods below (you may add other methods as needed).

   B. Bird - 03/18/2019
   (Add your name/studentID/date here)
*/
/*yuwu14
V00917423
16/6/2019*/

import java.util.LinkedList;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;



public class HullBuilder{

    /* Add constructors as needed */
	
	ArrayList<Point2d> Point = new ArrayList<Point2d>(2048);
	
	LinkedList<Point2d> Hull = new LinkedList<Point2d> ();
	
    /* addPoint(P)
       Add the point P to the internal point set for this object.
       Note that there is no facility to delete points (other than
       destroying the HullBuilder and creating a new one). 
    */
    public void addPoint(Point2d P){
		Point.add(P);
		Point.ensureCapacity(1024*1024*2048);
		
    }
    public static int chirality( Point2d P, Point2d Q, Point2d R ){
        double c = (Q.y-P.y)*(R.x-P.x) + (P.x-Q.x)*(R.y-P.y);
        if (c < 0)
            return -1;
        else if (c > 0)
            return 1;
        else
            return 0;
    }
	
	/*public int count_a(Point2d one[]){
		int count = 0;
		for(int i =0;i<one.length;i++){
			if(one[i] != null){
				count = count+1;
			}
		}
		return count;
	}*/

    /* getHull()
       Return a java.util.LinkedList object containing the points
       in the convex hull, in order (such that iterating over the list
       will produce the same ordering of vertices as walking around the 
       polygon).
    */
    // Using some implementation advise to build convex hull from 
    // https://www.geeksforgeeks.org/convex-hull-set-1-jarviss-algorithm-or-wrapping/ 

    public LinkedList<Point2d> getHull(){
        int m = 0;
		for(int i = 1; i<Point.size();i++){
			if(Point.get(i).x<Point.get(m).x){
				m = i;
			}
		}
		
		int l = m;
		int n = 0;
		do{
			Hull.push(Point.get(l));
			n = (l + 1)%Point.size();
			for(int i = 0; i< Point.size();i++){
				if(chirality(Point.get(l),Point.get(i),Point.get(n))== -1){
					n = i;
				}
			}
			l = n;

			
		}while(l != m); 
		return Hull;
    }
	// Check if the point Q lies on PR. 
	boolean is_on_line(Point2d P, Point2d Q, Point2d R){
		if(((Q.x <= P.x)&&(Q.x>=R.x))||((Q.x >= P.x)&&(Q.x<=R.x))){
			if(((Q.y <= P.y)&&(Q.y>=R.y))||((Q.y >= P.y)&&(Q.y<=R.y))){
				return true;
			}
		}
		return false;
    }
	
	// Check the intersection status for ab, cd.
	boolean intersect(Point2d a, Point2d b, Point2d c, Point2d d){
		int x1 = chirality(a,b,c);
		int x2 = chirality(a,b,d);
		int x3 = chirality(c,d,a);
		int x4 = chirality(c,d,b);		
		if(x1 != x2 && x3 != x4){
			return true;
		}
		if(x1 == 0){
			if(is_on_line(a,c,b)){
				return true;
			}
		}
		if(x2 == 0){
			if(is_on_line(a,d,b)){
				return true;
			}
		}
		if(x3 == 0){
			if(is_on_line(c,a,d)){
				return true;
			}
		}
		if(x4 == 0){
			if(is_on_line(c,b,d)){
				return true;
			}
		}		
		return false;
	}
	
    /* isInsideHull(P)
       Given an point P, return true if P lies inside the convex hull
       of the current point set (note that P may not be part of the
       current point set). Return false otherwise.
     */
    public boolean isInsideHull(Point2d P){
		Point2d[] Hull_1 = Hull.toArray(new Point2d[Hull.size()]);
		Point2d Check = new Point2d(1024*2048*1024,P.y);
		int count = 0;
		for(int i =0; i < Hull_1.length-1;i++){
			if(intersect(Hull_1[i],Hull_1[i+1],P,Check)){
				if(chirality(Hull_1[i],P,Hull_1[i+1])== 0){
					if(is_on_line(Hull_1[i],P,Hull_1[i+1])){
						return true;
					}
				}
				count = count +1;
			}
		}

		if(intersect(Hull_1[Hull_1.length-1],Hull_1[0],P,Check)){
			if(chirality(Hull_1[Hull_1.length-1],P,Hull_1[0])== 0){
				if(is_on_line(Hull_1[Hull_1.length-1],P,Hull_1[0])){
					return true;
				}
			}
			count = count +1;
		}
	
		if(count%2 == 1){
			return true;
		}
		return false;
		
    }
}
