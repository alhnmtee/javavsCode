import java.util.concurrent.Semaphore;

public class Restoran extends Thread {

    private static final int masaSayisi = 6;
    private static final int müsteriSayisi = 8; 
    private static final int öncelikliMusteri = 2;
    private static final int garsonSayisi = 3;
    private static final int asciSayisi = 2;

    private static Semaphore tablesSemaphore;
    private static Semaphore priorityCustomersSemaphore;
    private static Semaphore ovenSemaphore;

    private Garson[] garsons;
    private Chef[] chefs;
    private Masa[] masalar;

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

        masalar = new Masa[masaSayisi];
        for (int i = 0; i < masaSayisi; i++) {
            masalar[i] = new Masa(i + 1);
        }
    }

    @Override
    public void run() {
        Semaphore orderSemaphore = new Semaphore(3);


        Customer[] customers = new Customer[müsteriSayisi];

        // Öncelikli müşterileri oluştur
        for (int i = 0; i < öncelikliMusteri; i++) {
            customers[i] = new PriorityCustomer(i, garsons[i % garsonSayisi], 65, orderSemaphore, chefs[i % asciSayisi], masalar[i]);
            Thread customerThread = new Thread(customers[i]);
            customerThread.start();
        }

        // Geri kalan müşterileri oluştur ve sırayla başlat
        for (int i = öncelikliMusteri; i < müsteriSayisi; i++) {
            customers[i] = new Customer(i, garsons[i % garsonSayisi], 30, orderSemaphore, chefs[i % asciSayisi], masalar[i % masaSayisi]);
            Thread customerThread = new Thread(customers[i]);
            customerThread.start();
        }
    }

    static class Customer implements Runnable {
        private int customerId;
        private Garson garson;
        private int age;
        public Semaphore orderSemaphore;
        public boolean orderTaken;
        private Chef chef;
        private Masa masa;

        public Customer(int customerId, Garson garson, int age, Semaphore orderSemaphore, Chef chef, Masa masa) {
            this.customerId = customerId;
            this.garson = garson;
            this.age = age;
            this.orderSemaphore = orderSemaphore;
            this.orderTaken = false;
            this.chef = chef;
            this.masa = masa;
        }

        @Override
        public void run() {
            try {
                // Her müşteri masa için sıraya girer
                tablesSemaphore.acquire();

                // Kontrol eklenerek masa sayısını aşan müşterilerin oturmasını engelle
                if (masa.dolumuKontrol()) {
                    System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturamadı. Masa dolu.");
                    tablesSemaphore.release();
                    return;
                }

                System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masa için sırada bekliyor.");
                Thread.sleep(1000);

                // Yeni kontrol: Eğer masa boşsa otur, aksi halde sırayı bekle
                while (masa.dolumuKontrol()) {
                    System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturamadı. Masa dolu. Beklemede...");
                    Thread.sleep(7000);
                }

                System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturdu. Masası: " + masa.getMasaNo());
                masa.setdolumu(true);

                if (!orderTaken) {
                    orderSemaphore.acquire();
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş aldı from Müşteri " + customerId + " (Yaş: " + age + ")");
                    Thread.sleep(2000);
                    orderSemaphore.release();
                    orderTaken = true;
                }

                Thread.sleep(5000);
                if (orderTaken) {
                    ovenSemaphore.acquire();
                    System.out.println("Aşçı " + chef.chefId + " Yemek hazırlanıyor.");
                    Thread.sleep(3000);
                    System.out.println("Aşçı " + chef.chefId + " Yemek hazırlandı.");
                    ovenSemaphore.release();
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş getirdi to Müşteri " + customerId + " (Yaş: " + age + ")");
                } else {
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş getiremedi to Müşteri " + customerId + " (Yaş: " + age + ")");
                }

                ovenSemaphore.acquire();
                System.out.println("Kasa: Müşteri ödemesi alınıyor. Müşteri: " + customerId + " (Yaş: " + age + ")  ödeme alındı.");
                Thread.sleep(2000);
                ovenSemaphore.release();

                Thread.sleep(1000);

                // Müşteri masadan kalktığında masa boşalır
                masa.setdolumu(false);
                tablesSemaphore.release();
                System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masadan kalktı.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class PriorityCustomer extends Customer {
        public PriorityCustomer(int customerId, Garson garson, int age, Semaphore orderSemaphore, Chef chef, Masa masa) {
            super(customerId, garson, age, orderSemaphore, chef, masa);
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    if (priorityCustomersSemaphore.tryAcquire()) {
                        // Öncelikli müşteri oturduğunda masa dolu kontrolü
                        if (super.masa.dolumuKontrol()) {
                            System.out.println("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") masaya oturamadı. Masa dolu.");
                            priorityCustomersSemaphore.release();
                            return;
                        }

                        System.out.println("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) masaya oturdu. Masası: " + super.masa.getMasaNo());
                        super.masa.setdolumu(true); // Öncelikli müşteri oturduğunda masa dolu
                        Thread.sleep(1000);

                        if (!orderTaken) {
                            orderSemaphore.acquire();
                            System.out.println("Garson " + super.garson.getGarsonId() + " sipariş aldı from Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri)");
                            Thread.sleep(2000);
                            orderSemaphore.release();
                            orderTaken = true;
                        }

                        if (orderTaken) {
                            ovenSemaphore.acquire();
                            System.out.println("Aşçı " + super.chef.chefId + " Yemek hazırlanıyor.");
                            Thread.sleep(3000);
                            System.out.println("Aşçı " + super.chef.chefId + " Yemek hazırlandı.");
                            ovenSemaphore.release();
                            System.out.println("Garson " + super.garson.getGarsonId() + " sipariş getirdi to Müşteri " + super.customerId + " (Yaş: " + super.age + ")");
                        } else {
                            System.out.println("Garson " + super.garson.getGarsonId() + " sipariş getirmedi to Müşteri " + super.customerId + " (Yaş: " + super.age + ")");
                        }

                        ovenSemaphore.acquire();
                        System.out.println("Kasa: Müşteri ödemesi alınıyor. Müşteri: " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) ödeme alındı.");
                        Thread.sleep(2000);
                        ovenSemaphore.release();

                        Thread.sleep(1000);

                        Thread.sleep(5000);
                        super.masa.setdolumu(false); // Öncelikli müşteri masadan kalktığında masa boşalır
                        priorityCustomersSemaphore.release();
                        System.out.println("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) masadan kalktı.");
                        break;
                    }
                    Thread.sleep(1000);
                }
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

    static class Masa {
        private int masaNo;
        private boolean dolumu;

        public Masa(int masaNo) {
            this.masaNo = masaNo;
            this.dolumu = false;
        }

        public int getMasaNo() {
            return masaNo;
        }

        public boolean dolumuKontrol() {
            return dolumu;
        }

        public void setdolumu(boolean occupied) {
            dolumu = occupied;
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