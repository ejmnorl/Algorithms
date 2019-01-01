package bfs;

public class bfs {
    /*
    https://www.lintcode.com/problem/binary-tree-level-order-traversal/description
    Binary Tree Level Order Traversal
    Given a binary tree, return the level order traversal of its nodes' values.
    (ie, from left to right, level by level).
     */

    public List<List<Integer>> levelOrder(TreeNode root) {
        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Queue<TreeNode> q = new LinkedList<>();
        int preSize = 1;
        q.add(root);

        while (!q.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            TreeNode cur = null;
            int curSize = 0;

            while (preSize > 0) {
                cur = q.remove();
                list.add(cur.val);
                preSize--;
                if (cur.left != null) {
                    q.add(cur.left);
                    curSize++;
                }
                if (cur.right != null) {
                    q.add(cur.right);
                    curSize++;
                }
            }
            preSize = curSize;
            ret.add(list);
        }
        return ret;
    }

    /*todo: preorder, inorder ...method???
    https://www.lintcode.com/problem/serialize-and-deserialize-binary-tree/description
    Serialize and Deserialize Binary Tree
    Design an algorithm and write code to serialize and deserialize a binary tree.
    Writing the tree to a file is called 'serialization' and
    reading back from the file to reconstruct the exact
    same binary tree is 'deserialization'.
     */

