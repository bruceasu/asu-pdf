package me.asu.pdf;

import java.awt.Color;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class TableStyle
{

    // Table attributes
    float marginTop;
    float marginLeft;
    float marginRight;
    float marginBottom;
    float cellMargin;

    float height;
    float rowHeight;
    float width;

    PDRectangle pageSize;
    boolean     isLandscape;

    // font attributes
    PDFont textFont   = PDType1Font.TIMES_ROMAN;
    PDFont headerFont = PDType1Font.HELVETICA_BOLD;
    float  fontSize   = 10;


    Color   borderColor           = Color.BLACK;
    Color   textColor             = Color.BLACK;
    boolean showBorder              = false;

    public Color borderColor()
    {
        return borderColor;
    }

    public TableStyle borderColor(Color borderColor)
    {
        this.borderColor = borderColor;
        return this;
    }

    public Color textColor()
    {
        return textColor;
    }

    public TableStyle textColor(Color textColor)
    {
        this.textColor = textColor;
        return this;
    }

    public boolean showBorder()
    {
        return showBorder;
    }

    public TableStyle showBorder(boolean showBorder)
    {
        this.showBorder = showBorder;
        return this;
    }

    public float cellMargin(){return cellMargin;}

    public TableStyle cellMargin(float cellMargin)
    {
        this.cellMargin = cellMargin;
        return this;
    }



    public float marginTop()
    {
        return marginTop;
    }

    public TableStyle marginTop(float margin)
    {
        this.marginTop = margin;
        return this;
    }

    public float marginLeft()
    {
        return marginLeft;
    }

    public TableStyle marginLeft(float margin)
    {
        this.marginLeft = margin;
        return this;
    }

    public float marginRight()
    {
        return marginRight;
    }

    public TableStyle marginRight(float margin)
    {
        this.marginRight = margin;
        return this;
    }

    public float marginBottom()
    {
        return marginBottom;
    }

    public TableStyle marginBottom(float margin)
    {
        this.marginBottom = margin;
        return this;
    }

    public float width()
    {
        return width;
    }

    public TableStyle width(float width)
    {
        this.width = width;
        return this;
    }





    public PDRectangle pageSize()
    {
        return pageSize;
    }

    public TableStyle pageSize(PDRectangle pageSize)
    {
        this.pageSize = pageSize;
        return this;
    }

    public PDFont headerFont()
    {
        return headerFont;
    }

    public TableStyle headerFont(PDFont font)
    {
        this.headerFont = font;
        return this;
    }

    public PDFont textFont()
    {
        return textFont;
    }

    public TableStyle textFont(PDFont font)
    {
        this.textFont = font;
        return this;
    }


    public float fontSize()
    {
        return fontSize;
    }

    public TableStyle fontSize(float fontSize)
    {
        this.fontSize = fontSize;
        return this;
    }


    public float height()
    {
        return height;
    }

    public TableStyle height(float height)
    {
        this.height = height;
        return this;
    }

    public float rowHeight()
    {
        return rowHeight;
    }

    public TableStyle rowHeight(float rowHeight)
    {
        this.rowHeight = rowHeight;
        return this;
    }


    public boolean landscape()
    {
        return isLandscape;
    }

    public TableStyle landscape(boolean isLandscape)
    {
        this.isLandscape = isLandscape;
        return this;
    }

    public float getLogicPageWidth() {
        return isLandscape ? pageSize.getHeight() : pageSize.getWidth();
    }
}
