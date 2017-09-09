package com.etouch.connsTests;


import java.util.ArrayList;
import java.util.List;

public class Practice {

 public static void main(String[] args) 
 {
	 List<Integer> List1 = new ArrayList<Integer>();
	 List<Integer> List2 = new ArrayList<Integer>();
	 List<Integer> List3 = new ArrayList<Integer>();
	 List1.add(5);
	 List1.add(0);
	 List1.add(-5);
	 List1.add(0);
	 List1.add(2);
	 List1.add(0);
	 List1.add(10);
	 System.out.println("List1 : " + List1);
	 for(int i=0;i<List1.size();i++ )
	 {
		 if (List1.get(i)==0)
		 {
			 List2.add(List1.get(i));
		 }
		
	 }
		 for(int i=0;i<List1.size();i++ )
		 {
			 if (List1.get(i)!=0)
			 List2.add(List1.get(i));
		 }
		 
		 System.out.println("List2 final: " + List2);
		 
	 }
	 
	 
	 }
 