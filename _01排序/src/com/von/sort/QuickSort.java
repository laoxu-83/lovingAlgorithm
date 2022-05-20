package com.von.sort;



/**
 * @author xwx
 * @create 2020/6/14 18:18
 **/
public class QuickSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        sort(0,array.length);
    }

    /**
     * 对 [begin, end) 范围的元素进行快速排序
     */
    private void sort(int begin, int end) {
        if((end-begin)<2) return;
        //确定轴点位置
        int pivot = pivotIndex(begin,end);
        //对子序列快速排序
        sort(begin,pivot);
        sort(pivot+1,end);
    }

    /**
     * 构造出 [begin, end) 范围的轴点元素
     * @return 轴点元素的最终位置
     */
    private int pivotIndex(int begin, int end){
        // 随机选择轴点元素
        swap(begin, begin + (int)Math.random()*(end - begin));
        // 备份begin位置的元素
        E pivot = array[begin];
        //end指向最后一个元素
        end--;
        //***************如何左右交替扫描***********
        while(begin < end){
            // 从右往左扫描
            while(begin < end) {
                if (cmp(pivot,array[end]) < 0) { // 右边元素> 轴点元素
                    end--;
                } else {  //右边元素 <= 轴点元素
                    array[begin] = array[end];
                    begin++;
                    break;
                }
            }

            // 从左往右扫描
            while(begin < end) {
                if(cmp(pivot, array[begin]) > 0){ // 左边元素 < 轴点元素
                    begin++;
                }else{ // 左边元素 >= 轴点元素
                    array[end--] = array[begin];
                    break;
                }
            }
        }

        // 将轴点元素放入最终的位置
        array[begin] = pivot;
        // 返回轴点元素的位置
        return begin; // begin==end
    }



}

