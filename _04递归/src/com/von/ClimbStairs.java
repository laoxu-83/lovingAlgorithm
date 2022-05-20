package com.von;

/**
 * @author xwx
 * @create 2020/6/18 16:05
 **/
public class ClimbStairs {
    int climb1(int n){
        if(n<=2) return n;
        return climb1(n-1)+climb1(n-2);
    }

    int climb2(int n){
        if(n<=2) return n;
        int first = 1;
        int second = 2;
        for(int i = 3;i<=n;i++){
            second = first+second;
            first = second -first;
        }
        return second;
    }
}
