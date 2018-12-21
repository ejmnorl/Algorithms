package binary_search;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class basic_bs {
    /*
    Classical Binary Search

    https://www.lintcode.com/problem/classical-binary-search/description
        Example
        Given [1, 2, 2, 4, 5, 5].

        For target = 2, return 1 or 2.

        For target = 5, return 4 or 5.

        For target = 6, return -1.

        Challenge
        O(logn) time
     */


    /**
     * @param nums: An integer array sorted in ascending order
     * @param target: An integer
     * @return: An integer
     */
    public int findPosition(int[] nums, int target) {
        // write your code here
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if(nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                l = l + 1;
            } else {
                r = r - 1;
            }
        }
        return -1;
    }

    /*
    First Position of Target
    https://www.lintcode.com/problem/first-position-of-target/description

    Description
    For a given sorted array (ascending order) and a target number, find the first index of this number in O(log n) time complexity.

    If the target number does not exist in the array, return -1.

    Have you met this question in a real interview?
    Example
    If the array is [1, 2, 3, 3, 4, 5, 10], for given target 3, return 2.

    Challenge
    If the count of numbers is bigger than 2^32, can your code work properly?

    Related Problems:

    https://www.lintcode.com/problem/closest-number-in-sorted-array/description
    https://www.lintcode.com/problem/classical-binary-search/description
    https://www.lintcode.com/problem/first-position-of-target/

     */

    /**
     * @param nums: The integer array.
     * @param target: Target to find.
     * @return: The first position of target. Position starts from 0.
     */

    public int binarySearch(int[] nums, int target) {
        // write your code here
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        if (nums[l] == target) {
            return l;
        }
        if (nums[r] == target) {
            return r;
        }
        return -1;
    }

    /*
    Last Position of Target
    https://www.lintcode.com/problem/last-position-of-target/description

    Description
    Find the last position of a target number in a sorted array. Return -1 if target does not exist.

    Have you met this question in a real interview?
    Example
    Given [1, 2, 2, 4, 5, 5].

    For target = 2, return 2.

    For target = 5, return 5.

    For target = 6, return -1.
     */

    /**
     * @param nums: An integer array sorted in ascending order
     * @param target: An integer
     * @return: An integer
     */
    public int lastPosition(int[] nums, int target) {
        // write your code here
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int l = 0, r = nums.length - 1;
        while (l + 1 < r) {
            int mid = l + (r - l)/2;
            if (nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid;
            }
        }
        if (nums[r] == target) {
            return r;
        }
        if (nums[l] == target) {
            return l;
        }
        return -1;
    }

    /*
    First Bad Version
    https://www.lintcode.com/problem/first-bad-version/description
    Description
    The code base version is an integer start from 1 to n. One day, someone committed a bad version in the code case, so it caused this version
    and the following versions are all failed in the unit tests. Find the first bad version.
    You can call isBadVersion to help you determine which version is the first bad one.
    The details interface can be found in the code's annotation part.
    Please read the annotation in code area to get the correct way to call isBadVersion in different language.
    For example, Java is SVNRepo.isBadVersion(v)

    Example
    Given n = 5:

    isBadVersion(3) -> false
    isBadVersion(5) -> true
    isBadVersion(4) -> true
    Here we are 100% sure that the 4th version is the first bad version.

    Challenge
    You should call isBadVersion as few as possible.
     */

    /**
     * @param n: An integer
     * @return: An integer which is the first bad version.
     */
    public int findFirstBadVersion(int n) {
        // write your code here
        while (l < r) {
            int mid = l + (r - l)/2;
            if (!SVNRepo.isBadVersion(mid)) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        if (SVNRepo.isBadVersion(l)) {
            return l;
        }
        if (SVNRepo.isBadVersion(r)) {
            return r;
        }
        return -1;
    }

    /*
    Search in a Big Sorted Array
    https://www.lintcode.com/problem/search-in-a-big-sorted-array/description

    Description
    Given a big sorted array with positive integers sorted by ascending order.
    The array is so big so that you can not get the length of the whole array directly,
    and you can only access the kth number by ArrayReader.get(k) (or ArrayReader->get(k) for C++).
    Find the first index of a target number. Your algorithm should be in O(log k),
    where k is the first index of the target number.

    Return -1, if the number doesn't exist in the array.

    If you accessed an inaccessible index (outside of the array), ArrayReader.get will return 2,147,483,647.

    Example
    Given [1, 3, 6, 9, 21, ...], and target = 3, return 1.

    Given [1, 3, 6, 9, 21, ...], and target = 4, return -1.

    Challenge
    O(log k), k is the first index of the given target number.

     */

    /*
     * @param reader: An instance of ArrayReader.
     * @param target: An integer
     * @return: An integer which is the first index of target.
     */
    /*
    !!!! REDO
     */

    //TODO
//    public int searchBigSortedArray(ArrayReader reader, int target) {
//        // write your code here
//        int l = 0, r = k;
//        while (l < r) {
//            int pivot = xx;
//            if (ArrayReader.get(pivot) < target) {
//                l = pivot + 1;
//            } else {
//                r = pivot;
//            }
//        }
//
//    }
//
//    private boolean isOutRange(int x) {
//        int[] x = {2,147,483,647};
//        Set<Integer> set = new HashSet<Integer>(Arrays.asList(x));
//        return set.contains(ArrayReadder.get(x));
//    }



    /* REDO!! Corner case!!!
    Find Minimum in Rotated Sorted Array
    https://www.lintcode.com/problem/find-minimum-in-rotated-sorted-array/description

    Description
    Suppose a sorted array is rotated at some pivot unknown to you beforehand.

    (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

    Find the minimum element.

    You may assume no duplicate exists in the array.

    Have you met this question in a real interview?
    Example
    Given [4, 5, 6, 7, 0, 1, 2] return 0
     */


    /**
     * @param nums: a rotated sorted array
     * @return: the minimum number in the array
     */


    public int findMin(int[] nums) {
        // write your code here
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int l = 0, r = nums.length - 1;
        int minSoFar = Integer.MAX_VALUE;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            minSoFar = Math.min(minSoFar, nums[mid]);
            if (nums[l] > nums[r]) { //rotated
                if (nums[mid] >= nums[l]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else { //sorted
                r = mid - 1;
            }
        }
        return minSoFar;
    }


    /*
    Smallest Rectangle Enclosing Black Pixels
    https://www.lintcode.com/problem/smallest-rectangle-enclosing-black-pixels/description

    Description
    An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel.
    The black pixels are connected,
    i.e., there is only one black region.
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
    /**
     * @param image: a binary matrix with '0' and '1'
     * @param x: the location of one of the black pixels
     * @param y: the location of one of the black pixels
     * @return: an integer
     */

    public int minArea(char[][] image, int x, int y) {
        // write your code here
        if (image == null || image.length == 0 || image[0].length == 0) {
            return 0;
        }

        int leftMost = searchHor(image, true, 0, y);
        int rightMost = searchHor(image, false, y + 1, image[0].length - 1);
        if (rightMost == -1) {
            rightMost = y;
        }

        int upMost = searchVer(image, true, 0, x);
        int downMost = searchVer(image, false, x + 1, image.length - 1);
        if (downMost == -1) {
            downMost = x;
        }

        return (rightMost - leftMost + 1) * (downMost - upMost + 1);
    }


    private int searchVer(char[][] image, boolean up, int top, int bottom) {
        if (up) {
            int upMost = Integer.MAX_VALUE;
            while (top <= bottom) {
                int mid = top + (bottom - top) / 2;
                if (hasBlack(image, true, mid, -1)) {
                    upMost = mid;
                    bottom = mid - 1;
                } else {
                    top = mid + 1;
                }
            }
            return upMost == Integer.MAX_VALUE ? -1 : upMost;
        } else {
            int downMost = -1;
            while (top <= bottom) {
                int mid = (bottom - top) / 2 + top;
                if (hasBlack(image, true, mid, -1)) {
                    downMost = mid;
                    top = mid + 1;
                } else {
                    bottom = mid - 1;
                }
            }
            return downMost;
        }
    }

    private int searchHor (char[][] image, boolean left, int l, int r) {
        if (left) {
            int leftMost = Integer.MAX_VALUE;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (hasBlack(image, false, -1, mid)) {
                    leftMost = mid;
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            return leftMost == Integer.MAX_VALUE ? -1 : leftMost;
        } else {
            int rightMost = -1;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (hasBlack(image, false, -1, mid)) {
                    rightMost = mid;
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            return rightMost;
        }
    }

    private boolean hasBlack(char[][] image, boolean checkRow, int x, int y) {
        if (checkRow) {
            for (int j = 0; j < image[0].length; j++) {
                if (image[x][j] == '1') {}
                return true;
            }
            return false;
        } else {//check column
            for (int i = 0; i < image.length; i++) {
                if (image[i][y] == '1') {
                    return true;
                }
            }
            return false;
        }
    }


    /*
         Search a 2D Matrix
         https://www.lintcode.com/problem/search-a-2d-matrix/description

        Write an efficient algorithm that searches for a value in an m x n matrix.

        This matrix has the following properties:
        Integers in each row are sorted from left to right.
        The first integer of each row is greater than the last integer of the previous row.


        Example
        Consider the following matrix:

        [
            [1, 3, 5, 7],
            [10, 11, 16, 20],
            [23, 30, 34, 50]
        ]
        Given target = 3, return true.

        Challenge
        O(log(n) + log(m)) time
     */

    /**
     * @param matrix: matrix, a list of lists of integers
     * @param target: An integer
     * @return: a boolean, indicate whether matrix contains target
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        // write your code here
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int row = findRow(matrix, target);
        if (row == -1) {
            return false;
        }
        return searchTar(matrix, row, target);
    }

    private int findRow(int[][] matrix, int target) {
        int l = 0, r = matrix.length - 1;
        int ret = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (matrix[mid][0] <= target) {
                ret = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ret;
    }

    private boolean searchTar(int[][] matrix, int row, int target) {
        int l = 0, r = matrix[0].length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (matrix[row][mid] == target) {
                return true;
            }
            if (matrix[row][mid] < target) {
                l = mid + 1;
            }  else {
                r = mid - 1;
            }
        }
        return false;
    }

    /*
        Search a 2D Matrix II
        https://www.lintcode.com/problem/search-a-2d-matrix-ii/description

        Write an efficient algorithm that searches for a value in an m x n matrix,
        return the occurrence of it.

        This matrix has the following properties:

        Integers in each row are sorted from left to right.
        Integers in each column are sorted from up to bottom.
        No duplicate integers in each row or column.
        Example
        Consider the following matrix:

        [
          [1, 3, 5, 7],
          [2, 4, 7, 8],
          [3, 5, 9, 10]
        ]
        Given target = 3, return 2.

        Challenge
        O(m+n) time and O(1) extra space
     */

    /**
     * @param matrix: A list of lists of integers
     * @param target: An integer you want to search in matrix
     * @return: An integer indicate the total occurrence of target in the given matrix
     */
    public int searchMatrix(int[][] matrix, int target) {
        // write your code here
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int cnt = 0;
        int x = matrix.length - 1, y = 0;
        while (x >= 0 && y <= matrix[0].length - 1) {
            if (matrix[x][y] == target) {
                cnt++;
            }
            if (target <= matrix[x][y]) {
                x--;
            } else {
                y++;
            }
        }
        return cnt;
    }

    /*
    Search for a Range
    https://www.lintcode.com/problem/search-for-a-range/description

    Given a sorted array of n integers,
    find the starting and ending position of a given target value.
    If the target is not found in the array, return [-1, -1].

    Example
    Given [5, 7, 7, 8, 8, 10] and target value 8,
    return [3, 4].

    Challenge
    O(log n) time.
     */

    /**
     * @param A: an integer sorted array
     * @param target: an integer to be inserted
     * @return: a list of length 2, [index1, index2]
     */
    public int[] searchRange(int[] A, int target) {
        // write your code here
        int[] ret = {-1, -1};
        if (A == null || A.length == 0) {
            return ret;
        }
        int start = -1;
        int l = 0, r = A.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (A[mid] == target) {
                start = mid;
            }
            if (A[mid] >= target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        int end = -1;
        l = 0;
        r = A.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (A[mid] == target) {
                end = mid;
            }
            if (A[mid] <= target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        ret[0] = start;
        ret[1] = end;
        return ret;
    }


    /*
    https://www.lintcode.com/problem/total-occurrence-of-target/description
    Total Occurrence of Target
        Given a target number and an integer array sorted in ascending order.
        Find the total number of occurrences of target in the array.

        Example
        Given [1, 3, 3, 4, 5] and target = 3, return 2.

        Given [2, 2, 3, 4, 6] and target = 4, return 1.

        Given [1, 2, 3, 4, 5] and target = 6, return 0.

        Challenge
        Time complexity in O(logn)
     */
    /**
     * @param A: A an integer array sorted in ascending order
     * @param target: An integer
     * @return: An integer
     */
    public int totalOccurrence(int[] A, int target) {
        // write your code here
        if (A == null || A.length == 0) {
            return 0;
        }

        int start = -1;
        int l = 0, r = A.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (A[mid] == target) {
                start = mid;
            }
            if (A[mid] >= target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        if (start == -1) {
            return 0;
        }

        int end = -1;
        l = 0;
        r = A.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (A[mid] == target) {
                end = mid;
            }
            if (A[mid] <= target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return end - start + 1;
    }


    /* TODO
    https://www.lintcode.com/problem/maximum-number-in-mountain-sequence/description
    Maximum Number in Mountain Sequence

    Description
    Given a mountain sequence of n integers which increase firstly and then decrease,
    find the mountain top.

    Example
    Given nums = [1, 2, 4, 8, 6, 3] return 8
    Given nums = [10, 9, 8, 7], return 10
     */

    /**
     * @param nums: a mountain sequence which increase firstly and then decrease
     * @return: then mountain top
     */
    public int mountainSequence(int[] nums) {
        // write your code here


    }

    /* REDO!!!
    https://www.lintcode.com/problem/find-peak-element/description
   Find Peak Element
    There is an integer array which has the following features:

    The numbers in adjacent positions are different.
    A[0] < A[1] && A[A.length - 2] > A[A.length - 1].
    We define a position P is a peak if:

    A[P] > A[P-1] && A[P] > A[P+1]
    Find a peak element in this array. Return the index of the peak.

    Example
    Given [1, 2, 1, 3, 4, 5, 7, 6]
    Return index 1 (which is number 2) or 6 (which is number 7)

    Challenge
    Time complexity O(logN)

    Notice
    It's guaranteed the array has at least one peak.
    The array may contain multiple peeks, find any of them.
    The array has at least 3 numbers in it.

     */

    /**
     * @param A: An integers array.
     * @return: return any of peek positions.
     */
    public static int findPeak(int[] A) { //NOTE: LOGIC OF THIS VERSION IS TOTALLY WRONG!!!!
        // write your code here
        if (A == null || A.length <= 2) {
            return 0;
        }
        int l = 0, r = A.length - 1;
        while (l + 1 < r) {
            int mid = l + (r - l) / 2;
            if (A[mid] > A[l] && A[mid] > A[r]) {
                return mid;
            }
            if (A[mid] > A[l]) {
                l = mid;
            } else {
                r = mid;
            }
        }
        if (A[l] > A[r]) {
            return l;
        }
        if (A[r] > A[l]) {
            return r;
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] x = {13,20,21,7};
        int ret = findPeak(x);
        System.out.println("ret = " + ret);
    }

    /* REDO
    https://www.lintcode.com/problem/search-in-rotated-sorted-array/description
    Search in Rotated Sorted Array
    Suppose a sorted array is rotated at some pivot unknown to you beforehand.

    (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

    You are given a target value to search.
    If found in the array return its index, otherwise return -1.
    You may assume no duplicate exists in the array.

    Example
    For [4, 5, 1, 2, 3] and target=1, return 2.
    For [4, 5, 1, 2, 3] and target=0, return -1.

    Challenge
    O(logN) time
     */
    /**
     * @param A: an integer rotated sorted array
     * @param target: an integer to be searched
     * @return: an integer
     */
    public int search(int[] A, int target) {
        // write your code here
        if (A == null || A.length == 0) {
            return -1;
        }
        return core(A, target, 0, A.length - 1);
    }

    private int core(int[] A, int target, int l, int r) {
        if (A == null || A.length == 0) {
            return -1;
        }
        int l = 0, r = A.length - 1;
        while (l + 1 < r) {
            int mid = l + (r - l) / 2;
            if (target == A[mid]) {
                return mid;
            }
            if (A[mid] < A[l]) {
                if (target > A[mid] && target <= A[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else {
                if (target >= A[l] && target < A[mid] ) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
        }
        if (A[l] == target) {
            return l;
        }
        if (A[r] == target) {
            return r;
        }
        return -1;
    }


    /*
    Search in Rotated Sorted Array II
    Follow up for Search in Rotated Sorted Array:

    What if duplicates are allowed?
    Would this affect the run-time complexity? How and why?
    Write a function to determine if a given target is in the array.
     */

    /**
     * @param A: an integer ratated sorted array and duplicates are allowed
     * @param target: An integer
     * @return: a boolean
     */
    public boolean search(int[] A, int target) {
        // write your code here
        if (A == null || A.length == 0) {
            return false;
        }
        int l = 0, r =  A.length - 1;
        return core(A, target, 0, A.length - 1);
    }

    private boolean core(int[] A, int target, int l, int r) {
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (A[mid] == target) {
                return true;
            }
            if (A[mid] > A[l]) {
                if (target >= A[l] && target < A[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else if (A[mid] < A[l]) {
                if (target > A[mid] && target <= A[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else {
                return core(A, target, l , mid - 1) || core(A, target, mid + 1, r);
            }
        }
        return false;
    }

//    public static void main(String[] args) {
//        System.out.println("TEST DOUBLE MAX: " + Double.MAX_VALUE);
//    }
}