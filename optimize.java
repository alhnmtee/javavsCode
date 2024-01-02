import javax.swing.*;
import java.io.UnsupportedEncodingException;


public class optimize {
    public optimize(int kacSaniye, int kacMusteri) throws UnsupportedEncodingException {
        JOptionPane jop = new JOptionPane();
        int bestNetProfit = Integer.MIN_VALUE;
        int bestAsciSayisi = 0;
        int bestGarsonSayisi = 0;
        int bestMasaSayisi = 0;

        int minMasaSayisi = (kacMusteri / 2);
        int maxMasaSayisi = kacMusteri*2;
        int maxGarsonSayisi=kacMusteri;
        int maxAscisayisi=kacMusteri/2;




        for (int asciSayisi = 1; asciSayisi <= maxAscisayisi; asciSayisi++) {
            for (int garsonSayisi = 1; garsonSayisi <= maxGarsonSayisi; garsonSayisi++) {
                for (int masaSayisi = minMasaSayisi; masaSayisi <= maxMasaSayisi; masaSayisi++) {

                    problem2 simulation = new problem2(kacSaniye, kacMusteri, asciSayisi, garsonSayisi, masaSayisi);
                    if (simulation.netProfit > bestNetProfit) {
                        bestNetProfit = simulation.netProfit;
                        bestAsciSayisi = asciSayisi;
                        bestGarsonSayisi = garsonSayisi;
                        bestMasaSayisi = masaSayisi;
                    }
                }
            }
        }

      //  System.out.println("En iyi net profit: " + bestNetProfit);
        //System.out.println("En iyi Aşçı Sayısı: " + bestAsciSayisi);
        //System.out.println("En iyi Garson Sayısı: " + bestGarsonSayisi);
        //System.out.println("En iyi Masa Sayısı: " + bestMasaSayisi);
        jop.showMessageDialog(null, "En iyi net Kar: " + bestNetProfit + "\n" + "En iyi Aşçı Sayısı: " + bestAsciSayisi +
                "\n" + "En iyi Garson Sayısı: " + bestGarsonSayisi + "\n" + "En iyi Masa Sayısı: " + bestMasaSayisi);

    }
}
