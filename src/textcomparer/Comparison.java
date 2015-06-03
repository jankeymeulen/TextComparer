/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author jan
 */
public class Comparison
{

    private ArrayList<String> source;
    private ArrayList<String> dest;
    private ArrayList<Comparison> results;
    private Match match;
    private int[] workspace;
    private final int srcOffset;
    private final int dstOffset;
    private Model model;

    //empty, initial Comparison
    public Comparison(ArrayList<String> source, ArrayList<String> dest, Model model)
    {
        this(source, dest, new Match(0, 0, 0, 0, "", ""), 0, 0, model);
    }

    //Comparison as result of earlier Comparison
    public Comparison(ArrayList<String> sourceText, ArrayList<String> destText,
            Match match, int srcOffset, int dstOffset, Model model)
    {
        this.source = sourceText;
        this.dest = destText;

        this.match = match;
        this.srcOffset = srcOffset;
        this.dstOffset = dstOffset;
        this.model = model;

        results = new ArrayList();

        int smax = 0;
        for (String s : source)
        {
            if (s.length() > smax)
            {
                smax = s.length();
            }
        }
        int dmax = 0;
        for (String d : dest)
        {
            if (d.length() > dmax)
            {
                dmax = d.length();
            }
        }

        workspace = new int[(smax + 1) * (dmax + 1)];

    }

    public Match getMatch()
    {
        return match;
    }

    private int findStringMatch(int sourceIndex, int start, int end, int dist, int length)
    {
        String aS = source.get(sourceIndex);

        if (aS.length() >= length && start >= 0 && start <= dest.size()
                && end >= 0 && end <= dest.size()) //long enough and within limits?
        {
            ListIterator<String> bIt = dest.listIterator(start);

            while (bIt.hasNext() && bIt.nextIndex() < end + 1) //iterate second
            {
                String bS = bIt.next();

                if (damlevlim(aS, bS, dist + 1) <= dist) //find match
                {
                    return bIt.nextIndex() - 1;
                }
            }

        }

        return -1;
    }

    private int findCenterStringMatch(int sourceIndex, int start, int end, int dist, int length)
    {
        String s = source.get(sourceIndex);

        if (s.length() >= length && start >= 0 && start <= dest.size()
                && end >= 0 && end <= dest.size()) //long enough and within limits?
        {


            int halfWindow = (end - start) / 2;
            int center = halfWindow + start;
            int absPointer = 0;
            String d = "";

            while (absPointer <= halfWindow) //iterate window
            {
                //look forward
                if (center + absPointer < end && center + absPointer <= dest.size())
                {
                    d = dest.get(center + absPointer);
                    if (d.length() >= length && damlevlim(s, d, dist + 1) <= dist) //find match
                    {
                        return center + absPointer;
                    }
                }

                //look backward
                if (center - absPointer > start && center - absPointer >= 0)
                {
                    d = dest.get(center - absPointer);
                    if (d.length() >= length && damlevlim(s, d, dist + 1) <= dist) //find match
                    {
                        return center - absPointer;
                    }


                }

                absPointer++;

            }

        }

        return -1;
    }

    private TreeMap<Integer, Integer> findWindowMatch(int s, int d, int dist, int length, int window, int maxSkip, boolean adaptiveSkips)
    {
        // put initial results in resultMap
        TreeMap<Integer, Integer> resultMap = new TreeMap();
        resultMap.put(s, d);

        s++;
        d++; //for easier implementation
        boolean matchFound = true;

        int skips = 0;

        while (s < source.size() && matchFound)
        {
            if (source.get(s).length() >= length) //word is long enough
            {
                //search in the window
                int wD = findCenterStringMatch(s, Math.max(0, d - window), Math.min(d + window, dest.size()), dist, length);

                if (wD >= 0) //found match in window
                {
                    resultMap.put(s, wD);
                    s++;
                    d = ++wD; //again for easier implementation when using zero windows
                    //skips = 0; //reset skips
                    if(skips > 0 && adaptiveSkips)
                    {
                        skips--;
                    }
                }
                else //no match found
                {
                    if (skips < maxSkip) //possibly skip a word
                    {
                        skips++;
                        s++;
                        d++;  //for easier implementation when using zero windows
                    }
                    else
                    {
                        matchFound = false;
                    }
                }
            }
            else //ignore short word, continue;
            {
                //resultMap.put(s, d); //assume they are still part of the comparison
                s++;
                d++; //for easier implementation

            }
        }

        return resultMap;
    }

    private Comparison createResult(TreeMap<Integer, Integer> resultMap)
    {

        ArrayList<String> sourceText = new ArrayList();
        StringBuffer sourceBuf = new StringBuffer();

        for (int i = resultMap.firstKey(); i <= resultMap.lastKey(); i++)
        {
            sourceText.add(source.get(i));
            sourceBuf.append(source.get(i) + " ");
        }

        ArrayList<String> destText = new ArrayList();
        TreeSet<Integer> destSet = new TreeSet();
        destSet.addAll(resultMap.values());
        StringBuffer destBuf = new StringBuffer();

        for (int i = destSet.first(); i <= destSet.last(); i++)
        {
            destText.add(dest.get(i));
            destBuf.append(dest.get(i) + " ");
        }

        Match m = new Match(resultMap.firstKey() + srcOffset, resultMap.lastKey() + srcOffset,
                destSet.first() + dstOffset, destSet.last() + dstOffset, sourceBuf.toString(), destBuf.toString());

        return new Comparison(sourceText, destText, m, resultMap.firstKey(), destSet.first(), model);
    }

