import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class UI extends JFrame implements Runnable  {


    private JPanel mainMenu;
    private  Font f2 = new Font("Arial", Font.BOLD, 20);
    private  Font f1 = new Font("Monaco", Font.BOLD, 16);
    private static  String token = Sender.aut("http://localhost:8891/login", "admin", "1234");;

    private static  GridBagConstraints c;
    private static  ArrayList<Product> product;
    private static JScrollPane scrollPane;
    private static JComboBox jcb;

    private static  JButton b5;
    private static  JButton b6;
    private static  JButton b7;
    private static  JButton b8;

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
          //  autentificationFrame();

        }


        private void mainMenuFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

            mainMenu = new JPanel(new GridBagLayout());
            c = new GridBagConstraints();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


            //TODO show all
            String tmp = null;
            try {
               // tmp = Sender.doGet("http://localhost:8891/api/good/fruits", token);
                tmp = Sender.doGet("http://localhost:8891/api/all", token);

            } catch (Exception e) {
                e.printStackTrace();
            }
        //    System.out.println("str "+tmp);


            // Інформаційна область
           updateJtable(parser(tmp));


            // create checkbox
            String tables= null;
            try {
                tables = Sender.doGet("http://localhost:8891/api/tables", token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String[] tab = tables.split("\n");
            ArrayList<String> tm = new ArrayList<>();
            tm.add("all");
            for(String t:tab)
                tm.add(t);

            jcb = new JComboBox(tm.toArray());

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
                groupFrame(false);
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
                groupFrame(true);
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
                JOptionPane paneOne = new JOptionPane();
                int response = JOptionPane.showConfirmDialog(mainMenu, "Do you want to delete group?","Message",JOptionPane.YES_NO_OPTION);
                if(	response  == JOptionPane.YES_OPTION) {
                    //TODO
                    JOptionPane.showMessageDialog(paneOne, "Group deleted!",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                }
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


            b5 = new JButton("Add item");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 3;
            c.gridx = 0;
            c.ipady = 20;
            b5.addActionListener(e ->{
                addItemFrame(table);
            });
            mainMenu.add(b5, c);
            b5.setFont(f1);
            b5.setVisible(false);

            b6 = new JButton("Edit item");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 3;
            c.gridx = 1;
            c.ipady = 20;
            b6.addActionListener(e ->{
                changeItemFrame(table);
            });
            mainMenu.add(b6, c);
            b6.setFont(f1);
            b6.setVisible(false);


            b7 = new JButton("Delete item");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 1;
            c.gridy = 3;
            c.gridx = 2;
            c.ipady = 20;
            b7.addActionListener(e ->{
                JOptionPane paneOne = new JOptionPane();
                int response = JOptionPane.showConfirmDialog(mainMenu, "Do you want to delete item?","Message",JOptionPane.YES_NO_OPTION);
                if(	response  == JOptionPane.YES_OPTION) {
                    //TODO
                    JOptionPane.showMessageDialog(paneOne, "Item deleted!",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            mainMenu.add(b7, c);
            b7.setFont(f1);
            b7.setVisible(false);



            b8= new JButton("Increase/Decrease quantity");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.2;
            c.gridwidth = 2;
            c.gridy = 3;
            c.gridx = 3;
            c.ipady = 20;
            b8.addActionListener(e -> {});
            mainMenu.add(b8, c);
            b8.setFont(f1);
            b8.setVisible(false);


            jcb.addItemListener(itemEvent -> {
                b5.setVisible(false);
                b6.setVisible(false);
                b7.setVisible(false);
                b8.setVisible(false);
                //TODO mfc table
                if (itemEvent.getSource() == jcb) {
                     getAndUpdateTable();
                }
                revalidate();
                repaint();

            });





            setContentPane(mainMenu);
            setVisible(true);

        }


private  void groupFrame(boolean change) {
    JPanel paneOne = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    JLabel newName = new JLabel("Group name");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.1;
    c.ipady = 20;
    paneOne.add(newName, c);
    newName.setFont(f1);

    // Поле для введення назви нових груп.
    final JTextField newGroupName = new JTextField();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 0.6;
    c.ipady = 20;
    paneOne.add(newGroupName, c);
    newGroupName.setFont(f1);

    JButton b = new JButton("Change");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 0.5;
    c.gridy = 0;
    c.gridx = 2;
    c.weightx = 0.3;
    c.gridwidth = 2;
    c.ipady = 20;
    if(change) {
        b.addActionListener(e1 -> {
            optionFrame("Group name changed");

            optionFrameErr("Group name is busy");
//
            setContentPane(mainMenu);
        });
    }else{
        b.setText("Add group");
        b.addActionListener(e1 -> {
            optionFrame("New group added");

            optionFrameErr("Group name is busy");
            //TODO
            setContentPane(mainMenu);
        });
    }

    paneOne.add(b, c);
    b.setFont(f1);
    setContentPane(paneOne);
    revalidate();
    repaint();
}


    private void changeItemFrame(JTable table) {
        JPanel paneOne = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel newName = new JLabel("Name");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(newName, c);
        newName.setFont(f1);

        // Поле для введення назви нових груп.
        final JTextField newGroupName0 = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(newGroupName0, c);
        newGroupName0.setFont(f1);
        newGroupName0.setText(table.getValueAt(table.getSelectedRow(), 1).toString());

        JLabel description = new JLabel("Description");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(description, c);
        description.setFont(f1);

        // Поле для введення назви нових груп.
        final JTextField newGroupName1 = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(newGroupName1, c);
        newGroupName1.setFont(f1);
        newGroupName1.setText(table.getValueAt(table.getSelectedRow(), 2).toString());

        JLabel producer = new JLabel("Producer");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(producer, c);
        producer.setFont(f1);

        // Поле для введення назви нових груп.
        final JTextField newGroupName2 = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(newGroupName2, c);
        newGroupName2.setFont(f1);
        newGroupName2.setText(table.getValueAt(table.getSelectedRow(), 3).toString());

        JLabel price = new JLabel("Price");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(price, c);
        price.setFont(f1);

        // Поле для введення назви нових груп.
        final JTextField newGroupName3 = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(newGroupName3, c);
        newGroupName3.setFont(f1);
        newGroupName3.setText(table.getValueAt(table.getSelectedRow(), 5).toString());

        // запускає метод зміни.
        JButton b = new JButton("Change");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 4;
        c.gridx = 0;
        c.weightx = 0.3;
        c.gridwidth = 2;
        c.ipady = 20;

        b.addActionListener(e -> {
            //TODO change elem to DB  POST
            optionFrame("Item was changed");

            optionFrameErr("You can't change item with this params");
            setContentPane(mainMenu);
        });

        paneOne.add(b, c);
        b.setFont(f1);

        setContentPane(paneOne);
        revalidate();
        repaint();
    }


    private void autentificationFrame() {
        JPanel paneOne = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel login = new JLabel("Login");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(login, c);
        login.setFont(f1);

        // Поле для введення назви нових груп.
        final JTextField newGroupName0 = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(newGroupName0, c);
        newGroupName0.setFont(f1);

        JLabel pass = new JLabel("Password");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(pass , c);
        pass.setFont(f1);

        // Поле для введення назви нових груп.
        final JTextField newGroupName1 = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(newGroupName1, c);
        newGroupName1.setFont(f1);


        // запускає метод зміни.
        JButton b = new JButton("Enter");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 2;
        c.gridx = 0;
        c.weightx = 0.3;
        c.gridwidth = 2;
        c.ipady = 20;

        //TODO autentification
        b.addActionListener(e -> {
            try{
                System.out.println("log "+newGroupName0.getText());
                System.out.println("pass " +newGroupName1.getText());
                if( newGroupName0.getText().equals("admin")&&newGroupName1.getText().equals("1234")) {
                    token = Sender.aut("http://localhost:8891/login", newGroupName0.getText(), newGroupName1.getText());
                  optionFrame("You successfully entered");

                  setContentPane(mainMenu);

                    System.out.println("token: "+token);
                }else throw new Exception();
            } catch (Exception e1) {
                optionFrameErr("Password or login is incorrect");
                newGroupName0.setText("");
                newGroupName1.setText("");
            }
        });

        paneOne.add(b, c);
        b.setFont(f1);

        setContentPane(paneOne);
        revalidate();
        repaint();
    }


    private void addItemFrame(JTable table) {
        JPanel paneOne = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel newName = new JLabel("Name");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(newName, c);
        newName.setFont(f1);

        final JTextField newNameTF = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(newNameTF, c);
        newNameTF.setFont(f1);
        newNameTF.setText("");

        JLabel description = new JLabel("Description");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(description, c);
        description.setFont(f1);

        final JTextField descriptiomTF = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(descriptiomTF, c);
        descriptiomTF.setFont(f1);
        descriptiomTF.setText("");

        JLabel producer = new JLabel("Producer");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(producer, c);
        producer.setFont(f1);

        final JTextField producerTF = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(producerTF, c);
        producerTF.setFont(f1);

        JLabel quantity = new JLabel("Quantity");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(quantity, c);
        quantity.setFont(f1);

        final JTextField quantityTF = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(quantityTF, c);
        quantityTF.setFont(f1);
        quantityTF.setText("");

        JLabel price = new JLabel("Price");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0.1;
        c.ipady = 20;
        paneOne.add(price, c);
        price.setFont(f1);


        final JTextField priceTF = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        c.weightx = 0.6;
        c.ipady = 20;
        paneOne.add(priceTF, c);
        priceTF.setFont(f1);
        priceTF.setText("");


        String tables= null;
        try {
            tables = Sender.doGet("http://localhost:8891/api/tables", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] tab = tables.split("\n");
        final JComboBox  cb = new JComboBox(tab);

        if(jcb.getSelectedItem().equals("all")){
            // create checkbox

            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1;
            c.gridy = 0;
            c.gridx = 0;
            c.weightx = 0.3;
            c.gridwidth = 2;
            c.ipady = 20;
            paneOne.add(cb, c);
            cb.setFont(f1);
        }



        JButton b = new JButton("Add item");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 6;
        c.gridx = 0;
        c.weightx = 0.3;
        c.gridwidth = 2;
        c.ipady = 20;

        b.addActionListener(e -> {
            //TODO add elem to DB  PUT
            try{

                if(newNameTF.getText().length()>0&&quantityTF.getText().length()>0&&priceTF.getText().length()>0) {

                    JSONObject jsonObject = new JSONObject();
                    if(!jcb.getSelectedItem().equals("all")){
                        jsonObject.put("group", jcb.getSelectedItem());
                    }else {
                        jsonObject.put("group", cb.getSelectedItem());
                    }
                    jsonObject.put("description", descriptiomTF.getText());
                    jsonObject.put("manufacturer", producerTF.getText());
                    jsonObject.put("naming", newNameTF.getText());
                    jsonObject.put("price", Long.parseLong(priceTF.getText()));
                    jsonObject.put("quantity", Long.parseLong(quantityTF.getText()));
                    String s =Sender.doPut("http://localhost:8891/api/good", jsonObject, token);
                    System.out.println("put"+s);
                    System.out.println("group"+jcb.getSelectedItem());
                    if(s.contains("409")) {
                        System.out.println(s.contains("409"));
                        throw new Exception();

                    }
                    optionFrame("Product was added");
                    getAndUpdateTable();
                    setContentPane(mainMenu);
                }else throw new Exception();
            } catch (Exception e1) {
                optionFrameErr("You can't add item with this params!");
                newNameTF.setText("");
                descriptiomTF.setText("");
                producerTF.setText("");
                quantityTF.setText("");
                priceTF.setText("");

            }

        });

        paneOne.add(b, c);
        b.setFont(f1);


        JButton be = new JButton("Exit");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 7;
        c.gridx = 0;
        c.weightx = 0.3;
        c.gridwidth = 2;
        c.ipady = 20;
        be.addActionListener(e-> setContentPane(mainMenu)
        );
        paneOne.add(be, c);
        be.setFont(f1);

        setContentPane(paneOne);
        revalidate();
        repaint();
    }


    private void updateJtable(ArrayList<Product> product){
        model = new StoreTableModel(product);
        table = new JTable(model);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 10;
        c.ipady = 350;
        scrollPane = new JScrollPane(table);
        mainMenu.add(scrollPane, c);
        table.setFont(f1);

        //make column invisible
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        table.getSelectionModel().addListSelectionListener(e -> {
            b5.setVisible(true);
            b6.setVisible(true);
            b7.setVisible(true);
            b8.setVisible(true);
            //System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
        });
    }

    private void optionFrame(String message){
        JOptionPane pane = new JOptionPane();
        JOptionPane.showMessageDialog(mainMenu, message,
                "Message",
                JOptionPane.INFORMATION_MESSAGE);

    }

    private void optionFrameErr(String err_message){
            new JOptionPane();
            JOptionPane.showMessageDialog(mainMenu, err_message,
                    "Error", JOptionPane.WARNING_MESSAGE);

    }


    private ArrayList<Product> parser(String s){
        String[] strArr = s.split("\n");
        product = new ArrayList<>();
        JSONParser parser = new JSONParser();
        for (int i = 0; i <strArr.length; i++) {
            JSONObject json=null;
            try {
                json = (JSONObject) parser.parse(strArr[i]);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            product.add(new Product(json));
        }
        return product;
    }

    private void getAndUpdateTable(){
        String tmp = null;
        try {
            if(!jcb.getSelectedItem().equals("all")){

                tmp = Sender.doGet("http://localhost:8891/api/good/"+ jcb.getSelectedItem(), token);
            }else{
                tmp = Sender.doGet("http://localhost:8891/api/"+ jcb.getSelectedItem(), token);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mainMenu.remove(scrollPane);
        updateJtable(parser(tmp));
    }




    public static void main(String[] args) {
        new Thread(new UI()).start();
    }

    @Override
    public void run() {
        init();
    }
}
