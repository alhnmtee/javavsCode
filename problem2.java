import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class problem2 {

    static Masa[] masa;
    static Queue<Masa> waitingQueue = new LinkedList<>();
    static Queue<Masa> kasaQueue = new LinkedList<>();
    static Queue<Masa> asciQueue = new LinkedList<>();
    static Queue<Masa> garsonQueue = new LinkedList<>();
    static int garsonSayisi = 6;
    static int asciSayisi = 2;
    static int asciKapasite = asciSayisi * 2; // Aşçı kapasitesi

    public problem2(int saniye,int müsteriSayisi) throws UnsupportedEncodingException {
        int numTables = 8;
        int totalProfit = 0;
        int totalGarson = garsonSayisi;
        int totalAsci = asciSayisi;
        int gercekAsci=asciSayisi*2;

        JFrame frame = new JFrame("Output");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Create a panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a textarea with scrollbar
        JTextArea textArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Redirect System.out.println to the textarea
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea), true, StandardCharsets.UTF_8);
        System.setOut(printStream);

        // Add the panel to the frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);


        masa = new Masa[numTables];

        for (int i = 0; i < masa.length; i++) {
            masa[i] = new Masa(i, 0);
        }

        for (int time = 0; time < 30; time++) {
            if (time == 0)
                continue;

            System.out.println("\nTime: " + time);

            // Garson işlemleri
            if (!kasaQueue.isEmpty()) {
                Masa servedCustomer = kasaQueue.peek();
                if (time - servedCustomer.getArrivalTime() >= 1) {
                    kasaQueue.poll();
                    totalProfit++;
                    masa[servedCustomer.tableNumber].isAvailable = true;
                    System.out.println("Customer at " + servedCustomer.tableNumber + " paid at time " + time);
                }
            }

            // Aşçı işlemleri
            if (!asciQueue.isEmpty()) {
                System.out.println(gercekAsci);
                int asciKuyrukSize = Math.min(gercekAsci, asciQueue.size()); // Aşçı kapasitesi kadar kontrol
                for (int i = 0; i < asciKuyrukSize; i++) {
                    Masa preparedMeal = asciQueue.poll();
                    if (time - preparedMeal.arrivalTime >= 3) {
                        preparedMeal.siparisSüresi = -1; // Yemek hazırlandı
                        System.out.println("Aşçı yemek hazırladı masa " + preparedMeal.tableNumber);
                        asciKapasite++; // Yemek yapıldığı için kapasite bir arttırılır
                    } else {
                        asciQueue.add(preparedMeal);
                    }
                }
            }

            ArrayList<Integer> bosMasaNumaralari = new ArrayList<>();
            for (int i = 0; i < masa.length; i++) {
                if (masa[i].isAvailable) {
                    bosMasaNumaralari.add(i);
                }
            }

            // boş masa numaralarını yazdır
            for (Integer masaNumarasi : bosMasaNumaralari) {
                System.out.println("Boş Masa Numarası: " + masaNumarasi);
            }

            // Oturacak müşteri eklemesi
            if (time % saniye == 0) {
                oturacakMusteriEkle(müsteriSayisi, time);
            } else if (!waitingQueue.isEmpty() && time % saniye == 0) {
                oturacakMusteriEkle(müsteriSayisi, time);
            }

            // Sıradan oturacak müşteri eklemesi
            if (!waitingQueue.isEmpty() && masadaBosMasaSayisi() > 0) {
                Masa c = waitingQueue.poll();
                sıradanOturacakMusteriEkle(c.getArrivalTime());
            }

            // Garson sipariş alma işlemleri
            for (Integer masaNumarasi : bosMasaNumaralari) {
                if (masaNumarasi >= 0 && masaNumarasi < masa.length) {
                    boolean isAvailable = masa[masaNumarasi].isAvailable;
                    if (!isAvailable) {
                        if (garsonSayisi > 0) {
                            for (Masa m : masa) {
                                if (m.tableNumber == masaNumarasi && m.getSiparisSüresi() == -1) {
                                    m.siparisSüresi = time + 2;
                                    garsonSayisi--;
                                    System.out.println("Garson bir sipariş aldı masa " + masaNumarasi);
                                     
                                }
                            }
                        } else {
                            System.out.println("Garson bulunamadı!");
                        }
                    } else {
                        System.out.println("Masa boş");
                    }
                }
            }

            // Siparişi alınan masalara servis yapılır
            for (Masa m : masa) {
                if (time == m.getSiparisSüresi() && m.getSiparisSüresi() != -1) {
                    garsonSayisi++;
                    m.siparisSüresi = -1;
                    System.out.println("Sipariş alındı masa " + m.tableNumber);
                    asciQueue.add(new Masa(m.tableNumber, time));
                    if (asciKapasite > 0) {
                        asciKapasite--;
                    }else{
                        garsonQueue.add(new Masa(m.tableNumber));
                    }
                    
                }
            }
            // Bekleme süresi dolan müşterileri kuyruktan çıkar
            ArrayList<Masa> customersToRemove = new ArrayList<>();
            for (Masa customer : waitingQueue) {
                if (time - customer.getArrivalTime() >= 20) {
                    customersToRemove.add(customer);
                }
            }
            waitingQueue.removeAll(customersToRemove);

            // Masaların durumlarını yazdır
            for (Masa m : masa) {
                System.out.println("Masa " + m.tableNumber + ": " + (m.isAvailable ? "Boş" : "Dolu"));
            }

            // Garson ve aşçı sayılarını yazdır
            System.out.println("Garson Sayısı " + garsonSayisi);
            System.out.println("Aşçı Sayısı " + totalAsci);

            // Kuyrukların boyutlarını yazdır
            System.out.println("Waiting Queue Size: " + waitingQueue.size());
            System.out.println("Kasa Queue Size: " + kasaQueue.size());
            System.out.println("Aşçı Queue Size: " + asciQueue.size());

        }

        int totalCost = numTables  + totalGarson  + totalAsci;
        int netProfit = totalProfit - totalCost;

        System.out.println("\nFinal Results:");
        System.out.println("Total Garson: " + totalGarson);
        System.out.println("Total Aşçı: " + totalAsci);
        System.out.println("Total Profit: " + totalProfit);
        System.out.println("Total Cost: " + totalCost);
        System.out.println("Net Profit: " + netProfit);
    }

    static void oturacakMusteriEkle(int numCustomers, int arrivalTime) {
        for (int i = 0; i < numCustomers; i++) {
            if (masadaBosMasaSayisi() > 0) {
                for (int j = 0; j < masa.length; j++) {
                    if (masa[j].isAvailable) {
                        masa[j].isAvailable = false;
                        kasaQueue.add(new Masa(masa[j].tableNumber, arrivalTime + 8));
                        break;
                    }
                }
            } else {
                waitingQueue.add(new Masa(arrivalTime + 8));
            }
        }
    }

    static void sıradanOturacakMusteriEkle(int arrivalTime) {
        if (masadaBosMasaSayisi() > 0) {
            for (int i = 0; i < masa.length; i++) {
                if (masa[i].isAvailable) {
                    masa[i].isAvailable = false;
                    kasaQueue.add(new Masa(masa[i].tableNumber, arrivalTime + 8));
                    break;
                }
            }
        }
    }

    static int masadaBosMasaSayisi() {
        int bosMasaSayisi = 0;
        for (Masa m : masa) {
            if (m.isAvailable) {
                bosMasaSayisi++;
            }
        }
        return bosMasaSayisi;
    }

    static class Masa {
        int tableNumber;
        boolean isAvailable;
        int siparisSüresi;
        int arrivalTime;
        int yemekHazirlamaSuresi;

        public Masa(int tableNumber, int arrivalTime) {
            this.tableNumber = tableNumber;
            this.isAvailable = true;
            this.siparisSüresi = -1;
            this.arrivalTime = arrivalTime;
            this.yemekHazirlamaSuresi = 3;
        }


        public int getSiparisSüresi() {
            return siparisSüresi;
        }

        public int getArrivalTime() {
            return arrivalTime;
        }
        public int getYemekHazirlamaSuresi() {
            return yemekHazirlamaSuresi;
        }

        public Masa(int arrivalTime) {
            this.arrivalTime = arrivalTime;
        }
    }
    class CustomOutputStream extends OutputStream {
    private JTextArea textArea;

    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char) b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
}