    //find matches in the whole source text
    public ArrayList<Comparison> findMatches(int dist, int length, int window, int skip, int words, boolean adaptiveSkips)
    {
        int s = 0; //init source index
        int d = 0; //init dest index

        //resulting comparison will be saved here
        results = new ArrayList();

        long startTime = System.currentTimeMillis();

        while (s < source.size()) //iterate source
        {
            if (source.get(s).length() >= length) //source word is long enough
            {

                d = 0; //reset position in dst text
                boolean keepLooking = true;

                while (d >= 0 && d < dest.size() && keepLooking) //iterate dest
                {
                    d = findStringMatch(s, d, dest.size(), dist, length); //find first dMatch

                    if (d >= 0) //found first match
                    {
                        //find whole match
                        TreeMap<Integer, Integer> resultMap = findWindowMatch(s, d, dist, length, window, skip, adaptiveSkips);

                        if (resultMap.size() >= words) //enough words in match
                        {
                            results.add(createResult(resultMap));
                            s = resultMap.lastKey() + 1; //go to first position after match
                            keepLooking = false; //stop looking
                        }
                        else // not enough words, increment d to look further
                        {
                            TreeSet<Integer> destSet = new TreeSet();
                            destSet.addAll(resultMap.values());
                            d = Math.max(destSet.last(), d + 1);
                        }

                    }
                    else
                    {
                        //no first match found
                    }



                }
                if (d < 0 || d >= dest.size())
                {
                    s++; //found nothing, go further
                    //System.out.print(s + " ");
                }
                if (!keepLooking)
                {
                    System.err.println("Found: " + results.get(results.size() - 1).getMatch().getSource()); //found something,
                }
            }
            else //word too short
            {
                s++;
            }

            if (s % 100 == 0)
            {
                System.err.printf("%3.2f %% complete\n", ((s + 0.0) / (source.size() + 0.0) * 100));
                if (model != null)
                {
                    model.setComparisonCurrentWord(s);
                }
            }

        }

        long endTime = System.currentTimeMillis();

        System.err.printf("Done in %.3f s\n", (endTime - startTime + 0.0) / 1000);

        return results;
    }

    @Override
    public String toString()
    {
        StringBuffer b = new StringBuffer();


        for (String s : source)
        {
            b.append(s + " ");
        }
        b.append("\nmatches:\n");


        for (String d : dest)
        {
            b.append(d + " ");
        }
        return b.toString();

    }

    private int damlevlim(String s, String t, int limit)
    {
        int lenS = s.length();
        int lenT = t.length();


        if (lenS < lenT)
        {
            if (lenT - lenS >= limit)
            {
                return limit;
            }
        }
        else if (lenT < lenS)
        {
            if (lenS - lenT >= limit)
            {
                return limit;
            }
        }
        int lenS1 = lenS + 1;
        int lenT1 = lenT + 1;
        if (lenS1 == 1)
        {
            return (lenT < limit) ? lenT : limit;
        }
        if (lenT1 == 1)
        {
            return (lenS < limit) ? lenS : limit;
        }

        int dlIndex = 0;
        int sPrevIndex = 0, tPrevIndex = 0, rowBefore = 0, min = 0, tmp = 0, best = 0, cost = 0;
        int tri = lenS1 + 2;
        // start row with constant
        dlIndex = 0;
        for (tmp = 0; tmp < lenT1; tmp++)
        {
            workspace[dlIndex] = tmp;
            dlIndex += lenS1;
        }
        for (int sIndex = 0; sIndex < lenS; sIndex++)
        {
            dlIndex = sIndex + 1;
            workspace[dlIndex] = dlIndex; // start column with constant
            best = limit;
            for (int tIndex = 0; tIndex < lenT; tIndex++)
            {
                rowBefore = dlIndex;
                dlIndex += lenS1;
                //deletion
                min = workspace[rowBefore] + 1;
                // insertion
                tmp = workspace[dlIndex - 1] + 1;
                if (tmp < min)
                {
                    min = tmp;
                }
                cost = 1;
                if (s.charAt(sIndex) == t.charAt(tIndex))
                {
                    cost = 0;
                }
                if (sIndex > 0 && tIndex > 0)
                {
                    if (s.charAt(sIndex) == t.charAt(tPrevIndex) && s.charAt(sPrevIndex) == t.charAt(tIndex))
                    {
                        tmp = workspace[rowBefore - tri] + cost;
                        // transposition
                        if (tmp < min)
                        {
                            min = tmp;
                        }
                    }
                }
                // substitution
                tmp = workspace[rowBefore - 1] + cost;
                if (tmp < min)
                {
                    min = tmp;
                }
                workspace[dlIndex] = min;
                if (min < best)
                {
                    best = min;
                }
                tPrevIndex = tIndex;
            }
            if (best >= limit)
            {
                return limit;
            }
            sPrevIndex = sIndex;
        }
        if (workspace[dlIndex] >= limit)
        {
            return limit;
        }
        else
        {
            return workspace[dlIndex];
        }
    }
}


