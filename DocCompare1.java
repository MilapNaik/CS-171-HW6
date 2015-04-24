public class DocCompare1 {
    // three methods for calculating the similarity of documents:

    public static double nestedLoop(String[] doc1, String[] doc2) {
		 // nested loop: sort doc1, for each unique word in doc1,
		 // find all occurences in doc2 using a sequential search
		 String temp1[] = java.util.Arrays.copyOf(doc1, doc1.length);
		 java.util.Arrays.sort(temp1);
		
		 double similarity = 0.0;
		 // used to test if only unique words are found
		 String last = "";
		
		 for (int i = 0; i < temp1.length; i++) {
		     if (!(temp1[i].equals(last))) {
		    	 String unique = temp1[i];
		    	 double count = 0.0;
		    	 for (int j = 0; j < doc2.length; j++) {
		    		 if (doc2[j].equals(unique)) {
		    			 count++;
		    		 }//closes if
		    	 }//closes for
		    	 similarity += count / doc2.length;
		     }//closes if
		     last = temp1[i];
		 	}//closes for
		 return similarity;
    }//closes method

    public static int binSearch(String[] document, String key, int index) {
		 int low = index;
		 int high = document.length - 1;
		 int mid;
		
		 while (low <= high) {
		     mid = (low + high) / 2;
		     int compare = document[mid].compareTo(key);
		     if (compare < 0) {
		    	 low = mid + 1;
		     }//closes if
		     else if (compare > 0) {
		    	 high = mid - 1;
		     }//closes else if
		     else {
		    	 return mid;
		     }//closes else
		 }//closes while
		 return -1;
	 }//closes method

    public static double indexNestedLoop(String[] doc1, String[] doc2) {
		 // index nested loop: sort doc1 and doc2, for each unique word in doc1,
		 // find all occurences in doc2 using a binary search
		 String temp1[] = java.util.Arrays.copyOf(doc1, doc1.length);
		 String temp2[] = java.util.Arrays.copyOf(doc2, doc2.length);
		 java.util.Arrays.sort(temp1);
		 java.util.Arrays.sort(temp2);
		
		 double similarity = 0.0;
		 // used to test if only unique words are found
		 String last = "";
		 int doc2Index = 0;
		
		 for (int i = 0; i < temp1.length; i++) {
		     if (!(temp1[i].equals(last))) {
		    	 String unique = temp1[i];
		    	 double count = 0;
		    	 int left = binSearch(temp2, unique, doc2Index);
		    	 int right = left;
		    	 int find = left;
			
		    	 if (find != -1) {
		    		 count = 1;
		    		 while (left - 1 >= 0) {
		    			 if (temp2[left - 1].compareTo(unique) == 0) {
		    				 count++;
		    				 left--;
		    			 }//closes if
		    			 else break;
		    		 }//closes while
		    		 while (right + 1 < doc2.length) {
		    			 if (temp2[right + 1].compareTo(unique) == 0) {
		    				 count++;
		    				 doc2Index = right;
		    				 right++;
		    			 }//closes if
		    			 else break;
		    		 }//closes while
		    	 }//closes if
		    	 similarity += count / temp2.length;
		     }//closes if
		     last = temp1[i];
		 }//closes for
		 return similarity;
	}//closes method

    public static double sortMerge(String[] doc1, String[] doc2) {
		 // sort merge: sort doc1 and doc2, for each unique word in doc1,
		 // find all occurences in doc2 by "merging"
		 String temp1[] = java.util.Arrays.copyOf(doc1, doc1.length);
		 String temp2[] = java.util.Arrays.copyOf(doc2, doc2.length);
		 java.util.Arrays.sort(temp1);
		 java.util.Arrays.sort(temp2);
		
		 String[] result = new String[temp2.length];
		
		 double similarity = 0.0;
		 // used to test if unique words are found
		 String last = "";
		 double count = 0.0;
		 int doc2Index = 0;
		
		 for (int i = 0; i < temp1.length; i++) {
		     if (!(temp1[i].equals(last))) {
		    	 String unique = temp1[i];
		    	 int k = 0;
		    	 while (doc2Index < temp2.length) {
			    	 if (unique.compareTo(temp2[doc2Index]) == 0) {
			    		 result[k] = temp2[doc2Index];
			    		 k++;
			    		 count++;
			    		 doc2Index++;
			    	 }//closes if
			    	 else if (unique.compareTo(temp2[doc2Index]) > 0) {
			    		 doc2Index++;
			    	 }//closes else if
			    	 else if (unique.compareTo(temp2[doc2Index]) < 0) {
			    		 break;
			    	 }//closes else if
		    	 }//closes while
		     }//closes if
		     last = temp1[i];
		 }//closes for
		 similarity = count / temp2.length;
		 return similarity;
	}//closes method

    public static void main(String[] args) {
    	if (args.length < 2) {
    		System.out.println("java DocCompare file1 file2");
    		System.exit(0);
    	}

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
    }
}