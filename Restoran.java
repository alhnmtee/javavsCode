import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.*;
import java.awt.*;

public class Restoran extends Thread {

    private static final int masaSayisi = 6;
    private  int müsteriSayisi = 8;
    private  int öncelikliMusteri = 2;

    private int müsteriSayisi2=0;
    private int öncelikliMusteri2=0;
    private int müsteriSayisi3=0;
    private int öncelikliMusteri3=0;

    private static final int garsonSayisi = 3;
    private static final int asciSayisi = 2;

    private static Semaphore tablesSemaphore;
    private static Semaphore priorityCustomersSemaphore;
    private static Semaphore ovenSemaphore;//aşçi için
    private static Semaphore kasaSemaphore;//Kasa için
    private Semaphore cookSemaphore;

    private Garson[] garsons;
    private Chef[] chefs;
    private Masa[] masalar;

    JFrame frame;
    JPanel panel;
    JLabel KasaResimLabel;
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
    Thread customerThread;
    Customer[] customers=new Customer[90];

   ArrayList<Thread> customerThreads=new ArrayList<>();

   boolean durdurDevam=false;
    public static int ToplamKazanc=0;


    public Restoran(int toplamMusteriSayisi, int oncelikliMusteriSayisi,int toplamMusteriSayisi2, int oncelikliMusteriSayisi2,int toplamMusteriSayisi3, int oncelikliMusteriSayisi3) {
        öncelikliMusteri = oncelikliMusteriSayisi;
        müsteriSayisi = toplamMusteriSayisi;
        öncelikliMusteri2 = oncelikliMusteriSayisi2;
        müsteriSayisi2 = toplamMusteriSayisi2;
        öncelikliMusteri3 = oncelikliMusteriSayisi3;
        müsteriSayisi3 = toplamMusteriSayisi3;
        tablesSemaphore = new Semaphore(masaSayisi);
        priorityCustomersSemaphore = new Semaphore(öncelikliMusteri);
        ovenSemaphore = new Semaphore(asciSayisi);
        kasaSemaphore = new Semaphore(1);
        cookSemaphore = new Semaphore(asciSayisi*2);

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
        // panel.add(MasaArea);
        // MasaArea.setBounds(50,100,400,385);
        MasaArea.setEditable(false);
        JScrollPane scrollPaneMasa = new JScrollPane(MasaArea);
        scrollPaneMasa.setBounds(50, 100, 400, 385);
        panel.add(scrollPaneMasa);

        GarsonArea=new JTextArea();
        //panel.add(GarsonArea);
        //GarsonArea.setBounds(500,100,400,385);
        GarsonArea.setEditable(false);
        JScrollPane scrollPaneGarson = new JScrollPane(GarsonArea);
        scrollPaneGarson.setBounds(500, 100, 400, 385);
        panel.add(scrollPaneGarson);

        KasaArea=new JTextArea();
        //panel.add(KasaArea);
        //KasaArea.setBounds(950,100,450,385);
        KasaArea.setEditable(false);
        JScrollPane scrollPaneKasa = new JScrollPane(KasaArea);
        scrollPaneKasa.setBounds(950, 100, 450, 385);
        panel.add(scrollPaneKasa);

        AsciArea=new JTextArea();
        //panel.add(AsciArea);
        //AsciArea.setBounds(1450,100,400,385);
        AsciArea.setEditable(false);
        JScrollPane scrollPaneAsci= new JScrollPane(AsciArea);
        scrollPaneAsci.setBounds(1450, 100, 400, 385);
        panel.add(scrollPaneAsci);

        MasaArea.setFont(fontarea);
        GarsonArea.setFont(fontarea);
        KasaArea.setFont(fontarea);
        AsciArea.setFont(fontarea);

        KasaResimLabel = new JLabel();
        panel.add(KasaResimLabel);
        KasaResimLabel.setBounds(1300, 550, 70, 70);
        KasaResimLabel.setVisible(false);

        ImageIcon photoIcon2 = new ImageIcon("C:\\Users\\ASUS\\Desktop\\cashier.png");
        Image image2 = photoIcon2.getImage();
        Image scaledImage2 = image2.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        JLabel photoLabel2 = new JLabel(scaledIcon2);
        photoLabel2.setBounds(1000,550, 350,350);
        panel.add(photoLabel2);

        ImageIcon photoIcon = new ImageIcon("C:\\Users\\ASUS\\Desktop\\masa.jpg");
        Image image = photoIcon.getImage();
        Image scaledImage = image.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel photoLabel = new JLabel(scaledIcon);
        photoLabel.setBounds(0, 0,screenWidth,screenHeight);
        panel.add(photoLabel);
        photoLabel.setVisible(false);




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

        customers = new Customer[müsteriSayisi];
        Semaphore orderSemaphore = new Semaphore(3);

        for (int i = 0; i < öncelikliMusteri; i++) {
            customers[i] = new PriorityCustomer(i, garsons[i % garsonSayisi], 65, orderSemaphore, chefs[i % asciSayisi], masalar[i]);
          customerThread= new Thread(customers[i]);
          customerThreads.add(customerThread);

        }

        for (int i = öncelikliMusteri; i < müsteriSayisi; i++) {
            customers[i] = new Customer(i, garsons[i % garsonSayisi], 30, orderSemaphore, chefs[i % asciSayisi], masalar[i % masaSayisi]);
            Thread customerThread = new Thread(customers[i]);
            customerThreads.add(customerThread);

        }


        JButton dalga=new JButton("2. Adım");
        panel.add(dalga);
        dalga.setBounds(560,800,150,70);
        dalga.setVisible(true);
        dalga.addActionListener(actionEvent -> {
            customerThreads.clear();
            //clear customers array
            for (int i = 0; i <müsteriSayisi ; i++) {
                customers[i]=null;
            }
            customers = new Customer[müsteriSayisi2];
            System.out.println(öncelikliMusteri2 + " "+müsteriSayisi2);

            for (int i = 0; i <öncelikliMusteri2 ; i++) {
                customers[i] = new PriorityCustomer(i, garsons[i % garsonSayisi], 65, orderSemaphore, chefs[i % asciSayisi], masalar[i]);
                Thread customerThread = new Thread(customers[i]);
                customerThreads.add(customerThread);
                customerThread.start();
            }
            for (int i = öncelikliMusteri2; i < müsteriSayisi2; i++) {
                customers[i] = new Customer(i, garsons[i % garsonSayisi], 30, orderSemaphore, chefs[i % asciSayisi], masalar[i % masaSayisi]);
                Thread customerThread = new Thread(customers[i]);
                customerThreads.add(customerThread);
                customerThread.start();

            }
        });

        JButton dalga2=new JButton("3. Adım");
        panel.add(dalga2);
        dalga2.setBounds(560,1000,150,70);
        dalga2.setVisible(true);
        dalga2.addActionListener(actionEvent -> {
            customerThreads.clear();
            //clear customers array
            for (int i = 0; i <müsteriSayisi2 ; i++) {
                customers[i]=null;
            }
            customers = new Customer[müsteriSayisi3];


            for (int i = 0; i <öncelikliMusteri3 ; i++) {
                customers[i] = new PriorityCustomer(i, garsons[i % garsonSayisi], 65, orderSemaphore, chefs[i % asciSayisi], masalar[i]);
                Thread customerThread = new Thread(customers[i]);
                customerThreads.add(customerThread);
                customerThread.start();
            }
            for (int i = öncelikliMusteri3; i < müsteriSayisi3; i++) {
                customers[i] = new Customer(i, garsons[i % garsonSayisi], 30, orderSemaphore, chefs[i % asciSayisi], masalar[i % masaSayisi]);
                Thread customerThread = new Thread(customers[i]);
                customerThreads.add(customerThread);
                customerThread.start();

            }
        });


        JButton durdur=new JButton("Durdur");
        panel.add(durdur);
        durdur.setBounds(560,900,150,70);
        durdur.setVisible(true);
        durdur.addActionListener(actionEvent -> {
            if(durdurDevam){
                durdurDevam=false;
                durdur.setText("Durdur");
                for(Thread thread:customerThreads){
                    thread.resume();
                }
            }else{
                durdurDevam=true;
                durdur.setText("Devam");
                for(Thread thread:customerThreads){
                    thread.suspend();
                }
            }
        });




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
    private void showPaymentImage() {
        ImageIcon icon = new ImageIcon("C:\\Users\\ASUS\\Desktop\\coin.png");
        Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        KasaResimLabel.setIcon(new ImageIcon(image));
        KasaResimLabel.setVisible(true);
        ToplamKazanc++;
    }
    @Override
    public void run() {


      //customers start
        for(Thread thread:customerThreads){
            thread.start();
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
                    Thread.sleep(5000);
                }

                System.out.println("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturdu. Masası: " + masa.getMasaNo());
                masa.setdolumu(true);
                MasaArea.append("Müşteri " + customerId + " (Yaş: " + age + ") masaya oturdu. Masası: " + masa.getMasaNo() + "\n");
                tableButtons[masa.getMasaNo() - 1].setBackground(Color.RED);
                Thread.sleep(2000);


                if (!orderTaken) {
                    orderSemaphore.acquire();
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş aldı from Müşteri " + customerId + " (Yaş: " + age + ")");
                    Thread.sleep(2000);
                    orderSemaphore.release();
                    orderTaken = true;
                    GarsonArea.append("Garson " + garson.getGarsonId() + " sipariş aldı from Müşteri " + customerId + " (Yaş: " + age + ")" + "\n");
                    garsonButtons[garson.getGarsonId()-1].setBackground(Color.RED);
                    Thread.sleep(2000);
                    garsonButtons[garson.getGarsonId()-1].setBackground(Color.GREEN);
                }

                Thread.sleep(3000);
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


                } else {
                    System.out.println("Garson " + garson.getGarsonId() + " sipariş getiremedi to Müşteri " + customerId + " (Yaş: " + age + ")");
                    GarsonArea.append("Garson " + garson.getGarsonId() + " sipariş getiremedi to Müşteri " + customerId + " (Yaş: " + age + ")" + "\n");
                }

                kasaSemaphore.acquire();
                System.out.println("Kasa: Müşteri ödemesi alınıyor. Müşteri: " + customerId + " (Yaş: " + age + ")  ödeme alındı.");
                Thread.sleep(2000);
                kasaSemaphore.release();
                KasaArea.append("Müşteri ödemesi alınıyor. Müşteri: " + customerId + " (Yaş: " + age + ")  ödeme alındı." + "\n");
                showPaymentImage();
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
                            MasaArea.append("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") masaya oturamadı. Masa dolu." + "\n");
                            priorityCustomersSemaphore.release();

                            return;
                        }