    /**
     * This method will be invoked first, you should design your own algorithm
     * to serialize a binary tree which denote by a root node to a string which
     * can be easily deserialized by your own "deserialize" method later.
     */
    public String serialize(TreeNode root) {
        // write your code here
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        sb.append(root.val);
        sb.append(",");
        while (!q.isEmpty()) {
               TreeNode cur = q.poll();
               if (cur.left == null) {
                   sb.append("*");
               } else {
                   sb.append(cur.left.val + "");
                   q.offer(cur.left);
               }
               sb.append(",");
               if (cur.right == null) {
                   sb.append("*");
               } else {
                   sb.append(cur.right.val + "");
                   q.offer(cur.right);
               }
               sb.append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    /**
     * This method will be invoked second, the argument data is what exactly
     * you serialized at method "serialize", that means the data is not given by
     * system, it's given by your own serialize method. So the format of data is
     * designed by yourself, and deserialize it here as you serialize it in
     * "serialize" method.
     */
    public TreeNode deserialize(String data) {
        // write your code here
        if (data == null || data.trim().length() == 0) {
            return null;
        }
        String[] vals = data.split(",");
        TreeNode root = new TreeNode(Integer.valueOf(vals[0]));
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (i < vals.length) {
            TreeNode cur = q.poll();
            String val = vals[i++];
            if (val.equals("*")) {
                cur.left = null;
            } else {
                cur.left = new TreeNode(Integer.valueOf(val));
                q.offer(cur.left);
            }
            val = vals[i++];
            if (val.equals("*")) {
                cur.right = null;
            } else {
                cur.right = new TreeNode(Integer.valueOf(val));
                q.offer(cur.right);
            }
        }
        return root;
    }

    /*
    http://www.lintcode.com/en/problem/binary-tree-level-order-traversal-ii/
    Binary Tree Level Order Traversal II

    Given a binary tree, return the bottom-up level order traversal of its nodes' values.
    (ie, from left to right, level by level from leaf to root).
     */

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.push(root);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            List<Integer> list = new ArrayList<>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = list.poll();
                list.add(cur.val);
                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }
            ret.add(0, list);
        }
        return ret;
    }

    /*
    http://www.lintcode.com/en/problem/binary-tree-zigzag-level-order-traversal/

    Binary Tree Zigzag Level Order Traversal
    Given a binary tree, return the zigzag level order traversal of its nodes' values.
    (ie, from left to right, then right to left for the next level and alternate between).

     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }

        Stack<TreeNode> stk1 = new Stack<>();
        Stack<TreeNode> stk2 = new Stack<>();
        boolean leftToRight = true;
        stk1.push(root);

        while (!stk1.isEmpty() || !stk2.isEmpty()) {
            int size = leftToRight ? stk1.size() : stk2.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {

                TreeNode cur = null;
                if (leftToRight) {
                    cur = stk1.pop();
                    if (cur.left != null) {
                        stk2.push(cur.left);
                    }
                    if (cur.right != null) {
                        stk2.push(cur.right);
                    }
                } else {
                    cur = stk2.pop();
                    if (cur.right != null) {
                        stk1.push(cur.right);
                    }
                    if (cur.left != null) {
                        stk1.push(cur.left);
                    }
                }
                list.add(cur.val);
            }
            leftToRight = !leftToRight;
            ret.add(list);
        }
        return ret;
    }

    /*TODO:?????
    http://www.lintcode.com/problem/graph-valid-tree/
    Graph Valid Tree
    Given n nodes labeled from 0 to n - 1 and a list of undirected edges
    (each edge is a pair of nodes), write a function to check whether
    these edges make up a valid tree.

    Example
    Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
    Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
     */
    public boolean validTree(int n, int[][] edges) {
        // write your code here
//        if (n <= 0 || edges == null || edges.length == 0 || edges[0].length == 0) {
//            return false;
//        }
        if (n <= 0) {
            return false;
        }
        if (edges == null || edges.length == 0 || edges[0].length == 0) {
            if (n == 1) {
                return true;
            }
            return false;
        }

        Map<Integer, Set<Integer>> adj = generateAdj(edges);
//        Set<Integer> visited = new HashSet<>();
        Set<Integer> visiting = new HashSet<>();

        Queue<Integer> q = new LinkedList<>();
        q.offer(edges[0][0]);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int cur = q.poll();
                if (visiting.contains(cur)) {
                    return false;
                }
                visiting.add(cur);
                Set<Integer> neighbors = adj.get(cur);
                if (neighbors != null) {
                    for (Integer neighbor : neighbors) {
                        if (!visiting.contains(neighbor)) {
                            q.offer(neighbor);
                        }
                    }
                }
            }
        }
        return visiting.size() == n ? true : false;
    }

    private Map<Integer, Set<Integer>> generateAdj(int[][] edges) {
        Map<Integer, Set<Integer>> ret = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int node = edges[i][0];
            if (!ret.containsKey(node)) {
                ret.put(node, new HashSet<>());
            }
            ret.get(node).add(edges[i][1]);

            if (!ret.containsKey(edges[i][1])) {
                ret.put(edges[i][1], new HashSet<>());
            }
            ret.get(edges[i][1]).add(edges[i][0]);
        }
        return ret;
    }

    /*redo:  todo: sepearate copy nodes and copy edges
    http://www.lintcode.com/problem/clone-graph/
    Clone Graph
    Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.

    How we serialize an undirected graph:

    Nodes are labeled uniquely.

    We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.

    As an example, consider the serialized graph {0,1,2#1,2#2,2}.

    The graph has a total of three nodes, and therefore contains three parts as separated by #.

    First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
    Second node is labeled as 1. Connect node 1 to node 2.
    Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
     */

    /**
     * Definition for undirected graph.
     * class UndirectedGraphNode {
     *     int label;
     *     ArrayList<UndirectedGraphNode> neighbors;
     *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
     * };
     **/
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        // write your code here
        if (node == null) {
            return null;
        }
        Map<Integer, UndirectedGraphNode> map = new HashMap<>();
        core(map, node);
        return map.get(node.label);
    }

    private void core(Map<Integer, UndirectedGraphNode> map,  UndirectedGraphNode node) {
        if (node == null) {
            return;
        }
        map.put(node.label,new UndirectedGraphNode(node.label));

        ArrayList<UndirectedGraphNode> curNeighbors = map.get(node.label).neighbors;

        for (UndirectedGraphNode neighbor : node.neighbors) {
            if (!map.containsKey(neighbor.label)) {
                core(map, neighbor);
            }
            curNeighbors.add(map.get(neighbor.label));
        }
    }


    /**
     * Definition for graph node.
     * class UndirectedGraphNode {
     *     int label;
     *     ArrayList<UndirectedGraphNode> neighbors;
     *     UndirectedGraphNode(int x) {
     *         label = x; neighbors = new ArrayList<UndirectedGraphNode>();
     *     }
     * };

    Given a undirected graph, a node and a target,
    return the nearest node to given node which value of it is target,
    return NULL if you can't find.

    There is a mapping store the nodes' values in the given parameters.


    Give a node 1, target is 50

    there a hash named values which is [3,4,10,50,50], represent:
    Value of node 1 is 3
    Value of node 2 is 4
    Value of node 3 is 10
    Value of node 4 is 50
    Value of node 5 is 50

    Return node 4  */


    public UndirectedGraphNode searchNode(ArrayList<UndirectedGraphNode> graph,
                                          Map<UndirectedGraphNode, Integer> values,
                                          UndirectedGraphNode node,
                                          int target) {
        // write your code here
        if (node == null || values.get(node) == target) {
            return node;
        }

        Set<UndirectedGraphNode> visited = new HashSet<>();
        Queue<UndirectedGraphNode> q = new LinkedList<>();
        q.offer(node);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                UndirectedGraphNode cur = q.poll();
                visited.add(cur);
                for (UndirectedGraphNode neighbor : cur.neighbors) {
                    if (!visited.contains(neighbor)) {
                        if (values.get(neighbor) == target) {
                            return neighbor;
                        }
                        q.offer(neighbor);
                    }
                }
            }
        }
        return null;
    }

    /*
    http://www.lintcode.com/problem/topological-sorting/
    Topological Sorting
    Given an directed graph, a topological order of the graph nodes is defined as follow:
        For each directed edge A -> B in graph, A must before B in the order list.
        The first node in the order can be any node in the graph with no nodes direct to it.

    Find any topological order for the given graph.
    The topological order can be:
    [0, 1, 2, 3, 4, 5]
    [0, 2, 3, 1, 5, 4]

    Challenge
    Can you do it in both BFS and DFS?
     */
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // write your code here
        if (graph == null || graph.size() <= 1) {
            return graph;
        }
        Set<DirectedGraphNode> visited = new HashSet<>();
        ArrayList<DirectedGraphNode> ret = new ArrayList<>();
        for (DirectedGraphNode node : graph) {
            if (!visited.contains(node)) {
                dfs(node, visited, ret);
            }
        }
        return ret;
    }

    private void dfs(DirectedGraphNode cur, Set<DirectedGraphNode> visited, ArrayList<DirectedGraphNode> ret) {
        if (cur == null) {
            return;
        }
        visited.add(cur);
        for (DirectedGraphNode neighbor : cur.neighbors) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited, ret);
            }
        }
        ret.add(0, cur);
    }

    //M2
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // write your code here
        if (graph == null || graph.size() <= 1) {
            return graph;
        }
        Queue<DirectedGraphNode> q = new LinkedList<>();
        Set<DirectedGraphNode> visited = new HashSet<>();
        Map<DirectedGraphNode, List<DirectedGraphNode>> parents = new HashMap<>();
        Map<DirectedGraphNode, Integer> outDegrees = new HashMap<>();
        List<DirectedGraphNode> zero = new ArrayList<>();

        for (DirectedGraphNode nd : graph) {
            if (!visited.contains(nd)) {
                q.offer(nd);
                visited.add(nd);
                while (!q.isEmpty()) {
                    int size = q.size();
                    for (int i = 0; i < size; i++) {
                        DirectedGraphNode cur = q.poll();
                        System.out.println("cur = " + cur.label);

                        outDegrees.put(cur, cur.neighbors.size());
                        if (cur.neighbors.size() == 0) {
                            zero.add(cur);
                        }
                        for (DirectedGraphNode neighbor : cur.neighbors) {
                            if (!parents.containsKey(neighbor)) {
                                parents.put(neighbor, new ArrayList<>());
                            }
                            parents.get(neighbor).add(cur);
                            if (!visited.contains(neighbor)) {
                                q.offer(neighbor);
                                visited.add(neighbor);
                            }
                        }
                    }
                }
            }
        }

        ArrayList<DirectedGraphNode> ret = new ArrayList<>();

        while (zero.size() != 0) {
            DirectedGraphNode cur = zero.remove(0);
            print(zero);
            ret.add(0, cur);
            List<DirectedGraphNode> l = parents.get(cur);
            if (l != null) {
                for (DirectedGraphNode x : l) {
                    outDegrees.put(x, outDegrees.get(x) - 1);
                    if (outDegrees.get(x) == 0) {
                        zero.add(x);
                    }
                }
            }
        }
        return ret;
    }

    private void print(List<DirectedGraphNode> list) {
        for(DirectedGraphNode x : list) {
            System.out.print(x.label + "->");
        }
        System.out.println();
    }

    /*
    https://www.lintcode.com/problem/course-schedule-ii/description
    Course Schedule II
    There are a total of n courses you have to take, labeled from 0 to n - 1.
    Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
    Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.
    There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.
     */

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // write your code here


    }

    /* REDO !!!
    Sequence Reconstruction
    Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs.
    The org sequence is a permutation of the integers from 1 to n, with 1 ≤ n ≤ 10^4.
    Reconstruction means building a shortest common supersequence of the sequences in seqs
    (i.e., a shortest sequence so that all sequences in seqs are subsequences of it).
    Determine whether there is only one sequence that can be reconstructed from seqs and it is the org sequence.

    Example
    Given org = [1,2,3], seqs = [[1,2],[1,3]]
    Return false
    Explanation:
            [1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence that can be reconstructed.

    Given org = [1,2,3], seqs = [[1,2]]
    Return false
    Explanation:
    The reconstructed sequence can only be [1,2].

    Given org = [1,2,3], seqs = [[1,2],[1,3],[2,3]]
    Return true
    Explanation:
    The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the original sequence [1,2,3].

    Given org = [4,1,5,2,6,3], seqs = [[5,2,6,3],[4,1,5,2]]
    Return true
    */

    public boolean sequenceReconstruction(int[] org, int[][] seqs) {
        // write your code here
        if (seqs == null || seqs.length == 0 || seqs[0].length == 0) {
            if (org == null || org.length == 0) {
                return true;
            }
            return false;
        }
        if (org == null || org.length == 0) {
            return false;
        }

        Map<Integer, List<Integer>> adj = new HashMap<>();
        Set<Integer> nodes = new HashSet<>();
        Map<Integer, Integer> indegrees = new HashMap<>();

        List<Integer> zero = new ArrayList<>();


        Queue<Integer> q = new LinkedList<>();
        for (Integer  i : nodes) {
            if (!indegrees.containsKey(i)) {
                zero.add(i);
                if (zero.size() > 1) {
                    return false;
                }
            }
        }

        if (zero.size() != 1) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Integer start = zero.remove(0);
        visited.add(start);
        q.offer(start);

        List<Integer> list = new ArrayList<>();
        list.add(start);


        while (!q.isEmpty()) {
                Integer cur = q.poll();
                List<Integer> neighbors = adj.get(cur);
                if (neighbors != null) {
                    for (Integer neighbor : neighbors) {
                        indegrees.put(neighbor, indegrees.get(neighbor) - 1);
                        if (indegrees.get(neighbor) == 0) {
                            zero.add(neighbor);
                        }
                        if (zero.size() > 1) {
                            return false;
                        }

                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            q.offer(neighbor);
                        }
                    }
                }

                if (zero.size() > 1) {
                    return false;
                }
                if (zero.size() == 0) {
                    if (!q.isEmpty()) {
                        return false;
                    }
                }
                list.add(zero.remove(0));
        }
        return isEqual(list, org);
    }

    private void generateAdj(int[][] seqs, Map<Integer, List<Integer>> adj, Set<Integer> nodes, Map<Integer, Integer> indegrees) {
        for (int i = 0; i < seqs.length; i++) {
            int from = seqs[i][0];
            nodes.add(from);
            for (int j = 1; j < seqs[i].length; j++) {
                from = seqs[i][j - 1];
                int to = seqs[i][j];
                nodes.add(to);
                if (indegress.containsKey(to)) {
                    indegrees.put(to, 1);
                } else {
                    indegrees.put(to, indegress.get(to) + 1);
                }

                if (!adj.containksKey(from)) {
                    adj.put(from, new ArrayList<>());
                }
                adj.get(from).add(to);
            }
        }
    }

    private boolean isEqual(List<Integer> list, int[] seqs) {
        if (seqs.length != list.size()) {
            return false;
        }
        for (int i = 0; i < seqs.length; i++) {
            if (list.get(i) != seqs[i]) {
                return false;
            }
        }
        return true;
    }

    /* TODO: M2 union find
    https://www.lintcode.com/problem/number-of-islands/description
    Number of Islands
    Given a boolean 2D matrix, 0 is represented as the sea,
    1 is represented as the island.
    If two 1 is adjacent, we consider them in the same island. We only consider up/down/left/right adjacent.
    Find the number of islands.
     */

    public int numIslands(boolean[][] grid) {
        // write your code here
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length, n = grid[0].length;
        Set<Integer>  visited = new HashSet<>();

        int cnt = 0;
        int[][] go = {{0,1}, {0,-1}, {1,0}, {-1, 0}};
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int val = i * m + j;
                if (grid[i][j] && !visited.contains(val)) {
                    cnt++;
                    q.offer(val);
                    visited.add(val);
                    while (!q.isEmpty()) {
                        int cur = q.poll();
                        int r = cur / m;
                        int c = cur % m;
                        for (int k = 0; k < 4; k++) {
                            int newR = r + go[k][0];
                            int newC = c + go[k][1];
                            int newVal = newR * m + newC;
                            if (inBound(m, n, newR, newC) && grid[newR][newC] && !visited.contains(newVal))  {
                                visited.add(newVal);
                                q.offer(newVal);
                            }
                        }
                    }
                }
            }
        }
        return cnt;
    }

    private boolean inBound(int m, int n, int newR, int newC) {
        if (newR < 0 || newR >= m || newC < 0 || newC >= n) {
            return false;
        }
        return true;
    }

    /*
    http://www.lintcode.com/problem/zombie-in-matrix/
    Zombie in Matrix
    Given a 2D grid, each cell is either a wall 2, a zombie 1 or people 0 (the number zero, one, two).
    Zombies can turn the nearest people(up/down/left/right) into zombies every day,
    but can not through wall. How long will it take to turn all people into zombies?
    Return -1 if can not turn all people into zombies.
    Example
    Given a matrix:

    0 1 2 0 0
    1 0 0 2 1
    0 1 0 0 0

    return 2
     */

    public int zombie(int[][] grid) {
        // write your code here
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        Queue<Integer> q = new LinkedList<>();
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int[][] shift = {{0,1}, {0,-1}, {1,0}, {-1,0}};

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    q.offer(i * m + j);
                    System.out.println("i = " + i + " j = " + j);
                }
            }
        }
        int cnt = 0;


        while (!q.isEmpty()) {
            int size = q.size();
            cnt++;
            for (int i = 0; i < size; i++) {
                Integer cur = q.poll();

                int r = cur / m;
                int c = cur % m;
                for (int j = 0; j < 4; j++) {
                    int newR = r + shift[j][0];
                    int newC = c + shift[j][1];
                    if (inBound(m, n, newR, newC) && grid[newR][newC] == 0) {
                        grid[newR][newC] = 1;
                        q.offer(newR * m + newC);
                    }
                }
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " , ");
                if (grid[i][j] == 0) {
                    return -1;
                }
            }
            System.out.println();
        }
        return cnt - 1;
    }


    private boolean inBound(int m, int n, int newR, int newC) {
        if (newR < 0 || newR >= m || newC < 0 || newC >= n) {
            return false;
        }
        return true;
    }

    /*
    http://www.lintcode.com/problem/knight-shortest-path/
    Knight Shortest Path

    Given a knight in a chessboard (a binary matrix with 0 as empty and 1 as barrier) with a source position,
    find the shortest path to a destination position, return the length of the route.
    Return -1 if knight can not reached.
    Example

    [[0,0,0],
     [0,0,0],
     [0,0,0]]
    source = [2, 0] destination = [2, 2] return 2

    [[0,1,0],
     [0,0,0],
     [0,0,0]]
    source = [2, 0] destination = [2, 2] return 6

    [[0,1,0],
     [0,0,1],
     [0,0,0]]
    source = [2, 0] destination = [2, 2] return -1

    Clarification
    If the knight is at (x, y), he can get to the following positions in one step:
    (x + 1, y + 2)
    (x + 1, y - 2)
    (x - 1, y + 2)
    (x - 1, y - 2)
    (x + 2, y + 1)
    (x + 2, y - 1)
    (x - 2, y + 1)
    (x - 2, y - 1)

    Notice
    source and destination must be empty.
    Knight can not enter the barrier.
     */

    public int shortestPath(boolean[][] grid, Point source, Point destination) {
        // write your code here
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        if (grid[source.x][source.y] || grid[destination.x][destination.y]) {
            return -1;
        }
        if(source.x == destination.x && source.y == destination.y) {
            return 0;
        }

        int[][] shift = {{1,2}, {1, -2}, {-1, 2}, {-1, -2}, {2,1}, {2,-1}, {-2,1}, {-2,-1}};

        Queue<Integer> q = new LinkedList<>();
        int  m = grid.length, n = grid[0].length;
        int start = source.x * n + source.y;
        q.offer(start);
        int step = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            step++;
            for (int i = 0; i < size; i++) {
                Integer cur = q.poll();
                int r = cur / n;
                int c = cur % n;
                for (int j = 0; j < shift.length; j++) {
                    int newR = r + shift[j][0];
                    int newC = c + shift[j][1];
                    if (inBound(m,n,newR,newC) && !grid[newR][newC]) {
                        if (newR == destination.x && newC == destination.y) {
                            return step;
                        }
                        grid[newR][newC] = true;
                        q.offer(newR * n + newC);
                    }
                }
            }
        }
        return -1;
    }

    private boolean inBound(int m, int n, int newR, int newC) {
        if (newR < 0 || newR >= m || newC < 0 || newC >= n) {
            return false;
        }
        return true;
    }

    /* todo: ???????

    http://www.lintcode.com/problem/build-post-office-ii/
    Build Post Office II

    Given a 2D grid, each cell is either a wall 2,
    an house 1 or empty 0 (the number zero, one, two),
    find a place to build a post office so that the sum of the distance from the post office to all the houses is smallest.

    Return the smallest sum of distance. Return -1 if it is not possible.
    Example
    Given a grid:
    0 1 0 0 0
    1 0 0 2 1
    0 1 0 0 0

    return 8, You can build at (1,1). (Placing a post office at (1,1), the distance that post office to all the house sum is smallest.)
    Challenge

    Solve this problem within O(n^3) time.
    Notice

        You cannot pass through wall and house, but can pass through empty.
        You only build post office on an empty.
     */

    public int shortestDistance(int[][] grid) {
        // write your code here



    }

    /*
    http://www.lintcode.com/problem/connected-component-in-undirected-graph/
    Connected Component in Undirected Graph

    Find the number connected component in the undirected graph.
    Each node in the graph contains a label and a list of its neighbors.
    (a connected component (or just component) of an undirected graph is a subgraph
    in which any two vertices are connected to each other by paths,
    and which is connected to no additional vertices in the supergraph.)
    Example

    Given graph:

    A------B  C
     \     |  |
      \    |  |
       \   |  |
        \  |  |
          D   E

    Return {A,B,D}, {C,E}. Since there are two connected component which is {A,B,D}, {C,E}
     */

    public List<List<Integer>> connectedSet(List<UndirectedGraphNode> nodes) {
        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        if (nodes == null || nodes.size() == 0) {
            return ret;
        }
        Set<UndirectedGraphNode> visited = new HashSet<>();
        for (UndirectedGraphNode node : nodes) {
            if (!visited.contains(node)) {
                List<Integer> path = new ArrayList<>();
                core(path, visited, node);
                Collections.sort(path);
                ret.add(path);
            }
        }
        return ret;
    }

    private void core(List<Integer> path, Set<UndirectedGraphNode> visited, UndirectedGraphNode cur) {
        visited.add(cur);
        path.add(cur.label);

        ArrayList<UndirectedGraphNode> neighbors = cur.neighbors;
        if (neighbors != null && neighbors.size() > 0) {
            for (UndirectedGraphNode neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    core(path, visited, neighbor);
                }
            }
        }
    }

    /*
    http://www.lintcode.com/problem/smallest-rectangle-enclosing-black-pixels/
    Smallest Rectangle Enclosing Black Pixels

    An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel.
    The black pixels are connected, i.e., there is only one black region.
    Pixels are connected horizontally and vertically.
    Given the location (x, y) of one of the black pixels,
    return the area of the smallest (axis-aligned) rectangle that encloses all black pixels.
    Example

    For example, given the following image:
    [
      "0010",
      "0110",
      "0100"
    ]

    and x = 0, y = 2,
    Return 6.

     */

    public int minArea(char[][] image, int x, int y) {
        // write your code here
        if (image == null || image.length == 0 || image[0].length == 0) {
            return 0;
        }
        int m = image.length, n = image[0].length;
        int up = x;
        int down = x;
        int left = y;
        int right = ;y
        int[][] shift = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        boolean[][] visited = new boolean[m][n];

        Queue<Integer> q = new LinkedList<>();
        q.offer(x * n + y);
        visited[x][y] = true;
        while (!q.isEmpty()) {
            int cur = q.poll();
            int r = cur / n;
            int c = cur % n;
            for (int i = 0; i < 4; i++) {
                int newR = r + shift[i][0];
                int newC = c + shift[i][1];
                if (inBound(m,n,newR,newC) && !visited[newR][newC] && images[newR][newC] == '1') {
                    up = Math.min(up, newR);
                    down = Math.max(down, newR);
                    left = Math.min(left, newC);
                    right = Math.max(right, newC);
                    visited[newR][newC] = true;
                    q.offer(newR * n + newC);
                }
            }
        }
        return (right - left + 1) * (down - up + 1);
    }


    private boolean inBound(int m, int n, int newR, int newC) {
        if (newR < 0 || newR >= m || newC < 0 || newC >= n) {
            return false;
        }
        return true;
    }

    /*
    http://www.lintcode.com/problem/word-ladder/
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
        if (start == null || start.length() == 0 || end == null || end.length() == 0) {
            return 0;
        }
        if (start.equals(end)) {
            return 1;
        }
        Queue<String> q = new LinkedList<>();
        q.offer(start);

        if (dict.contains(start)) {
            dict.remove(start);
        }
        int steps = 1;

        while (!q.isEmpty()) {
            int size = q.size();
            steps++;
            for (int i = 0; i < size; i++) {
                String cur = q.poll();
                List<String> neighbors = getNeighbors(cur, dict, end);
                for (String neighbor : neighbors) {
                    if (neighbor.equals(end)) {
                        return steps;
                    }
                    q.offer(neighbor);
                }
            }
        }
        return 0;
    }

    private List<String> getNeighbors(String cur, Set<String> dict, String end) {
        List<String> ret = new ArrayList<>();
        char[] chars = cur.toCharArray();
        for (int i = 0; i < cur.length(); i++) {
            char original = chars[i];
            for (int j = 0; j < 26; j++) {
                char x = (char) ('a' + j);
                chars[i] = x;
                String newS = new String(chars);
                if (dict.contains(newS)) {
                    dict.remove(newS);
                    ret.add(newS);
                }
                if (newS.equals(end)) {
                    ret.add(newS);
                }
                chars[i] = original;
            }
        }
        return ret;
    }
}













/*
http://www.lintcode.com/en/help/binary-tree-representation/
题目及解答: http://www.lintcode.com/en/problem/binary-tree-serialization/ http://www.jiuzhang.com/solutions/binary-tree-serialization/


Binary Tree Level Order Traversal II
http://www.lintcode.com/en/problem/binary-tree-level-order-traversal-ii/
http://www.jiuzhang.com/solutions/binary-tree-level-order-traversal-ii/

Binary Tree Zigzag Order Traversal
http://www.lintcode.com/en/problem/binary-tree-zigzag-level-order-traversal/
http://www.jiuzhang.com/solutions/binary-tree-zigzag-level-order-traversal/

Convert Binary Tree to Linked Lists by Depth
http://www.lintcode.com/en/problem/convert-binary-tree-to-linked-lists-by-depth/
http://www.jiuzhang.com/solutions/convert-binary-tree-to-linked-lists-by-depth/


 Graph Valid Tree
http://www.lintcode.com/problem/graph-valid-tree/
http://www.jiuzhang.com/solutions/graph-valid-tree/


Clone Graph (F)
http://www.lintcode.com/problem/clone-graph/
http://www.jiuzhang.com/solutions/clone-graph/

Search Graph Nodes (A)
http://www.lintcode.com/problem/search-graph-nodes/
http://www.jiuzhang.com/solutions/search-graph-nodes/

Topological Sorting
http://www.lintcode.com/problem/topological-sorting/
http://www.jiuzhang.com/solutions/topological-sorting/

 Course Schedule I && II (G+A+F+Z)
http://www.lintcode.com/en/problem/course-schedule/
http://www.lintcode.com/problem/course-schedule-ii/

裸拓扑排序
Sequence Reconstruction (G+A)
http://www.lintcode.com/problem/sequence-reconstruction/
判断是否只存在一个拓扑排序的序列 只需要保证队列中一直最多只有1个元素即可

 矩阵中的宽度优先搜索 BFS in Matrix

  Number of Islands
http://www.lintcode.com/problem/number-of-islands/
http://www.jiuzhang.com/solutions/number-of-islands/


 Zombie in Matrix
http://www.lintcode.com/problem/zombie-in-matrix/
http://www.jiuzhang.com/solutions/zombie-in-matrix/

 Knight Shortest Path
http://www.lintcode.com/problem/knight-shortest-path/
http://www.jiuzhang.com/solutions/knight-shortest-path/


 Build Post Office II
http://www.lintcode.com/problem/build-post-office-ii/
http://www.jiuzhang.com/solutions/build-post-office-ii/


相关问题 图的遍历(由点及面)
• 无向图联通块
• http://www.lintcode.com/problem/connected-component-in-undirected-graph/
• 覆盖黑点的最小矩阵(BFS无法AC但是可以作为BFS的练习题)
• http://www.lintcode.com/problem/smallest-rectangle-enclosing-black-pixels/
简单图最短路径
• 单词阶梯
• http://www.lintcode.com/problem/word-ladder/
 */


