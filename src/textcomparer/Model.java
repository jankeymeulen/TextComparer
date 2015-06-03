/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jan
 */
public class Model implements ChangeListener
{
    //source resp. destination texts, arraylist of words

    private ArrayList<String> srcText;
    private ArrayList<String> dstText;
    //key: begin of match, value: end, both expressed as word positions in resp. texts
    private TreeMap<Integer, Integer> srcWordMatches;
    private TreeMap<Integer, Integer> dstWordMatches;
    //mapping between begin of word and character and vice versa, filled in by TextArea
    private TreeMap<Integer, Integer> srcWordMap;
    private TreeMap<Integer, Integer> srcCharMap;
    private TreeMap<Integer, Integer> dstWordMap;
    private TreeMap<Integer, Integer> dstCharMap;
    //mapping between matches in source and destination texts, again word positions
    private TreeMap<Integer, Integer> srcDstWordMatches, dstSrcWordMatches;
    //paramaters, both current as the ones during the last full comparison
    private int maxDist;
    private int minLength;
    private int maxWindow;
    private int maxSkip;
    private int minWords;
    private boolean adaptiveSkips;
    private int compMaxDist;
    private int compMinLength;
    private int compMaxWindow;
    private int compMaxSkip;
    private int compMinWords;
    private boolean compAdaptiveSkips;
    //filenames of the respective texts
    private File srcFile, dstFile;
    //should files be normalized when read
    private boolean normalize;
    //current selection in both words and chars, in both source and dest TextArea
    private int srcSelectionWordStart, dstSelectionWordStart;
    private int srcSelectionWordEnd, dstSelectionWordEnd;
    private int srcSelectionCharStart, dstSelectionCharStart;
    private int srcSelectionCharEnd, dstSelectionCharEnd;
    //speaks for itself
    private MainWindow mainWindow;
    private SrcPane srcPane;
    private DstPane dstPane;
    private CompareProgressDialog compareProgress;
    private ArrayList<ChangeListener> changeListenerList;
    private ArrayList<SelectionListener> selectionListenerList;
    private ImageIcon icon;
    //holds the last full comparison
    private ArrayList<Comparison> fullComparisons;
    //holds the current matches
    private ArrayList<Match> currentMatches;
    private int comparisonCurrentWord;