                        System.out.println("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) masaya oturdu. Masası: " + super.masa.getMasaNo());
                        super.masa.setdolumu(true); // Öncelikli müşteri oturduğunda masa dolu
                        MasaArea.append("Öncelikli Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) masaya oturdu. Masası: " + super.masa.getMasaNo() + "\n");
                        tableButtons[super.masa.getMasaNo() - 1].setBackground(Color.GRAY);
                        Thread.sleep(1000);
                        if (!orderTaken) {
                            orderSemaphore.acquire();
                            System.out.println("Garson " + super.garson.getGarsonId() + " sipariş aldı from Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri)");
                            Thread.sleep(2000);
                            orderSemaphore.release();
                            orderTaken = true;
                            GarsonArea.append("Garson " + super.garson.getGarsonId() + " sipariş aldı from Müşteri " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri)" + "\n");
                            garsonButtons[super.garson.getGarsonId()-1].setBackground(Color.RED);
                            Thread.sleep(2000);
                            garsonButtons[super.garson.getGarsonId()-1].setBackground(Color.GREEN);
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

                        }else {
                            System.out.println("Garson " + super.garson.getGarsonId() + " sipariş getirmedi to Müşteri " + super.customerId + " (Yaş: " + super.age + ")");
                            GarsonArea.append("Garson " + super.garson.getGarsonId() + " sipariş getirmedi to Müşteri " + super.customerId + " (Yaş: " + super.age + ")" + "\n");
                        }

                        kasaSemaphore.acquire();
                        System.out.println("Kasa: Müşteri ödemesi alınıyor. Müşteri: " + super.customerId + " (Yaş: " + super.age + ") (Öncelikli Müşteri) ödeme alındı.");
                        Thread.sleep(2000);
                        kasaSemaphore.release();
                        KasaArea.append("Müşteri ödemesi alınıyor.Müşteri: " + super.customerId + " (Yaş: " + super.age + ") (Önc Müş) ödeme alındı." + "\n");

                        Thread.sleep(1000);

                        Thread.sleep(5000);
                        super.masa.setdolumu(false);
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