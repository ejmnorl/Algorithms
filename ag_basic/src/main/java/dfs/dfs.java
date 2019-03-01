package dfs;

public class dfs {
    /*todo : note!!!! What's the time complexity???!!!!!!   O(n*2^n)
     Subsets
    Given a set of distinct integers, return all possible subsets.

    Example
    If S = [1,2,3], a solution is:

    [
      [3],
      [1],
      [2],
      [1,2,3],
      [1,3],
      [2,3],
      [1,2],
      []
    ]
    Challenge
    Can you do it in both recursively and iteratively?
     */
    //M1: recursive
    public List<List<Integer>> subsets(int[] nums) {
        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Arrays.sort(nums);
        ret.add(new ArrayList<>());
        dfs(nums, 0, path, ret);
        return ret;
    }

    private void dfs(int[] nums, int start, List<Integer> path, List<List<Integer>> ret) {
        if (start >= nums.length) {
            return;
        }
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);
            ret.add(new ArrayList<>(path));
            core(nums, i + 1, path, ret);
            path.remove(path.size() - 1);
        }
    }

    //M2: iterative
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        ret.add(new ArrayList<>());

        for (int i = 0; i < nums.length; i++) {
                for (List<Integer> list : ret) { //NOTE: java.util.ConcurrentModificationException!!!!!!!!
                    List<Integer> newL = new ArrayList<>(list);
                    newL.add(nums[i]);
                    ret.add(newL);
                }
        }
        return ret;
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        ret.add(new ArrayList<>());
        Arrays.sort(nums);
        for (int j = 0; j < nums.length; j++) {
            int size = ret.size();
            for (int i = 0; i < size; i++) {
                List<Integer> list = ret.get(i);
                List<Integer> newL = new ArrayList<>(list);
                newL.add(nums[j]);
                ret.add(newL);
            }
        }
        return ret;
    }

    /*
    https://www.lintcode.com/problem/combination-sum/description
    Combination Sum
    Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

    The same repeated number may be chosen from C unlimited number of times.

    Example
    Given candidate set [2,3,6,7] and target 7, a solution set is:

    [7]
    [2, 2, 3]
    Notice
    All numbers (including target) will be positive integers.
    Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
    The solution set must not contain duplicate combinations.
     */

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return ret;
        }
        Arrays.sort(candidates);
        core(candidates, target, 0, 0, ret, list);
        return ret;
    }

    private void core(int[] candidates, int target, int sum, int start, List<List<Integer>> ret, List<Integer> list) {
        if (sum > target) {
            return;
        }
        if (sum == target) {
            ret.add(new ArrayList<>(list));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if(i > 0 && candidates[i] == candidates[i - 1]) {
                continue;
            }
            list.add(candidates[i]);
            core(candidates, target, sum + candidates[i], i, ret, list);
            list.remove(list.size() - 1);
        }

    }

    /*
    https://www.lintcode.com/problem/combination-sum-ii/description
    Combination Sum II
    Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
    Each number in C may only be used once in the combination.
    Example
    Given candidate set [10,1,6,7,2,1,5] and target 8,

    A solution set is:

    [
      [1,7],
      [1,2,5],
      [2,6],
      [1,1,6]
    ]
    Notice
    All numbers (including target) will be positive integers.
    Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
    The solution set must not contain duplicate combinations.
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return ret;
        }
        Arrays.sort(candidates);
        core(candidates, target, 0, 0, ret, list);
        return ret;
    }

    private void core(int[] candidates, int target, int sum, int start, List<List<Integer>> ret, List<Integer> list) {
        if (sum > target) {
            return;
        }
        if (sum == target) {
            ret.add(new ArrayList<>(list));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if(i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            if (candidates[i] > target) {
                break;
            }
            list.add(candidates[i]);
            core(candidates, target, sum + candidates[i], i + 1, ret, list);
            list.remove(list.size() - 1);
        }
    }

    /*
    https://www.lintcode.com/problem/palindrome-partitioning/description
    Palindrome Partitioning
    Given a string s, partition s such that every substring of the partition is a palindrome.
    Return all possible palindrome partitioning of s.
    Example
    Given s = "aab", return:
    [
      ["aa","b"],
      ["a","a","b"]
    ]
     */
    public List<List<String>> partition(String s) {
        // write your code here
        List<List<String>> ret = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ret;
        }
        List<String> list = new ArrayList<>();
        dfs(s, 0, list, ret);
        return ret;
    }

    private void dfs(String s, int start, List<String> path, List<List<String>> ret) {
        if (start == s.length()) {
            ret.add(new ArrayList<>(path));
            return;
        }

        for (int j = start + 1; j <= s.length(); j++) {
            String newS = s.substring(start, j);
            if (isPalindrome(newS)) {
                path.add(newS);
                dfs(s, j, path, ret);
                path.remove(path.size() - 1);
            }
        }

    }

    private boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

    NOTE: 一共有2^(n-1)种切法
    say， "aab"  看做 "a1a2b3"
          切成： a ab   ==> 选[1]
                a a b  ==> 选[1,2]
                aab    ==> 选[]
                aa  b  ==> 选[2]
        一共有2(n-1)种切法

    /*
    https://www.lintcode.com/problem/permutations/description
    Permutations
    Given a list of numbers, return all possible permutations.

    Example
    For nums = [1,2,3], the permutations are:

    [
      [1,2,3],
      [1,3,2],
      [2,1,3],
      [2,3,1],
      [3,1,2],
      [3,2,1]
    ]
    Challenge
    Do it without recursion.
     */

    public List<List<Integer>> permute(int[] nums) {
        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        if (nums == null ) {
            return ret;
        }
        List<Integer> path = new ArrayList<>();
        dfs(nums, path, ret);
        return ret;
    }

    private void dfs(int[] nums, List<Integer> path, List<List<Integer>> ret) {
        if (path.size() == nums.length) {
            ret.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!path.contains(nums[i])) {
                path.add(nums[i]);
                dfs(nums, path, ret);
                path.remove(path.size() - 1);
            }
        }
    }
    /* redo
    https://www.lintcode.com/problem/permutations-ii/description
     Permutations II
    Given a list of numbers with duplicate number in it. Find all unique permutations.

    Example
    For numbers [1,2,2] the unique permutations are:

    [
      [1,2,2],
      [2,1,2],
      [2,2,1]
    ]
    Challenge
    Using recursion to do it is acceptable. If you can do it without recursion, that would be great!

     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        if (nums == null) {
            return ret;
        }
        Arrays.sort(nums);
        core(nums, ret, list, visited);
        return ret;
    }

    private void core(int[] nums, List<List<Integer>> ret, List<Integer> list,  Set<Integer> visited ) {
        if (list.size() == nums.length) {
            ret.add(new ArrayList<>(list));
        }

        for (int i = 0; i < nums.length; i++) {
            if (i != 0 && nums[i] == nums[i - 1] && !visited.contains(i - 1)) {
                continue;
            }
            if (!visited.contains(i)) {
                visited.add(i);
                list.add(nums[i]);
                core(nums, ret, list, visited);
                list.remove(list.size() - 1);
                visited.remove(i);
            }
        }
    }

    /* redo: needs to be more simplified and clean, here's check diag. logic is ugly
    https://www.lintcode.com/problem/n-queens/
    N-Queens
    The n-queens puzzle is the problem of placing n queens on an n×n chessboard
    such that no two queens attack each other.
    Given an integer n, return all distinct solutions to the n-queens puzzle.
    Each solution contains a distinct board configuration of the n-queens' placement,
    where 'Q' and '.' both indicate a queen and an empty space respectively.

    Example
    There exist two distinct solutions to the 4-queens puzzle:
    [
      // Solution 1
      [".Q..",
       "...Q",
       "Q...",
       "..Q."
      ],
      // Solution 2
      ["..Q.",
       "Q...",
       "...Q",
       ".Q.."
      ]
    ]
    Challenge
    Can you do it without recursion?
     */
    public List<List<String>> solveNQueens(int n) {
        // write your code here
        List<List<String>> ret = new ArrayList<>();
        if (n <= 0) {
            return ret;
        }
        boolean[][] visited = new boolean[n][n];
        List<String> list = new ArrayList<>();
        dfs(n, 0, ret, list, visited);
        return ret;
    }

    private void dfs(int n, int level, List<List<String>> ret, List<String> list, boolean[][] visited) {
            if (level == n) {
                ret.add(new ArrayList<>(list));
                return;
            }

            for (int i = 0; i < n; i++) {
                if (isValid(level, i, visited)) {
                    visited[level][i] = true;
                    list.add(buildStr(i, n));
                    dfs(n, level + 1, ret, list, visited);
                    list.remove(list.size() - 1);
                    visited[level][i] = false;
                }
            }
    }

    private String buildStr(int i, int n) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < n; k++) {
            if (k == i) {
                sb.append("Q");
            } else {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    private boolean isValid(int r, int c, boolean[][] visited) {
        for (int i = 0; i < r; i++) {
            if (visited[i][c]) {
                return false;
            }
        }

        //dia
        int n = visited.length;
        int delta1 = n - 1;
        int delta2 = n + 1;
        int start = r * n + c;
        while (start - delta1 >= 0) {
            start -= delta1;
            if (visited[start / n][start % n]) {
                return false;
            }
        }
        start = r * n + c;
        while (start - delta2 >= 0) {
            start -= delta2;
            if (visited[start / n][start % n]) {
                return false;
            }
        }
        return true;
    }

    /*
    https://www.lintcode.com/problem/word-ladder/description
    Word Ladder
    Given two words (start and end), and a dictionary,
    find the length of shortest transformation sequence from start to end, such that:

    Only one letter can be changed at a time
    Each intermediate word must exist in the dictionary
    Example
    Given:
    start = "hit"
    end = "cog"
    dict = ["hot","dot","dog","lot","log"]
    As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
    return its length 5.

    Notice
    Return 0 if there is no such transformation sequence.
    All words have the same length.
    All words contain only lowercase alphabetic characters.
     */

    public int ladderLength(String start, String end, Set<String> dict) {
        // write your code here
        if (dict == null || dict.size() == 0) {
            return 0;
        }
        if (start.equals(end)) {
            return 1;
        }
        Queue<String> q = new LinkedList<>();
        if (!dict.contains(end)) {
            dict.add(end);
        }
        if (dict.contains(start)) {
            dict.remove(start);
        }

        int len = 1;
        q.offer(start);
        while (!q.isEmpty()) {
            int size = q.size();
            len++;
            for (int i = 0; i < size; i++) {
                String cur = q.poll();
                for (String neighbor : getNeighbors(dict, cur)) {
                    // System.out.println("neighbor = " + neighbor);
                    if (neighbor.equals(end)) {
                        return len;
                    }
                    q.offer(neighbor);

                }
            }
        }
        return -1;
    }

    private List<String> getNeighbors(Set<String> dict, String word) {
        List<String> ret = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {

            for (char x = 'a'; x <= 'z'; x++) {

                char ori = chars[i];
                if (x == ori) {
                    continue;
                }
                chars[i] = x;
                String newW = new String(chars);
                // System.out.println("newW = " + newW);
                if (dict.contains(newW)) {
                    ret.add(newW);
                    dict.remove(newW);
                }
                chars[i] = ori;
            }
        }
        return ret;
    }

    /*
    https://www.lintcode.com/problem/word-ladder-ii/
    Word Ladder II
    Given two words (start and end), and a dictionary,
    find all shortest transformation sequence(s) from start to end, such that:

    Only one letter can be changed at a time
    Each intermediate word must exist in the dictionary
    Example
    Given:
    start = "hit"
    end = "cog"
    dict = ["hot","dot","dog","lot","log"]

    Return
    [
      ["hit","hot","dot","dog","cog"],
      ["hit","hot","lot","log","cog"]
    ]
    Notice
    All words have the same length.
    All words contain only lowercase alphabetic characters.
     */
    public List<List<String>> findLadders(String start, String end, Set<String> dict) {
        // write your code here
        List<List<String>> ret = new ArrayList<>();
        if (dict == null || dict.size() == 0) {
            return ret;
        }
        if (start.equals(end)) {
            List<String> list = new ArrayList<>();
            list.add(start);
            ret.add(list);
            return ret;
        }

        if (!dict.contains(end)) {
            dict.add(end);
        }
        if (dict.contains(start)) {
            dict.remove(start);
        }

        Map<String, Set<String>> adj = new HashMap<>();
        buildAdj(start, end, dict, adj);
        for (String x : adj.keySet()) {
            System.out.println("22 " + adj.get(x));
        }



        List<String> curPath = new ArrayList<>();
        buildRet(start, end, adj, ret, curPath);
        return ret;



        // return buildRet(start, end, adj).get(start);
    }

    private void buildAdj(String start, String end, Set<String> dict,  Map<String, Set<String>> adj) {
        Queue<String> q = new LinkedList<>();
        q.offer(start);
        while(!q.isEmpty()) {
            int size = q.size();
            Set<String> in = new HashSet<>();
            for (int i = 0; i < size; i++) {
                String cur = q.poll();
                if (cur.equals(end)) {
                    return;
                }
                for (String neighbor : getNeighbors(cur, dict)) {
                    // System.out.println("neighbor = " + neighbor);
                    if (!adj.containsKey(neighbor)) {
                        adj.put(neighbor, new HashSet<>());
                    }
                    adj.get(neighbor).add(cur);
                    // System.out.println("!!! NEIGHBOR = " + neighbor);
                    if (!in.contains(neighbor)) {
                        in.add(neighbor);
                        q.offer(neighbor);
                    }
                }
            }
            for(String x : in) {
                dict.remove(x);
            }
        }
    }


    // private void buildRet(String start, String cur, Map<String, Set<String>> adj, Map<String, List<List<String>>> map) { //dfs wrong version
    //     if (!map.containsKey(cur)) {
    //         List<String> tmp = new ArrayList<>();
    //         tmp.add(cur);
    //         List<List<String>> ll = new ArrayList<>();
    //         ll.add(tmp);
    //         map.put(cur, ll);
    //     }
    //     if (start.equals(cur)) {
    //         return;
    //     }
    //     for (String neighbor : adj.get(cur)) {
    //         System.out.println("ABC: cur = " + cur);
    //         for (List<String> path : map.get(cur)) {
    //             List<String> newL = new ArrayList<>(path);
    //             newL.add(0, neighbor);
    //             if (!map.containsKey(neighbor)) {
    //                 map.put(neighbor, new ArrayList<>());
    //             }
    //             map.get(neighbor).add(newL);
    //             System.out.println("ABCABC: cur lead to new path: " + newL);
    //         }

    //         // System.out.println("ABCABCABC: neighbor all path: " );
    //         for (List<String> xx : map.get(neighbor)) {
    //             System.out.println("ABCABCABC: " + xx);
    //         }
    //         buildRet(start, neighbor, adj, map);
    //     }
    // }


    private void buildRet(String start, String cur, Map<String, Set<String>> adj, List<List<String>> ret, List<String> curPath) { //dfs

        curPath.add(0, cur);
        System.out.println("curPath = " + curPath);
        if (start.equals(cur)) {

            ret.add(new ArrayList<>(curPath));
            curPath.remove(0);
            System.out.println("HIT: curPath = " + curPath);
            return;
        }
        for (String neighbor : adj.get(cur)) {

            buildRet(start, neighbor, adj, ret, curPath);
        }
        curPath.remove(0);
    }

    private List<String> getNeighbors(String word, Set<String> dict) {
        List<String> ret = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {

            for (char x = 'a'; x <= 'z'; x++) {

                char ori = chars[i];
                if (x == ori) {
                    continue;
                }
                chars[i] = x;
                String newW = new String(chars);
                // System.out.println("newW = " + newW);
                if (dict.contains(newW)) {
                    ret.add(newW);

                }
                chars[i] = ori;
            }
        }
        return ret;
    }



}
