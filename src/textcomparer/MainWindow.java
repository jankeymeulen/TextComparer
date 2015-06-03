/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

/**
 *
 * @author jan
 */
import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame
{

    private Model model;
    private HighLightPane leftPane, rightPane;
    private long start,stop;

    MainWindow(Model model)
    {
        super();
        start = System.currentTimeMillis();
        this.setIconImage(model.getIcon().getImage());
        this.model = model;
        leftPane = model.createSrcPane();
        rightPane = model.createDstPane();
    }

    private JPanel ResultPanel()
    {

        JPanel panel = new JPanel(new GridLayout(1, 0, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JScrollPane(leftPane));
        panel.add(new JScrollPane(rightPane));

        HighLightPaneListener srcLst = new HighLightPaneListener();
        //leftPane.addMouseListener(srcLst);
        leftPane.addCaretListener(srcLst);

        HighLightPaneListener dstLst = new HighLightPaneListener();
        //rightPane.addMouseListener(dstLst);
        rightPane.addCaretListener(dstLst);

        return panel;
    }

    void start()
    {
        this.setJMenuBar(new MenuBar(model));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Text Comparer");
        this.add(ResultPanel());
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setVisible(true);
        stop = System.currentTimeMillis();
        System.err.println("Main screen created in "+(stop-start)+" ms");
    }

}
