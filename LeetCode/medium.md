

## 2. Add Two Numbers 

You are given two **non-empty** linked lists representing two non-negative integers. The digits are stored in **reverse order** and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

**Example:**

```
Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
Explanation: 342 + 465 = 807.
```

## Solution

------

#### Approach 1: Elementary Math

**Intuition**

Keep track of the carry using a variable and simulate digits-by-digits sum starting from the head of list, which contains the least-significant digit.

![Illustration of Adding two numbers](https://leetcode.com/articles/Figures/2_add_two_numbers.svg)

*Figure 1. Visualization of the addition of two numbers: 342 + 465 = 807.Each node contains a single digit and the digits are stored in reverse order.*

**Algorithm**

Just like how you would sum two numbers on a piece of paper, we begin by summing the least-significant digits, which is the head of l1l1 and l2l2. Since each digit is in the range of 0…9, summing two digits may "overflow". For example 5 + 7 = 12 In this case, we set the current digit to 2 and bring over the carry = 1 to the next iteration. carry must be either 00 or 11 because the largest possible sum of two digits (including the carry) is 9 + 9 + 1 = 19

The pseudocode is as following:

- Initialize current node to dummy head of the returning list.
- Initialize carry to 0.
- Initialize p and q to head of l1l1 and l2l2 respectively.
- Loop through listsl1l1  and l2l2 until you reach both ends.
  - Set xx to node pp's value. If pp has reached the end of l1l1, set to 00.
  - Set yy to node qq's value. If qq has reached the end of l2l2, set to 00.
  - Set sum = x + y + carrysum=x+y+carry.
  - Update carry = sum / 10carry=sum/10.
  - Create a new node with the digit value of (sum \bmod 10)(summod10) and set it to current node's next, then advance current node to next.
  - Advance both pp and qq.
- Check if carry = 1carry=1, if so append a new node with digit 11 to the returning list.
- Return dummy head's next node.

Note that we use a dummy head to simplify the code. Without a dummy head, you would have to write extra conditional statements to initialize the head's value.

Take extra caution of the following cases:

| Test case                             | Explanation                                                  |
| ------------------------------------- | ------------------------------------------------------------ |
| l1=[0,1]l1=[0,1] l2=[0,1,2]l2=[0,1,2] | When one list is longer than the other.                      |
| l1=[]l1=[] l2=[0,1]l2=[0,1]           | When one list is null, which means an empty list.            |
| l1=[9,9]l1=[9,9] l2=[1]l2=[1]         | The sum could have an extra carry of one at the end, which is easy to forget. |

<iframe src="https://leetcode.com/playground/5onAHA8v/shared" frameborder="0" width="100%" height="378" name="5onAHA8v" style="box-sizing: border-box;"></iframe>

**Complexity Analysis**

- Time complexity : O($\max(m, n)$) 
- Space complexity : O($\max(m, n)$) 

## 3. Longest Substring Without Repeating Characters 

Given a string, find the length of the **longest substring** without repeating characters.

**Example 1:**

```
Input: "abcabcbb"
Output: 3 
Explanation: The answer is "abc", with the length of 3. 
```

**Example 2:**

```
Input: "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
```

**Example 3:**

```
Input: "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3. 
             Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
```

## Solution

------

#### Approach 1: Brute Force

**Intuition**

Check all the substring one by one to see if it has no duplicate character.

**Algorithm**

Suppose we have a function `boolean allUnique(String substring)` which will return true if the characters in the substring are all unique, otherwise false. We can iterate through all the possible substrings of the given string `s` and call the function `allUnique`. If it turns out to be true, then we update our answer of the maximum length of substring without duplicate characters.

<iframe src="https://leetcode.com/playground/dDeYomT6/shared" frameborder="0" width="100%" height="395" name="dDeYomT6" style="box-sizing: border-box;"></iframe>

**Complexity Analysis**

- Time complexity : O(n^3)

- Space complexity : O(min(n, m))

------

#### Approach 2: Sliding Window

**Algorithm**

<iframe src="https://leetcode.com/playground/gajHJS2a/shared" frameborder="0" width="100%" height="361" name="gajHJS2a" style="box-sizing: border-box;"></iframe>

**Complexity Analysis**

- Time complexity : O(2n) = O(n) In the worst case each character will be visited twice by ii and jj.

- Space complexity : O(min(m, n)). Same as the previous approach. We need O(k) space for the sliding window, where k is the size of the `Set`. The size of the Set is upper bounded by the size of the string n and the size of the charset/alphabet m. 

------

#### Approach 3: Sliding Window Optimized

The above solution requires at most 2n steps. In fact, it could be optimized to require only n steps. Instead of using a set to tell if a character exists or not, we could define a mapping of the characters to its index. Then we can skip the characters immediately when we found a repeated character.

The reason is that if s[j]s[j] have a duplicate in the range [i, j)[i,j) with index j&#x27;j′, we don't need to increase ii little by little. We can skip all the elements in the range [i, j&#x27;][i,j′] and let ii to be j&#x27; + 1j′+1 directly.

**Java (Using HashMap)**

<iframe src="https://leetcode.com/playground/ers9VnKH/shared" frameborder="0" width="100%" height="310" name="ers9VnKH" style="box-sizing: border-box;"></iframe>

**Java (Assuming ASCII 128)**

The previous implements all have no assumption on the charset of the string `s`.

If we know that the charset is rather small, we can replace the `Map` with an integer array as direct access table.

Commonly used tables are:

- `int[26]` for Letters 'a' - 'z' or 'A' - 'Z'
- `int[128]` for ASCII
- `int[256]` for Extended ASCII

<iframe src="https://leetcode.com/playground/KgRWfFiE/shared" frameborder="0" width="100%" height="276" name="KgRWfFiE" style="box-sizing: border-box;"></iframe>

**Complexity Analysis**

- Time complexity : O(n)O(n). Index jj will iterate nn times.
- Space complexity (HashMap) : O(min(m, n))O(min(m,n)). Same as the previous approach.
- Space complexity (Table): O(m)O(m). mm is the size of the charset.





## 5. Longest Palindromic Substring 

Given a string **s**, find the longest palindromic substring in **s**. You may assume that the maximum length of **s**is 1000.

**Example 1:**

```
Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.
```

**Example 2:**

```
Input: "cbbd"
Output: "bb"
```

## Summary

This article is for intermediate readers. It introduces the following ideas: Palindrome, Dynamic Programming and String Manipulation. Make sure you understand what a palindrome means. A palindrome is a string which reads the same in both directions. For example, SS = "aba" is a palindrome, SS = "abc" is not.

## Solution

------

#### Approach 1: Longest Common Substring

**Common mistake**

Some people will be tempted to come up with a quick solution, which is unfortunately flawed (however can be corrected easily):

> Reverse SS and become S&#x27;S′. Find the longest common substring between SS and S&#x27;S′, which must also be the longest palindromic substring.

This seemed to work, let’s see some examples below.

For example, SS = "caba", S&#x27;S′ = "abac".

The longest common substring between SS and S&#x27;S′ is "aba", which is the answer.

Let’s try another example: SS = "abacdfgdcaba", S&#x27;S′ = "abacdgfdcaba".

The longest common substring between SS and S&#x27;S′ is "abacd". Clearly, this is not a valid palindrome.

**Algorithm**

We could see that the longest common substring method fails when there exists a reversed copy of a non-palindromic substring in some other part of SS. To rectify this, each time we find a longest common substring candidate, we check if the substring’s indices are the same as the reversed substring’s original indices. If it is, then we attempt to update the longest palindrome found so far; if not, we skip this and find the next candidate.

This gives us an O(n^2)O(n2) Dynamic Programming solution which uses O(n^2)O(n2) space (could be improved to use O(n)O(n) space). Please read more about Longest Common Substring [here](https://en.wikipedia.org/wiki/Longest_common_substring). 



------

#### Approach 2: Brute Force

The obvious brute force solution is to pick all possible starting and ending positions for a substring, and verify if it is a palindrome.

**Complexity Analysis**

- Time complexity : O($$n^3$$)O(n3). Assume that nn is the length of the input string, there are a total of \binom{n}{2} = \frac{n(n-1)}{2}(2n)=2n(n−1) such substrings (excluding the trivial solution where a character itself is a palindrome). Since verifying each substring takes O(n)O(n) time, the run time complexity is O(n^3)O(n3).

- Space complexity : O(1)O(1). 

------

#### Approach 3: Dynamic Programming

To improve over the brute force solution, we first observe how we can avoid unnecessary re-computation while validating palindromes. Consider the case "ababa". If we already knew that "bab" is a palindrome, it is obvious that "ababa" must be a palindrome since the two left and right end letters are the same.

We define P(i,j)P(i,j) as following:





P(i,j)={true,false,if the substring Si…Sj is a palindromeotherwise. P(i,j)={true,if the substring Si…Sj is a palindromefalse,otherwise. 





Therefore,



P(i, j) = ( P(i+1, j-1) \text{ and } S_i == S_j )P(i,j)=(P(i+1,j−1) and Si==Sj)



The base cases are:



P(i, i) = trueP(i,i)=true





P(i, i+1) = ( S_i == S_{i+1} )P(i,i+1)=(Si==Si+1)



This yields a straight forward DP solution, which we first initialize the one and two letters palindromes, and work our way up finding all three letters palindromes, and so on...

**Complexity Analysis**

- Time complexity : O($$n^2$$)O(n2). This gives us a runtime complexity of O(n^2)O(n2).
- Space complexity : O(n^2)O(n2). It uses O(n^2)O(n2) space to store the table.

**Additional Exercise**

Could you improve the above space complexity further and how? 



------

#### Approach 4: Expand Around Center

In fact, we could solve it in O(n^2)O(n2) time using only constant space.

We observe that a palindrome mirrors around its center. Therefore, a palindrome can be expanded from its center, and there are only 2n - 12n−1 such centers.

You might be asking why there are 2n - 12n−1 but not nn centers? The reason is the center of a palindrome can be in between two letters. Such palindromes have even number of letters (such as "abba") and its center are between the two 'b's.

<iframe src="https://leetcode.com/playground/5w5ZZtTd/shared" frameborder="0" width="100%" height="446" name="5w5ZZtTd" style="box-sizing: border-box;"></iframe>

**Complexity Analysis**

- Time complexity : O(n^2)O(n2). Since expanding a palindrome around its center could take O(n)O(n) time, the overall complexity is O(n^2)O(n2).

- Space complexity : O(1)O(1). 

------

#### Approach 5: Manacher's Algorithm

There is even an O(n)O(n) algorithm called Manacher's algorithm, explained [here in detail](https://articles.leetcode.com/longest-palindromic-substring-part-ii/). However, it is a non-trivial algorithm, and no one expects you to come up with this algorithm in a 45 minutes coding session. But, please go ahead and understand it, I promise it will be a lot of fun.











## 15. 3Sum

Medium

Given an array `nums` of *n* integers, are there elements *a*, *b*, *c* in `nums` such that *a*+ *b* + *c* = 0? Find all unique triplets in the array which gives the sum of zero.

**Note:**

The solution set must not contain duplicate triplets.

**Example:**

```
Given array nums = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]
```

Approach:

- sort the array
- for each element i search 2 sum for the target -nums[i];
  - solve by 2 pointers at j = i+1 and k = n-1;
    - avoid duplicates 
- Optimisation
  - check 3 element there
  - array contains only positive or only negative.
  - avoid duplicates by check consecutive element to be same

```c++
class Solution {
public:
    vector<vector<int>> threeSum(vector<int>& nums) {
        
        sort(nums.begin() , nums.end());
        vector< vector<int> > ans;
        int n = nums.size();
        if(n<3 || nums[0]>0 || nums[n-1]<0)
        {
            return ans;
        }
        for(int i=0;i<n-2;i++)
        {
            int target = - nums[i];
            int j =i+1;
            int k =n-1;
            if(i>0 && nums[i] == nums[i-1])
                continue;
            while(j<k)
            {
                int c = nums[j]+nums[k];
                if(c==target)
                {
                    ans.push_back({nums[i],nums[j],nums[k]});
                     while(j<k   && nums[j]==nums[j+1]) j++;
                    while(j<k  && nums[k]==nums[k-1]) k--;
                    j++;
                    k--;
                }
                else if (c<target) 
                    j++;
                else
                    k--;
            }
                   
        }
        return ans;
        
    }
};
```



## 22. Generate Parentheses 

Given *n* pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

For example, given *n* = 3, a solution set is:

```
[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]
```

#### Approach 1: Brute Force

**Intuition**

We can generate all 2^{2n}22n sequences of `'('` and `')'` characters. Then, we will check if each one is valid.

**Algorithm**

To generate all sequences, we use a recursion. All sequences of length `n` is just `'('` plus all sequences of length `n-1`, and then `')'` plus all sequences of length `n-1`.

To check whether a sequence is valid, we keep track of `balance`, the net number of opening brackets minus closing brackets. If it falls below zero at any time, or doesn't end in zero, the sequence is invalid - otherwise it is valid.

```c++
class Solution {
public:
    vector<string> generateParenthesis(int n) {
        vector<string> ans;
        for(int mask=0; mask < 1<<(2*n); mask++)
        {
            string s1= "";
            if(__builtin_popcount(mask)!=n)
                continue;
            for(int i =0; i <2*n;i++)
            {
                if(mask&1<<i)
                    s1+='(';
                else
                    s1+=')';
            }
            if(isValid(s1))
            ans.push_back(s1);
        }
        return ans;
    }
    bool isValid(string s) {
        stack<char> stk;
        for(int i=0;i<s.size();i++)
        {
            if(s[i]=='('||s[i]=='{'||s[i]=='[')
                stk.push(s[i]);
            else
            {
                if(stk.empty())
                    return false;
                if(s[i]==')')
                {
                    if(stk.top()!='(')
                        return false;
                    else 
                        stk.pop();
                }
                  if(s[i]=='}')
                {
                    if(stk.top()!='{')
                        return false;
                    else 
                        stk.pop();
                }
                  if(s[i]==']')
                {
                    if(stk.top()!='[')
                        return false;
                    else 
                        stk.pop();
                }
            }
        }
        if(stk.empty())
            return true;
        else
            return false;
    }
};
```
**Complexity Analysis**

- Time Complexity : O(2^{2n}n). For each of 2^{2n}sequences, we need to create and validate the sequence, which takes O(n)O(n) work.

- Space Complexity : O(2^{2n}n)O(22nn). Naively, every sequence could be valid. See [Approach 3](https://leetcode.com/articles/generate-parentheses/#approach-3-closure-number) for development of a tighter asymptotic bound. 

------

#### Approach 2: Backtracking

**Intuition and Algorithm**

Instead of adding `'('` or `')'` every time as in [Approach 1](https://leetcode.com/articles/generate-parentheses/#approach-1-brute-force), let's only add them when we know it will remain a valid sequence. We can do this by keeping track of the number of opening and closing brackets we have placed so far.

We can start an opening bracket if we still have one (of `n`) left to place. And we can start a closing bracket if it would not exceed the number of opening brackets.

<iframe src="https://leetcode.com/playground/npPa38Mh/shared" frameborder="0" width="100%" height="378" name="npPa38Mh" style="box-sizing: border-box;"></iframe>

**Complexity Analysis**

Our complexity analysis rests on understanding how many elements there are in `generateParenthesis(n)`. This analysis is outside the scope of this article, but it turns out this is the `n`-th Catalan number $$\frac{1}{n+1}\binom{2n}{n}$$, which is bounded asymptotically by $$\frac{4^n}{n\sqrt{n}}$$.

- Time Complexity : O ($$\frac{4^n}{\sqrt{n}}​$$ ). Each valid sequence has at most `n` steps during the backtracking procedure.

- Space Complexity : O($$\frac{4^n}{\sqrt{n}}$$). as described above, and using O(n) space to store the sequence. 

------

#### Approach 3: Closure Number

**Intuition**

To enumerate something, generally we would like to express it as a sum of disjoint subsets that are easier to count.

Consider the *closure number* of a valid parentheses sequence `S`: the least `index >= 0` so that `S[0], S[1], ..., S[2*index+1]` is valid. Clearly, every parentheses sequence has a unique *closure number*. We can try to enumerate them individually.

**Algorithm**

For each closure number `c`, we know the starting and ending brackets must be at index `0` and `2*c + 1`. Then, the `2*c` elements between must be a valid sequence, plus the rest of the elements must be a valid sequence.

<iframe src="https://leetcode.com/playground/Z3ZYfRAo/shared" frameborder="0" width="100%" height="293" name="Z3ZYfRAo" style="box-sizing: border-box;"></iframe>

**Complexity Analysis**

- Time and Space Complexity : O(\dfrac{4^n}{\sqrt{n}})O(n4n). The analysis is similar to [Approach 2](https://leetcode.com/articles/generate-parentheses/#approach-2-backtracking).





## 31. Next Permutation 

Implement **next permutation**, which rearranges numbers into the lexicographically next greater permutation of numbers.

If such arrangement is not possible, it must rearrange it as the lowest possible order (i.e, sorted in ascending order).

The replacement must be **in-place** and use only constant extra memory.

Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.

```
1,2,3` → `1,3,2`
`3,2,1` → `1,2,3`
`1,1,5` → `1,5,1
```

## Summary

We need to find the next lexicographic permutation of the given list of numbers than the number formed by the given array.

## Solution

------

#### Approach 1: Brute Force

**Algorithm**

In this approach, we find out every possible permutation of list formed by the elements of the given array and find out the permutation which is just larger than the given one. But this one will be a very naive approach, since it requires us to find out every possible permutation which will take really long time and the implementation is complex. Thus, this approach is not acceptable at all. Hence, we move on directly to the correct approach.

**Complexity Analysis**

- Time complexity : O(n!)O(n!). Total possible permutations is n!n!.

- Space complexity : O(n)O(n). Since an array will be used to store the permutations. 


------

#### Approach 2: Single Pass Approach

**Algorithm**

First, we observe that for any given sequence that is in descending order, no next larger permutation is possible. For example, no next permutation is possible for the following array: `[9, 5, 4, 3, 1]`

We need to find the first pair of two successive numbers a[i]a[i] and a[i-1]a[i−1], from the right, which satisfy a[i] &gt; a[i-1]a[i]>a[i−1]. Now, no rearrangements to the right of a[i-1]a[i−1] can create a larger permutation since that subarray consists of numbers in descending order. Thus, we need to rearrange the numbers to the right of a[i-1]a[i−1] including itself.

Now, what kind of rearrangement will produce the next larger number? We want to create the permutation just larger than the current one. Therefore, we need to replace the number a[i-1]a[i−1] with the number which is just larger than itself among the numbers lying to its right section, say a[j]a[j].

![ Next Permutation ](https://leetcode.com/media/original_images/31_nums_graph.png)

We swap the numbers a[i-1]a[i−1] and a[j]a[j]. We now have the correct number at index i-1i−1. But still the current permutation isn't the permutation that we are looking for. We need the smallest permutation that can be formed by using the numbers only to the right of a[i-1]a[i−1]. Therefore, we need to place those numbers in ascending order to get their smallest permutation.

But, recall that while scanning the numbers from the right, we simply kept decrementing the index until we found the pair a[i]a[i] and a[i-1]a[i−1] where, a[i] &gt; a[i-1]a[i]>a[i−1]. Thus, all numbers to the right of a[i-1]a[i−1] were already sorted in descending order. Furthermore, swapping a[i-1]a[i−1] and a[j]a[j] didn't change that order. Therefore, we simply need to reverse the numbers following a[i-1]a[i−1] to get the next smallest lexicographic permutation.

The following animation will make things clearer:

![Next Permutation](https://leetcode.com/media/original_images/31_Next_Permutation.gif)

```c++
class Solution {
public:
    void nextPermutation(vector<int>& A) {
        int n = A.size();
    int ii = -1;
    int jj = 0;
    int i;
    for(i=n-1;i>0;i--)
        if(A[i-1]<A[i])
        {  	ii = i-1;  
            break;
        }  
    if(ii==-1) // already reverse sorted 
    {  	reverse(A.begin(),A.end());
    	return ;
	}
    for(int j =n-1;j>=ii+1;j--)
       if(A[j]>A[ii])
        { 	jj  = j; 
            break;
        }   
    //swap
    int temp = A[ii];
    A[ii] = A[jj];
    A[jj] = temp;
    //reverse
    reverse(A.begin()+ii+1,A.end());
    }
};
```



**Complexity Analysis**

- Time complexity : O(n). In worst case, only two scans of the whole array are needed.
- Space complexity : O(1). No extra space is used. In place replacements are done.