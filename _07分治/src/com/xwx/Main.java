package com.xwx;

/**最大子序列问题
 * @author xwx
 * @create 2020/6/19 14:13
 **/
public class Main {
    public static void main(String[] args) {
      int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubArray0(nums));
    }

    /**
     * 动态规划
     * 假设 dp(i) 是以 nums[i] 结尾的最大连续子序列和（nums是整个序列）
     * @param nums
     * @return
     */
    static int maxSubArray01(int[] nums){
        if(nums==null || nums.length==0) return 0;
        int dp = nums[0];
        int max = dp;
        for(int i=1;i<nums.length;i++){
            if(dp<=0){
                dp=nums[i];
            } else{
                dp = dp+nums[i];
            }
            max = Math.max(max,dp);
        }
        return max;
    }

    static int maxSubArray0(int[] nums){
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for(int i=1;i<dp.length;i++){
            if(dp[i-1]<=0){
                dp[i]=nums[i];
            } else{
                dp[i] = dp[i-1]+nums[i];
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }

    /**
     * 分治
     * @param nums
     * @return
     */
    private static int maxSubArray(int[] nums) {
        if(nums==null || nums.length==0) return 0;
        return maxSubArray(nums,0,nums.length);
    }

    static int maxSubArray(int[] nums,int begin, int end){
        // 递归基: end - begin < 2, 说明只有一个元素, nums[begin] == nums[end]
        if(end-begin<2) return nums[begin];

        int mid = (begin+end) >> 1;
        int leftMax = Integer.MIN_VALUE;
        int leftsum = 0;
        for(int i = mid-1; i>=begin;i--){
            leftsum += nums[i];
            if(leftMax<leftsum) leftMax = leftsum;
        }

        int rightMax = Integer.MIN_VALUE;
        int rightsum = 0;
        for(int i = mid; i<end;i++){
            rightsum += nums[i];
            rightMax = Math.max(rightMax,rightsum);
        }
        return Math.max(leftMax+rightMax,
                Math.max(maxSubArray(nums,begin,mid),
                        maxSubArray(nums,mid,end)));
    }

    /**
     * 暴力解法
     * @param nums
     * @return
     */
    private static int maxSubArray1(int[] nums) {
        if(nums==null || nums.length==0) return 0;
        int max = Integer.MIN_VALUE;
        for(int begin=0;begin<nums.length;begin++){
            for(int end = begin;end<nums.length;end++){
                int sum = 0;
                sum += nums[end];
                max = Math.max(max,sum);
            }
        }
        return max;
    }

    private static int maxSubArray2(int[] nums) {
        if(nums==null || nums.length==0) return 0;
        int max = Integer.MIN_VALUE;
        for(int begin=0;begin<nums.length;begin++){
            for(int end = begin;end<nums.length;end++){
                int sum = 0;
                for(int i = begin;i<=end;i++){
                    sum += nums[i];
                }
                max = Math.max(max,sum);
            }
        }
        return max;
    }
}
