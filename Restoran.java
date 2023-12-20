import java.util.Random;
import java.util.concurrent.Semaphore;


public class Restoran extends Thread {

    private static final int masaSayisi = 6;
    private static final int müsteriSayisi = 6;
    private static final int öncelikliMusteri = 2;
    private static final int garsonSayisi = 3;
    private static final int asciSayisi = 2;

    private static Semaphore tablesSemaphore;
    private static Semaphore priorityCustomersSemaphore;
    private static Semaphore ovenSemaphore;
  

    private Garson[] garsons;
    private Chef[] chefs; 

    public Restoran() {
        tablesSemaphore = new Semaphore(masaSayisi);
        priorityCustomersSemaphore = new Semaphore(öncelikliMusteri);
        ovenSemaphore = new Semaphore(asciSayisi);

        garsons = new Garson[garsonSayisi];
        for (int i = 0; i < garsonSayisi; i++) {
            garsons[i] = new Garson(i);
        }

        chefs = new Chef[asciSayisi];
        for (int i = 0; i < asciSayisi; i++) {
            chefs[i] = new Chef(i);
        }
    }

    @Override
    public void run() {
        Semaphore orderSemaphore = new Semaphore(3);
        Random random = new Random();
        boolean hasPriorityCustomers = false;

        for (int i = 0; i < müsteriSayisi; i++) {
            Runnable customerRunnable;
            int age = random.nextInt(61) + 20;
            if (age > 65) {
                customerRunnable = new PriorityCustomer(i, garsons[i % garsonSayisi], age, orderSemaphore,chefs[i % asciSayisi]);
                hasPriorityCustomers = true;
            } else {
                customerRunnable = new Customer(i, garsons[i % garsonSayisi], age, orderSemaphore,chefs[i % asciSayisi]);
            }
            Thread customerThread = new Thread(customerRunnable);
            customerThread.start();
        }

        if (!hasPriorityCustomers) {
            for (int i = 0; i < müsteriSayisi; i++) {
                Runnable customerRunnable;
                int age = random.nextInt(61) + 20;
                customerRunnable = new Customer(i, garsons[i % garsonSayisi], age, orderSemaphore,chefs[i % asciSayisi]);
                Thread customerThread = new Thread(customerRunnable);
                customerThread.start();
            }
        }
    }

    static class Customer implements Runnable {
        private int customerId;
        private Garson garson;
        private int age;
        public Semaphore orderSemaphore;
        public boolean orderTaken;
        private Chef chef;

        public Customer(int customerId, Garson garson, int age, Semaphore orderSemaphore,Chef chef) {
            this.customerId = customerId;
            this.garson = garson;
            this.age = age;
            this.orderSemaphore = orderSemaphore;
            this.orderTaken = false;
            this.chef = chef;

        }

        @Override
        public void run() {
            try {
                tablesSemaphore.acquire();
                System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masa için sırada bekliyor.");
                Thread.sleep(500);
                System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturdu.");

                if (!orderTaken) {
                    orderSemaphore.acquire();
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş aldı from Müşteri " + customerId + " (Yaş: " + age + ")");
                    Thread.sleep(2000);
                    orderSemaphore.release();
                    orderTaken = true;
                }

                Thread.sleep(5000);
                if(orderTaken){
                     ovenSemaphore.acquire();
                    System.out.println("Aşçı " + chef.chefId + " Yemek hazırlanıyor.");
                    Thread.sleep(3000);
                    System.out.println("Aşçı " + chef.chefId + " Yemek hazırlandı.");
                    ovenSemaphore.release();
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş getirdi to Müşteri " + customerId + " (Yaş: " + age + ")");
                  
                }
                else{
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş getiremedi to Müşteri " + customerId + " (Yaş: " + age + ")");
                } 

                        ovenSemaphore.acquire();
                        System.out.println("Kasa: Müşteri ödemesi alınıyor. Müşteri: " + customerId + " (Yaş: " + age + ")  ödeme alındı.");
                        Thread.sleep(2000); 
                        ovenSemaphore.release();
                    
                    Thread.sleep(1000);
    
                tablesSemaphore.release();
                System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masadan kalktı.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Chef implements Runnable {
        private int chefId;

        public Chef(int chefId) {
            this.chefId = chefId;
        }

        @Override
        public void run() {

        }
 
        }

    static class PriorityCustomer extends Customer {
        public PriorityCustomer(int customerId, Garson garson, int age, Semaphore orderSemaphore,Chef chef) {
            super(customerId, garson, age, orderSemaphore,chef);
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    if (priorityCustomersSemaphore.tryAcquire()) {
                        System.out.println("Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) masaya oturdu.");

                        if (!orderTaken) {
                            orderSemaphore.acquire();
                            System.out.println("Garson " + super.garson.getGarsonId() + " sipariş aldı from Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri)");
                            Thread.sleep(2000);
                            orderSemaphore.release();
                            orderTaken = true;
                        }
                          if(orderTaken){
                     ovenSemaphore.acquire(); 
                    System.out.println("Aşçı " + super.chef.chefId + " Yemek hazırlanıyor.");
                    Thread.sleep(3000); 
                    System.out.println("Aşçı " + super.chef.chefId + " Yemek hazırlandı.");
                    ovenSemaphore.release();
                    System.out.println("Garson " + super.garson.getGarsonId() + " sipariş getirdi to Müşteri " + super.customerId + " (Yaş: " + super.age + ")");
                  
                }
                else{
                    System.out.println("Garson " + super.garson.getGarsonId() + " sipariş getirmedi to Müşteri " + super.customerId + " (Yaş: " + super.age + ")");
                } 
                        ovenSemaphore.acquire();
                        System.out.println("Kasa: Müşteri ödemesi alınıyor. Müşteri: "+ super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) ödeme alındı.");
                        Thread.sleep(2000);
                
                        ovenSemaphore.release();
                    
                    Thread.sleep(1000);
           
                        Thread.sleep(5000);
                        priorityCustomersSemaphore.release();
                        System.out.println("Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) masadan kalktı.");
                        break;
                    }
                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


 
    

    static class Kasa implements Runnable {
        @Override
        public void run() {
        }
    }


    static class Garson {
        private int garsonId;

        public Garson(int garsonId) {
            this.garsonId = garsonId;
        }

        public int getGarsonId() {
            return garsonId;
        }
    }
}
