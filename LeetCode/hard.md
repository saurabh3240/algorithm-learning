#Regular Expression Matching (IMP)

Given an input string (`s`) and a pattern (`p`), implement regular expression matching with support for `'.'` and `'*'`.

```
'.' Matches any single character.
'*' Matches zero or more of the preceding element.
```

The matching should cover the **entire** input string (not partial).

**Note:**

- `s` could be empty and contains only lowercase letters `a-z`.
- `p` could be empty and contains only lowercase letters `a-z`, and characters like `.` or `*`.

My soluion


​                
```C++
class Solution {
public:
    bool isMatch(string s, string p) {
        int n = s.size();
        int m = p.size();
        vector<vector<bool> > v(m+1, vector<bool>(n+1,false));
        v[0][0]=true;
        for(int i=1;i<=m;i++)
        {
            if(p[i-1]=='*')
                v[i][0] = v[i-2][0];
            for(int j=1;j<=n;j++)
            {
                if(p[i-1]=='.')
                    v[i][j] = max(v[i-1][j-1],v[i][j]);
                else if(p[i-1]=='*')
                {
                    if(p[i-2]=='.')
                        v[i][j] =   max(v[i-2][j],v[i][j-1]);
                    else
                    {
                        bool ex =  p[i-2]==s[j-1]? bool(v[i][j-1]):false;
                        v[i][j] = max((bool)v[i-2][j],ex);
                    }                        
                }
                else
                    v[i][j] = p[i-1]==s[j-1] ? v[i-1][j-1]: false;
            }
        }        
        return v[m][n];
    }
};
```




#### Approach 1: Recursion

**Intuition**

If there were no Kleene stars (the `*` wildcard character for regular expressions), the problem would be easier - we simply check from left to right if each character of the text matches the pattern.

When a star is present, we may need to check many different suffixes of the text and see if they match the rest of the pattern. A recursive solution is a straightforward way to represent this relationship.

**Algorithm**

Without a Kleene star, our solution would look like this:

<iframe src="https://leetcode.com/playground/Z2XSmAHG/shared" frameborder="0" width="100%" height="123" name="Z2XSmAHG" style="box-sizing: border-box; color: rgb(51, 51, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;"></iframe>



If a star is present in the pattern, it will be in the second position \text{pattern[1]}pattern[1]. Then, we may ignore this part of the pattern, or delete a matching character in the text. If we have a match on the remaining strings after any of these operations, then the initial inputs matched.

<iframe src="https://leetcode.com/playground/EX8cYcs3/shared" frameborder="0" width="100%" height="293" name="EX8cYcs3" style="box-sizing: border-box; color: rgb(51, 51, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;"></iframe>



**Complexity Analysis**

- Time Complexity: Let T, PT,P be the lengths of the text and the pattern respectively. In the worst case, a call to `match(text[i:], pattern[2j:])` will be made \binom{i+j}{i}(ii+j) times, and strings of the order O(T - i)O(T−i)and O(P - 2*j)O(P−2∗j) will be made. Thus, the complexity has the order \sum_{i = 0}^T \sum_{j = 0}^{P/2} \binom{i+j}{i} O(T+P-i-2j)∑i=0T∑j=0P/2(ii+j)O(T+P−i−2j). With some effort outside the scope of this article, we can show this is bounded by O\big((T+P)2^{T + \frac{P}{2}}\big)O((T+P)2T+2P).

- Space Complexity: For every call to `match`, we will create those strings as described above, possibly creating duplicates. If memory is not freed, this will also take a total of O\big((T+P)2^{T + \frac{P}{2}}\big)O((T+P)2T+2P) space, even though there are only order O(T^2 + P^2)O(T2+P2) unique suffixes of PP and TT that are actually required. 

------

#### Approach 2: Dynamic Programming

**Intuition**

As the problem has an **optimal substructure**, it is natural to cache intermediate results. We ask the question \text{dp(i, j)}dp(i, j): does \text{text[i:]}text[i:] and \text{pattern[j:]}pattern[j:] match? We can describe our answer in terms of answers to questions involving smaller strings.

**Algorithm**

