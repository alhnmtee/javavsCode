import java.util.Random;
import java.util.concurrent.Semaphore;

class Restaurant1 {
    private static final int NUM_TABLES = 6;
    private static final int NUM_CUSTOMERS = 6;
    private static final int NUM_PRIORITY_CUSTOMERS = 2;
    private static final int NUM_GARSONS = 2;
    private static final int NUM_Kasa=1;

    private static final int NUM_Asci = 2;

    private Semaphore tablesSemaphore;
    private Semaphore priorityCustomersSemaphore;
    private Semaphore orderSemaphore;
    private Semaphore siparisSemaphore;
    private Semaphore odemeSemaphore;
    private Garson[] garsons;
    private tables[] tables;
    public boolean finish;
    public boolean[] booleanArray;
    private Asci[] asci;

    public Restaurant1() {
        tablesSemaphore = new Semaphore(NUM_TABLES);
        priorityCustomersSemaphore = new Semaphore(NUM_PRIORITY_CUSTOMERS);
        orderSemaphore = new Semaphore(1);
        siparisSemaphore= new Semaphore(2);
        garsons = new Garson[NUM_GARSONS]; // Create an array to store garsons
        booleanArray=new boolean[NUM_TABLES];
        for (int i = 0; i < NUM_GARSONS; i++) {
            garsons[i] = new Garson(i);
        }
        asci = new Asci[NUM_Asci]; // Create an array to store garsons
        for (int i = 0; i < NUM_Asci; i++) {
            asci[i] = new Asci(i);
        }
    }

    public void start() {
        Semaphore orderSemaphore = new Semaphore(2);
        Semaphore odemeSemaphore = new Semaphore(1);
        Random random = new Random();
        boolean hasPriorityCustomers = false; // Flag to check if there are priority customers
        finish=false;
        booleanArray[0]=true;
        booleanArray[1]=true;
        booleanArray[2]=true;
        booleanArray[3]=true;
        booleanArray[4]=true;
        booleanArray[5]=true;

            for (int tablesId = 0; tablesId < NUM_TABLES; tablesId++) {
                for (int i = 0; i < NUM_CUSTOMERS; i++) {
                if(tablesId==0 && booleanArray[tablesId]==true)
                {
                    Thread customerThread;
                    Thread siparisThread;
                    Thread odemeThread;
                    int age = random.nextInt(61) + 20;
                    if (age > 65) {

                        customerThread = new Thread(new PriorityCustomer(i, garsons[i % NUM_GARSONS], age, orderSemaphore,tablesId,booleanArray));
                        siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                        hasPriorityCustomers = true; // Set the flag if there is a priority customer
                        odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                    } else {
                        customerThread = new Thread(new Customer(i, garsons[i % NUM_GARSONS], age, orderSemaphore));
                        siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                        odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                    }
                    customerThread.start();
                    siparisThread.start();
                    odemeThread.start();
                    booleanArray[tablesId]=false;
                }
                    if(tablesId==1 && booleanArray[tablesId]==true)
                    {
                        Thread customerThread;
                        Thread siparisThread;
                        Thread odemeThread;
                        int age = random.nextInt(61) + 20;
                        if (age > 65) {

                            customerThread = new Thread(new PriorityCustomer(i, garsons[i % NUM_GARSONS], age, orderSemaphore,tablesId,booleanArray));
                            siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                            hasPriorityCustomers = true; // Set the flag if there is a priority customer
                            odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                        } else {
                            customerThread = new Thread(new Customer(i, garsons[i % NUM_GARSONS], age, orderSemaphore));
                            siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                            odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                        }
                        customerThread.start();
                        siparisThread.start();
                        odemeThread.start();
                        booleanArray[tablesId]=false;
                    }
                    if(tablesId==2 && booleanArray[tablesId]==true)
                    {
                        Thread customerThread;
                        Thread siparisThread;
                        Thread odemeThread;
                        int age = random.nextInt(61) + 20;
                        if (age > 65) {

                            customerThread = new Thread(new PriorityCustomer(i, garsons[i % NUM_GARSONS], age, orderSemaphore,tablesId,booleanArray));
                            siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                            hasPriorityCustomers = true; // Set the flag if there is a priority customer
                            odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                        } else {
                            customerThread = new Thread(new Customer(i, garsons[i % NUM_GARSONS], age, orderSemaphore));
                            siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                            odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                        }
                        customerThread.start();
                        siparisThread.start();
                        odemeThread.start();
                        booleanArray[tablesId]=false;
                    }
                    if(tablesId==3 && booleanArray[tablesId]==true)
                    {
                        Thread customerThread;
                        Thread siparisThread;
                        Thread odemeThread;
                        int age = random.nextInt(61) + 20;
                        if (age > 65) {

                            customerThread = new Thread(new PriorityCustomer(i, garsons[i % NUM_GARSONS], age, orderSemaphore,tablesId,booleanArray));
                            siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                            hasPriorityCustomers = true; // Set the flag if there is a priority customer
                            odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                        } else {
                            customerThread = new Thread(new Customer(i, garsons[i % NUM_GARSONS], age, orderSemaphore));
                            siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                            odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                        }
                        customerThread.start();
                        siparisThread.start();
                        odemeThread.start();
                        booleanArray[tablesId]=false;
                    }
                    if(tablesId==4 && booleanArray[tablesId]==true)
                    {
                        Thread customerThread;
                        Thread siparisThread;
                        Thread odemeThread;
                        int age = random.nextInt(61) + 20;
                        if (age > 65) {

                            customerThread = new Thread(new PriorityCustomer(i, garsons[i % NUM_GARSONS], age, orderSemaphore,tablesId,booleanArray));
                            siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                            hasPriorityCustomers = true; // Set the flag if there is a priority customer
                            odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                        } else {
                            customerThread = new Thread(new Customer(i, garsons[i % NUM_GARSONS], age, orderSemaphore));
                            siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                            odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                        }
                        customerThread.start();
                        siparisThread.start();
                        odemeThread.start();
                        booleanArray[tablesId]=false;
                    }
                    if(tablesId==5 && booleanArray[tablesId]==true)
                    {
                        Thread customerThread;
                        Thread siparisThread;
                        Thread odemeThread;
                        int age = random.nextInt(61) + 20;
                        if (age > 65) {

                            customerThread = new Thread(new PriorityCustomer(i, garsons[i % NUM_GARSONS], age, orderSemaphore,tablesId,booleanArray[tablesId]));
                            siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                            hasPriorityCustomers = true; // Set the flag if there is a priority customer
                            odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                        } else {
                            customerThread = new Thread(new Customer(i, garsons[i % NUM_GARSONS], age, orderSemaphore,tablesId,booleanArray[tablesId]));
                            siparisThread = new Thread(new siparis(i, garsons[i % NUM_GARSONS]));
                            odemeThread = new Thread(new odeme(i, NUM_Kasa, odemeSemaphore, finish));
                        }
                        customerThread.start();
                        siparisThread.start();
                        odemeThread.start();
                        booleanArray[tablesId]=false;
                    }
            }
        }
    }
class odeme implements Runnable{
        private int tablesId;
        private int kasaId;
    public Semaphore odemeSemaphore;
    public boolean finish;

