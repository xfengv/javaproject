package com.xfeng.javafeng;


import java.util.zip.CRC32;

public class MyClass {
    public static void main(String[] args) {
        System.out.println("hello world");

        int[] arr = new int[]{111, 16, 2, 19, 12, 180, 11, 14, 122, 12, 234, 563, 14};
        System.out.println("source");
        printArr(arr);


        //selectSort(arr);
        //bubbleSort(arr);
        //insertSort(arr);
        //int process = process(arr, 0, arr.length - 1);
        //mergeSort(arr, 0, arr.length - 1);
        quickSort(arr, 0, arr.length - 1);


        System.out.println("");
        System.out.println("source end");
        printArr(arr);


        CRC32 crc32 = new CRC32();
        crc32.update("腾讯手机管家".getBytes());
        long value = crc32.getValue();
        System.out.println("");
        System.out.println("crc32:"+value);


        KugouCRC32 kugouCRC32 = new KugouCRC32();
        byte[] bytes = "腾讯手机管家".getBytes();
        kugouCRC32.crc(bytes,0,bytes.length);
        long kugouCRC32value = kugouCRC32.getValue();
        System.out.println("");
        System.out.println("kugoucrc32:"+kugouCRC32value);
    }

    private static void quickSort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        //先找一个随机的location放到最后面
        //swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
        int[] location = myPartition(arr, l, r);
        quickSort(arr, l, location[0] - 1);
        quickSort(arr, location[1] + 1, r);
    }

    private static int[] myPartition(int[] arr, int l, int r) {
        int left = l - 1;//只标记小于区的下标
        int right = r;//只标记大于区的下标 因为已经交换过一次了，所以为r
        while (right > l) {
            if (arr[l] < arr[r]) {
                left++;
                swap(arr, l, left);
                l++;
            } else if (arr[l] > arr[r]) {
                right--;
                swap(arr, l, right);
            } else {
                l++;
            }
        }
        swap(arr, right, r);
        int[] position = new int[2];
        position[0] = left + 1;
        position[1] = right;
        return position;
    }

    private static int[] partition(int[] arr, int l, int r) {
        int leftPosition = 0;
        int rightPosition = 0;
        int midPosition = 0;
        for (int i = l; i < r; i++) {
            if (arr[i] < arr[r]) {
                swap(arr, l + leftPosition, i);
                leftPosition++;
            } else if (arr[i] == arr[r]) {
                //swap(arr, l + leftPosition + midPosition, i);
                midPosition++;
            } else {
                swap(arr, l + leftPosition + midPosition + rightPosition, i);
                rightPosition++;
            }
        }
        swap(arr, leftPosition + midPosition, r);
        int[] position = new int[2];
        position[0] = leftPosition;
        position[1] = leftPosition + midPosition;
        return position;
    }


    public static int[] partition2(int[] arr, int L, int R) {
        int less = L - 1;

//    思考这里为什么是R，不是R-1
//    因为R位置的数和基数交换了，交换后R位置上是基数，L 位置上的数要和 R 位置上的基数对比

        int more = R;
        while (L < more) {
            if (arr[L] < arr[R]) {  //注意这里是 l 和 r 位置的元素比较。也就是l和基数比较
                swap(arr, ++less, L++);   //more 和 less 只是标记大于等于小于区的下标，和比较无关
            } else if (arr[L] > arr[R]) {
                swap(arr, --more, L);
            } else {
                L++;
            }
        }

//    这里排完左中右的数，就把more位置上的数（也就是>基数区域的第一个数）和最后R位置上的基数换回来
//    左边数（<基数的数不变）
//    中间数（=基数的数最后加一个，加的就是>基数区域的第一个数换成了最后R位置上的基数）
//    右边数（>基数的数，最后的交换相当于整体往后移动了，最后一个也就没了）
        swap(arr, more, R);
        //为什么这里是less+1,more
        //因为上一步把R和more又换回来， 中间数多了一个，more位置上的数也就从>区第一个数变成了=区最后一个数，看下图
        return new int[]{less + 1, more};
    }

    private static void mergeSort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }


    // 只有这个排序算法用到了min参数
    private static void merge(int[] arr, int l, int mid, int r) {
        int[] tempArr = new int[r - l + 1];
        int i = l;
        int j = mid + 1;
        int k = 0;
        // 较小的数移动到新数组中
        while (i <= mid && j <= r) {
            if (arr[i] < arr[j]) {
                tempArr[k++] = arr[i++];
            } else {
                tempArr[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            tempArr[k++] = arr[i++];
        }
        while (j <= r) {
            tempArr[k++] = arr[j++];
        }

        System.arraycopy(tempArr, 0, arr, l, tempArr.length);
    }

    private static void printArr(int[] arr) {
        for (int num : arr) {
            System.out.print(" " + num);
        }
    }

    // 二分法求最大值
    private static int process(int[] arr, int start, int end) {
        if (start == end) {
            return arr[start];
        }
        int midPosition = start + ((end - start) >> 1);
        int leftMax = process(arr, start, midPosition);
        int rightMax = process(arr, midPosition + 1, end);
        return Math.max(leftMax, rightMax);
    }


    // 插入排序
    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                }
            }
        }
    }

    // 冒泡排序
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }


    //选择排序
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int minPosition = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            minPosition = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minPosition]) {
                    minPosition = j;
                }
            }
            swap(arr, i, minPosition);
        }
    }


    private static void swap(int[] arr, int i, int minPosition) {
        int temp = arr[minPosition];
        arr[minPosition] = arr[i];
        arr[i] = temp;
    }
}