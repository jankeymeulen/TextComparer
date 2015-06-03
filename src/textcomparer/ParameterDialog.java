/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jan
 */
public class ParameterDialog extends JDialog
{

    private JTextField dist, length, window, skip, words;
    private JCheckBox adaptive;

    Action cancelListener = new AbstractAction()
    {

        public void actionPerformed(ActionEvent actionEvent)
        {
            setVisible(false);
            dispose();
        }
    };

    Action okListener = new AbstractAction()
    {

        public void actionPerformed(ActionEvent actionEvent)
        {
            setVisible(false);
            model.setMaxDist(new Integer(dist.getText()));
            model.setMinLength(new Integer(length.getText()));
            model.setMaxWindow(new Integer(window.getText()));
            model.setMaxSkip(new Integer(skip.getText()));
            model.setMinWords(new Integer(words.getText()));
            model.setAdaptiveSkips(adaptive.isSelected());
            dispose();
        }
    };
    private final Model model;

    ParameterDialog(Model model)
    {
        super(model.getMainWindow(), "Set comparison parameters", true);
        this.setIconImage(model.getIcon().getImage());
        this.model = model;

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(7, 2, 10, 0));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.add(new JLabel("LD Distance"));
        dist = new JTextField(model.getMaxDist() + "", 5);
        contentPanel.add(dist);

        contentPanel.add(new JLabel("Word length"));
        length = new JTextField(model.getMinLength() + "", 5);
        contentPanel.add(length);

        contentPanel.add(new JLabel("Search window"));
        window = new JTextField(model.getMaxWindow() + "", 5);
        contentPanel.add(window);

        contentPanel.add(new JLabel("Word skip"));
        skip = new JTextField(model.getMaxSkip() + "", 5);
        contentPanel.add(skip);

        contentPanel.add(new JLabel("Result length"));
        words = new JTextField(model.getMinWords() + "", 5);
        contentPanel.add(words);

        contentPanel.add(new JLabel(""));
        adaptive = new JCheckBox("Adaptive skips",model.isAdaptiveSkips());
        contentPanel.add(adaptive);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(okListener);
        contentPanel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(cancelListener);
        contentPanel.add(cancelButton);

        this.setContentPane(contentPanel);
        this.pack();

        Dimension parentSize = model.getMainWindow().getSize();
        Dimension dialogSize = this.getSize();
        Point p = model.getMainWindow().getLocation();
        this.setLocation(p.x + parentSize.width / 2 - dialogSize.width / 2,
                p.y + parentSize.height / 2 - dialogSize.height / 2);
    }
}
