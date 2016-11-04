/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.io.*;
/**
 *
 * @author Ganesh Bhargav
 */
class TextLength {
	ArrayList<String> titles;
    FileReader input_file = null;
    BufferedReader buffered_input_file = null;
    String text = new String();
    String pattern = new String("http");
    int n,m,tfcount;
    int tf[][]; 
    ArrayList<String> textbylines = new ArrayList<>(); 
    ArrayList<String> urls = new ArrayList<>();
    //ArrayList<String> title=new ArrayList<>();
    PrintWriter write = null;
    ArrayList<Character> ref = new ArrayList<>();
    public static final int NO_OF_CHARS = 256;
    HashMap<Integer,Integer> hm = new HashMap<>();
    String finaltext = new String();
    TextLength()
    {
        try
        {
        m = pattern.length();
        titles=new ArrayList<>();
        input_file = new FileReader("//home//anagh//Documents//AdvAlgo//Assignment_2//AESOP TALES (with random URLs).txt");
        buffered_input_file = new BufferedReader(input_file);
        System.out.println("Pattern Length = "+pattern.length());
        tf = new int[(pattern.length()+1)][NO_OF_CHARS];
        write = new PrintWriter("//home//anagh//Documents//AdvAlgo//Assignment_2//Output.txt","UTF-8");
        System.out.println("Pattern = "+pattern);
        
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
        
    }
    void removeextraspaces()
    {
      String line = null;
      try
      {
        while((line = buffered_input_file.readLine())!=null)
        {
            for(int i = 0;i<line.length()-1;++i)
            {
                if(line.charAt(i) == ' ' && line.charAt(i+1) == ' ')
                {
                    line = line.substring(0,i)+line.substring(i+1,line.length());
                }
				
            }
             textbylines.add(line);
             text+=line+" ";
         }
      }
      catch(IOException e)
      {
         e.printStackTrace();
      }
            
    }   
    
    String writetoFile()
    {
        write.println(text);
        return text;
    }
    int getNextState(int state, int x)
    {
        if (state < m && x == pattern.charAt(state))
            return state + 1;
        int ns, i;
        for (ns = state; ns > 0; ns--)
        {
            if (pattern.charAt(ns - 1) == x)
            {
                for (i = 0; i < ns - 1; i++)
                {
                    if (pattern.charAt(i) != pattern.charAt(state - ns + 1 + i))
                        break;
                }
                if (i == ns - 1)
                    return ns;
            }
        }
        return 0;
    }
    void finiteautomatonmatcher()
    {
        int n = text.length();
        int i, state = 0;
        for (i = 0; i < n; i++)
        {
            state = tf[state][text.charAt(i)];
            if (state == m)
            {
                int index = i-m+1;
                while(text.charAt(index)!=' ' && text.charAt(index)!='\n' && text.charAt(index)!='\t' && text.charAt(index)!='\r')
                {
                    index++;
                }
                String tempurl = text.substring(i-m+1, index);
                urls.add(tempurl);
                hm.put(i-m+1, index);
            }
        }
        
    }
   void removeURLS()
    {  
		StringBuffer textBuffer=new StringBuffer(text);
		for (String x : urls){
			int ind=textBuffer.indexOf(x);
			if(ind==-1) continue;
			int l=x.length();
			for(int i=0;i<l;i++)textBuffer.deleteCharAt(ind);
		}
		text=textBuffer.toString();
    }
    void Maketransitionfunction()
    {
        int state, x;
        for (state = 0; state <= m; ++state)
            for (x = 0; x <NO_OF_CHARS; ++x)
                tf[state][x] = getNextState(state, x);
    }
    void print_stats()
    {
        System.out.println("----------------------URLS Are-------------------------");
        for(String url : urls)
            System.out.println(url);
        System.out.println("Text length "+text.length());
        System.out.println("---------------------Titles are-----------------------");
        for(String i:titles){
            System.out.println(i);
            }
    }
        
    private boolean isEmptyLine(String x){
		for(int i=0;i<x.length();i++){
			int ord=(int)x.charAt(i);
			if((ord>=65 && ord<=90) || (ord>=97 && ord<=122) || ord==(int)'.')
				return false;
		}
		return true;
	}
	
	public void findTitles(){
		try{
			input_file = new FileReader("//home//anagh//Documents//AdvAlgo//Assignment_2//AESOP TALES (with random URLs).txt");
			buffered_input_file = new BufferedReader(input_file);
			String prev=buffered_input_file.readLine();
			String pws=buffered_input_file.readLine();
			String next=buffered_input_file.readLine();
			while(next!=null){
				if(isEmptyLine(prev) && isEmptyLine(next) && pws.indexOf('.')==-1)
						titles.add(pws);
				prev=pws;
				pws=next;
				next=buffered_input_file.readLine();
			}
		}
		catch(IOException e){}
	}
	
	public void appendStar(){
            StringBuffer textBuffer=new StringBuffer(text);
		for(String t: titles){
			int indx=textBuffer.indexOf(t);
			textBuffer.insert(indx,'*');
			textBuffer.insert(indx+t.length(),'*');
		}
                text=textBuffer.toString();
	}
    public static void main(String args[])
    {
    
     /*TextLength Tl = new TextLength(); 
     Tl.findTitles();
     Tl.appendStar();
     Tl.removeextraspaces();
     Tl.pattern="http";
     Tl.Maketransitionfunction();
     Tl.finiteautomatonmatcher();
     Tl.pattern="ftp";
     Tl.m=Tl.pattern.length();
     Tl.Maketransitionfunction();
     Tl.finiteautomatonmatcher();
     Tl.removeURLS();
     String text = Tl.writetoFile();
     System.out.println(Tl.text);
     Tl.print_stats();
     

     //call the Find_Pattern
     System.out.println("Time is in nano seconds");
     System.out.println("-------------------RabinKarp----------------------");
     long rabinstart = System.nanoTime();
     Tl.RabinfindPattern(text,"start",0,len);
     long rabinend = System.nanoTime();
     System.out.println("Rabin Karp Execution = "+(rabinend-rabinstart));
    
     
     long kmpstart = System.nanoTime();
     System.out.println("-------------------KMP--------------------------");
     Tl.KMPfindPattern(text,"start");  
     long kmpend = System.nanoTime();
     System.out.println("KMP Execution = "+(kmpend-kmpstart));
     
     
     long crossindexstart = System.nanoTime();
     System.out.println("-----------------Build_Cross_Index-----------------");
     Tl.add_index(text);
     long crossindexend = System.nanoTime();
     System.out.println("Cross Index Execution = "+(crossindexend-crossindexstart));
     
     
     System.out.println("-----------------suffix Array-----------------");
     
     long suffixarraystart = System.nanoTime();
     suffixArray suffix = new suffixArray(text+'*');
     System.out.println(suffix.numOfPatternOcurrences("start"));
     long suffixarrayend = System.nanoTime();
     System.out.println("Suffix array Execution = "+(suffixarrayend-suffixarraystart)); 

     System.out.println("---------Palindrome------------");
     Tl.Palindrome(text);*/
     System.out.println("Time is in nano seconds");
     long rabinstart = System.nanoTime();

     TextLength Tl = new TextLength();   
     Tl.removeextraspaces();
     Tl.pattern="http";
     Tl.Maketransitionfunction();
     Tl.finiteautomatonmatcher();
     Tl.pattern="ftp";
     Tl.m=Tl.pattern.length();
     Tl.Maketransitionfunction();
     Tl.finiteautomatonmatcher();
     Tl.removeURLS();
     Tl.findTitles();
     Tl.appendStar();
     String text = Tl.writetoFile();
     //System.out.println(text);
     Tl.print_stats();
     long rabinend = System.nanoTime();
     System.out.println("Normalization Execution = "+(rabinend-rabinstart));
     System.out.println();

     int len=text.length();
     //call the Find_Pattern
     System.out.println("-------------------RabinKarp----------------------");
     rabinstart = System.nanoTime();
     Tl.RabinfindPattern(text,"The Two Dogs",56541,59160);
     rabinend = System.nanoTime();
     System.out.println("Rabin Karp Execution = "+(rabinend-rabinstart));
     System.out.println();

     System.out.println("-------------------KMP--------------------------");
     rabinstart = System.nanoTime();
     Tl.KMPfindPattern(text,"The Two Dogs",0,len); 
     rabinend = System.nanoTime();
     System.out.println("KMP Execution = "+(rabinend-rabinstart));
     System.out.println();

     System.out.println("-------------------RabinKarp With Pattern--------------------------");
     rabinstart = System.nanoTime();
     Tl.wrapRabin(text,"he","The Two Dogs","The Widow and the Sheep");
     rabinend = System.nanoTime();
     System.out.println("Rabin Karp  Execution = "+(rabinend-rabinstart));
     System.out.println();

     System.out.println("-----------------suffix Array-----------------");
     rabinstart = System.nanoTime();
     suffixArray suffix = new suffixArray(text+'*');
     System.out.println("Number of occurences "+suffix.numOfPatternOcurrences("start"));
     rabinend = System.nanoTime();
     System.out.println("Suffix Execution = "+(rabinend-rabinstart));
     System.out.println();

     System.out.println("-----------------Build_Cross_Index-----------------");
     rabinstart = System.nanoTime();
     Tl.add_index(text);
     rabinend = System.nanoTime();
     System.out.println("Execution = "+(rabinend-rabinstart));
     System.out.println();
     
     System.out.println("---------Palindrome------------");
     rabinstart = System.nanoTime();
     Tl.Palindrome(text);
     rabinend = System.nanoTime();
     System.out.println("Palindrome Execution = "+(rabinend-rabinstart));
     System.out.println();
        
    }
 /*--------------------------------------------------------------------------------------------------------------------*/   
    /*Find_Pattern part starts here */

    //RabinKarp    
    void wrapRabin(String text,String pattern,String start,String end){
        int st=text.indexOf(start);
        int en=text.indexOf(end);
        System.out.println(st+"  "+en);

        RabinfindPattern(text,pattern,st,en);
    }
    
    void RabinfindPattern(String text,String pattern,int start,int end){
		int d=128;
		int q=100;
		int n=text.length();
		int m=pattern.length();
		int t=0,p=0;
		int h=1;
		int i,j;
		for (i=0;i<m-1;i++)
        		h = (h*d)%q;
        	for (i=0;i<m;i++){
			p = (d*p + pattern.charAt(i))%q;
			t = (d*t + text.charAt(i))%q;
			}
		for(i=0;i<end-m;i++){
			if(p==t){
				for(j=0;j<m;j++)
					if(text.charAt(j+i)!=pattern.charAt(j))
					break;
				if(j==m && i>=start)
					System.out.println("Pattern match found at index "+i);			
			}
			if(i<end-m){
				t =(d*(t - text.charAt(i)*h) + text.charAt(i+m))%q;
				if(t<0)
					t=t+q;
			}	
		}								
	}
	/*--------------------------------------------------------------------------------------------------------------------*/
	//KMP
	void wrapKMP(String text,String pattern,String start,String end){
        int st=text.indexOf(start);
        int en=text.indexOf(end);
        System.out.println(st+"  "+en);
        KMPfindPattern(text,pattern,st,en);
    }
	
	int[] SPA;
	void KMPfindPattern(String text,String pattern,int start,int end){
	    int n=text.length(), m=pattern.length();
	    int i=start;//for text
	    int j=0;//for pattern
	    SPA=new int[m];
	    computeSPArray(m,pattern,SPA);
	    while(i<end){
	      if(pattern.charAt(j)==text.charAt(i)){
		 i++;
		 j++;	
	      }	
	      if(j==m){
	         System.out.println("The pattern found at "+(i-j));
		 j=SPA[j-1];
		}
	      else if (i < end && pattern.charAt(j) != text.charAt(i)){
		if (j != 0)
		   j=SPA[j-1];
		else
		   i=i+1;
		}
	    }
	}
	
	
	//KMP Pre process
	void computeSPArray(int m,String pattern,int[] SPA){
	    int len=0;
	    int i=1;
       // System.out.println(pattern.charAt(m-1));

	    while(i<=m-1){

	       if(pattern.charAt(i) == pattern.charAt(len)){
                  len++;
                  SPA[i] = len;
                  i++;
               }
               else{
               	  if(len!=0){
               	     len=SPA[len]-1;
                     if(len < 0)
                        len=0;   
                  }
                  

               	  else{
               	     SPA[i]=len;
               	     i++;
               	    }   
               }	   
	    }
	}

	//suffix tree part 
/*--------------------------------------------------------------------------------------------------------------------*/
/*Find_Pattern part ends here */  
	
/*Build_Cross_Index starts*/
  
	void add_index(String s){
       // System.out.println(s);
	HashMap<String,Integer> hmap = new HashMap<String,Integer>();
	   int n=s.length();
	   String[] ar=s.split(" ");
	   String key;
       System.out.println("arr leangth is : " +ar.length);
	   for (int i = 0; i < ar.length; i++){
	      ar[i] = ar[i].replaceAll("[\\(,.!;:'*&\\)?\"]","");
	      ar[i] = ar[i].replaceAll("[0-9]","");
          key=ar[i].toUpperCase();
          
          if(hmap.containsKey(key)) {
          hmap.put(key, hmap.get(key) + 1);  
          }
          else{
	      hmap.put(ar[i].toUpperCase(),1);
	      }
        }  
	   //build_index(hmap);
       Map<String,Integer> map = new TreeMap<String,Integer>(hmap);
       GenerateCrossIndex(map,titles,s);   
	}
	
	/*void build_index(HashMap hmap){
           Map<String,Integer> map = new TreeMap<String,Integer>(hmap); 
           Set set = map.entrySet();
           Iterator iterator = set.iterator();
           while(iterator.hasNext()) {
               Map.Entry me2 = (Map.Entry)iterator.next();
               System.out.print(me2.getKey() + ": ");
               System.out.println(me2.getValue());
               }	
	}*/
    int RabinfindIndex(String text,String pattern){
        int d=128;
        int q=100;
        int n=text.length();
        int m=pattern.length();
        int t=0,p=0;
        int h=1;
        int i,j;
        for (i=0;i<m-1;i++)
                h = (h*d)%q;
            for (i=0;i<m;i++){
            p = (d*p + pattern.charAt(i))%q;
            t = (d*t + text.charAt(i))%q;
            }
        for(i=0;i<n-m;i++){
            if(p==t){
                for(j=0;j<m;j++)
                    if(text.charAt(j+i)!=pattern.charAt(j))
                    break;
                if(j==m)
                    break;          
            }
            if(i<n-m){
                t =(d*(t - text.charAt(i)*h) + text.charAt(i+m))%q;
                if(t<0)
                    t=t+q;
            }   
        }
        return i;                               
    }
    void GenerateCrossIndex(Map mp,ArrayList<String> titles,String s)
    {
         String text=s;
         //System.out.println(s);
         Set wordset = mp.entrySet();
         Iterator itr = wordset.iterator();
         int j=0;
         int[] start=new int[titles.size()];
         int[] end=new int[titles.size()];
            while(j<titles.size())
         {
            start[j] = RabinfindIndex(text,titles.get(j));
            j++;
         }
         //System.out.println(Arrays.toString(start));
         while(itr.hasNext()){
               Map.Entry me = (Map.Entry)itr.next();
               String wrd=(String)me.getKey();

               System.out.print(me.getKey() + ": ");
               System.out.println(me.getValue());
               
               /*System.out.println();
               for(int i=0;i<titles.size()-1;i++){
                int count=RabinKarp(text,wrd,start[i],start[i+1]);

                if(count!=0)
                    System.out.print(titles.get(i)+":"+count+"\t");
               
               }
               System.out.println();   */         
         }

    }

    int RabinKarp(String text,String pattern,int start,int end){
        int d=128;
        int q=100;
        int n=text.length();
        int m=pattern.length();
        int t=0,p=0;
        int h=1;
        int i,j;
        text=text.toUpperCase();
        for (i=0;i<m-1;i++)
                h = (h*d)%q;
            for (i=0;i<m;i++){
            p = (d*p + pattern.charAt(i))%q;
            t = (d*t + text.charAt(i))%q;
            }
        int count=0;    
        while(i < end-m){
            if(p==t){
                for(j=0;j<m;j++)
                    if(text.charAt(j+i)!=pattern.charAt(j))
                    break;
                if(j==m && i>=start){
                    count++;
                }
            }
            if(i<end-m){
                t =(d*(t - text.charAt(i)*h) + text.charAt(i+m))%q;
                if(t<0)
                    t=t+q;
            }
        i++;       
        }
        return count;
    }     

/*Build_Cross_Index ends*/

/*--------------------------------------------------------------------------------------------------------------------*/

/*Palindrome starts here*/
static void Palindrome(String text)
    {
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++){
          words[i] = words[i].replaceAll("[\\(,.!;:'*&\\)?\"]","");
          words[i] = words[i].replaceAll("[0-9]","");
      }
      //System.out.println(Arrays.toString(words));
        int longest = 0;
        for(int i =0;i<words.length;i++)
        {
            boolean isPalindrome = true;
            //if(words[i].length()>longest)
            //{
            for(int j=0,k=words[i].length()-1;j<k;j++,k--)
            {
                if(words[i].charAt(j)!=words[i].charAt(k))
                {   
                    isPalindrome = false;
                    break;
                }
            }
            //}
            if(isPalindrome && words[i].length()!=1 && words[i].length()!=0)
                {
                //  longest = words[i].length();
                    System.out.println(words[i]);
                }
        }
    }

/*Palindrome ends here*/
	  
}

