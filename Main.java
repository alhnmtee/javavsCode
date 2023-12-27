import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import java.awt.GridLayout;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

        public static void main(String[] args) {
            AtomicInteger toplamMusteriSayisi= new AtomicInteger();
            AtomicInteger oncelikliMusteriSayisi= new AtomicInteger();
           
        JFrame frame = new JFrame();
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(3, 2));
        frame.setLocation(900, 500);

        JButton button = new JButton("İşlemi Başlat");
        JLabel toplamLabel = new JLabel("Toplam Müşteri Sayısı:");
        JTextField toplamField = new JTextField();
        JLabel oncelikliLabel = new JLabel("Öncelikli Müşteri Sayısı:");
        JTextField oncelikliField = new JTextField();

        frame.add(toplamLabel);
        frame.add(toplamField);
        frame.add(oncelikliLabel);
        frame.add(oncelikliField);

        frame.add(button);
        frame.setVisible(true);
        button.addActionListener(e -> {
            try {
            
                toplamMusteriSayisi.set(Integer.parseInt(toplamField.getText()));
                oncelikliMusteriSayisi.set(Integer.parseInt(oncelikliField.getText()));
                frame.setVisible(false);
              Restoran restaurant = new Restoran(toplamMusteriSayisi.get(), oncelikliMusteriSayisi.get());
            restaurant.start();
            } catch (NumberFormatException ex) {
            
                JOptionPane.showMessageDialog(frame, "Lütfen geçerli bir sayı girin.",
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
        }
