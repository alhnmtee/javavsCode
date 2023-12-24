import java.util.concurrent.Semaphore;
import javax.swing.*;
import java.awt.*;

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

     JFrame frame;
     JPanel panel;
     

     JTextArea MasaArea;
     JTextArea GarsonArea;
     JTextArea KasaArea;
     JTextArea AsciArea;

     JButton problem2;
     JButton[] tableButtons;
     JButton[] garsonButtons;
     JButton[] asciButtons;
    
     JLabel masLabel;
     JLabel garsonLabel;
     JLabel kasaLabel;
     JLabel asciLabel;

     
    


    public Restoran() {
        tablesSemaphore = new Semaphore(masaSayisi);
        priorityCustomersSemaphore = new Semaphore(öncelikliMusteri);
        ovenSemaphore = new Semaphore(asciSayisi);

        garsons = new Garson[garsonSayisi];
        for (int i = 0; i < garsonSayisi; i++) {
            garsons[i] = new Garson(i+1);
        }

        chefs = new Chef[asciSayisi];
        for (int i = 0; i < asciSayisi; i++) {
            chefs[i] = new Chef(i+1);
        }

        masalar = new Masa[masaSayisi];
        for (int i = 0; i < masaSayisi; i++) {
            masalar[i] = new Masa(i + 1);
        }

            Font font = new Font("Arial", Font.BOLD, 20);
            Font labelFont=new Font("Arial",Font.BOLD,30);
            Font fontarea = new Font("Arial",Font.ROMAN_BASELINE, 14);
            
            frame = new JFrame();
            panel = new JPanel();

            frame.add(panel);

            frame.setLayout(null);
            panel.setLayout(null);
    
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = (int) screenSize.getWidth();
            int screenHeight = (int) screenSize.getHeight();
            frame.setSize(screenWidth, screenHeight);
            panel.setSize(screenWidth, screenHeight);

        

        masLabel=new JLabel("MASALAR");
        garsonLabel=new JLabel("GARSONLAR");
        kasaLabel=new JLabel("KASA");
        asciLabel=new JLabel("AŞÇILAR");

        masLabel.setBounds(50,50,200,50);
        garsonLabel.setBounds(500,50,200,50);
        kasaLabel.setBounds(950,50,200,50);
        asciLabel.setBounds(1450,50,200,50);

        masLabel.setFont(labelFont);
        garsonLabel.setFont(labelFont);
        kasaLabel.setFont(labelFont);
        asciLabel.setFont(labelFont);

        masLabel.setForeground(Color.BLUE);
        garsonLabel.setForeground(Color.BLUE);
        kasaLabel.setForeground(Color.BLUE);
        asciLabel.setForeground(Color.BLUE);
        

        panel.add(masLabel);
        panel.add(garsonLabel);
        panel.add(kasaLabel);
        panel.add(asciLabel);

            
        MasaArea=new JTextArea();
        panel.add(MasaArea);
        MasaArea.setBounds(50,100,400,385);
        MasaArea.setEditable(false);

        GarsonArea=new JTextArea();
        panel.add(GarsonArea);
        GarsonArea.setBounds(500,100,400,385);
        GarsonArea.setEditable(false);

        KasaArea=new JTextArea();
        panel.add(KasaArea);
        KasaArea.setBounds(950,100,450,385);
        KasaArea.setEditable(false);

        AsciArea=new JTextArea();
        panel.add(AsciArea);
        AsciArea.setBounds(1450,100,400,385);
        AsciArea.setEditable(false);
                
        MasaArea.setFont(fontarea);
        GarsonArea.setFont(fontarea);
        KasaArea.setFont(fontarea);
        AsciArea.setFont(fontarea);



        tableButtons = new JButton[masaSayisi];
        for (int i = 0; i < masaSayisi; i++) {
            tableButtons[i] = new JButton(String.valueOf(i + 1));
            tableButtons[i].setEnabled(false);
            tableButtons[i].setContentAreaFilled(false);
            tableButtons[i].setOpaque(true);
            tableButtons[i].setBackground(null);
            panel.add(tableButtons[i]);
            tableButtons[i].setBounds(50 + (i % 3) * 100, 600 + (i / 3) * 100, 60, 60);
            tableButtons[i].setVisible(true);
            tableButtons[i].setFont(font);
            tableButtons[i].setBackground(Color.green);
            
        }

        garsonButtons=new JButton[garsonSayisi];
        for(int i=0;i<garsonSayisi;i++){
            garsonButtons[i]=new JButton(String.valueOf(i+1));
            garsonButtons[i].setEnabled(false);
            garsonButtons[i].setContentAreaFilled(false);
            garsonButtons[i].setOpaque(true);
            garsonButtons[i].setBackground(null);
            panel.add(garsonButtons[i]);
            garsonButtons[i].setBounds(550+(i%3)*100,600+(i/3)*100,60,60);
            garsonButtons[i].setVisible(true);
            garsonButtons[i].setBackground(Color.green);
        }

        asciButtons=new JButton[asciSayisi];
        for(int i=0;i<asciSayisi;i++){
            asciButtons[i]=new JButton(String.valueOf(i+1));
            asciButtons[i].setEnabled(false);
            asciButtons[i].setContentAreaFilled(false);
            asciButtons[i].setOpaque(true);
            asciButtons[i].setBackground(null);
            panel.add(asciButtons[i]);
            asciButtons[i].setBounds(1550+(i%3)*100,600+(i/3)*100,60,60);
            asciButtons[i].setVisible(true);
            asciButtons[i].setBackground(Color.green);
        }

       JButton kasa= new JButton("Kasa");
        panel.add(kasa);
        kasa.setBounds(560,80,250,130);
        kasa.setVisible(false);
        
        
        problem2= new JButton("PROBLEM 2");
        panel.add(problem2);
        problem2.setBounds(1600,90,80,80);
        problem2.setVisible(false);
        problem2.addActionListener(actionEvent -> {
            //Problem 2 için geçiş olacak şimdilik boş 
        });
        
        frame.setVisible(true);
        panel.setVisible(true);
        MasaArea.setVisible(true);
        GarsonArea.setVisible(true);
        KasaArea.setVisible(true);
        AsciArea.setVisible(true);


    }

    @Override
    public void run() {
        Semaphore orderSemaphore = new Semaphore(3);

        
        Customer[] customers = new Customer[müsteriSayisi];

       
        for (int i = 0; i < öncelikliMusteri; i++) {
            customers[i] = new PriorityCustomer(i, garsons[i % garsonSayisi], 65, orderSemaphore, chefs[i % asciSayisi], masalar[i]);
            Thread customerThread = new Thread(customers[i]);
            customerThread.start();
        }

       
        for (int i = öncelikliMusteri; i < müsteriSayisi; i++) {
            customers[i] = new Customer(i, garsons[i % garsonSayisi], 30, orderSemaphore, chefs[i % asciSayisi], masalar[i % masaSayisi]);
            Thread customerThread = new Thread(customers[i]);
            customerThread.start();
        }
    }

     class Customer implements Runnable {
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
            
                tablesSemaphore.acquire();

               
                if (masa.dolumuKontrol()) {
                    System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturamadı. Masa dolu.");
                    tablesSemaphore.release();
                    MasaArea.append("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturamadı. Masa dolu." + "\n");
                    return;
                }

                System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masa için sırada bekliyor.");
                Thread.sleep(1000);
                MasaArea.append("Müşteri " + customerId + " (Yaş: " + age + ") masa için sırada bekliyor." + "\n");

                // Yeni kontrol: Eğer masa boşsa otur, aksi halde sırayı bekle
                while (masa.dolumuKontrol()) {
                    System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturamadı. Masa dolu. Beklemede...");
                    Thread.sleep(7000);
                }

                System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturdu. Masası: " + masa.getMasaNo());
                masa.setdolumu(true);
                MasaArea.append("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturdu. Masası: " + masa.getMasaNo() + "\n");
                tableButtons[masa.getMasaNo() - 1].setBackground(Color.RED);
                Thread.sleep(1000);

                if (!orderTaken) {
                    orderSemaphore.acquire();
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş aldı from Müşteri " + customerId + " (Yaş: " + age + ")");
                    Thread.sleep(2000);
                    orderSemaphore.release();
                    orderTaken = true;
                    GarsonArea.append("Garson " + garson.getGarsonId() + " sipariş aldı from Müşteri " + customerId + " (Yaş: " + age + ")" + "\n");
                    garsonButtons[garson.getGarsonId()-1].setBackground(Color.RED);
                        }

                Thread.sleep(5000);
                if (orderTaken) {
                    ovenSemaphore.acquire();
                    System.out.println("Aşçı " + chef.chefId + " Yemek hazırlanıyor.");
                    Thread.sleep(3000);
                    AsciArea.append("Aşçı " + chef.chefId + " Yemek hazırlanıyor." + "\n");
                    asciButtons[chef.chefId-1].setBackground(Color.RED);

                    System.out.println("Aşçı " + chef.chefId + " Yemek hazırlandı.");
                    ovenSemaphore.release();
                    AsciArea.append("Aşçı " + chef.chefId + " Yemek hazırlandı." + "\n");
                    asciButtons[chef.chefId-1].setBackground(Color.GREEN);
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş getirdi to Müşteri " + customerId + " (Yaş: " + age + ")");
                    GarsonArea.append("Garson " + garson.getGarsonId() + " sipariş getirdi to Müşteri " + customerId + " (Yaş: " + age + ")" + "\n");
                    garsonButtons[garson.getGarsonId()-1].setBackground(Color.GREEN);
                } else {
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş getiremedi to Müşteri " + customerId + " (Yaş: " + age + ")");
                    GarsonArea.append("Garson " + garson.getGarsonId() + " sipariş getiremedi to Müşteri " + customerId + " (Yaş: " + age + ")" + "\n");
                }

                ovenSemaphore.acquire();
                System.out.println("Kasa: Müşteri ödemesi alınıyor. Müşteri: " + customerId + " (Yaş: " + age + ")  ödeme alındı.");
                Thread.sleep(2000);
                ovenSemaphore.release();
                KasaArea.append("Müşteri ödemesi alınıyor. Müşteri: " + customerId + " (Yaş: " + age + ")  ödeme alındı." + "\n");

                Thread.sleep(1000);

                // Müşteri masadan kalktığında masa boşalır
                masa.setdolumu(false);
                tablesSemaphore.release();
                System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masadan kalktı.");
                MasaArea.append("Müşteri " + customerId + " (Yaş: " + age + ") masadan kalktı." + "\n");
                tableButtons[masa.getMasaNo() - 1].setBackground(Color.GREEN);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

     class PriorityCustomer extends Customer {
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
                            MasaArea.append("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") masaya oturamadı. Masa dolu." + "\n");
                            return;
                        }

                        System.out.println("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) masaya oturdu. Masası: " + super.masa.getMasaNo());
                        super.masa.setdolumu(true); // Öncelikli müşteri oturduğunda masa dolu
                        Thread.sleep(1000);
                        MasaArea.append("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) masaya oturdu. Masası: " + super.masa.getMasaNo() + "\n");
                        tableButtons[super.masa.getMasaNo() - 1].setBackground(Color.GRAY);
                        if (!orderTaken) {
                            orderSemaphore.acquire();
                            System.out.println("Garson " + super.garson.getGarsonId() + " sipariş aldı from Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri)");
                            Thread.sleep(2000);
                            orderSemaphore.release();
                            orderTaken = true;
                            GarsonArea.append("Garson " + super.garson.getGarsonId() + " sipariş aldı from Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri)" + "\n");
                            garsonButtons[super.garson.getGarsonId()-1].setBackground(Color.RED);
                        }

                        if (orderTaken) {
                            ovenSemaphore.acquire();
                            System.out.println("Aşçı " + super.chef.chefId + " Yemek hazırlanıyor.");
                            AsciArea.append("Aşçı " + super.chef.chefId + " Yemek hazırlanıyor." + "\n");
                            asciButtons[super.chef.chefId-1].setBackground(Color.RED);
                            Thread.sleep(3000);
                            System.out.println("Aşçı " + super.chef.chefId + " Yemek hazırlandı.");
                            ovenSemaphore.release();
                            AsciArea.append("Aşçı " + super.chef.chefId + " Yemek hazırlandı." + "\n");
                            asciButtons[super.chef.chefId-1].setBackground(Color.GREEN);
                            System.out.println("Garson " + super.garson.getGarsonId() + " sipariş getirdi to Müşteri " + super.customerId + " (Yaş: " + super.age + ")");
                            GarsonArea.append("Garson " + super.garson.getGarsonId() + " sipariş getirdi to Müşteri " + super.customerId + " (Yaş: " + super.age + ")" + "\n");
                            garsonButtons[super.garson.getGarsonId()-1].setBackground(Color.GREEN);
                        } else {
                            System.out.println("Garson " + super.garson.getGarsonId() + " sipariş getirmedi to Müşteri " + super.customerId + " (Yaş: " + super.age + ")");
                            GarsonArea.append("Garson " + super.garson.getGarsonId() + " sipariş getirmedi to Müşteri " + super.customerId + " (Yaş: " + super.age + ")" + "\n");
                        }

                        ovenSemaphore.acquire();
                        System.out.println("Kasa: Müşteri ödemesi alınıyor. Müşteri: " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) ödeme alındı.");
                        Thread.sleep(2000);
                        ovenSemaphore.release();
                        KasaArea.append("Müşteri ödemesi alınıyor.Müşteri: " + super.customerId + " (Yaş: " + super.age + ") (Önc Müş) ödeme alındı." + "\n");

                        Thread.sleep(1000);

                        Thread.sleep(5000);
                        super.masa.setdolumu(false); // Öncelikli müşteri masadan kalktığında masa boşalır
                        priorityCustomersSemaphore.release();
                        System.out.println("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) masadan kalktı.");
                        MasaArea.append("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) masadan kalktı." + "\n");
                        tableButtons[super.masa.getMasaNo() - 1].setBackground(Color.GREEN);
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