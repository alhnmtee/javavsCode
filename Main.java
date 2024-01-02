import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import java.awt.GridLayout;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        AtomicInteger toplamMusteriSayisi= new AtomicInteger();
        AtomicInteger oncelikliMusteriSayisi= new AtomicInteger();
        AtomicInteger toplamMusteriSayisi2= new AtomicInteger();
        AtomicInteger oncelikliMusteriSayisi2= new AtomicInteger();
        AtomicInteger toplamMusteriSayisi3= new AtomicInteger();
        AtomicInteger oncelikliMusteriSayisi3= new AtomicInteger();

        JFrame problemSec = new JFrame();
        problemSec.setSize(400, 400);
        problemSec.setLayout(null);
        problemSec.setLocation(900, 500);
        JButton problem1 = new JButton("Problem 1");
        JButton problem2 = new JButton("Problem 2");
        problem1.setBounds(100, 100, 200, 50);
        problem2.setBounds(100, 200, 200, 50);
        problemSec.add(problem1);
        problemSec.add(problem2);
        problemSec.setVisible(true);
        problem1.addActionListener(e -> {
            problemSec.setVisible(false);
            JFrame frame = new JFrame();
            frame.setSize(700, 300);
            frame.setLayout(new GridLayout(4, 4));
            frame.setLocation(850, 500);

            JButton button = new JButton("İşlemi Başlat");
            JLabel toplamLabel = new JLabel("1:Toplam Müşteri Sayısı:");
            JTextField toplamField = new JTextField();
            JLabel oncelikliLabel = new JLabel("1:Öncelikli Müşteri Sayısı:");
            JTextField oncelikliField = new JTextField();
            JLabel toplamLabel2 = new JLabel("2:Toplam Müşteri Sayısı:");
            JTextField toplamField2 = new JTextField();
            JLabel oncelikliLabel2 = new JLabel("2:Öncelikli Müşteri Sayısı:");
            JTextField oncelikliField2 = new JTextField();
            JLabel toplamLabel3 = new JLabel("3:Toplam Müşteri Sayısı:");
            JTextField toplamField3= new JTextField();
            JLabel oncelikliLabel3 = new JLabel("3:Öncelikli Müşteri Sayısı:");
            JTextField oncelikliField3 = new JTextField();

            frame.add(toplamLabel);
            frame.add(toplamField);
            frame.add(oncelikliLabel);
            frame.add(oncelikliField);
            frame.add(toplamLabel2);
            frame.add(toplamField2);
            frame.add(oncelikliLabel2);
            frame.add(oncelikliField2);
            frame.add(toplamLabel3);
            frame.add(toplamField3);
            frame.add(oncelikliLabel3);
            frame.add(oncelikliField3);


            frame.add(button);
            frame.setVisible(true);
            button.addActionListener(e2 -> {
                try {

                    toplamMusteriSayisi.set(Integer.parseInt(toplamField.getText()));
                    oncelikliMusteriSayisi.set(Integer.parseInt(oncelikliField.getText()));
                    toplamMusteriSayisi2.set(Integer.parseInt(toplamField2.getText()));
                    oncelikliMusteriSayisi2.set(Integer.parseInt(oncelikliField2.getText()));
                    toplamMusteriSayisi3.set(Integer.parseInt(toplamField3.getText()));
                    oncelikliMusteriSayisi3.set(Integer.parseInt(oncelikliField3.getText()));
                    frame.setVisible(false);
                    Restoran restaurant = new Restoran(toplamMusteriSayisi.get(), oncelikliMusteriSayisi.get(),toplamMusteriSayisi2.get(),
                            oncelikliMusteriSayisi2.get(),toplamMusteriSayisi3.get(), oncelikliMusteriSayisi3.get());
                    restaurant.start();
                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(frame, "Lütfen geçerli bir sayı girin.",
                            "Hata", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        problem2.addActionListener(e -> {
            problemSec.setVisible(false);
            JFrame frame = new JFrame();
            frame.setSize(300, 150);
            frame.setLayout(new GridLayout(3, 2));
            frame.setLocation(900, 500);

            JButton button = new JButton("İşlemi Başlat");
            JLabel toplamLabel = new JLabel("Kaç saniyede");
            JTextField toplamField = new JTextField();
            JLabel oncelikliLabel = new JLabel("Kaç Müşteri");
            JTextField oncelikliField = new JTextField();

            frame.add(toplamLabel);
            frame.add(toplamField);
            frame.add(oncelikliLabel);
            frame.add(oncelikliField);

            frame.add(button);
            frame.setVisible(true);

            button.addActionListener(e2 -> {
                try {

                    toplamMusteriSayisi.set(Integer.parseInt(toplamField.getText()));
                    oncelikliMusteriSayisi.set(Integer.parseInt(oncelikliField.getText()));

                    frame.setVisible(false);
                    optimize optimizeClass = new optimize(toplamMusteriSayisi.get(), oncelikliMusteriSayisi.get());
                    //problem2 problem2Class = new problem2(toplamMusteriSayisi.get(), oncelikliMusteriSayisi.get(),1,4,5);

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(frame, "Lütfen geçerli bir sayı girin.",
                            "Hata", JOptionPane.ERROR_MESSAGE);
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
            });

        });

    }
}
