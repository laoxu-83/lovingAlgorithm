package com.von;

/**最长上升子序列长度
 * @author xwx
 * @create 2020/6/20 20:18
 **/
public class LIS {
    public static void main(String[] args) {
        System.out.println(lengthOfLIS(new int[]{10, 2, 2, 5, 1, 7, 101, 18}));
    }

    /**
     * 二分搜索的方式
     * @param nums
     * @return
     */
    static int lengthOfLIS1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        // 牌堆的数量
        int len = 0;
        // 牌顶数组
        int[] top = new int[nums.length];
        // 遍历所有的牌
        for(int num : nums){
            //在牌堆中二分查找
            int begin=0;
            int end = len;
            while(begin<end){
                int mid = (begin+end) >> 1;
                if(num<top[mid]){
                    end = mid;
                } else{
                    begin = mid+1;
                }
            }
            //覆盖牌顶
            top[begin] = num;
            // 检查是否要新建一个牌堆
            if(begin == len) len++;
        }
        return len;
    }


    private static int lengthOfLIS(int[] nums) {
        if(nums==null || nums.length==0) return 0;
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for(int i=0;i<nums.length;i++){
            dp[i]=1;
            for(int j=0;j<i;j++){
                if(nums[i]<=nums[j]) continue;
                dp[i] = Math.max(dp[i],dp[j]+1);
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }
}
