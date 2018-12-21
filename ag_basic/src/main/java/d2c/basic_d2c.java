package d2c;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class basic_d2c {
    /*
    https://www.lintcode.com/problem/binary-tree-preorder-traversal/description
    Binary Tree Preorder Traversal
    Given a binary tree, return the preorder traversal of its nodes' values.

    Challenge
    Can you do it without recursion?
     */

    /**
     * Definition of TreeNode:
     * public class TreeNode {
     *     public int val;
     *     public TreeNode left, right;
     *     public TreeNode(int val) {
     *         this.val = val;
     *         this.left = this.right = null;
     *     }
     * }
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        // write your code here
        List<Integer> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        core(root, ret);
        return ret;
    }

    private void core(TreeNode tr, List<Integer> ret) {
        if(tr == null) {
            return;
        }
        ret.add(tr.val);
        core(tr.left, ret);
        core(tr.right, ret);
    }


    //Iteration
    public List<Integer> preorderTraversal(TreeNode root) {
           List<Integer> ret = new ArrayList<>();
           if (root == null) {
               return ret;
           }
           Stack<TreeNode> stk = new Stack<>();
           TreeNode cur = root;
           while (!stk.isEmpty() || cur != null) {
               if (cur == null) {
                   cur = stk.pop();
               }

               ret.add(cur.val);
               if (cur.right != null) {
                   stk.push(cur.right);
               }
               cur = cur.left;
           }
           return ret;
    }

    /*
    https://www.lintcode.com/problem/binary-tree-inorder-traversal/description
    Binary Tree Inorder Traversal
    Given a binary tree, return the inorder traversal of its nodes' values.
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        // write your code here
        List<Integer> ret = new ArrayList<>();
        core(root, ret);
        return ret;
    }

    private void core(TreeNode tn, List<Integer> ret) {
        if (tn != null) {
            core(tn.left, ret);
            ret.add(tn.val);
            core(tn.right, ret);
        }
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Stack<TreeNode> s = new Stack<>();
        TreeNode cur = root;
        while (!s.isEmpty() || cur != null) {
            while (cur != null) {
                s.push(cur);
                cur = cur.left;
            }
            cur = s.pop();
            ret.add(cur.val);
            cur = cur.right;
        }
        return ret;
    }

    /*
    https://www.lintcode.com/problem/binary-tree-postorder-traversal/description
    Binary Tree Postorder Traversal
    Given a binary tree, return the postorder traversal of its nodes' values.
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        // write your code here
        List<Integer> ret = new ArrayList<>();
        core(root, ret);
        return ret;

    }
    private void core(TreeNode tn, List<Integer> ret) {
        if (tn != null) {
            core(tn.left, ret);
            core(tn.right, ret);
            ret.add(tn.val);
        }
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Stack<TreeNode> s = new Stack<>();
        TreeNode cur = root;
        while (!s.isEmpty() || cur != null) {
            while (cur != null) {
                if (cur.right != null) {
                    s.push(cur.right);
                }
                s.push(cur);
                cur = cur.left;
            }
            cur = s.pop();
            if (!s.isEmpty() && cur.right != null && s.peek().val == cur.right.val) {
                TreeNode tmp = cur;
                cur = s.pop();
                s.push(tmp);
            } else {
                ret.add(cur.val);
                cur = null;
            }
        }
        return ret;
    }

    /*
    http://www.lintcode.com/problem/maximum-depth-of-binary-tree/
    Maximum Depth of Binary Tree

    Given a binary tree, find its maximum depth.

    The maximum depth is the number of nodes along the longest path
    from the root node down to the farthest leaf node.
     */

    /**
     * @param root: The root of binary tree.
     * @return: An integer
     */
    //m1: d&c
    public int maxDepth(TreeNode root) {
        // write your code here
        if (root == null) {
            return 0;
        }
        return core(root);
    }

    private int core(TreeNode cur) {
        if (cur == null) {
            return 0;
        }
        int l = core(cur.left);
        int r = core(cur.right);
        return Math.max(l, r) + 1;
    }

    private int dep = 0;
    //m2: traverse
    public int maxDepth(TreeNode root) {
        core(root, 1);
        return dep;
    }

    private void core(TreeNode cur, int d) {
        if (cur == null) {
            return;
        }
        dep = Math.max(d, dep);
        core(cur.left, d + 1);
        core(cur.right, d + 1);
    }

    /*
    http://www.lintcode.com/en/problem/binary-tree-paths/
    Binary Tree Paths
    Given a binary tree, return all root-to-leaf paths.
     */
    public List<String> binaryTreePaths(TreeNode root) {
        // write your code here
        List<String> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        List<String> builder = new ArrayList<>();
        core(root, builder, ret);
        return ret;
    }

    private void core(TreeNode cur, List<String> builder, List<String> ret) {
        builder.add("->");
        builder.add(cur.val + "");
        if (cur.left == null && cur.right == null) {
            addResult(builder, ret);
        }
        if (cur.left != null) {
            core(cur.left, builder, ret);
        }
        if (cur.right != null) {
            core(cur.right, builder, ret);
        }
        delete(builder);
    }

    private void delete(List<String> builder) {
        builder.remove(sb.size() - 1);
        builder.remove(sb.size() - 1);
    }

    private void addResult(List<String> builder, List<String> ret) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < builder.size(); i++) {
            sb.append(builder.get(i));
        }
        ret.add(sb.toString());
    }

    /*
    https://www.lintcode.com/problem/minimum-subtree/description
    Minimum Subtree
    Given a binary tree, find the subtree with minimum sum.
    Return the root of the subtree.
     */

    /**
     * @param root: the root of binary tree
     * @return: the root of the minimum subtree
     */
    public TreeNode findSubtree(TreeNode root) {
        // write your code here
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        core(root);
        return ret;
    }
    private int min = Integer.MAX_VALUE;
    private TreeNode ret = null;

    private int core(TreeNode cur) {
        if (cur == null) {
            return 0;
        }
        int l = core(cur.left);
        int r = core(cur.right);

        int val = l + r + cur.val;
        if (val < min) {
            min = val;
            ret = cur;
        }
        return val;
    }
    //M2
    private class Ret {
        int includeCurNode;
        int globalMin;
        TreeNode globalNode = null;

        public Ret(){
            this.includeCurNode = 0;
            this.globalMin = Integer.MAX_VALUE;
            this.globalNode = null;
        }
        public Ret(int includeCurNode, int globalMin, TreeNode tn) {
            this.includeCurNode = includeCurNode;
            this.globalMin = globalMin;
            this.globalNode = tn;
        }
    }

    public TreeNode findSubtree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        return core(root).globalNode;
    }
    private Ret core(TreeNode cur) {
        if (cur == null) {
            return new Ret(0, Integer.MAX_VALUE, null);
        }

        Ret le = core(cur.left);
        Ret ri = core(cur.right);
        Ret rt = new Ret();
        int treeWithCurNode = le.includeCurNode + ri.includeCurNode + cur.val;
        rt.includeCurNode = treeWithCurNode;

//        if (treeWithCurNode < le.globalMin && treeWithCurNode < ri.globalMin) {
//            rt.globalMin = treeWithCurNode;
//            rt.globalNode = cur;
//        } else if (treeWithCurNode < le.globalMin) {
//            rt.globalMin = ri.globalMin;
//            rt.globalNode = ri.globalNode;
//        } else {//treeWithCurNode < ri.globalMin
//            rt.globalMin = le.globalMin;
//            rt.globalNode = le.globalNode;
//        }
        Ret toCmp = (le.globalMin < ri.globalMin) ? le : ri;
        if (treeWithCurNode < toCmp.globalMin) {
            rt.globalMin = treeWithCurNode;
            rt.globalNode = cur;
        } else {
            rt.globalMin = toCmp.globalMin;
            rt.globalNode = toCmp.globalNode;
        }

        return rt;
    }

    private int min(int a, int b, int c) {
       return a < b ? (a < c ? a : c) : (b < c ? b : c);
    }

    /*
    http://www.lintcode.com/problem/balanced-binary-tree/
    Balanced Binary Tree

    Given a binary tree, determine if it is height-balanced.
    For this problem,
    a height-balanced binary tree is defined as a binary tree in which
    the depth of the two subtrees of every node never differ by more than 1.
     */

    private class RET {
        boolean isBST;
        int height;
        public RET() {
            isBST = true;
            height = 0;
        }
        public RET(boolean isBST, int height) {
            this.isBST = isBST;
            this.height = height;
        }
    }

    public boolean isBalanced(TreeNode root) {
        // write your code here
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        return core(root).isBST;
    }
    private RET core(TreeNode tn) {
        if (tn == null) {
            return new RET(true, 0);
        }
        RET l = core(tn.left);
        if (l.isBST == false) {
            return new RET(false, -1);
        }
        RET r = core(tn.right);
        if (r.isBST == false) {
            return new RET(false, -1);
        }
        if (Math.abs(l.height - r.height) > 1) {
            return new RET(false, -1);
        }
        return new RET(true, Math.max(l.height, r.height) + 1);
    }

    /*
http://www.lintcode.com/problem/subtree-with-maximum-average/
Subtree with Maximum Average
Given a binary tree, find the subtree with maximum average.
Return the root of the subtree.
 */
    private class RET {
        int sum;
        int num;
        double globalMax;
        TreeNode globalMaxNode;

        public RET() {
            this.sum = 0;
            this.num = 0;
            this.globalMax = -Double.MAX_VALUE;
            this.globalMaxNode = null;
        }
    }

    public TreeNode findSubtree2(TreeNode root) {
        // write your code here
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        return core(root).globalMaxNode;

    }
    private RET core(TreeNode cur) {
        if (cur == null) {
            return new RET();
        }
        RET ret = new RET();
        RET l = core(cur.left);
        RET r = core(cur.right);

        ret.sum = l.sum + r.sum + cur.val;
        ret.num = l.num + r.num + 1;

        double avg = (double) ret.sum / ret.num;
        RET toCmp = l.globalMax > r.globalMax ? l : r;
//        System.out.println("TOCMPT : sum" + toCmp.sum + " #: " + toCmp.num + " ~~CUR~~ " + cur.val + "CUR AVG: " + avg);

        if (avg > toCmp.globalMax) {
            ret.globalMax = avg;
            ret.globalMaxNode = cur;
        } else {
            ret.globalMax = toCmp.globalMax;
            ret.globalMaxNode = toCmp.globalMaxNode;
        }
        return ret;
    }

    /* TODO: interative method (tranverse)
    https://www.lintcode.com/problem/flatten-binary-tree-to-linked-list/description
    Flatten Binary Tree to Linked List
    Flatten a binary tree to a fake "linked list" in pre-order traversal.
    Here we use the right pointer in TreeNode as the next pointer in ListNode.
     */

    private class RET {
        TreeNode head;
        TreeNode tail;

        public RET(TreeNode head, TreeNode tail) {
            this.head = head;
            this.tail = tail;
        }

        public RET() {}
    }
    public void flatten(TreeNode root) {
        // write your code here
        if (root == null || (root.left == null && root.right == null)) {
            return;
        }
        core(root);
    }

    private RET core(TreeNode cur) {
        if (cur == null) {
            return null;
        }
        if (cur.left == null && cur.right == null) {
            return new RET(cur, cur);
        }

        RET l = core(cur.left);
        RET r = core(cur.right);
        cur.left = null;
        cur.right = null;
        TreeNode tail = null;
        if (l != null) {
            cur.right = l.head;
            tail = l.tail;
            if (r != null) {
                l.tail.right = r.head;
                tail = r.tail;
            }
        } else { // l == null
            if (r != null) {
                cur.right = r.head;
                tail = r.tail;
            } else {//l == null, r == null
                tail = cur;
            }
        }
        return new RET(cur, tail);
    }

    /*
    https://www.lintcode.com/problem/lowest-common-ancestor/
     Lowest Common Ancestor of a Binary Tree

    Given the root and two nodes in a Binary Tree.
    Find the lowest common ancestor(LCA) of the two nodes.

    The lowest common ancestor is the node with largest depth
    which is the ancestor of both nodes.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode A, TreeNode B) {
        // write your code here
        if (root == null || A == null || B == null) {
            return null;
        }
        return core(root, A, B);
    }

    private TreeNode core(TreeNode cur, TreeNode A, TreeNode B) {
        if (cur == null) {
            return null;
        }
        TreeNode ret = null;
        if (cur == A || cur == B) {
            ret = cur;
        }
        TreeNode l = core(cur.left, A, B);
        TreeNode r = core(cur.right, A, B);
        if (l != null && r != null) {
            return cur;
        }

        if (l == null && r != null) {
            ret = (ret == null ? r : cur);
        } else if (l != null && r == null) {
            ret = (ret == null ? l : cur);
        }
        return ret;
    }

    /*
    http://www.lintcode.com/problem/binary-tree-longest-consecutive-
sequence/
    Binary Tree Longest Consecutive Sequence
    Given a binary tree, find the length of the longest consecutive sequence path.

    The path refers to any sequence of nodes from some starting node
    to any node in the tree along the parent-child connections.
    The longest consecutive path need to be from parent to child
    (cannot be the reverse).

     */

    private int globalInc = 1;
    private int globalDec = 1;

    public int longestConsecutive(TreeNode root) {
        // write your code here
        if (root == null) {
            return 0;
        }
        core(root, root.val - 1, 0, 0);
        return Math.max(globalDec, globalInc);
    }

    private void core(TreeNode cur, int parentVal, int incLen, int decLen) {
        if (cur == null) {
            return;
        }
        int curIncLen = 1;
        int curDecLen = 1;

        if (cur.val == parentVal + 1) {
            curIncLen = incLen + 1;
        } else if (cur.val == parentVal - 1) {
            curDecLen = decLen + 1;
        }

        globalInc =Math.max(globalInc, curIncLen);
        globalDec = Math.max(globalDec, curDecLen);
        core(cur.left, cur.val, curIncLen, curDecLen);
        core(cur.right, cur.val, curIncLen, curDecLen);
    }

    /*
    https://www.lintcode.com/problem/binary-tree-path-sum/description
    Binary Tree Path Sum

    Given a binary tree, find all paths that sum of the nodes in the path
    equals to a given number target.
    A valid path is from root node to any of the leaf nodes.
     */

    public List<List<Integer>> binaryTreePathSum(TreeNode root, int target) {
        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }

        List<Integer> list = new ArrayList<>();
        core(root, target, 0, list, ret);
        return ret;
    }

    private void core(TreeNode cur, int target, int sumSoFar, List<Integer> list, List<List<Integer>> ret) {
        if (cur == null) {
            return;
        }
        list.add(cur.val);
        if (cur.left == null && cur.right == null) {
            if (sumSoFar + cur.val == target) {
                ret.add(new ArrayList<Integer>(list));
            }
            list.remove(list.size() - 1);
            return;
        }
        core(cur.left, target, sumSoFar + cur.val, list, ret);
        core(cur.right, target, sumSoFar + cur.val, list, ret);

        list.remove(list.size() - 1);
    }

    /*REDO

    https://www.lintcode.com/problem/binary-tree-path-sum-ii/description
    Binary Tree Path Sum II

    Your are given a binary tree in which each node contains a value.
    Design an algorithm to get all paths which sum to a given value.
    The path does not need to start or end at the root or a leaf,
    but it must go in a straight line down.
     */

    public List<List<Integer>> binaryTreePathSum2(TreeNode root, int target) {

        // write your code here
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Map<Integer, List<List<Integer>>> map = new HashMap<>();
//        List<List<Integer>> l = new ArrayList<List<Integer>>();
//        l.add(new ArrayList<Integer>());
//        map.put(0, l);
        core(root, target, map, ret);
        map.entrySet().stream().map(x -> {
           int kk = x.getKey();
            List<List<Integer>> val = x.getValue();
            System.out.println("KEY : " + kk);
           return 0;
        });
        return map.containsKey(target) ? map.get(target) : new ArrayList<List<Integer>>();
    }

    private void core(TreeNode cur, int target, Map<Integer, List<List<Integer>>> map, List<List<Integer>> ret) {
        if (cur == null) {
            return;
        }
        if (!map.containsKey(cur.val)) {
            map.put(cur.val, new ArrayList<List<Integer>>());
        }
        List<Integer> ll = new ArrayList<>();
        ll.add(cur.val);
        map.get(cur.val).add(ll);

        for (Map.Entry<Integer, List<List<Integer>>> en : map.entrySet()) {
            int k = en.getKey();
            int newK = k + cur.val;
            List<List<Integer>> list = en.getValue();
            list.stream().map(it -> {
                        List<Integer>  l = new ArrayList<Integer>(it);
                        l.add(cur.val);
                        if (!map.containsKey(newK)) {
                            map.put(newK, new ArrayList<List<Integer>>());
                        }
                        map.get(newK).add(l);
                        return l;
                    });
        }
        core(cur.left, target, map, ret);
        core(cur.right, target, map, ret);
    }

    /* TODO
    http://www.lintcode.com/problem/binary-tree-path-sum-iii/
    Binary Tree Path Sum III

    Give a binary tree, and a target number,
    find all path that the sum of nodes equal to target,
    the path could be start and end at any node in the tree.
     */


    /*
    http://www.lintcode.com/problem/validate-binary-search-tree/
    Validate Binary Search Tree

    Given a binary tree, determine if it is a valid binary search tree (BST).
    Assume a BST is defined as follows:

    The left subtree of a node contains only nodes with keys less than the node's key.
    The right subtree of a node contains only nodes with keys greater than the node's key.
    Both the left and right subtrees must also be binary search trees.
    A single node tree is a BST
     */

    public boolean isValidBST(TreeNode root) {
        // write your code here
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        return core(root).isBST;
    }

    private class RET {
        int min;
        int max;
        boolean isBST;
        public RET() {
            this.min = Integer.MAX_VALUE;
            this.max = Integer.MIN_VALUE;
            this.isBST = true;
        }
    }

    private RET core(TreeNode root) {
        if (root == null) {
            return new RET();
        }
        RET l = core(root.left);
        RET r = core(root.right);

        RET cur = new RET();
        if (!l.isBST || !r.isBST) {
            cur.isBST = false;
            return cur;
        }
        if ((root.left != null && root.val <= l.max) || (root.right != null && root.val >= r.min)) {
//            System.out.println("cur : " + root.val + " lMax : " + l.max + " rMin : " + r.min);
            cur.isBST = false;
            return cur;
        }

        cur.min = Math.min(root.val, Math.min(l.min, r.min));
        cur.max = Math.max(root.val, Math.max(l.max, r.max));
        cur.isBST = l.isBST && r.isBST;
//        System.out.println("CUR : " + root.val + "  min : " + cur.min + " max : " + cur.max + " isBST : " + cur.isBST);
        return cur;
    }

    public boolean isValidBST(TreeNode root) {
        // write your code here
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        return core(root.left, Integer.MIN_VALUE, root.val) && core(root.right, root.val, Integer.MAX_VALUE);
    }

    //M2
    private boolean core(TreeNode cur, int lBound, int rBound) {
        if (cur == null) {
            return true;
        }

        if ((cur.val != Integer.MIN_VALUE && cur.val <= lBound) || (cur.val != Integer.MAX_VALUE && cur.val >= rBound)) {
            return false;
        }
        return core(cur.left, lBound, cur.val) && core(cur.right, cur.val, rBound);

    }

    /*
    http://www.lintcode.com/problem/convert-binary-search-tree-to-do
ubly-linked-list/

    Convert Binary Search Tree to Doubly Linked List
    Convert a binary search tree to doubly linked list with in-order traversal.
     */
