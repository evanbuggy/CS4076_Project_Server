import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class SumArray_ForkJoin extends RecursiveTask<Long> {
    static final int SEQUENTIAL_THRESHOLD = 10;
    int high;
    int low;

    ArrayList<String> list = new ArrayList<String>();

    SumArray_ForkJoin(ArrayList<String> list, int high, int low) {
        this.list = list;
        this.high = high;
        this.low = low;
    }

    protected Long compute() {
        if (high - low <= SEQUENTIAL_THRESHOLD) {
            long sum = 0;
            for(int i=low; i < high; ++i)
                if () {

                }
            return sum;
        } else {
            int mid = low + (high - low) / 2;
            SumArray_ForkJoin left  = new SumArray_ForkJoin(array, low, mid);
            SumArray_ForkJoin right = new SumArray_ForkJoin(array, mid, high);
            left.fork();
            long rightAns = right.compute();
            long leftAns  = left.join();
            return leftAns + rightAns;
        }
    }

    static long Sum(int[] array) {
        return ForkJoinPool.commonPool().invoke(new SumArray_ForkJoin(array,0,array.length));
    }



    public static void main(String[] args) {
        int[] array = new Random().ints(10000, 10, 100000).toArray();
        long sum = SumArray_ForkJoin.Sum(array);
        System.out.println("Ans:" + sum);
    }
}