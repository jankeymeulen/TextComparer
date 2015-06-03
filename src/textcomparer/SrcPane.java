/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textcomparer;

import java.util.TreeMap;

/**
 *
 * @author jan
 */
public class SrcPane extends HighLightPane {

    public SrcPane(Model model)
    {
        super(model);
    }

    @Override
    int getTextSize()
    {
        return model.getSrcText().size();
    }

    @Override
    void setLink(int w, int c)
    {
        model.addSrcLink(w, c);
    }

    @Override
    String getWord(int w)
    {
        return model.getSrcText().get(w);
    }

    @Override
    TreeMap<Integer, Integer> getHighLights()
    {
        return model.getSrcWordMatches();
    }

    @Override
    Integer getCharPos(int w)
    {
        return model.getSrcChar(w);
    }

    @Override
    int getSelectionCharStart()
    {
        return model.getSrcSelectionCharStart();
    }

    @Override
    int getSelectionCharEnd()
    {
        return model.getSrcSelectionCharEnd();
    }

    @Override
    int getSelectionWordStart()
    {
        return model.getSrcSelectionWordStart();
    }

    @Override
    int getSelectionWordEnd()
    {
        return model.getSrcSelectionWordEnd();
    }

    @Override
    void wordClicked()
    {
        //selectWordAction.actionPerformed(null); //select word after click
        model.srcClicked(this.getSelectionStart());
    }

}
