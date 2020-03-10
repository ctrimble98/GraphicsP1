import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputPanel extends JPanel {

    public InputPanel() {
        setLayout(new GridLayout(3, 3));
        add(new NumericTextField("x"));
        add(new NumericTextField("y"));
        add(new NumericTextField("z"));
    }

    public class NumericTextField extends JPanel {

        private JTextField valueField;

        public NumericTextField(String labelString) {
            valueField = new JTextField();

            setLayout(new GridLayout(1, 2));
            add(new JLabel(labelString));
            add(valueField);

            valueField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
//                    String value = valueField.getText();
//                    try {
//                        Long.parseLong(value);
//                    } catch (Exception ex) {
//                        if (value.length() > 1) {
//                            valueField.setText(value.substring(0, value.length() - 1));
//                        } else {
//                            valueField.setText("");
//                        }
//                    }
                    if ((e.getKeyChar() < '0' || e.getKeyChar() > '9') && e.getKeyChar() != '.') {
                        e.consume();
                    }
                }
            });
        }
    }
}
