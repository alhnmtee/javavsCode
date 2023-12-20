import java.util.concurrent.Semaphore;
import java.util.Random;

class Restaurant1 extends Thread {
    private static final int NUM_TABLES = 6;
    private static final int NUM_CUSTOMERS = 6;
    private static final int NUM_PRIORITY_CUSTOMERS = 2;
    private static final int NUM_GARSONS = 2;

    private Semaphore tablesSemaphore;
    private Semaphore priorityCustomersSemaphore;

    private Garson[] garsons;

    public Restaurant1() {

        tablesSemaphore = new Semaphore(NUM_TABLES);
        priorityCustomersSemaphore = new Semaphore(NUM_PRIORITY_CUSTOMERS);

        garsons = new Garson[NUM_GARSONS];
        for (int i = 0; i < NUM_GARSONS; i++) {
            garsons[i] = new Garson(i);
        }
    }

    public void start() {
    
     Semaphore orderSemaphore = new Semaphore(2);
    Random random = new Random();
    boolean hasPriorityCustomers = false; // Flag to check if there are priority customers

    for (int i = 0; i < NUM_CUSTOMERS; i++) {
        Thread customerThread;
        int age = random.nextInt(61) + 20;
        if (age > 65) {
            customerThread = new Thread(new PriorityCustomer(i, garsons[i % NUM_GARSONS], age, orderSemaphore));
            hasPriorityCustomers = true; // Set the flag if there is a priority customer
        } else {
            customerThread = new Thread(new customer(i, garsons[i % NUM_GARSONS], age, orderSemaphore));
        }
        customerThread.start();
    }

    // If there are no priority customers, start the normal customers
    if (!hasPriorityCustomers) {
        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            Thread customerThread;
            int age = random.nextInt(61) + 20;
            customerThread = new Thread(new customer(i, garsons[i % NUM_GARSONS], age, orderSemaphore));
            customerThread.start();
    }
}
    }

    class customer implements Runnable {
        private int customerId;
        private Garson garson;
        private int age;
        public Semaphore orderSemaphore;
        public boolean orderTaken;

        public customer(int customerId, Garson garson, int age, Semaphore orderSemaphore) {
            this.customerId = customerId;
            this.garson = garson;
            this.age = age;
            this.orderSemaphore = orderSemaphore;
            this.orderTaken = false;
        }

        @Override
        public void run() {
            try {
                tablesSemaphore.acquire();
                System.out.println("Customer " + customerId + " (Age: " + age + ") is waiting for a table.");
                Thread.sleep(500);
                tablesSemaphore.acquire();
                System.out.println("Customer " + customerId + " (Age: " + age + ") is sitting at a table.");

                if (!orderTaken) {
                    orderSemaphore.acquire();
                    System.out.println("Garson " + garson.getGarsonId() + " is taking an order from Customer " + customerId + " (Age: " + age + ")");
                    Thread.sleep(2000);

                    System.out.println("Garson " + garson.getGarsonId() + " sipariş aldı from Customer " + customerId + " (Age: " + age + ")");
                    orderSemaphore.release();
                    orderTaken = true;
                }

                Thread.sleep(2000);

                tablesSemaphore.release();
                System.out.println("Customer " + customerId + " (Age: " + age + ") left the table.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class PriorityCustomer extends customer {
        public PriorityCustomer(int customerId, Garson garson, int age, Semaphore orderSemaphore) {
            super(customerId, garson, age, orderSemaphore);
        }

        @Override
        public void run() {
            try {
                if (priorityCustomersSemaphore.tryAcquire()) {
                    System.out.println("Customer " + super.customerId + " (Age: " + super.age + ") (Priority) is sitting at a table.");

                    if (!orderTaken) {
                        orderSemaphore.acquire();
                        System.out.println("Garson " + super.garson.getGarsonId() + " is taking an order from Customer " + super.customerId + " (Age: " + super.age + ") (Priority)");
                        Thread.sleep(2000);

                        System.out.println("Garson " + super.garson.getGarsonId() + " sipariş aldı from Customer " + super.customerId + " (Age: " + super.age + ") (Priority)");
                        orderSemaphore.release();
                        orderTaken = true;
                    }

                    Thread.sleep(2000);

                    priorityCustomersSemaphore.release();
                    System.out.println("Customer " + super.customerId + " (Age: " + super.age + ") (Priority) left the table.");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Garson {
        private int garsonId;

        public Garson(int garsonId) {
            this.garsonId = garsonId;
        }

        public int getGarsonId() {
            return garsonId;
        }
    }
}
