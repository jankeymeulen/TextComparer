/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import e.ptextarea.PCaretListener;
import e.ptextarea.PTextArea;
import java.awt.event.*;

/**
 *
 * @author jan
 */
public class HighLightPaneListener implements PCaretListener
{

    public void caretMoved(PTextArea textArea, int selectionStart, int selectionEnd)
    {
        ((HighLightPane)textArea).wordClicked();
    }
}
