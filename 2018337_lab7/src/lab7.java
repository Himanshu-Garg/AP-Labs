import java.util.*;
import java.lang.*;

public class lab7 {

    interface Subject {
        public void user_interface();
    }

    interface Observer {
        public void update_result(fibonacci f, long time);
    }

    public static class fibonacci {
        private static Map<Integer, fibonacci> instances = new HashMap<Integer, fibonacci>();
        private final int n; // immutable
        private final int result; // immutable

        private fibonacci(int n) {
            this.n = n;

            if(n<2) {
                this.result = n;
            }
            else {
                this.result = fibonacci.getInstance(n-1).result
                        + fibonacci.getInstance(n-2).result;
            }
        }

        public static fibonacci getInstance(int n)
        {
            if (!instances.containsKey(n)) {
                instances.put(n, new fibonacci(n));
            }
            return instances.get(n);
        }

    }

    public static class consumer implements Runnable, Observer {
        private boolean terminate;
        private task_pool tp;
        private results_pool r;

        consumer(task_pool p, results_pool r) {
            terminate = false;
            this.tp = p;
            this.r = r;
        }

        public void setTerminate(boolean terminate) {
            this.terminate = terminate;
        }

        @Override
        public void run() {

            while(!terminate) {
                int n = tp.remove();
                if(n==-1) {
                    try {Thread.sleep((int)Math.random()*1000);}
                    catch (InterruptedException e) {}
                }
                else {
                    // here fibonacci of that no will be found

                    // System.nanoTime() gives the current time in the system...
                    long start_time = System.nanoTime();
                    fibonacci f = fibonacci.getInstance(n);
                    long end_time = System.nanoTime();
                    update_result(f, end_time - start_time);
                }
            }
        }

        @Override
        public void update_result(fibonacci f, long time) {
            r.add(f, time);
        }
    }

    public static class results_pool {
        private volatile Queue<fibonacci> result;
        private volatile Queue<Long> time;

        results_pool() {
            result = new LinkedList<>();
            time = new LinkedList<>();
        }

        public synchronized void add(fibonacci f, long t) {
            result.add(f);
            time.add(t);
        }

        public synchronized void display() {
            Iterator i1 = result.iterator();
            Iterator i2 = time.iterator();

            while (i1.hasNext() && i2.hasNext()) {
                fibonacci f = (fibonacci) i1.next();
                long t = (long) i2.next();
                System.out.println("fib(" + f.n + ") = " + f.result
                        + " (Time = " + t + " )");
            }
        }

    }

    public static class task_pool {
        private volatile Queue<Integer> q;

        task_pool() {
            q = new LinkedList<>();
        }

        public synchronized int getSize() {
            return q.size();
        }

        public synchronized void add(int n) {
            q.add(n);
        }

        public synchronized int remove() {
            if(q.size()==0) {return -1;}
            else {return q.remove();}
        }


    }



    public static class producer implements Subject {
        private final int no_of_consumer_threads;
        private task_pool tp;
        private results_pool r;

        private List<consumer> consumers = new ArrayList<>();
        private List<Thread> consumers_threads = new ArrayList<>();

        producer(int n) {
            no_of_consumer_threads = n;
            tp = new task_pool();
            r = new results_pool();

            for (int i=0;i<n;i++) {
                consumer c = new consumer(tp,r);
                consumers.add(c);
                consumers_threads.add(new Thread(c));
            }

            start_all_threads();
            user_interface();
        }

        private void start_all_threads() {
            for (Thread t: consumers_threads) {
                t.start();
            }
        }

        public int getNo_of_consumer_threads() { return no_of_consumer_threads; }

        public void user_interface() {
            System.out.println("Rules ------>");
            System.out.println(" -1 = display computer results");
            System.out.println(" -2 = exit after displaying computed results");
            System.out.println(" 1 to 45 = compute its fibonacci value");
            System.out.println(" ... Starting ...");

            while(true) {
                Scanner scan = new Scanner(System.in);
                System.out.print("Enter input (according to rules): ");
                int input = scan.nextInt();

                if(input == -1) {
                    r.display();
                }
                else if (input == -2) {
                    exit();
                }
                else if (input>=1 && input<=45) {
                    tp.add(input);
                    //notifyAll();
                }
                else {System.out.println("Invalid input...");}

            }
        }

        private void exit() {
            while(true) {
                if(tp.getSize()!=0) {
                    try {Thread.sleep(2000);}
                    catch (InterruptedException e) {System.out.println("Stop interrupting");}
                }
                else {
                    for(consumer c: consumers) { c.setTerminate(true); }
                    for(Thread t: consumers_threads) {
                        try { t.join(); }
                        catch (InterruptedException e) {System.out.println("Stop interrupting");}
                    }

                    r.display();
                    System.exit(0);
                }
            }

        }



    }






    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter no. of Consumer threads to generate: ");
        int no_of_consumer_threads = scan.nextInt();

        producer p = new producer(no_of_consumer_threads);

        //System.out.println(fibonacci.getInstance(4).result);
        //System.out.println(fibonacci.getInstance(5).result);
        //System.out.println(fibonacci.getInstance(6).result);
        //System.out.println(fibonacci.getInstance(7).result);
        //System.out.println(fibonacci.getInstance(8).result);




    }
}
