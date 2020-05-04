package me.asu.pdf;

import java.awt.Color;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ParagraphStyle
{

    float left, top, width, height;
    float padLeft = 0, padTop = 0, padRight = 0, padBottom = 0;
    PDFont              font     = PDType1Font.TIMES_ROMAN;
    Color               color    = Color.BLACK;
    int                 fontSize = 10;
    HorizontalAlignment align    = HorizontalAlignment.LEFT;

    public PDFont getFont()
    {
        return this.font;
    }

    public int getFontSize()
    {
        return this.fontSize;
    }

    public Color getColor()
    {
        return this.color;
    }

    public HorizontalAlignment getAlign()
    {
        return this.align;
    }


    public float getLeft()
    {
        return left;
    }

    public ParagraphStyle setLeft(float left)
    {
        this.left = left;
        return this;
    }

    public float getTop()
    {
        return top;
    }

    public ParagraphStyle setTop(float top)
    {
        this.top = top;
        return this;
    }

    public float getWidth()
    {
        return width;
    }

    public ParagraphStyle setWidth(float width)
    {
        this.width = width;
        return this;
    }

    public float getHeight()
    {
        return height;
    }

    public ParagraphStyle setHeight(float height)
    {
        this.height = height;
        return this;
    }


    public float padLeft()
    {
        return padLeft;
    }

    public ParagraphStyle padLeft(float padLeft)
    {
        this.padLeft = padLeft;
        return this;
    }

    public float padTop()
    {
        return padTop;
    }

    public ParagraphStyle padTop(float padTop)
    {
        this.padTop = padTop;
        return this;
    }

    public float padRight()
    {
        return padRight;
    }

    public ParagraphStyle padRight(float padRight)
    {
        this.padRight = padRight;
        return this;
    }

    public float padBottom()
    {
        return padBottom;
    }

    public ParagraphStyle padBottom(float padBottom)
    {
        this.padBottom = padBottom;
        return this;
    }


}
