import java.util.*;

import javax.swing.text.html.FormView;

public class problem2 {

    static Masa[] masa;
    static Queue<Customer> waitingQueue = new LinkedList<>();
    static Queue<Customer> kasaQueue = new LinkedList<>();

    public static void main(String[] args) {
        int numTables = 4;
        int totalProfit = 0;

        masa = new Masa[numTables];

        for (int i = 0; i < masa.length; i++) {
            masa[i] = new Masa(i); 
        }

        for (int time = 0; time < 30; time++) {
            if(time==0){
               continue;
            }

            System.out.println("\nTime: " + time);
            if (!kasaQueue.isEmpty()) {
                Customer servedCustomer = kasaQueue.peek();
                if (time - servedCustomer.getArrivalTime() >= 1) {
                    kasaQueue.poll(); // Kasa sırasından müşteriyi çıkar
                    totalProfit++;
                    bosMasaAyarla(); // Masa boş hale getir
                }
            }

            if (time % 5 == 0) {
                // Her 5 saniyede bir 4 müşteri gelsin
                oturacakMusteriEkle(4, time);
            }
           else if(!waitingQueue.isEmpty() && time % 5 == 0) {
                oturacakMusteriEkle(4,time); // Her 5 saniyede bir gelen müşterileri ekle
            }

            if(!waitingQueue.isEmpty() && masadaBosMasaSayisi() > 0) {
                // Eğer boş masa varsa ve waitingQueue'de müşteri varsa
                Customer c = waitingQueue.poll();
                // WaitingQueue'den bir eleman çıkar
                sıradanOturacakMusteriEkle(c.getArrivalTime());
            }


            

            //20 saniyeden fazla bekleyen müşterileri waitingQueue'den çıkar
            List<Customer> customersToRemove = new ArrayList<>();
            for (Customer customer : waitingQueue) {
                if (time - customer.getArrivalTime() >= 20) {
                    customersToRemove.add(customer);
                }
            }
            waitingQueue.removeAll(customersToRemove);

            for (Masa m : masa) {
                System.out.println("Masa " + m.tableNumber + ": " + (m.isAvailable ? "Boş" : "Dolu"));
            }
            System.out.println("Waiting Queue Size: " + waitingQueue.size());
            System.out.println("Kasa Queue Size: " + kasaQueue.size());
        }

        int totalCost = numTables * 1;
        int netProfit = totalProfit - totalCost;

        System.out.println("\nFinal Results:");
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
                        kasaQueue.add(new Customer(arrivalTime + 8)); 
                        break;
                    }
                }
            } else {
                waitingQueue.add(new Customer(arrivalTime + 8 ));
            }
        }
    }
    static void sıradanOturacakMusteriEkle(int arrivalTime){
        //sıradan 1 kişi masaya ekle
        if(masadaBosMasaSayisi()>0){
            for(int i=0;i<masa.length;i++){
                if(masa[i].isAvailable){
                    masa[i].isAvailable=false;
                    kasaQueue.add(new Customer(arrivalTime+8));
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

    static void bosMasaAyarla() {
        for (Masa m : masa) {
            if (!m.isAvailable) {
                m.isAvailable = true; 
                break;
            }
        }
    }

    static class Masa {
        int tableNumber;
        boolean isAvailable;

        public Masa(int tableNumber) {
            this.tableNumber = tableNumber;
            this.isAvailable = true;
        }
    }

    static class Customer {
        int arrivalTime;

        public Customer(int arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public int getArrivalTime() {
            return arrivalTime;
        }
    }
}
