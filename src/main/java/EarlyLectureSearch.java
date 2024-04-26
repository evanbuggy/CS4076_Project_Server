import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class EarlyLectureSearch extends RecursiveTask<Classes> {
    static final int SEQUENTIAL_THRESHOLD = 10;
    int high;
    int low;

    Classes list;
    Classes earlySlots;

    EarlyLectureSearch(Classes earlySlots, Classes list, int high, int low) {
        this.list = list;
        this.earlySlots = earlySlots;
        this.high = high;
        this.low = low;
    }

    protected Classes compute() {
        if (high - low <= SEQUENTIAL_THRESHOLD) {
            Classes unable_to_move = new Classes();
            for(int i = low; i < high; i++)
                for (int k = 0; k < earlySlots.getSize(); k++) {
                    if (list.getClass(i).getDate().equals(earlySlots.getClass(k).getDate()) &&
                        Objects.equals(list.getClass(i).getRoom(), earlySlots.getClass(k).getRoom())) {

                        unable_to_move.addClass(list.getClass(i));
                    }
                }
            return unable_to_move;
        } else {
            int mid = low + (high - low) / 2;
            EarlyLectureSearch left  = new EarlyLectureSearch(earlySlots, list, mid, low);
            EarlyLectureSearch right = new EarlyLectureSearch(earlySlots, list, mid, low);
            left.fork();
            Classes rightAns = right.compute();
            Classes leftAns = left.join();
            leftAns.addFromOtherClass(rightAns.getClasses());
            return leftAns;
        }
    }

    static Classes Search(Classes earlySlots, Classes list) {
        return ForkJoinPool.commonPool().invoke(new EarlyLectureSearch(earlySlots, list, list.getSize(), 0));
    }
}