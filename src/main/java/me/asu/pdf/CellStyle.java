package me.asu.pdf;

import java.awt.Color;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class CellStyle
{

    public static final int IGNORE  = -1;
    public static final int TEXT    = 0;
    public static final int INTEGER = 1;
    public static final int DECIMAL = 2;
    public static final int DATE    = 3;

    float width;
    float padLeft = 1, padTop = 1, padRight = 1, padBottom = 1;
    int colspan = 1, rowspan = 1;
    int cellType = 0; // 0: ignore , 1: text, 2: number, 3: date 3: ...

    HorizontalAlignment alignH                  = HorizontalAlignment.LEFT;
    VerticalAlignment   alignV                  = VerticalAlignment.MIDDLE;
    boolean             rowJustNeedBottomBorder = false;
    PDFont              font;
    float               fontSize;
    Color               color;

    public Color color()
    {
        return color;
    }

    public CellStyle color(Color color)
    {
        this.color = color;
        return this;
    }

    public PDFont font()
    {
        return font;
    }

    public CellStyle font(PDFont cellFont)
    {
        this.font = cellFont;
        return this;
    }

    public float fontSize()
    {
        return fontSize;
    }

    public CellStyle fontSize(float wifontSizedth)
    {
        this.fontSize = fontSize;
        return this;
    }

    public float cellType()
    {
        return cellType;
    }

    public CellStyle cellType(int cellType)
    {
        this.cellType = cellType;
        return this;
    }


    public float width()
    {
        return width;
    }

    public CellStyle width(float width)
    {
        this.width = width;
        return this;
    }

    public CellStyle colspan(int colspan)
    {
        this.colspan = colspan;
        return this;
    }

    public int colspan()
    {
        return this.colspan;
    }

    public int rowspan()
    {
        return rowspan;
    }

    public CellStyle rowspan(int rowspan)
    {
        this.rowspan = rowspan;
        return this;
    }


    public float padLeft()
    {
        return padLeft;
    }

    public CellStyle padLeft(float padLeft)
    {
        this.padLeft = padLeft;
        return this;
    }

    public float padTop()
    {
        return padTop;
    }

    public CellStyle padTop(float padTop)
    {
        this.padTop = padTop;
        return this;
    }

    public float padRight()
    {
        return padRight;
    }

    public CellStyle padRight(float padRight)
    {
        this.padRight = padRight;
        return this;
    }

    public float padBottom()
    {
        return padBottom;
    }

    public CellStyle padBottom(float padBottom)
    {
        this.padBottom = padBottom;
        return this;
    }

    public CellStyle padding(float padLeft, float padTop, float padRight, float padBottom)
    {
        this.padLeft = padLeft;
        this.padTop = padTop;
        this.padRight = padRight;
        this.padBottom = padBottom;
        return this;
    }


    public HorizontalAlignment alignH()
    {
        return alignH;
    }

    public CellStyle alignH(HorizontalAlignment alignH)
    {
        this.alignH = alignH;
        return this;
    }

    public VerticalAlignment alignV()
    {
        return alignV;
    }

    public CellStyle alignV(VerticalAlignment alignV)
    {
        this.alignV = alignV;
        return this;
    }

    public boolean rowJustNeedBottomBorder()
    {
        return rowJustNeedBottomBorder;
    }

    public CellStyle rowJustNeedBottomBorder(boolean rowJustNeedBottomBorder)
    {
        this.rowJustNeedBottomBorder = rowJustNeedBottomBorder;
        return this;
    }

    public CellStyle copyFrom(CellStyle style)
    {
        this.width = style.width;
        this.padLeft = style.padLeft;
        this.padTop = style.padTop;
        this.padRight = style.padRight;
        this.padBottom = style.padBottom;
        this.colspan = style.colspan;
        this.rowspan = style.rowspan;
        this.alignH = style.alignH;
        this.alignV = style.alignV;
        this.rowJustNeedBottomBorder = style.rowJustNeedBottomBorder;

        return this;
    }
}
