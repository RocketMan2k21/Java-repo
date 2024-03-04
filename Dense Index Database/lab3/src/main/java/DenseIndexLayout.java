
import javax.swing.*;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DenseIndexLayout {
    private JPanel panel1;
    private JButton insertButton;
    private JTextField insert_keyField;
    private JButton searchButton;
    private JTextPane textPane1;
    private JTextField search_keyField;
    private JTextField insert_valueField;
    private JTextField edit_keyField;
    private JTextField edit_valueField;
    private JTextField delete_keyField;
    private JButton editButton;
    private JButton DeleteButton;
    private JButton generateBtn;
    private DenseIndexDatabase database;

    public DenseIndexLayout() {
        database = new DenseIndexDatabase();
        setupTextPane();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int key = 0;
                try {
                    if(isValidString(search_keyField.getText()))
                        key = Integer.parseInt(search_keyField.getText().trim());
                    else
                        throw new NumberFormatException();
                    Record record = database.getRecordByKey(key);
                    textPane1.setText(record.toString());
                } catch (NumberFormatException ex) {
                    textPane1.setText("Invalid input for search key");
                } catch (RuntimeException ex) {
                    textPane1.setText("Record with key " + key + " is not found");
                }
            }
        });
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DenseIndexDatabase database1 = new DenseIndexDatabase("generatedIndex.bin", "generatedData.bin");
                int comparisons = 0;
                try {
                    comparisons = database1.fillDatabase(999);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                textPane1.setText("Database was filled successfully, average amount of comparisons: " +
                        comparisons);
            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int key =0;
                int value = 0;
                try{
                    if(isValidString(insert_keyField.getText()))
                      key = Integer.parseInt(insert_keyField.getText().trim());
                    else
                        throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    textPane1.setText("Invalid input for key");
                    return;
                }
                try{
                    if(isValidString(insert_valueField.getText()))
                        value = Integer.parseInt(insert_valueField.getText().trim());
                    else
                        throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    textPane1.setText("Invalid input for value");
                    return;
                }
                try {

                   database.insertRecord(new Record(key, value));
                } catch (IOException ex) {
                    textPane1.setText("Error inserting a record");
                    return;
                }catch (RuntimeException ex){
                    textPane1.setText("Record with key " + key + " already exists");
                    return;
                }
                textPane1.setText("New record is successfully saved");
            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int key =0;
                int value = 0;
                try{
                    if(isValidString(edit_keyField.getText().trim()))
                        key = Integer.parseInt(edit_keyField.getText().trim());
                    else{
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException ex){
                    textPane1.setText("Invalid input for key");
                    return;
                }
                try{
                    if(isValidString(edit_valueField.getText().trim()))
                      value = Integer.parseInt(edit_valueField.getText().trim());
                    else{
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException ex){
                    textPane1.setText("Invalid input for value");
                    return;
                }
                try {
                    database.editRecordByKey(key, value);
                } catch (IOException ex) {
                    textPane1.setText("Error saving a record");
                    return;
                }catch (RuntimeException ex){
                    textPane1.setText("Record with key: " + key + " doesn't exist" );
                }
                textPane1.setText("Record's value is successfully edited");
            }


        });

        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int key = 0;
                try {
                    if(isValidString(delete_keyField.getText().trim()))
                        key = Integer.parseInt(delete_keyField.getText().trim());
                    else
                        throw new NumberFormatException();
                    Record record = database.deleteRecordByKey(key);
                    textPane1.setText(record.toString());
                } catch (NumberFormatException ex) {
                    textPane1.setText("Invalid input for search key");
                    return;
                } catch (RuntimeException ex) {
                    textPane1.setText("Record with key " + key + " is not found");
                    return;
                } catch (IOException ex) {
                    textPane1.setText(ex.getMessage());
                    return;
                }
                textPane1.setText("Record with key: " + key + " deleted successfully" );
            }
        });
    }
    private void setupTextPane() {
        StyledDocument doc = textPane1.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    public void startForm(){
        JFrame jFrame  = new JFrame("Dense Index");
        jFrame.setContentPane(new DenseIndexLayout().panel1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static boolean isValidInt(String input) {
        try {
            // Try to parse the input as an integer
            int intValue = Integer.parseInt(input);

            // Check if the parsed value is equal to the original string (to handle cases like "01")
            return Integer.toString(intValue).equals(input);
        } catch (NumberFormatException e) {
            // Parsing failed, input is not a valid integer
            return false;
        }
    }

    public static boolean isValidString(String value) {
        // Check if the string contains only numeric characters and doesn't start with a leading zero
        return value.matches("[1-9]\\d*") && Integer.parseInt(value) > -1;
    }




}
