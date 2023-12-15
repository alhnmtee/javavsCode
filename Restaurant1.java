import java.util.concurrent.Semaphore;

class Restaurant1 {
    private static final int NUM_TABLES = 6;
    private static final int NUM_CUSTOMERS = 6;
    private static final int NUM_PRIORITY_CUSTOMERS = 2;

    private Semaphore tablesSemaphore;
    private Semaphore priorityCustomersSemaphore;

    public Restaurant1() {
        tablesSemaphore = new Semaphore(NUM_TABLES);
        priorityCustomersSemaphore = new Semaphore(NUM_PRIORITY_CUSTOMERS);
    }

    public void start() {
        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            Thread customerThread;
            if (i < NUM_PRIORITY_CUSTOMERS) {
                customerThread = new Thread(new PriorityCustomer(i));
            } else {
                customerThread = new Thread(new Customer(i));
            }
            customerThread.start();
        }
    }

    class Customer implements Runnable {
        private int customerId;

        public Customer(int customerId) {
            this.customerId = customerId;
        }

        @Override
        public void run() {
            try {
                tablesSemaphore.acquire();
                System.out.println("Customer " + customerId + " is waiting for a table.");
                tablesSemaphore.acquire(); // Additional acquire for non-priority customers
                System.out.println("Customer " + customerId + " is sitting at a table.");

                Thread.sleep(2000); // Customer sits at the table for 2 seconds

                tablesSemaphore.release();
                System.out.println("Customer " + customerId + " left the table.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class PriorityCustomer extends Customer {
        public PriorityCustomer(int customerId) {
            super(customerId);
        }

        @Override
        public void run() {
            try {
                priorityCustomersSemaphore.acquire();
                System.out.println("Customer " + super.customerId + " (Priority) is sitting at a table.");

                Thread.sleep(2000); // Customer sits at the table for 2 seconds

                priorityCustomersSemaphore.release();
                System.out.println("Customer " + super.customerId + " (Priority) left the table.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