class suffixArray {
    private Suffix[] suffixes;
    String text;
    private int lcp[];

    public suffixArray(String text) {
        
        this.text = text;
        int n = text.length();
        System.out.println("text length is : " +text.length());

        this.suffixes = new Suffix[n];

        int i=0;
        StringBuilder subtext ;
        while(i<text.length())
        {
           //System.out.println(i);
            subtext = new StringBuilder();
            int j=0;
            while(text.charAt(i+j)!='*')
            {
                subtext.append(text.charAt(i+j));
                j++;
            }

            subtext.append('$');
            j++;

            for(j = 0; j < subtext.length(); j++)
            {
                suffixes[i+j] = new Suffix(i+j,i+subtext.length()-1);
                
            }
            i=i+subtext.length();

        }
        Arrays.sort(suffixes);

       initializeLCP();
    }
    private void initializeLCP()
    {
        lcp = new int[suffixes.length];
        for(int i=0;i<suffixes.length;i++)
            lcp[i] = -1;
    }

    private class Suffix implements Comparable<Suffix> {
        private final int start;
        private final int end;

        private Suffix(int start, int end) {
            this.start = start;
            this.end = end;
        }
        private int length() {
            return end-start+1;
        }
        private char charAt(int i) {
            return suffixArray.this.text.charAt(this.start+i);
        }