    public Model()
    {
        this.srcText = new ArrayList<String>();
        this.dstText = new ArrayList<String>();
        srcWordMap = new TreeMap();
        srcCharMap = new TreeMap();
        dstWordMap = new TreeMap();
        dstCharMap = new TreeMap();
        normalize = true;

        icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));

        clearMatches();
    }

    private void clearMatches()
    {
        srcWordMatches = new TreeMap<Integer, Integer>();
        dstWordMatches = new TreeMap<Integer, Integer>();
        srcDstWordMatches = new TreeMap<Integer, Integer>();
        dstSrcWordMatches = new TreeMap<Integer, Integer>();
        this.setSrcSelection(-1, -1);
        this.setDstSelection(-1, -1);
    }

    public void setMatches(ArrayList<Match> matches)
    {

        srcWordMatches = new TreeMap<Integer, Integer>();
        dstWordMatches = new TreeMap<Integer, Integer>();
        srcDstWordMatches = new TreeMap<Integer, Integer>();
        dstSrcWordMatches = new TreeMap<Integer, Integer>();

        if (matches != null)
        {
            System.err.println("Creating model match list");
            for (Match m : matches)
            {
                srcWordMatches.put(m.getSrcStart(), m.getSrcEnd());
                dstWordMatches.put(m.getDstStart(), m.getDstEnd());
                srcDstWordMatches.put(m.getSrcStart(), m.getDstStart());
                dstSrcWordMatches.put(m.getDstStart(), m.getSrcStart());
            }

            currentMatches = matches;
        }
        else
        {
            System.err.println("Creating empty model match list");
            srcWordMatches.put(0, 0);
            dstWordMatches.put(0, 0);
            srcDstWordMatches.put(0, 0);
            dstSrcWordMatches.put(0, 0);

        }

        setSrcSelection(-1, -1);
        setDstSelection(-1, -1);
    }

    public MainWindow createMainWindow()
    {
        mainWindow = new MainWindow(this);
        return mainWindow;
    }

    public MainWindow getMainWindow()
    {
        return mainWindow;
    }

    public void addSrcLink(int w, int c)
    {
        srcWordMap.put(w, c);
        srcCharMap.put(c, w);
    }

    public Integer getSrcWord(int c)
    {
        return srcCharMap.floorEntry(c).getValue();
    }

    public Integer getSrcChar(int w)
    {
        return srcWordMap.get(w);
    }

    public void addDstLink(int w, int c)
    {
        dstWordMap.put(w, c);
        dstCharMap.put(c, w);
    }

    public Integer getDstWord(int c)
    {
        return dstCharMap.floorEntry(c).getValue();
    }

    public Integer getDstChar(int w)
    {
        return dstWordMap.get(w);
    }

    public TreeMap<Integer, Integer> getDstWordMatches()
    {
        return dstWordMatches;
    }

    public TreeMap<Integer, Integer> getSrcWordMatches()
    {
        return srcWordMatches;
    }

    public ArrayList<String> getSrcText()
    {
        return srcText;
    }

    public ArrayList<String> getDstText()
    {
        return dstText;
    }

    public void stateChanged(ChangeEvent e)
    {
    }

    public int getCompMaxDist()
    {
        return compMaxDist;
    }

    public int getCompMaxSkip()
    {
        return compMaxSkip;
    }

    public int getCompMaxWindow()
    {
        return compMaxWindow;
    }

    public int getCompMinLength()
    {
        return compMinLength;
    }

    public int getCompMinWords()
    {
        return compMinWords;
    }

    public boolean isCompAdaptiveSkips() {
        return compAdaptiveSkips;
    }

    public void setCompAdaptiveSkips(boolean compAdaptiveSkips) {
        this.compAdaptiveSkips = compAdaptiveSkips;
    }

    public int getMaxDist()
    {
        return maxDist;
    }

    public int getMaxSkip()
    {
        return maxSkip;
    }

    public int getMaxWindow()
    {
        return maxWindow;
    }

    public int getMinLength()
    {
        return minLength;
    }

    public int getMinWords()
    {
        return minWords;
    }

    public void setMaxDist(int maxDist)
    {
        this.maxDist = maxDist;
    }

    public void setMaxSkip(int maxSkip)
    {
        this.maxSkip = maxSkip;
    }

    public void setMaxWindow(int maxWindow)
    {
        this.maxWindow = maxWindow;
    }

    public void setMinLength(int minLength)
    {
        this.minLength = minLength;
    }

    public void setMinWords(int minWords)
    {
        this.minWords = minWords;
    }
    
    public boolean isAdaptiveSkips() {
        return adaptiveSkips;
    }

    public void setAdaptiveSkips(boolean adaptiveSkips) {
        this.adaptiveSkips = adaptiveSkips;
    }

    public File getDstFile()
    {
        return dstFile;
    }

    public void setDstFile(File dstFile)
    {
        this.dstFile = dstFile;
        TextIO r = new TextIO(normalize);
        try
        {
            r.openFile(dstFile);
        } catch (IOException ex)
        {
            JOptionPane.showMessageDialog(mainWindow, "Could not open destination file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dstText = r.getText();
        clearMatches();
        dstWordMap = new TreeMap();
        dstCharMap = new TreeMap();
        dstPane.updateText();

    }

    public File getSrcFile()
    {
        return srcFile;
    }

    public void setSrcFile(File srcFile)
    {
        this.srcFile = srcFile;
        TextIO r = new TextIO(normalize);
        try
        {
            r.openFile(srcFile);
        } catch (IOException ex)
        {
            JOptionPane.showMessageDialog(mainWindow, "Could not open source file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        srcText = r.getText();
        clearMatches();
        srcWordMap = new TreeMap();
        srcCharMap = new TreeMap();
        srcPane.updateText();

    }

    public int getDstSelectionWordEnd()
    {
        return dstSelectionWordEnd;
    }

    public void setDstSelectionWordEnd(int dstSelectionWordEnd)
    {
        this.dstSelectionWordEnd = dstSelectionWordEnd;
    }

    public int getDstSelectionWordStart()
    {
        return dstSelectionWordStart;
    }

    public void setDstSelectionWordStart(int dstSelectionWordStart)
    {
        this.dstSelectionWordStart = dstSelectionWordStart;
    }

    public int getSrcSelectionWordEnd()
    {
        return srcSelectionWordEnd;
    }

    public void setSrcSelectionWordEnd(int srcSelectionWordEnd)
    {
        this.srcSelectionWordEnd = srcSelectionWordEnd;
    }

    public int getSrcSelectionWordStart()
    {
        return srcSelectionWordStart;
    }

    public void setSrcSelectionWordStart(int srcSelectionWordStart)
    {
        this.srcSelectionWordStart = srcSelectionWordStart;
    }

    public ImageIcon getIcon()
    {
        return icon;
    }

    public synchronized void addChangeListener(
            javax.swing.event.ChangeListener listener)
    {
        if (changeListenerList == null)
        {
            changeListenerList = new java.util.ArrayList<ChangeListener>();
        }
        changeListenerList.add(listener);
    }

    public synchronized void removeChangeListener(
            javax.swing.event.ChangeListener listener)
    {
        if (changeListenerList != null)
        {
            changeListenerList.remove(listener);
        }
    }

    private void fireStateChanged()
    {
        java.util.ArrayList list;
        synchronized (this)
        {
            if (changeListenerList == null)
            {
                return;
            }
            list = (java.util.ArrayList<ChangeListener>) changeListenerList.clone();
        }
        for (int i = 0; i < list.size(); i++)
        {
            ((ChangeListener) list.get(i)).stateChanged(null);
        }
    }

    public int getSrcMatch(int w)
    {
        Integer start = srcWordMatches.floorKey(w);
        if (start != null && srcWordMatches.get(start) >= w)
        {
            return start;
        }
        else
        {
            return -1;
        }
    }

    public int getDstMatch(int w)
    {
        Integer start = dstWordMatches.floorKey(w);
        if (start != null && dstWordMatches.get(start) >= w)
        {
            return start;
        }
        else
        {
            return -1;
        }
    }

    public void srcClicked(int start)
    {
        //offset inherent to PTextArea
        //start++;

        System.err.println("Selected " + srcText.get(getSrcWord(start))+", "+start);

        if (getSrcWord(start) != null) //vinden we de klik?
        {
            if (getSrcMatch(getSrcWord(start)) != -1) //deel van een match?
            {
                setSrcSelection(getSrcMatch(getSrcWord(start)),
                        srcWordMatches.get(getSrcMatch(getSrcWord(start))));

                setDstSelection(srcDstWordMatches.get(srcSelectionWordStart),
                        dstWordMatches.get(srcDstWordMatches.get(srcSelectionWordStart)));
            }
            else
            {
                setSrcSelection(-1, -1);
                setDstSelection(-1, -1);
            }
        }
        else
        {
            setSrcSelection(-1, -1);
            setDstSelection(-1, -1);
        }

        fireSelectionChanged();

    }

    void dstClicked(int start)
    {
        //offset inherent to PTextArea
        //start++;

        System.err.println("Selected " + dstText.get(getDstWord(start))+", "+start);

        if (getDstWord(start) != null) //vinden we de klik?
        {
            if (getDstMatch(getDstWord(start)) != -1) //deel van een match?
            {
                setDstSelection(getDstMatch(getDstWord(start)),
                        dstWordMatches.get(getDstMatch(getDstWord(start))));

                setSrcSelection(dstSrcWordMatches.get(dstSelectionWordStart),
                        srcWordMatches.get(dstSrcWordMatches.get(dstSelectionWordStart)));
            }
            else
            {
                setSrcSelection(-1, -1);
                setDstSelection(-1, -1);
            }
        }
        else
        {
            setSrcSelection(-1, -1);
            setDstSelection(-1, -1);
        }

        fireSelectionChanged();

    }

    public void setSrcSelection(int sW, int eW)
    {
        srcSelectionWordStart = sW;
        srcSelectionWordEnd = eW;
        if (srcSelectionWordStart == -1 || srcSelectionWordEnd == -1)
        {
            srcSelectionCharStart = -1;
            srcSelectionCharEnd = -1;
        }
        else
        {
            srcSelectionCharStart = getSrcChar(srcSelectionWordStart);
            srcSelectionCharEnd = getSrcChar(srcSelectionWordEnd)
                    + srcText.get(srcSelectionWordEnd).length();
        }
        //fireSelectionChanged();
    }

    public void setDstSelection(int sW, int eW)
    {
        dstSelectionWordStart = sW;
        dstSelectionWordEnd = eW;
        if (dstSelectionWordStart == -1 || dstSelectionWordEnd == -1)
        {
            dstSelectionCharStart = -1;
            dstSelectionCharEnd = -1;
        }
        else
        {
            dstSelectionCharStart = getDstChar(dstSelectionWordStart);
            dstSelectionCharEnd = getDstChar(dstSelectionWordEnd)
                    + dstText.get(dstSelectionWordEnd).length();
        }
        //fireSelectionChanged();
    }

    int getSrcSelectionCharStart()
    {
        return srcSelectionCharStart;
    }

    int getSrcSelectionCharEnd()
    {
        return srcSelectionCharEnd;
    }

    int getDstSelectionCharStart()
    {
        return dstSelectionCharStart;
    }

    int getDstSelectionCharEnd()
    {
        return dstSelectionCharEnd;
    }

    HighLightPane createSrcPane()
    {
        srcPane = new SrcPane(this);
        return srcPane;
    }

    HighLightPane createDstPane()
    {
        dstPane = new DstPane(this);
        return dstPane;

    }

    void doNewCompare()
    {
        Comparison root = new Comparison(
                srcText, dstText, this);
//
////        ArrayList<String> first = new ArrayList();
////        first.addAll(text1.subList(0, (text1.size()/2)+100));
////        ArrayList<String> second = new ArrayList();
////        second.addAll(text1.subList((text1.size()/2)-100, text1.size()));
////
////        CompareThread t1 = new CompareThread(first, text2, dist, length, window, skip, words);
////        CompareThread t2 = new CompareThread(second, text2, dist, length, window, skip, words);
////
////        t1.start();
////        t2.start();

        CompareThread t = new CompareThread(this, root);
        t.start();

        //comparisonProgress = new ProgressMonitor(mainWindow,"Doing comparison","",0,srcText.size());
        compareProgress = new CompareProgressDialog(this);
        compareProgress.setVisible(true);
    }

    public void setResults(ArrayList<Comparison> results)
    {

        fullComparisons = results;

        ArrayList<Match> matches = new ArrayList();

        for (Comparison c : fullComparisons)
        {
            matches.add(c.getMatch());
        }

        compMaxDist = maxDist;
        compMinLength = minLength;
        compMaxWindow = maxWindow;
        compMaxSkip = maxSkip;
        compMinWords = minWords;

        setMatches(matches);
        fireStateChanged();

        compareProgress.setVisible(false);
        compareProgress.dispose();
    }

    void refineCompare()
    {
        ArrayList<Comparison> subResults = new ArrayList();


        for (Comparison c : fullComparisons)
        {

            subResults.addAll(c.findMatches(maxDist, minLength, maxWindow, maxSkip, minWords,adaptiveSkips));
        }

        SortedSet<Match> matchSet = new TreeSet();

        for (Comparison c : subResults)
        {
            matchSet.add(c.getMatch());
        }

        ArrayList<Match> matches = new ArrayList();
        matches.addAll(matchSet);

        setMatches(matches);
        fireStateChanged();


    }

    void resetCompare()
    {

        ArrayList<Match> matches = new ArrayList();

        for (Comparison c : fullComparisons)
        {
            matches.add(c.getMatch());
        }

        maxDist = compMaxDist;
        minLength = compMinLength;
        maxWindow = compMaxWindow;
        maxSkip = compMaxSkip;
        minWords = compMinWords;

        setMatches(matches);
        fireStateChanged();
    }

    public void saveMatches(File saveFile)
    {
        TextIO t = new TextIO(normalize);
        StringBuffer b = new StringBuffer();

        for (Match m : currentMatches)
        {
            b.append(m.getSrcStart() + ";" + m.getSource() + ";"
                    + m.getDstStart() + ";" + m.getDest() + "\n");
        }
        try
        {
            t.saveFile(saveFile, b.toString());
        } catch (IOException ex)
        {
            JOptionPane.showMessageDialog(mainWindow, "Could not write file.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void setComparisonCurrentWord(int w)
    {
        comparisonCurrentWord = w;
        compareProgress.ProgressListener.stateChanged(null);
    }

    public int getComparisonCurrentWord()
    {
        return comparisonCurrentWord;
    }

    public synchronized void addSelectionListener(
            SelectionListener listener)
    {
        if (selectionListenerList == null)
        {
            selectionListenerList = new java.util.ArrayList<SelectionListener>();
        }
        selectionListenerList.add(listener);
    }

    public synchronized void removeSelectionListener(
            SelectionListener listener)
    {
        if (selectionListenerList != null)
        {
            selectionListenerList.remove(listener);
        }
    }

    private void fireSelectionChanged()
    {
        java.util.ArrayList list;
        synchronized (this)
        {
            if (selectionListenerList == null)
            {
                return;
            }
            list = (java.util.ArrayList<SelectionListener>) selectionListenerList.clone();
        }
        for (int i = 0; i < list.size(); i++)
        {
            ((SelectionListener) list.get(i)).selectionChanged();
        }
    }
}
