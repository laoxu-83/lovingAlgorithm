package com.von;

/**最大连续子序列和
 * @author xwx
 * @create 2020/6/24 10:49
 **/
public class MaxSubArr {

    public static int maxSubArray(int[] nums){
        if(nums==null || nums.length==0) return -1;
        int[] dp = new int[nums.length];
        int max = dp[0] = 0;

        for(int i = 1;i<nums.length;i++){
            if(dp[i-1]>0){
                dp[i] = dp[i-1]+nums[i];
            } else{
                dp[i] = nums[i];
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }
}
