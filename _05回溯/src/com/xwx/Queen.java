package com.xwx;




/**
 * @author xwx
 * @create 2020/6/18 20:01
 **/
public class Queen {

    public static void main(String[] args) {
        Queen main = new Queen();
        main.placeQueen(4);
    }

    /**
     * 数组索引是行号，数组元素是列号(即皇后的位置),
     */
    int[] cols;
    int ways;

     void placeQueen(int n) {
        if(n<1) return;
        cols = new int[n];
        place(0);  // 从第0行开始摆放皇后
         System.out.println(n + "皇后一共有" + ways + "种摆法");
     }

    /**
     * 从第row行摆放皇后
     *
     * 如何完成回溯？
     * 当place(row+1)无法进入isValid后，就会进入 place(row)中的col++操作，
     *
     * 如何剪枝？
     * isValid
     * @param row
     */
    void place(int row){
        if (row == cols.length) {
            ways++;
            show();
            return;
        }
        // 对于每一行，一列一列的检查
        for(int col = 0; col<cols.length;col++){
            if(isValid(row,col)){  //如果该位置可以放置皇后
                cols[row] = col;
                place(row+1);  //进行下一行放置
            }
        }
    }

    boolean isValid(int row,int col){
        for(int i=0;i<row;i++){
            if(col == cols[i]) return false;
            // 第i行的皇后根第row行第col列格子处在同一斜线上
            // 45度角斜线: y-y0 = (x-x0), 则 (y-y0)/(x-x0) = 1, 表示为45度角的斜线
            if (row - i == Math.abs(col - cols[i])) {
                return false;
            }
        }
        return true;
    }

    void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (cols[row] == col) { // 摆放了皇后
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
