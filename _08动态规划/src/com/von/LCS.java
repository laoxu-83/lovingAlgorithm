package com.von;

/**
 * 最长公共子序列
 * 可以不连续
 * @author xwx
 * @create 2020/6/20 20:40
 **/
public class LCS {
    public static void main(String[] args) {
        int len = lcs2(new int[]{1,3,5,9,10}, new int[]{1,4,9,10});
        System.out.println(len);
    }

    /**
     * 动态规划
     * @param text1
     * @param text2
     * @return
     */

    //一维数组优化
    static int lcs5(String text1, String text2) {
        if (text1 == null || text2 == null ) return 0;
        char[] chars1 = text1.toCharArray();
        if(chars1.length==0) return 0;
        char[] chars2 = text2.toCharArray();
        if(chars2.length==0) return 0;

        char[] rowsChar = chars1, colsChar = chars2;
        if (chars1.length < chars2.length) {
            colsChar = chars1;
            rowsChar = chars2;
        }

        int[] dp = new int[colsChar.length+1];
        for(int i=1;i<=rowsChar.length;i++){
            int cur=0;
            for(int j=1;j<=colsChar.length;j++){
                int leftTop = cur;
                cur = dp[j];
                if(rowsChar[i-1]==colsChar[j-1]){
                    dp[j] = leftTop+1;
                }else{
                    dp[j] = Math.max(dp[j],dp[j-1]);
                }
            }
        }
        return dp[colsChar.length];
    }

    static int lcs4(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        int rows = 0, cols = 0;
        int[] rowsNum, colsNum;
        if (nums1.length > nums2.length) {
            cols = nums2.length;
            colsNum = nums2;
            rows = nums1.length;
            rowsNum = nums1;
        } else {
            cols = nums1.length;
            colsNum = nums1;
            rows = nums2.length;
            rowsNum = nums2;
        }
        int[] dp = new int[cols];
        for(int i=1;i<=rows;i++){
            int cur=0;
            for(int j=1;j<=cols;j++){
                int leftTop = cur;
                cur = dp[j];
                if(rowsNum[i-1]==colsNum[j-1]){
                    dp[j] = leftTop+1;
                }else{
                    dp[j] = Math.max(dp[j],dp[j-1]);
                }
            }
        }
        return dp[cols];
    }

    /**
     * 滚动数组
     */
    static int lcs3(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        int[][] dp = new int[2][nums2.length + 1];

        for (int i = 1; i <= nums1.length; i++) {
            int row = i&1;
            int prevRow = (i-1)&1;

            for (int j = 1; j <= nums2.length; j++) {
                if(nums1[i-1] == nums2[j-1]) {
                    dp[row][j] = dp[prevRow][j-1]+1;
                }
                else {
                    dp[row][j] = Math.max(dp[prevRow][j],dp[row][j-1]);
                }
            }
        }
        return dp[nums1.length&1][nums2.length];
    }

    //二维数组，动态规划
    static int lcs2(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        //序列1，
        for (int i = 1; i <= nums1.length; i++) {
            //序列2
            for (int j = 1; j <= nums2.length; j++) {
                //前i个元素最后一个元素的下标为i-1
                if(nums1[i-1] == nums2[j-1]) {
                    dp[i][j] = dp[i-1][j-1]+1;
                }
                else {
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[nums1.length][nums2.length];
    }


    private static int lcs1(int[] nums1, int[] nums2) {
        if(nums1 == null || nums2 == null) return 0;
        if(nums1.length == 0 || nums2.length == 0) return 0;
        return lcs1(nums1,nums1.length,nums2,nums2.length);
    }

    static int lcs1(int[] nums1,int i,int[] nums2,int j){
        if(i==0||j==0) return 0;  //递归基
        if(nums1[i]==nums2[j]) return lcs1(nums1,i-1,nums2,j-1)+1;
        return Math.max(lcs1(nums1,i-1,nums2,j),
                lcs1(nums1,i,nums2,j-1));
    }
}
