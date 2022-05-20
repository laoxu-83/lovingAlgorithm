package com.von;

import java.util.Arrays;

/**
 * 假设有 25 分、10 分、5 分、1 分的硬币，
 * 现要找给客户 41 分的零钱，如何办到硬币个数最少？
 */
public class CoinChange {
    public static void main(String[] args) {
        Integer[] faces={25,5,10,1};
        //逆序从大倒下排列
        Arrays.sort(faces, (Integer f1, Integer f2) -> f2 - f1);

        int money =41, coins = 0, i=0;
        while (i<faces.length){
            if(money < faces[i]){
                i++;
                continue;
            }

            System.out.println(faces[i]);
            money -= faces[i];
            coins++;
        }
        System.out.println(coins);
    }
}
