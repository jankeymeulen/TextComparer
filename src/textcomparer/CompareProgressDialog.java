/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jan
 */
public class CompareProgressDialog extends JDialog
{

    protected final JProgressBar bar;
    private final Model model;

    ChangeListener ProgressListener = new ChangeListener()
    {

        public void stateChanged(ChangeEvent e)
        {
            bar.setValue(model.getComparisonCurrentWord());
        }

    };
    

    CompareProgressDialog(Model model)
    {
        super(model.getMainWindow(), "Comparison Progress", true);
        this.setIconImage(model.getIcon().getImage());
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.model = model;

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2,1,10,10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.add(new JLabel("Comparison in progress..."));

        bar = new JProgressBar(0,model.getSrcText().size());
        contentPanel.add(bar);

        this.setContentPane(contentPanel);
        this.pack();

        Dimension parentSize = model.getMainWindow().getSize();
        Dimension dialogSize = this.getSize();
        Point p = model.getMainWindow().getLocation();
        this.setLocation(p.x + parentSize.width / 2 - dialogSize.width / 2,
                p.y + parentSize.height / 2 - dialogSize.height / 2);
    }
}