//    * Definition for Doubly-ListNode.
// * public class DoublyListNode {
// *     int val;
// *     DoublyListNode next, prev;
// *     DoublyListNode(int val) {
// *         this.val = val;
// *         this.next = this.prev = null;
// *     }
// * } * Definition of TreeNode:
//            * public class TreeNode {
// *     public int val;
// *     public TreeNode left, right;
// *     public TreeNode(int val) {
// *         this.val = val;
// *         this.left = this.right = null;
// *     }
// * }

    public DoublyListNode bstToDoublyList(TreeNode root) {
        // write your code here
        if (root == null) {
            return null;
        }
        DoublyListNode y = core(root).head;
        DoublyListNode x = y;

        while (x.next != null) {
            System.out.println(x.next.val + "-->");
            x = x.next;
        }
        return y;
    }
    private class RET {
        DoublyListNode head;
        DoublyListNode tail;
        int val;
        public RET(DoublyListNode head, DoublyListNode tail, int val) {
            this.head = head;
            this.tail = tail;
            this.val = val;
        }
        public RET(int val) {
            this.val = val;
            this.head = null;
            this.tail = null;
        }
    }

    private RET core(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            DoublyListNode ln = new DoublyListNode(root.val);
            return new RET(ln, ln, root.val);
        }
        RET l = core(root.left);
        RET r = core(root.right);
        RET cur = new RET(root.val);
        DoublyListNode node = new DoublyListNode(root.val);
        if (l != null) {
            cur.head = l.head;
            l.tail.next = node;
            node.prev = l.tail;

            cur.head = l.head;
            cur.tail = node;
            if (r != null) {
                node.next = r.head;
                r.head.prev = node;

                cur.tail = r.tail;
            }
        } else {//l == null
            cur.head = node;
            if (r != null) {
                node.next = r.head;
                r.head.prev = node;

                cur.tail = r.tail;
            } else {
                cur.tail = node;
            }
        }
        System.out.println("cur : " + cur.val + " head : " + cur.head.val + " tail : " + cur.tail.val);
        return cur;
    }


    /*
    http://www.lintcode.com/problem/binary-search-tree-iterator
    Binary Search Tree Iterator

    Design an iterator over a binary search tree with the following rules:
    Elements are visited in ascending order (i.e. an in-order traversal)
    next() and hasNext() queries run in O(1) time in average.
     */
    /**
     * Definition of TreeNode:
     * public class TreeNode {
     *     public int val;
     *     public TreeNode left, right;
     *     public TreeNode(int val) {
     *         this.val = val;
     *         this.left = this.right = null;
     *     }
     * }
     * Example of iterate a tree:
     * BSTIterator iterator = new BSTIterator(root);
     * while (iterator.hasNext()) {
     *    TreeNode node = iterator.next();
     *    do something for node
     * }
     */
    //TODO: preorder and postorder iterator

    public class BSTIterator {
        TreeNode cur;
        Stack<TreeNode> stk;

        /*
        * @param root: The root of binary tree.
        */public BSTIterator(TreeNode root) {
            // do intialization if necessary
            stk = new Stack<>();
            cur = root;
            while (cur != null) {
                stk.push(cur);
                cur = cur.left;
            }
        }

        /*
         * @return: True if there has next node, or false
         */
        public boolean hasNext() {
            // write your code here
            return !stk.isEmpty();
        }

        /*
         * @return: return next node
         */
        public TreeNode next() {
            // write your code here
            if (!hasNext()) {
                return null;
            }
            TreeNode ret =  stk.pop();
            TreeNode cur = ret.right;
            while (cur != null) {
                stk.push(cur);
                cur = cur.left;
            }
            return ret;
        }
    }

    /*
    http://www.lintcode.com/problem/inorder-successor-in-binary-search-tree/
    Inorder Successor in BST
    Given a binary search tree (See Definition) and a node in it,
    find the in-order successor of that node in the BST.

    If the given node has no in-order successor in the tree, return null.
     */

    /*
     * @param root: The root of the BST.
     * @param p: You need find the successor node of p.
     * @return: Successor of p.
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        // write your code here
        if (root == null ) {
            return root;
        }
        TreeNode candi = null;
        TreeNode cur = root;
        boolean isFound = false;
        while (cur != null) {
            if (cur.val == p.val) {
                isFound = true;
                if (cur.right != null) {
                    TreeNode ret = cur.right;
                    while (ret.left != null) {
                        ret = ret.left;
                    }
                    return ret;
                } else {
                    return candi;
                }
            } else if (p.val < cur.val) {
                candi = cur;
                cur = cur.left;
            } else { // p.val > cur.val
                cur = cur.right;
            }
        }
        return isFound ? candi : null;

    }

    /*
    Insert Node in a Binary Search Tree
    Given a binary search tree and a new tree node,
    insert the node into the tree.
    You should keep the tree still be a valid binary search tree.
     */

    public TreeNode insertNode(TreeNode root, TreeNode node) {
        // write your code here
        if (node == null) {
            return root;
        }
        if (root == null) {
            return node;
        }
        TreeNode cur = root;
        while (cur != null) {
//            System.out.println("cur = " + cur.val + " cur_left = " + cur.left.val + " cur_right = " + cur.right.val);

            if (cur.left != null && cur.right != null) {
                System.out.println("1");
                if (node.val < cur.val) {
                    cur = cur.left;
                } else if (node.val > cur.val) {
                    cur = cur.right;
                }
            } else if (cur.left == null && cur.right == null) {
                System.out.println("2");
                if (node.val < cur.val) {
                    cur.left = node;2
                }
                if (node.val > cur.val) {
                    cur.right = node;
                }
                cur = null;
            }else if (cur.left == null) {
                System.out.println("3");
                if (node.val < cur.val) {
                    cur.left = node;
//                    cur = null;
                    break;
                } else {
                    cur = cur.right;
                }
            } else if (cur.right == null) {
                System.out.println("4");
                if (node.val > cur.val) {
                    cur.right = node;
//                    cur = null;
                    break;
                } else {
                    cur = cur.left;
                }
            }
        }
        return root;
    }
    /*
    http://www.lintcode.com/problem/remove-node-in-binary-search-tree/
    Remove Node in Binary Search Tree

    Given a root of Binary Search Tree with unique value for each node.
    Remove the node with given value.
    If there is no such a node with given value in the binary search tree,
    do nothing. You should keep the tree still a binary search tree after removal.
     */

    public TreeNode removeNode(TreeNode root, int value) {
        // write your code here

    }



}













