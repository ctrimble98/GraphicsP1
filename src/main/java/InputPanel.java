import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputPanel extends JPanel {

    public InputPanel(Curves frame) {
        setLayout(new FlowLayout(FlowLayout.LEADING, 100, 5));
        NumericTextField xField = new NumericTextField("x:");
        NumericTextField yField = new NumericTextField("y:");
        NumericTextField zField = new NumericTextField("z:");

        JButton newPoint = new JButton("Add Point");
        JButton intersection = new JButton("Find Intersection");

        add(xField);
        add(yField);
        add(zField);
        add(newPoint);
        add(intersection);

        newPoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.addPoint(new Vector3(xField.getValue(), yField.getValue(), zField.getValue()));
                } catch (Exception ignored) {
                }

                xField.clear();
                yField.clear();
                zField.clear();
            }
        });

        intersection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.findRandomIntersection();
            }
        });
    }

    public class NumericTextField extends JPanel {

        private JTextField valueField;

        public NumericTextField(String labelString) {
            valueField = new JTextField();

            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            JLabel label = new JLabel(labelString);
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setPreferredSize(new Dimension(20, 20));
            add(label);
            valueField.setPreferredSize(new Dimension(100, 20));
            add(valueField);

            valueField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if ((e.getKeyChar() < '0' || e.getKeyChar() > '9')) {
                        e.consume();
                    }
                }
            });
        }

        public double getValue() {
            return Integer.parseInt(valueField.getText());
        }

        public void clear() {
            valueField.setText("");
        }
    }
}
