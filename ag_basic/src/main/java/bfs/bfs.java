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

    /*TODO:
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

    /*
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

}



Input

        {0,1,2,3,4#1,3,4#2,1,4#3,4#4}

        Output

        [0,2,1,3,4,4,4,4]







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
• http://www.lintcode.com/problem/connected-component-in-undirected-graph/ • 覆盖黑点的最小矩阵(BFS无法AC但是可以作为BFS的练习题)
• http://www.lintcode.com/problem/smallest-rectangle-enclosing-black-pixels/
简单图最短路径 • 单词阶梯
• http://www.lintcode.com/problem/word-ladder/
 */


