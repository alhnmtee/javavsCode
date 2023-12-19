import java.util.concurrent.Semaphore;

class Restaurant1 {
    private static final int NUM_TABLES = 6;
    private static final int NUM_CUSTOMERS = 6;
    private static final int NUM_PRIORITY_CUSTOMERS = 2;
    private static final int NUM_GARSONS = 2;

    private static final int NUM_Asci = 2;

    private Semaphore tablesSemaphore;
    private Semaphore priorityCustomersSemaphore;
    private Semaphore orderSemaphore;
    private Semaphore siparisSemaphore;
    private Garson[] garsons;

    private Asci[] asci;

    public Restaurant1() {
        tablesSemaphore = new Semaphore(NUM_TABLES);
        priorityCustomersSemaphore = new Semaphore(NUM_PRIORITY_CUSTOMERS);
        orderSemaphore = new Semaphore(1);
        siparisSemaphore= new Semaphore(2);
        garsons = new Garson[NUM_GARSONS]; // Create an array to store garsons
        for (int i = 0; i < NUM_GARSONS; i++) {
            garsons[i] = new Garson(i);
        }
        asci = new Asci[NUM_Asci]; // Create an array to store garsons
        for (int i = 0; i < NUM_Asci; i++) {
            asci[i] = new Asci(i);
        }
    }

    public void start() {
        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            Thread customerThread;
            if (i < NUM_PRIORITY_CUSTOMERS) {
                customerThread = new Thread(new PriorityCustomer(i, garsons[i % NUM_GARSONS]));
            } else {
                customerThread = new Thread(new Customer(i, garsons[i % NUM_GARSONS]));
            }
            customerThread.start();
        }
    }
    class siparis implements Runnable {
        private int customerId;

        private Restaurant1.Garson garson;

        private Asci asci;

        public siparis(int customerId, Restaurant1.Garson garson, Asci asci) {
            this.customerId = customerId;
            this.garson = garson;
            this.asci = asci;
        }

        @Override
        public void run() {
            try {
                siparisSemaphore.acquire();
                System.out.println("yemek siparisini yapan asci " + asci.getAsciId() + " bu kisiye yapıor" + customerId);
                siparisSemaphore.release();

                Thread.sleep(3000);

                siparisSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    class Customer implements Runnable {
        private int customerId;
        private Garson garson;

        public Customer(int customerId, Garson garson) {
            this.customerId = customerId;
            this.garson = garson;
        }

        @Override
        public void run() {
            try {
                tablesSemaphore.acquire();
                System.out.println("Customer " + customerId + " is waiting for a table.");
                tablesSemaphore.acquire();
                System.out.println("Customer " + customerId + " is sitting at a table.");

                orderSemaphore.acquire();
                System.out.println("Garson " + garson.getGarsonId() + " is taking an order from Customer " + customerId);
                orderSemaphore.release();

                Thread.sleep(2000);

                tablesSemaphore.release();
                System.out.println("Customer " + customerId + " left the table.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class PriorityCustomer extends Customer {
        public PriorityCustomer(int customerId, Garson garson) {
            super(customerId, garson);
        }

        @Override
        public void run() {
            try {
                priorityCustomersSemaphore.acquire();
                System.out.println("Customer " + super.customerId + " (Priority) is sitting at a table.");

                orderSemaphore.acquire();
                System.out.println("Garson " + super.garson.getGarsonId() + " is taking an order from Customer " + super.customerId + " (Priority)");
                orderSemaphore.release();

                Thread.sleep(2000);

                priorityCustomersSemaphore.release();
                System.out.println("Customer " + super.customerId + " (Priority) left the table.");
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

    public static void main(String[] args) {
        Restaurant1 restaurant = new Restaurant1();
        restaurant.start();
    }
}
