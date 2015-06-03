/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import e.ptextarea.PTextArea;
import e.ptextarea.PHighlight;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.util.TreeMap;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jan
 */
public abstract class HighLightPane extends PTextArea implements SelectionListener, ChangeListener
{

    private static final Color SELECTED = new Color(1f, 0f, 0f, 0.15f);
    private static final Color SELECTED_MATCH = new Color(255, 255, 100);
    private static final Color MATCH = new Color(200, 200, 255);
    protected Model model;

    public HighLightPane(Model model)
    {
        this.model = model;
        this.setEditable(false);
     
        this.setFont(new Font("Bitstream", Font.PLAIN, 12));

        model.addSelectionListener(this);
        model.addChangeListener(this);
    }

    abstract int getTextSize();

    abstract void setLink(int w, int c);

    abstract String getWord(int w);

    abstract TreeMap<Integer, Integer> getHighLights();

    abstract Integer getCharPos(int w);

    abstract int getSelectionCharStart();

    abstract int getSelectionCharEnd();

    abstract int getSelectionWordStart();

    abstract int getSelectionWordEnd();

    abstract void wordClicked();

    public void updateText()
    {
        int length = 0;

        StringBuffer b = new StringBuffer();

        for (int i = 0; i < getTextSize(); i++)
        {
            setLink(i, length);
            b.append(getWord(i) + " ");
            length += getWord(i).length() + 1;
        }

        this.setText(b);

        highlightMatches();
    }

    private void highlightMatches()
    {

        System.err.println("Highlighting " + getHighLights().size() + " matches.");

        this.removeHighlights("Match", 0, getText().length());

        for (Integer s : getHighLights().keySet())
        {
            PHighlight l = new ColorHighlight(this, Math.max(getCharPos(s)-1,0),
                    getCharPos(getHighLights().get(s)) + this.getWord(getHighLights().get(s)).length(),
                    MATCH, "Match");
            this.addHighlight(l);
        }

    }

    public void highlightSelection(int s, int e)
    {

        this.removeHighlights("SelectedMatch", 0, getText().length());

        if (s >= 0 && e <= this.getText().length())
        {
            System.err.println("Highlighting " + s + " to " + e);
            PHighlight l = new ColorHighlight(this, Math.max(s-1,0), e, SELECTED_MATCH, "SelectedMatch");
            addHighlight(l);
            ensureVisibilityOfOffset(s);
        }

    }

    public void stateChanged(ChangeEvent e)
    {
        highlightMatches();
    }

    public void selectionChanged()
    {
        highlightSelection(getSelectionCharStart(), getSelectionCharEnd());
    }
}
