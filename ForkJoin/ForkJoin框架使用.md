> 所用较少，就不再分析太多了

## ForkJoin框架使用

```java
public class LongSum extends RecursiveTask<Long> {

    static final int SEQUENTIAL_THRESHOLD = 1000;


    int low;
    int high;
    int[] array;

    LongSum(int[] arr, int lo, int hi) {
        array = arr;
        low = lo;
        high = hi;
    }

    /**
     * fork()方法：将任务放入队列并安排异步执行，一个任务应该只调用一次fork()函数，除非已经执行完毕并重新初始化。
     * tryUnfork()方法：尝试把任务从队列中拿出单独处理，但不一定成功。
     * join()方法：等待计算完成并返回计算结果。
     * isCompletedAbnormally()方法：用于判断任务计算是否发生异常。
     */
    protected Long compute() {
        if (high - low <= SEQUENTIAL_THRESHOLD) {
            long sum = 0;
            for (int i = low; i < high; ++i) {
                sum += array[i];
            }
            return sum;

        } else {
            int mid = low + (high - low) / 2;
            LongSum left = new LongSum(array, low, mid);
            LongSum right = new LongSum(array, mid, high);
            left.fork();
            right.fork();
            long rightAns = right.join();
            long leftAns = left.join();
            return leftAns + rightAns;
        }
    }
}
```

```java
public class LongSumMain {
	//获取逻辑处理器数量
	static final int NCPU = Runtime.getRuntime().availableProcessors();

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		int[] array = new int[20000000];
		Random random = new Random();
		for (int i = 0; i < 20000000; i++) {
			array[i] = random.nextInt(100);
		}
		//采用fork/join方式将数组求和任务进行拆分执行，最后合并结果
		LongSum longSum = new LongSum(array, 0, array.length);
  		ForkJoinPool forkJoinPool  = new ForkJoinPool(NCPU); //使用的线程数
		ForkJoinTask<Long> task = forkJoinPool.submit(longSum);
		System.out.println("forkjoin sum=" + task.get());

		if(task.isCompletedAbnormally()){
			System.out.println(task.getException().toString());
		}
		forkJoinPool.shutdown();
	}
}
```