We proceed with the same recursion as in [Approach 1](https://leetcode.com/articles/regular-expression-matching/#approach-1-recursion), except because calls will only ever be made to `match(text[i:], pattern[j:])`, we use \text{dp(i, j)}dp(i, j) to handle those calls instead, saving us expensive string-building operations and allowing us to cache the intermediate results.

*Top-Down Variation* building operations and allowing us to cache the intermediate results.

*Top-Down Variation* 

*Bottom-Up Variation*

<iframe src="https://leetcode.com/playground/dmAyPDG3/shared" frameborder="0" width="100%" height="395" name="dmAyPDG3" style="box-sizing: border-box; color: rgb(51, 51, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;"></iframe>



**Complexity Analysis**

- Time Complexity: Let T, PT,P be the lengths of the text and the pattern respectively. The work for every call to `dp(i, j)` for i=0, ... ,Ti=0,...,T; j=0, ... ,Pj=0,...,P is done once, and it is O(1)O(1) work. Hence, the time complexity is O(TP)O(TP).
- Space Complexity: The only memory we use is the O(TP)O(TP) boolean entries in our cache. Hence, the space complexity is O(TP)O(TP).

## 23. Merge k Sorted List 

Merge *k* sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

**Example:**

```
Input:
[
  1->4->5,
  1->3->4,
  2->6
]
Output: 1->1->2->3->4->4->5->6
```

## Solution

------

#### Approach 1: Brute Force

**Intuition & Algorithm**

- Traverse all the linked lists and collect the values of the nodes into an array.
- Sort and iterate over this array to get the proper value of nodes.
- Create a new sorted linked list and extend it with the new nodes.

  ```c++
  class Solution {
  public:
      ListNode* mergeKLists(vector<ListNode*>& lists) {
          vector<int> temp;
          ListNode* curr;
          for(int i = 0; i < lists.size(); i++){
              curr = lists[i];
              while(curr){
                  temp.push_back(curr->val);
                  curr = curr->next;
              }
          }
          ListNode* head = NULL;
          if(!temp.empty()){
              sort(temp.begin(),temp.end());
              head = new ListNode(temp[0]);
          }
          curr = head;
          for(int i = 1; i < temp.size(); i++){
              ListNode* t = new ListNode(temp[i]);
              curr->next = t;
              curr = t;
          }
          return head;
      }
  };
  ```



**Complexity Analysis**

- Time complexity : O(Nlog N)where NN is the total number of nodes.

  - Collecting all the values costs O(N)O(N) time.
  - A stable sorting algorithm costs O(N\log N)O(NlogN) time.
  - Iterating for creating the linked list costs O(N)O(N) time.

- Space complexity : O(N)O(N).

  - Sorting cost O(N)O(N) space (depends on the algorithm you choose).

  - Creating a new linked list costs O(N)O(N) space. 

------

#### Approach 2: Compare one by one

**Algorithm**

- Compare every \text{k}k nodes (head of every linked list) and get the node with the smallest value.
- Extend the final sorted linked list with the selected nodes.

```c++
class Solution {
public:
    ListNode* mergeKLists(vector<ListNode*>& lists) {
        ListNode nn = ListNode(0);
        ListNode *temp, *root;
        root = &nn;
        temp = root;
        while(1)
        {
            vector<pair<int,int> > v;
            for(int i=0;i<lists.size();i++)
            {
                if(lists[i]!=NULL)
                {
                    v.push_back({lists[i]->val,i});
                }
            }
            if(v.size()==0)
                break;
            auto p =std::min_element(v.begin(), v.end());
            int ind = (*p).second;
            temp->next = lists[ind];
         lists[ind] = lists[ind]->next;
            temp = temp->next;
            
        }
        return root->next;
        
    }
};
```


**Complexity Analysis**

- Time complexity : O(kN)O(kN) where \text{k}k is the number of linked lists.

  - Almost every selection of node in final linked costs O(k)O(k) (\text{k-1}k-1 times comparison).
  - There are NN nodes in the final linked list.

- Space complexity :

  - O(n)O(n) Creating a new linked list costs O(n)O(n) space.

  - O(1)O(1) It's not hard to apply in-place method - connect selected nodes instead of creating new nodes to fill the new linked list. 

------

#### Approach 3: Optimize Approach 2 by Priority Queue

**Algorithm**

Almost the same as the one above but optimize the **comparison process** by **priority queue**. You can refer [here](https://en.wikipedia.org/wiki/Priority_queue) for more information about it.


```c++

class Comp
{ public:
    bool operator()(const ListNode  *a, const ListNode *b)
    {
    return a->val  > b->val;
    }
};
bool compare (const ListNode  *a, const ListNode *b)
{
    return a->val  < b->val;
}
#define pb push_back
#define rep(i,a) for(int i=0; i<a; ++i)
class Solution {
public:
    ListNode* mergeKLists(vector<ListNode*>& lists) {
        priority_queue<ListNode*, vector<ListNode*>, Comp > pq;
        vector<ListNode *> v;
        if(lists.size()==0)
                return NULL;
        rep(i,lists.size())
        {
            if(lists[i]!=NULL)
            {   
                pq.push(lists[i]);
            }
        }
        if(pq.size()==0)
            return NULL;
        ListNode *root,*temp,*next,*top;       
        root = NULL;
        int i=0;
        while(!pq.empty())
        {   top = pq.top();
            next = pq.top()->next;
            // top->next = NULL;
            pq.pop();
             if(next!=NULL)
                    pq.push(next);
            if(i==0)
            {
                temp = top;
                root = temp;
                i++;
            }
            else
            {   temp->next = top;
                temp = temp->next;        
            }
        }
     return root;
    }
};
```

**Complexity Analysis**

- Time complexity : O(N\log k)O(Nlogk) where \text{k}k is the number of linked lists.

  - The comparison cost will be reduced to O(\log k)O(logk) for every pop and insertion to priority queue. But finding the node with the smallest value just costs O(1)O(1) time.
  - There are NN nodes in the final linked list.

- Space complexity :

  - O(n)O(n) Creating a new linked list costs O(n)O(n) space.

  - O(k)O(k) The code above present applies in-place method which cost O(1)O(1) space. And the priority queue (often implemented with heaps) costs O(k)O(k) space (it's far less than NN in most situations).

------

#### Approach 4: Merge lists one by one

**Algorithm**

Convert merge \text{k}k lists problem to merge 2 lists (\text{k-1}k-1) times. Here is the [merge 2 lists](https://leetcode.com/problems/merge-two-sorted-lists/description/) problem page.

**Complexity Analysis**

- Time complexity : O(kN)O(kN) where \text{k}k is the number of linked lists.

  - We can merge two sorted linked list in O(n)O(n) time where nn is the total number of nodes in two lists.
  - Sum up the merge process and we can get: O(\sum_{i=1}^{k-1} (i*(\frac{N}{k}) + \frac{N}{k})) = O(kN)O(∑i=1k−1(i∗(kN)+kN))=O(kN).

- Space complexity : O(1)O(1)

  - We can merge two sorted linked list in O(1)O(1) space. 

------

#### Approach 5: Merge with Divide And Conquer

**Intuition & Algorithm**

This approach walks alongside the one above but is improved a lot. We don't need to traverse most nodes many times repeatedly

- Pair up \text{k}k lists and merge each pair.
- After the first pairing, \text{k}k lists are merged into k/2k/2 lists with average 2N/k2N/k length, then k/4k/4, k/8k/8 and so on.
- Repeat this procedure until we get the final sorted linked list.

Thus, we'll traverse almost NN nodes per pairing and merging, and repeat this procedure about \log_{2}{k}log2k times.

![Divide_and_Conquer](https://leetcode.com/articles/Figures/23/23_divide_and_conquer_new.png)

<iframe src="https://leetcode.com/playground/8nnKQ4tP/shared" frameborder="0" width="100%" height="500" name="8nnKQ4tP" style="box-sizing: border-box;"></iframe>

**Complexity Analysis**

- Time complexity : O(N\log k)O(Nlogk) where \text{k}k is the number of linked lists.
  - We can merge two sorted linked list in O(n)O(n) time where nn is the total number of nodes in two lists.
  - Sum up the merge process and we can get: O\big(\sum_{i=1}^{log_{2}{k}}N \big)= O(N\log k)O(∑i=1log2kN)=O(Nlogk)
- Space complexity : O(1)O(1)
  - We can merge two sorted linked lists in O(1)O(1) space.

## 42. Trapping Rain Water 

https://leetcode.com/articles/trapping-rain-water/

## 44. Wild Card Matching

- can be solve by as regular expression matcher dp space O(mn) and time  O(m,n)
  - only last row results are neccessary space complexity can be reduced to O(n) where n = |s|


    ```C++
    class Solution {
    public:
        bool isMatch(string s, string p) {
            int n = s.size();
            int m = p.size();
            vector<vector<bool> > v(m+1, vector<bool>(n+1,false));
            v[0][0]=true;
            for(int i=1;i<=m;i++)
            {
                if(p[i-1]=='*')
                {
                    v[i][0] = v[i-1][0];     
                }
                for(int j=1;j<=n;j++)
                {
                    if(p[i-1]=='?')
                    {
                        v[i][j] = max(v[i-1][j-1],v[i][j]);
                    }
                    else if(p[i-1]=='*')
                    {
                            v[i][j] =   max(v[i-1][j],v[i][j-1]);           
                    }
                    else
                    {
                        v[i][j] = p[i-1]==s[j-1] ? v[i-1][j-1]: false;
                    }
                }
            }
            return v[m][n];
            
        }
    };
    ```

- Can solve greedy storing the last star position

  - O(m,n) time and space is O(1)

- ```C++
  class Solution {
  public:
      bool isMatch(string s, string p) {
          int star=-1;
          int ss;
          int pindex = 0,sindex=0;
          int size = s.size();
         while (sindex<size){
              if ((p[pindex]=='?')||(p[pindex]==s[sindex])){sindex++;pindex++;continue;} 
              else if (p[pindex]=='*'){star=pindex++; ss=sindex;continue;} 
              if (star!=-1){ pindex = star+1; sindex=++ss;continue;} 
              return false;
          }
          while (p[pindex]=='*'){pindex++;}
          return pindex==p.size();  
      }
  };
  ```

- 

