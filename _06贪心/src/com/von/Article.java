package com.von;

/**
 * @author xwx
 * @create 2020/6/19 11:41
 **/
public class Article {
     int weight;
     int value;
     Double valueDensity;

    public Article(int weight, int value) {
        this.weight = weight;
        this.value = value;
        valueDensity = value * 1.0 / weight;
    }
    @Override
    public String toString() {
        return "Article [weight=" + weight + ", value=" + value + ", ValueDensity=" + valueDensity + "]";
    }
}
