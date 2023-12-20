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
        frame.setSize(1000, 1000);
        panel.setSize(1000,1000);

        masa1 = new JButton("Masa 1");
        panel.add(masa1);
        masa1.setBounds(50, 100, 100, 100);
        masa1.setVisible(true);
        Label masaLabel1 = new Label("masa 1 durum");
        masaLabel1.setBounds(50, 200, 100, 70);
        panel.add(masaLabel1);

        masa2 = new JButton("Masa 2");
        panel.add(masa2);
        masa2.setBounds(180, 100, 100, 100);
        masa2.setVisible(true);
        Label masaLabel2 = new Label("masa 2 durum");
        masaLabel2.setBounds(180, 200, 100, 70);
        panel.add(masaLabel2);

        masa3 = new JButton("Masa 3");
        panel.add(masa3);
        masa3.setBounds(310, 100, 100, 100);
        masa3.setVisible(true);
        Label masaLabel3 = new Label("masa 3 durum");
        masaLabel3.setBounds(310, 200, 100, 70);
        panel.add(masaLabel3);

        masa4 = new JButton("Masa 4");
        panel.add(masa4);
        masa4.setBounds(50, 300, 100, 100);
        masa4.setVisible(true);
        Label masaLabel4 = new Label("masa 4 durum");
        masaLabel4.setBounds(50, 400, 100, 70);
        panel.add(masaLabel4);

        masa5 = new JButton("Masa 5");
        panel.add(masa5);
        masa5.setBounds(180, 300, 100, 100);
        masa5.setVisible(true);
        Label masaLabel5 = new Label("masa 4 durum");
        masaLabel5.setBounds(180, 400, 100, 70);
        panel.add(masaLabel5);

        masa6 = new JButton("Masa 6");
        panel.add(masa6);
        masa6.setBounds(310, 300, 100, 100);
        masa6.setVisible(true);
        Label masaLabel6 = new Label("masa 4 durum");
        masaLabel6.setBounds(310, 400, 100, 70);
        panel.add(masaLabel6);

        garson1 = new JButton("Garson 1");
        panel.add(garson1);
        garson1.setBounds(460, 110, 70, 70);
        garson1.setVisible(true);
        Label garsonLabel1 = new Label("garson 1 ne yapıyor");
        garsonLabel1.setBounds(540, 110, 100, 70);
        panel.add(garsonLabel1);

        garson2 = new JButton("Garson 2");
        panel.add(garson2);
        garson2.setBounds(460, 205, 70, 70);
        garson2.setVisible(true);
        Label garsonLabel2 = new Label("garson 2 ne yapıyor");
        garsonLabel2.setBounds(540, 205, 100, 70);
        panel.add(garsonLabel2);

        garson3 = new JButton("Garson 3");
        panel.add(garson3);
        garson3.setBounds(460, 310, 70, 70);
        garson3.setVisible(true);
        Label garsonLabel3 = new Label("garson 3 ne yapıyor");
        garsonLabel3.setBounds(540, 310, 100, 70);
        panel.add(garsonLabel3);

        asci1 = new JButton("Asci 1");
        panel.add(asci1);
        asci1.setBounds(50, 670, 200, 100);
        asci1.setVisible(true);
        Label asciLabel1 = new Label("asci1");
        asciLabel1.setBounds(500, 670, 100, 50);
        panel.add(asciLabel1);

        asci2 = new JButton("Asci 2");
        panel.add(asci2);
        asci2.setBounds(260, 670, 200, 100);
        asci2.setVisible(true);
        Label asciLabel2 = new Label("asci2");
        asciLabel2.setBounds(500, 725, 100, 50);
        panel.add(asciLabel2);

        kasa= new JButton("Kasa");
        panel.add(kasa);
        kasa.setBounds(700,150,300,150);
        kasa.setVisible(true);
        Label kasaLabel1 = new Label("kasa odemeler");
        kasaLabel1.setBounds(720, 310, 100, 100);
        panel.add(kasaLabel1);

        problem2= new JButton("PROBLEM 2");
        panel.add(problem2);
        problem2.setBounds(700,670,150,100);
        problem2.setVisible(true);
        problem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        frame.setVisible(true);
        panel.setVisible(true);
        garsonLabel1.setVisible(true);
        garsonLabel2.setVisible(true);
        garsonLabel3.setVisible(true);
        masaLabel1.setVisible(true);
        masaLabel2.setVisible(true);
        masaLabel3.setVisible(true);
        masaLabel4.setVisible(true);
        masaLabel5.setVisible(true);
        masaLabel6.setVisible(true);
        kasaLabel1.setVisible(true);
    }
}
