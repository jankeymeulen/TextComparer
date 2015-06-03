/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import e.ptextarea.PCoordinates;
import e.ptextarea.PTextArea;
import e.ptextarea.SelectionHighlight;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author jan
 */
public class ColorHighlight extends SelectionHighlight

{

    private final Color color;
    private final String name;
   
    ColorHighlight(PTextArea textArea, int startIndex, int endIndex, Color color, String name)
    {
        super(textArea, startIndex, endIndex);
        this.color = color;
        this.name = name;
    }

    @Override
    protected void paintHighlight(Graphics2D g, PCoordinates start, PCoordinates end, Insets insets, int lineHeight, int firstLineIndex, int lastLineIndex)
    {
        if (isEmpty())
        {
            return;
        }

        Point startPt = textArea.getViewCoordinates(start);
        Point endPt = textArea.getViewCoordinates(end);
        Color oldColor = g.getColor();
        int y = textArea.getLineTop(firstLineIndex);
        for (int i = firstLineIndex; i <= lastLineIndex; ++i)
        {
            int xStart = (i == start.getLineIndex()) ? startPt.x : insets.left;
            int xEnd = (i == end.getLineIndex()) ? endPt.x : textArea.getWidth() - insets.right;
            if (xStart == xEnd)
            {
                // If there's nothing to highlight on a line, avoid painting
                // the border. If it's painted, it'll be visible as a single
                // pixel wide, one line high tail at the bottom left corner of
                // the highlight. Arguably this is useful because it shows that
                // the newline is part of the selection, but no other text
                // component highlights like this.
                continue;
            }
            g.setColor(color);
            g.fill(new Rectangle(xStart, y, xEnd - xStart, lineHeight));
            int yBottom = y + lineHeight - 1;

            g.setColor(color.darker());
            // Draw this shape at the top of the selection (C will be
            // zero-length for a single-line selection):
            //        _____________________
            // _______|B         A
            //    C
            if (i == start.getLineIndex())
            {
                if (xStart > 0)
                {
                    // B
                    g.drawLine(xStart, y, xStart, yBottom);
                }
                // A
                g.drawLine(xStart, y, xEnd, y);
            }
            else if (i == start.getLineIndex() + 1)
            {
                final int Bx = Math.min(xEnd, startPt.x);
                if (Bx > insets.left)
                {
                    // C
                    g.drawLine(insets.left, y, Bx, y);
                }
            }

            // Draw this shape at the bottom of the selection (E will
            // be zero-length for a single-line selection):
            //            _________________
            // ___________|D     E
            //      F
            if (i == end.getLineIndex())
            {
                if (xEnd < textArea.getWidth() - insets.right)
                {
                    // D
                    g.drawLine(xEnd, y, xEnd, yBottom);
                }
                // F
                g.drawLine(xStart, yBottom, xEnd, yBottom);
            }
            else if (i == end.getLineIndex() - 1)
            {
                // E
                g.drawLine(Math.max(endPt.x, xStart), yBottom, xEnd, yBottom);
            }

            // Draw vertical lines at the left and right edges of the
            // selection; we only really need these if we have non-zero
            // margins, but they're important then.
            g.drawLine(xStart, y, xStart, yBottom);
            g.drawLine(xEnd, y, xEnd, yBottom);

            y += lineHeight;
        }
        g.setColor(oldColor);
    }

    @Override
    public String getHighlighterName()
    {
        return name;
    }
}