    public odeme(int tablesId, int kasaId, Semaphore odemeSemaphore, boolean finish) {
        this.tablesId = tablesId;
        this.kasaId = kasaId;
        this.odemeSemaphore=odemeSemaphore;
        this.finish = finish;
    }

    @Override
    public void run() {
        try {

            if (finish) {
                tablesSemaphore.acquire();
                System.out.println("masanın odemesi " + tablesId +"yapılıyor");
                Thread.sleep(500);
                orderSemaphore.release();
                finish = false;

            }
            Thread.sleep(2000);
            tablesSemaphore.release();
            System.out.println("masanın odemesi " + tablesId +"yapıldı.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    class siparis implements Runnable {
        private int customerId;

        private int garsonId;
        private Garson garson;
        private Asci asci;

        public siparis(int customerId,Garson garson) {
            this.customerId = customerId;
            this.garson = garson;
        }

        @Override
        public void run() {
            try {
                siparisSemaphore.acquire();
                System.out.println("yemek siparisini yapan asci " + " bu kisiye yapıor" + customerId);


                Thread.sleep(3000);
                siparisSemaphore.release();
                //orderSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    class Customer implements Runnable {
        private int customerId;
        private int tablesId;
        private Garson garson;
        private int age;
        public Semaphore orderSemaphore;
        public boolean orderTaken;
        public boolean[] booleanArray;
        public Customer(int customerId, Garson garson, int age, Semaphore orderSemaphore,int tablesId,boolean[] booleanArray[tablesId]) {
            this.customerId = customerId;
            this.garson = garson;
            this.age = age;
            this.orderSemaphore = orderSemaphore;
            this.orderTaken =false;
            this.tablesId=tablesId;
            this.booleanArray[tablesId]=booleanArray[tablesId];
        }

        @Override
        public void run() {
            try {
                tablesSemaphore.acquire();
                System.out.println("Customer " + customerId + " (Age: " + age + ") is waiting for a table.");
                Thread.sleep(500);
                tablesSemaphore.acquire();
                System.out.println("Customer " + customerId + " (Age: " + age + ") is sitting at a table.==="+tablesId);

                if (!orderTaken) {
                    orderSemaphore.acquire();
                    System.out.println("Garson " + garson.getGarsonId() + " is taking an order from Customer " + customerId + " (Age: " + age + ")");
                    Thread.sleep(2000);

                    System.out.println("Garson " + garson.getGarsonId() + " sipariş aldı from Customer " + customerId + " (Age: " + age + ")");
                    orderSemaphore.release();
                    orderTaken = true;
                    finish=true;
                }

                Thread.sleep(2000);

                tablesSemaphore.release();
                System.out.println("Customer " + customerId + " (Age: " + age + ") left the table.==="+tablesId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class PriorityCustomer extends Customer {
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
                        finish=true;
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

    public static void main(String[] args) {
        Restaurant1 restaurant = new Restaurant1();
        restaurant.start();
    }
}
