package com.xwx;

import java.util.Arrays;

/**
 * @author xwx
 * @create 2020/6/19 9:12
 **/
public class Pirate {
    public static void main(String[] args) {
        int[] weights = {3, 5, 4, 10, 7, 14, 2, 11};
        Arrays.sort(weights);

        int capacity = 30;
        int weight=0;
        int count = 0;
        for (int i=0;i<weights.length && weight<capacity;i++){
            int newWeight = weight+weights[i];
            if(newWeight<=capacity){
                weight=newWeight;
                count++;
                System.out.println(weights[i]);
            }
        }
        System.out.println("最多装了" + count + "件古董。");
    }
}
