# StringMatch
String matching using some very efficient algorithms

In the Text file ( AESOP TALES.txt ) provided as data which contains urls, multiple spaces. The following taks have to be completed.


1  Find_Length_of _Text(txtfile) : Normalize text by removing multiple blank chars to single blank char and remove(store) and by removing                                    website URLS that have infected text file using FSA based RegEx matcher .

2  Find_Pattern  (pattern , InTextRange,  algo ) : Find the number of occurrences of pattern using any one of the following algorithms                                                        1. Rabin-Karp, 
                                                   2. Knuth_Morris_Pratt
                                                   3.Suffix Tree (with Suffix arrays & LCP)       
                                                   InTextRange : can be  indices or two patterns (e.g two story titles).
                                                 
3  Build_Cross_Index(txtfile, algo ) : Build an Index (Lex sorted), (Word, Number of occurrences).

4  Find_Maximal,Palindromes(PalindromeSize,  InTextRange ) : Maximal palindromes of size greater than or equal to PalindromeSize, with                                                                  occurrences (Story titles and  indices).

5  Print_Stats ( ) : Text Size used,  URL infection  list found, Algo Used, Preprocessing time, Search time (Vary the parameters pattern ,                       InTextRange )  & Verification outcome. 
                                                   
                                                   
