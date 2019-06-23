import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class UI extends JFrame implements Runnable  {


    private JPanel mainMenu;
    private  Font f2 = new Font("Arial", Font.BOLD, 20);
    private  Font f1 = new Font("Monaco", Font.BOLD, 16);
    private static  GridBagConstraints c;
    private static  ArrayList<Product> product;
    private static JComboBox jcb;
    private static JTable table;
    private static TableModel model;


    private void init() {
            setSize(900, 600);
            setLocation(300, 300);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Manager of storage");
            try {
                mainMenuFrame();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        private void mainMenuFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

            mainMenu = new JPanel(new GridBagLayout());
            c = new GridBagConstraints();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


            //TODO
           product = new ArrayList<>();
            for (int i = 0; i <10; i++) {
                product.add(new Product(i,"name" + i, "description " + i, "producer" + i,i,i));
            }

            // Інформаційна область
            model = new StoreTableModel(product);
            table = new JTable(model);


            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 1;
            c.gridx = 0;
            c.gridwidth = 10;
            c.ipady = 350;
            JScrollPane scrollPane = new JScrollPane(table);
            mainMenu.add(scrollPane, c);
            table.setFont(f1);


            // create checkbox
            jcb = new JComboBox(new String[] { "group1", "group2", "group3", "group4", "group5" });
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 0;
            c.gridx = 0;
            c.ipady = 20;


            mainMenu.add(jcb, c);
            jcb.setFont(f1);



            JButton b1 = new JButton("Add group");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 0;
            c.gridx = 1;
            c.ipady = 20;
            b1.addActionListener(e ->{

            });
            mainMenu.add(b1, c);
            b1.setFont(f1);

            JButton b2 = new JButton("Edit group");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 0;
            c.gridx = 2;
            c.ipady = 20;
            b2.addActionListener(e ->{

            });
            mainMenu.add(b2, c);
            b2.setFont(f1);

            JButton b3 = new JButton("Delete group");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 0;
            c.gridx = 3;
            c.ipady = 20;
            b3.addActionListener(e ->{

            });
            mainMenu.add(b3, c);
            b3.setFont(f1);

            // кнопка виходу Зупиняє додаток.
            JButton b0 = new JButton("Exit");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 0;
            c.gridx =4;
            c.ipady = 20;
            b0.addActionListener(e -> System.exit(0));
            mainMenu.add(b0, c);
            b0.setFont(f1);


            JButton b5 = new JButton("Add item");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 3;
            c.gridx = 0;
            c.ipady = 20;
            b5.addActionListener(e ->{

            });
            mainMenu.add(b5, c);
            b5.setFont(f1);
            b5.setVisible(false);

            JButton b6 = new JButton("Edit item");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 3;
            c.gridx = 1;
            c.ipady = 20;
            b6.addActionListener(e ->{

            });
            mainMenu.add(b6, c);
            b6.setFont(f1);
            b6.setVisible(false);

            JButton b7 = new JButton("Delete item");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 3;
            c.gridx = 2;
            c.ipady = 20;
            b7.addActionListener(e ->{

            });
            mainMenu.add(b7, c);
            b7.setFont(f1);
            b7.setVisible(false);

            // кнопка виходу Зупиняє додаток.
            JButton b8= new JButton("Increase/Decrease quantity");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 2;
            c.gridy = 3;
            c.gridx = 3;
            c.ipady = 20;
            b8.addActionListener(e -> System.exit(0));
            mainMenu.add(b8, c);
            b8.setFont(f1);
            b8.setVisible(false);


            jcb.addItemListener(itemEvent -> {
                b5.setVisible(false);
                b6.setVisible(false);
                b7.setVisible(false);
                b8.setVisible(false);
                //TODO
                if (itemEvent.getSource() == jcb) {
                    //   System.out.println(jcb.getSelectedItem());
                    product = new ArrayList<>();
                    for (int i = 0; i <10; i++) {
                        product.add(new Product(i,"name" +jcb.getSelectedItem()+ i, "description " + i, "producer" + i,i,i));
                    }
                }
                model = new StoreTableModel(product);
                table = new JTable(model);
                revalidate();
                repaint();
            });



            table.getSelectionModel().addListSelectionListener(event -> {
                b5.setVisible(true);
                b6.setVisible(true);
                b7.setVisible(true);
                b8.setVisible(true);
                //System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
            });


            setContentPane(mainMenu);
            setVisible(true);

        }



    public static void main(String[] args) {
        new Thread(new UI()).start();
    }

    @Override
    public void run() {
        init();
    }
}
