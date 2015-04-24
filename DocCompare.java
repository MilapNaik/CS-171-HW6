/*
      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
      CODE WRITTEN BY OTHER STUDENTS. Milap Naik
      */

public class DocCompare {
	// three different algos for calculating the similarity of documents:
	public static double nestedLoop(String[] doc1, String[] doc2) {
	 	 String checkit[] = java.util.Arrays.copyOf(doc1, doc1.length);
		 java.util.Arrays.sort(checkit);
		
		 double sim = 0.0;
		 // used to test if only unique words are found
		 String lastword = "";
		
		 for (int i = 0; i < checkit.length; i++) {
		     if (!(checkit[i].equals(lastword))) {
		    	 String unique = checkit[i];
		    	 double count = 0.0;
		    	 for (int j = 0; j < doc2.length; j++) {
		    		 if (doc2[j].equals(unique)) {
		    			 count++;
		    		 }//closes if
		    	 }//closes for
		     //similarity calculation in assignment	 
		     sim += count / doc2.length; 
		     }//closes if
		 lastword = checkit[i];
		 }//closes for
		 return sim;
   }//closes nestedLoop
	
	public static int Search(String[] document, String key, int index) {
		 int l = index;
		 int hi = document.length - 1;
		 int middleval;

		 while (l <= hi) {
			 	middleval = (l + hi) / 2;
		     	int compare = document[middleval].compareTo(key);
		     	if (compare < 0) {
		     		l = middleval + 1;
		     	}//closes if
		     	else if (compare > 0) {
		     		hi = middleval - 1;
		     	}//closes else if
		     	else {
		     		return middleval;
		     	}//closes if
		 }//closes while
		 return -1;
    }//closes Search
	
	public static double indexNestedLoop(String[] doc1, String[] doc2) {
		 // sort doc1 and doc2, for every unique word in doc1,
		 // find all occurrences in doc2 using a binary search
		 String check1[] = java.util.Arrays.copyOf(doc1, doc1.length);
		 String check2[] = java.util.Arrays.copyOf(doc2, doc2.length);
		 java.util.Arrays.sort(check1);
		 java.util.Arrays.sort(check2);

		 double sim2 = 0.0;
		 // used to test if only unique words are found
		 String ult = "";
		 int doc2Ind = 0;

		 for (int k = 0; k < check1.length; k++) {
		     if (!(check1[k].equals(ult))) {
		    	 String uni1 = check1[k];
		    	 double count = 0;
		    	 int l = Search(check2, uni1, doc2Ind);
		    	 int r = l;
		    	 int f = l;
		
				  if (f != -1) {
				      count = 1;
				      while (l - 1 >= 0) {
				    	  if (check2[l - 1].compareTo(uni1) == 0) {
				    		  count++;
				    		  l--;
				    	  }//closes if
				    	  else break;
				      }//closes while
				      while (r + 1 < doc2.length) {
				    	  if (check2[r + 1].compareTo(uni1) == 0) {
				    		  count++;
				    		  doc2Ind = r;
				    		  r++;
				    	  }//closes if
				    	  else break;
				      }//closes while
				  }//closes if
				  sim2 += count / check2.length;
		     }//closes if
		     ult = check1[k];
		 }//closes for
		 return sim2;
    }//closes indexNestedLoop
	
	 public static double sortMerge(String[] doc1, String[] doc2) {
		 // sort doc1 and doc2, for each unique word in doc1,
		 // find all occurences in doc2 by "merging"
		 String checkit1[] = java.util.Arrays.copyOf(doc1, doc1.length);
		 String checkit2[] = java.util.Arrays.copyOf(doc2, doc2.length);
		 java.util.Arrays.sort(checkit1);
		 java.util.Arrays.sort(checkit2);

		 String[] sol = new String[checkit2.length];

		 double sim1 = 0.0;
		 // used to test if unique words are found
		 String last = "";
		 double c = 0.0;
		 int doc2Ind = 0;

		 for (int i = 0; i < checkit1.length; i++) {
		     if (!(checkit1[i].equals(last))) {
		    	 String uni = checkit1[i];
		    	 int k = 0;
		    	 while (doc2Ind < checkit2.length) {
		    		 if (uni.compareTo(checkit2[doc2Ind]) == 0) {
		    			 sol[k] = checkit2[doc2Ind];
		    			 k++;
		    			 c++;
		    			 doc2Ind++;
		    		 }//closes if
		    		 else if (uni.compareTo(checkit2[doc2Ind]) > 0) {
		    			 doc2Ind++;
		    		 }//closes else if
		    		 else if (uni.compareTo(checkit2[doc2Ind]) < 0) {
		    			 break;
		    		 }//closes else if
		    	 }//closes while
		     }//closes if
		     last = checkit1[i];
		 }//closes for
		 sim1 = c / checkit2.length;
		 return sim1;
    }//closes sortMerge
	 
	//public void put(String key, Value val()){
	
	public static void main(String[] args) {
		 if (args.length < 2) {
		     System.out.println("java DocCompare file1 file2");
		     System.exit(0);
		 }//closes if

		 In in1 = new In(args[0]);   // open file 1
		 In in2 = new In(args[1]);   // open file 2

		 String s;   // a string used to hold the content of the two files

		 s = in1.readAll();
		 s = s.toLowerCase();
		 s = s.replaceAll("[\",!.:;?()']", "");
		 String[] firstDoc = s.split("\\s+");

		 s = in2.readAll();
		 s = s.toLowerCase();
		 s = s.replaceAll("[\",!.:;?()']", "");
		 String[] secondDoc = s.split("\\s+");

		 long start = System.currentTimeMillis();
		 System.out.println("\nSimilarity: " + nestedLoop(firstDoc, secondDoc));
		 long stop = System.currentTimeMillis();
		 double elapsed = (stop - start) / 1000.0;
		 System.out.println("Nested Loop: " + elapsed + " seconds");

		 start = System.currentTimeMillis();
		 System.out.println("Similarity: " + indexNestedLoop(firstDoc, secondDoc));
		 stop = System.currentTimeMillis();
		 elapsed = (stop - start) / 1000.0;
		 System.out.println("Index Nested Loop: " + elapsed + " seconds");

		 start = System.currentTimeMillis();
		 System.out.println("Similarity: " + sortMerge(firstDoc, secondDoc));
		 stop = System.currentTimeMillis();
		 elapsed = (stop - start) / 1000.0;
		 System.out.println("Sort Merge: " + elapsed + " seconds\n");
	    }//closes main method
}//closes class