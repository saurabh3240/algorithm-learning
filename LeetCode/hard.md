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

