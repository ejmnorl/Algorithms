package d2c;

import java.util.ArrayList;
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
}
