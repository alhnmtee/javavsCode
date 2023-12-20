import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Arayuz  {
    JButton problem2;
    JButton masa1;
    JButton masa2;
    JButton masa3;
    JButton masa4;
    JButton masa5;
    JButton masa6;

    JButton garson1;
    JButton garson2;
    JButton garson3;

    JButton asci1;
    JButton asci2;

    JButton kasa;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    public Arayuz() {
        frame.add(panel);
        frame.setLayout(null);
        panel.setLayout(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        frame.setSize(screenWidth, screenHeight);
        panel.setSize(screenWidth, screenHeight);

        masa1 = new JButton("Masa 1");
        panel.add(masa1);
        masa1.setBounds(50, 50, 100, 100);
        masa1.setVisible(true);

        masa2 = new JButton("Masa 2");
        panel.add(masa2);
        masa2.setBounds(180, 50, 100, 100);
        masa2.setVisible(true);


        masa3 = new JButton("Masa 3");
        panel.add(masa3);
        masa3.setBounds(310, 50, 100, 100);
        masa3.setVisible(true);


        masa4 = new JButton("Masa 4");
        panel.add(masa4);
        masa4.setBounds(50, 170, 100, 100);
        masa4.setVisible(true);


        masa5 = new JButton("Masa 5");
        panel.add(masa5);
        masa5.setBounds(180, 170, 100, 100);
        masa5.setVisible(true);


        masa6 = new JButton("Masa 6");
        panel.add(masa6);
        masa6.setBounds(310, 170, 100, 100);
        masa6.setVisible(true);


        garson1 = new JButton("Garson 1");
        panel.add(garson1);
        garson1.setBounds(460, 60, 50, 50);
        garson1.setVisible(true);


        garson2 = new JButton("Garson 2");
        panel.add(garson2);
        garson2.setBounds(460, 130, 50, 50);
        garson2.setVisible(true);


        garson3 = new JButton("Garson 3");
        panel.add(garson3);
        garson3.setBounds(460, 200, 50, 50);
        garson3.setVisible(true);

        kasa= new JButton("Kasa");
        panel.add(kasa);
        kasa.setBounds(560,80,250,130);
        kasa.setVisible(true);


        asci1 = new JButton("Asci 1");
        panel.add(asci1);
        asci1.setBounds(830, 90, 200, 100);
        asci1.setVisible(true);

        asci2 = new JButton("Asci 2");
        panel.add(asci2);
        asci2.setBounds(1050, 90, 200, 100);
        asci2.setVisible(true);

        JTextArea MasaArea=new JTextArea("Masa Durumları");
        panel.add(MasaArea);
        MasaArea.setBounds(50,400,350,385);
        MasaArea.setEditable(false);


        JTextArea GarsonArea=new JTextArea("Garson Durumları");
        panel.add(GarsonArea);
        GarsonArea.setBounds(420,400,350,385);
        GarsonArea.setEditable(false);

        JTextArea KasaArea=new JTextArea("Kasa Durumu");
        panel.add(KasaArea);
        KasaArea.setBounds(790,400,350,385);
        KasaArea.setEditable(false);

        JTextArea AsciArea=new JTextArea("Ascı Durumu");
        panel.add(AsciArea);
        AsciArea.setBounds(1160,400,350,385);
        AsciArea.setEditable(false);

        problem2= new JButton("PROBLEM 2");
        panel.add(problem2);
        problem2.setBounds(1290,90,150,100);
        problem2.setVisible(true);
        problem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        MasaArea.setVisible(true);
        GarsonArea.setVisible(true);
        KasaArea.setVisible(true);
        AsciArea.setVisible(true);

        frame.setVisible(true);
        panel.setVisible(true);

    }
}