/*
chap1
https://www.lintcode.com/problem/classical-binary-search/description

https://www.lintcode.com/problem/first-position-of-target/description

https://www.lintcode.com/problem/last-position-of-target/description

https://www.lintcode.com/problem/first-bad-version/description

https://www.lintcode.com/problem/search-in-a-big-sorted-array/description

https://www.lintcode.com/problem/find-minimum-in-rotated-sorted-array/description

https://www.lintcode.com/problem/smallest-rectangle-enclosing-black-pixels/description


prac:
https://www.lintcode.com/problem/search-a-2d-matrix/
https://www.lintcode.com/problem/search-a-2d-matrix-ii/

https://www.lintcode.com/problem/search-for-a-range/
https://www.lintcode.com/problem/total-occurrence-of-target/

https://www.lintcode.com/en/problem/maximum-number-in-mountain-sequence/


chap2

Traverse a Binary Tree
  • Preorder:
 http://www.lintcode.com/problem/binary-tree-preorder-traversal/  http://www.jiuzhang.com/solutions/binary-tree-preorder-traversal/
• Inorder
 http://www.lintcode.com/en/problem/binary-tree-inorder-traversal/ • http://www.jiuzhang.com/solutions/binary-tree-inorder-traversal/
• Postorder:
 http://www.lintcode.com/en/problem/binary-tree-postorder-traversal/ • http://www.jiuzhang.com/solutions/binary-tree-postorder-traversal/

  Maximum Depth of Binary Tree
http://www.lintcode.com/problem/maximum-depth-of-binary-tree/
http://www.jiuzhang.com/solutions/maximum-depth-of-binary-tree/


Divide Conquer vs Traverse
  令狐大师兄手把手
  http://www.lintcode.com/en/problem/binary-tree-paths/
http://www.jiuzhang.com/solutions/binary-tree-paths/

 Minimum Subtree
http://www.lintcode.com/en/problem/minimum-subtree/
http://www.jiuzhang.com/solutions/minimum-subtree/
Traverse + Divide Conquer 课后作业:只用 Divide Conquer 来实现

BREAK

 Result Type class ResultType { int var1, var2; }

  Balanced Binary Tree
http://www.lintcode.com/problem/balanced-binary-tree/
http://www.jiuzhang.com/solutions/balanced-binary-tree/

When we need ResultType?
  Subtree with Maximum Average
http://www.lintcode.com/problem/subtree-with-maximum-average/
http://www.jiuzhang.com/solutions/subtree-with-maximum-average/
  Flattern Binary Tree to Linked List
http://www.lintcode.com/problem/flatten-binary-tree-to-linked-list/
http://www.jiuzhang.com/solutions/flatten-binary-tree-to-linked-list/

  Lowest Common Ancestor
http://www.lintcode.com/problem/lowest-common-ancestor/
http://www.jiuzhang.com/solutions/lowest-common-ancestor/
with parent pointer vs no parent pointer follow up: LCA II & III

 Binary Tree Longest Consecutive Sequence
http://www.lintcode.com/problem/binary-tree-longest-consecutive-
sequence/
http://www.jiuzhang.com/solutions/binary-tree-longest-consecutive -sequence/
follow up: BT LCS II & III


  Binary Tree Path Sum I && II && III
http://www.lintcode.com/problem/binary-tree-path-sum/
http://www.lintcode.com/problem/binary-tree-path-sum-ii/ http://www.lintcode.com/problem/binary-tree-path-sum-iii/


#######

 Binary Search Tree 二叉查找树，简称“BST” 又名“二叉搜索树”“排序二叉树”
  Validate Binary Search Tree
http://www.lintcode.com/problem/validate-binary-search-tree/
http://www.jiuzhang.com/solutions/validate-binary-search-tree/
traverse vs divide conquer

  Convert Binary Search Tree to Doubly Linked List
http://www.lintcode.com/problem/convert-binary-search-tree-to-do
ubly-linked-list/
http://www.jiuzhang.com/solutions/convert-binary-search-tree-to-d oubly-linked-list/

   Related Questions

• Binary Search Tree Iterator
• http://www.lintcode.com/problem/binary-search-tree-iterator
• http://www.jiuzhang.com/solutions/binary-search-tree-iterator

• In-order Successor in Binary Search Tree
• http://www.lintcode.com/problem/inorder-successor-in-binary-search-tree/
• http://www.jiuzhang.com/solutions/inorder-successor-in-binary-search-tree/

• Search Range in Binary Search Tree
• http://www.lintcode.com/problem/search-range-in-binary-search-tree/

• Insert Node in a Binary Search Tree
• http://www.lintcode.com/problem/insert-node-in-a-binary-search-tree/

• Remove Node in a Binary Search Tree
• http://www.lintcode.com/problem/remove-node-in-binary-search-tree/
• http://www.mathcs.emory.edu/~cheung/Courses/171/Syllabus/9-BinTree/BST-delete.html

 点题时间 http://www.jiuzhang.com/qa/983/

 */
