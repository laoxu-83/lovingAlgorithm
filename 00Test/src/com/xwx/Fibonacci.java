package com.xwx;

/**
 * @author xwx
 * @create 2020/5/27 19:56
 **/
public class Fibonacci {

    public static void main(String[] args) {
        TimeTool.check("fib1", new TimeTool.Task() {
            @Override
            public void execute() {
                System.out.println(fib1(30));
            }
        });

        TimeTool.check("fib2", new TimeTool.Task() {
            @Override
            public void execute() {
                System.out.println(fib2(30));
            }
        });
    }

    /**
     * 递归，O(2^n)
     * 问题在于函数的重复调用
     * @param n
     * @return
     */
    private static int fib1(int n){
        if (n<=1) return n;
        return fib1(n-1)+fib1(n-2);
    }

    /**
     * O(n)
     * @param n
     * @return
     */
    private static int fib2(int n){
        if (n<=1) return n;
        int first = 0;
        int second = 1;
        for (int i=0;i<n-1;i++){
            int sum = first + second;
            first = second;
            second = sum;
        }
        return second;
    }

    /**
     * 滚动数组进化
     * @param n
     * @return
     */
    private static int fib3(int n){
        if(n<=2) return 1;
        int first = 1;
        int second = 1;
        for (int i=3;i<=n;i++){
            second = second+first;
            first = second-first;
        }
        return second;
    }
}