        public int compareTo(Suffix that) {
            if (this == that) return 0;  // optimization
            int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return this.length() - that.length();
        }

        public String toString() {
            return suffixArray.this.text.substring(start,end+1);
        }
    }


    public int length() {
        return suffixes.length;
    }

    public Suffix index(int i) {
        if (i < 0 || i >= suffixes.length) throw new IndexOutOfBoundsException();
        return suffixes[i];
    }
    public int getLCP(String s1,Suffix s2)
    {
        int n = Math.min(s1.length(), s2.length());
        for (int i = 0; i < n; i++) {
            if (s1.charAt(i) != s2.charAt(i)) return i;
        }
        return n;
    }
    public int getLCP(int i) {
        if (i < 1 || i >= suffixes.length) throw new IndexOutOfBoundsException();
        if(lcp[i]==-1) 
            {
                lcp[i] = calculateLCP(suffixes[i], suffixes[i-1]);
            }
        return lcp[i];
    }

    // longest common prefix of s and t
    private static int calculateLCP(Suffix s, Suffix t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i)) return i;
        }
        return n;
    }

    public String select(int i) {
        if (i < 0 || i >= suffixes.length) throw new IndexOutOfBoundsException();
        return suffixes[i].toString();
    }

    public int rank(String query) {
        int lo = 0, hi = suffixes.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compare(query, suffixes[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    // compare query string to suffix
    private static int compare(String query, Suffix suffix) {
        int n = Math.min(query.length(), suffix.length());
        for (int i = 0; i < n; i++) {
            if (query.charAt(i) < suffix.charAt(i)) return -1;
            if (query.charAt(i) > suffix.charAt(i)) return +1;
        }
        return query.length() - suffix.length();
    }
    public int numOfPatternOcurrences(String query)
    {
        int low = 0;
        int high = suffixes.length-1;
        int index = patternSearch(query,low,high);

        int li,hi;
        li = index-1;
        hi = index+1;
        if(index!=-1)
        {
            int count = 1;
            while(li>=0)
            {
                if(getLCP(query,suffixes[li])==query.length())
                {
                    count++;
                    li--;
                }
                else
                {
                    break;
                }

            }
            while(hi<suffixes.length)
            {
                if(getLCP(query,suffixes[hi])==query.length())
                {
                    count++;
                    hi++;
                }
                else
                {
                    break;
                }
            }
            return count;
        }
        else return 0;

    }
    public int patternSearch(String query,int low,int high)
    {
        if(low<high)
        {
            int mid = (low+high)/2;
            if(getLCP(query,suffixes[mid])==query.length())
                return mid;
            int lcp = getLCP(query,suffixes[mid]);
            if(lcp==query.length())
            {
                return mid;
            }
            else if(suffixes[mid].length()>=query.length() || lcp<suffixes[mid].length())
            {
                if(query.charAt(lcp)>suffixes[mid].charAt(lcp))
                    return patternSearch(query,mid+1,high);
                else 
                    return patternSearch(query,low,mid);
            }
            else
            {
                return patternSearch(query,mid+1,high);
            }
            
        }
        return -1;
    }

}    
    
