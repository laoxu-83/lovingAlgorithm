package com.von;

/**最长公共子串
 * @author xwx
 * @create 2020/6/21 19:47
 **/
public class LCSSubString {
    public static void main(String[] args) {

    }

    static int lcs1(String str1,String str2) {
        if (str1 == null || str2 == null) return 0;
        char[] cs1 = str1.toCharArray();
        char[] cs2 = str2.toCharArray();
        if (cs1.length == 0 || cs2.length == 0) return 0;
        char[] cols = cs2;
        char[] rows = cs1;
        if(cs1.length<cs2.length){
            cols = cs1;
            rows = cs2;
        }

        int[] dp = new int[cols.length+1];
        int max = 0;
        for(int row=1;row<=cs1.length;row++){
            int cur=0;
            for (int col=1;col<=cs2.length;col++) {
                int leftTop = cur;
                cur = dp[col];
                if(rows[row-1]==cols[col-1]){
                    dp[col] = leftTop+1;
                } else {
                    dp[col] =0;
                }
                max = Math.max(max,dp[col]);
            }
        }
        return max;
    }

    static int lcs(String str1,String str2) {
        if (str1 == null || str2 == null) return 0;
        char[] cs1 = str1.toCharArray();
        char[] cs2 = str2.toCharArray();
        if (cs1.length == 0 || cs2.length == 0) return 0;

        int[][] dp = new int[cs1.length+1][cs2.length+1];
        int max=0;
        for(int i=1;i<=cs1.length;i++){
            for (int j=1;j<=cs2.length;j++){
                //注意i,j都是从1开始的，如果两个串前一个元素相等，再次比较
                if(cs1[i-1]==cs2[j-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    continue;
                }
                max =  Math.max(max,dp[i][j]);
            }
        }
        return max;
    }

}
