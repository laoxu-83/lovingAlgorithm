package com.von;

/**
 * @author xwx
 * @create 2020/6/19 22:06
 **/
public class CoinChange {
    public static void main(String[] args) {
        System.out.println(coin5(15, new int[] {1,5,20,25}));
    }


    static int coin5(int n,int faces[]){
        if(n<1 || faces.length==0 || faces == null){
            return -1;
        }
        int[] dp = new int[n+1];

        //从小往大算dp[i]
        for(int i =1;i<=n;i++){
            int min = Integer.MAX_VALUE;
            for(int face:faces){
                if(i<face || dp[i-face]<0) continue;
                min = Math.min(dp[i-face],min);
            }
            if (min == Integer.MAX_VALUE){  //无法凑齐
                dp[i]=-1;
            } else {
                dp[i] = min + 1;  //表明，选择了一次硬币
            }
        }
        //返回需要的值
        return dp[n];
    }
    /**
     *递推，自底向上
     * @param n
     * @return
     */
    static int coin4(int n){
        if(n<1) return -1;
        //假设 dp(n) 是凑到 n 分需要的最少硬币个数
        int[] dp = new int[n+1];
        //faces[i]是凑够i分时最后选择的硬币的面值
        int[] faces = new int[dp.length];
        for(int i=1;i<=n;i++){
            //从小往大算dp[i]
            int min = Integer.MAX_VALUE;
            if(i>=1 && dp[i-1]<min){
                min = dp[i-1];
                faces[i] = 1;
            }if(i>=5 && dp[i-5]<min){
                min = dp[i-5];
                faces[i] = 5;
            }if(i>=20 && dp[i-20]<min){
                min = dp[i-20];
                faces[i] = 20;
            }if(i>=25 && dp[i-25]<min){
                min = dp[i-25];
                faces[i] = 25;
            }
            dp[i] = min+1;
        }
        for (int j = 0;j<dp.length;j++){
            System.out.println(dp[j]);
        }
        System.out.println("--------------");
        print(faces,n);
        return dp[n];
    }

    static void print(int[] faces,int n){
        System.out.print("["+n+"] :");
        while(n>0){
            System.out.println(faces[n]);
            n -= faces[n];
        }
    }

    static int coin3(int n){
        if(n<1) return -1;
        int[] dp = new int[n+1];
        for(int i=1;i<=n;i++){
            //从小往大算dp[i]
            int min = Integer.MAX_VALUE;
            if(i>=1) min = Math.min(min,dp[i-1]);
            if(i>=5) min = Math.min(min,dp[i-5]);
            if(i>=20) min = Math.min(min,dp[i-20]);
            if(i>=25) min = Math.min(min,dp[i-25]);
            dp[i] = min+1;
        }
        return dp[n];
    }

    /**
     *记忆搜索，自顶向下
     * @param n
     * @return
     */
    static int coin2(int n){
        if(n<1) return -1;
        int[] dp = new int[n+1];
        dp[25] = dp[20] = dp[5] = dp[1] = 1;
        return coin2(n,dp);
    }
    private static int coin2(int n,int[] dp) {
        if(n<1) return Integer.MAX_VALUE;
        if(dp[n]==0) {
            int min1 = Math.min(coin2(n - 25,dp), coin2(n - 20,dp));
            int min2 = Math.min(coin2(n - 5,dp), coin2(n - 1,dp));
            dp[n] = Math.min(min1,min2)+1;
        }
        return dp[n];
    }


    /**
     * 暴力递归，自顶向下（出现了重叠子问题）
     * @param n
     * @return
     */
    private static int coin1(int n) {
        if(n<1) return Integer.MAX_VALUE;
        if(n==25||n==20||n==5||n==1) return 1;
        int min1 = Math.min(coin1(n-25),coin1(n-20));
        int min2 = Math.min(coin1(n-5),coin1(n-1));
        return Math.min(min1,min2)+1;
    }
}
