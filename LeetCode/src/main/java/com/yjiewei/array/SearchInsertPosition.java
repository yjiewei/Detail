package com.yjiewei.array;

/**
 * 剑指 Offer II 068. 查找插入位置
 * https://leetcode-cn.com/problems/N6YdxV/
 * https://leetcode-cn.com/problems/search-insert-position/
 * @author yjiewei
 * @date 2021/11/27
 */
public class SearchInsertPosition {
    public static void main(String[] args) {
        int[] nums = new int[]{1,3,5,6};
        int target = 5;
        int position = searchInsert(nums, target);
        System.out.println("要插入的位置是：" + position);
    }

    public static int searchInsert(int[] nums, int target) {
        // 很显然，这题就是用二分法
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (target < nums[mid]) {
                right = mid - 1;
            }else if(target > nums[mid]) {
                left = mid + 1;
            }else {
                return mid;
            }
        }
        return left;
    }
}
