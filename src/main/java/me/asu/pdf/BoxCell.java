package me.asu.pdf;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class BoxCell
{

    Object text = "";

    int index;

    BoxParagraph paragraph = null;

    CellStyle style = new CellStyle();
    String dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
    BoxCell() {

    }

    public static BoxCell create(Object text)
    {
        BoxCell boxCell = new BoxCell();
        boxCell.text = text;
        if (text == null) {
            return boxCell;
        }
        if (text instanceof Integer || text.getClass() == int.class || text instanceof Long
                || text.getClass() == long.class || text instanceof Short
                || text.getClass() == short.class || text instanceof Byte
                || text.getClass() == byte.class) {
            boxCell.style.cellType = CellStyle.INTEGER;
            boxCell.style.alignH(HorizontalAlignment.RIGHT);
        } else if (text instanceof Number) {
            boxCell.style.cellType = CellStyle.DECIMAL;
            boxCell.style.alignH(HorizontalAlignment.RIGHT);
        } else if (text instanceof Date) {
            boxCell.style.cellType = CellStyle.DATE;
            boxCell.style.alignH(HorizontalAlignment.CENTER);
        }

        return boxCell;
    }

    public static BoxCell create(BoxParagraph boxParagraph)
    {
        BoxCell boxCell = new BoxCell();
        boxCell.paragraph = boxParagraph;
        return boxCell;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public CellStyle style()
    {
        return style;
    }

    public String getText()
    {
        if (text == null) {
            return "";
        }
       switch (style.cellType){
            case CellStyle.IGNORE: return "";
            case CellStyle.DATE: return new SimpleDateFormat(dateFormat).format((Date)text);
            default:
                return this.text.toString();
       }

    }

    public BoxParagraph getParagraph()
    {
        return this.paragraph;
    }

    public void draw(PDPageContentStream contentStream,
                     float nextTextX,
                     float nextTextY,
                     BoxTable table,
                     Column col) throws IOException
    {
        String text = getText();
        PDFont font = table.getTextFont();

        float fontSize = table.getFontSize();
        if (style.font != null) {
            font = style.font;
            if (style.fontSize > 0) {
                fontSize = style.fontSize;
            }

        }
        if (font != null) {
            contentStream.setFont(font, fontSize);
        }

        Color textColor = table.getTextColor();
        if (style.color != null) {
            textColor = style.color;
        }

        contentStream.beginText();
        if (textColor != null) {
            contentStream.setNonStrokingColor(textColor);
        }
        float x = nextTextX + style.padLeft;
        float width = style.width;
        if (width == 0) {
            width = col.style.width;
        }
        // 中文字体就是屌
        // font.getStringWidth(text);  偏大
        // PdfUtil.calcTextLength(text) * table.getFontWidth() / 2 偏小
        float textWidth = PdfUtil.calcTextLength(text) * table.getFontWidth() / 2;
        if (style.alignH == HorizontalAlignment.CENTER) {
            float delta =
                    (width - textWidth - style.padRight) / 2;
            if (delta > 0) {
                x += delta;
            }

        } else if (style.alignH == HorizontalAlignment.RIGHT) {
            float delta = width - textWidth - style.padRight;
            if (delta > 0) {
                x += delta;
            }
        }

        float y = nextTextY;
        if (style.alignV == VerticalAlignment.TOP) {
            y = nextTextY - table.getFontHeight() - style.padTop ;
        } else if (style.alignV == VerticalAlignment.MIDDLE) {
            y = nextTextY - table.getRowHeight()/2 - table.getFontHeight() / 4;
        } else if (style.alignV == VerticalAlignment.BOTTOM) {
            y = nextTextY - table.getRowHeight() + style.padBottom;
        }

        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text != null ? text : "");
        contentStream.endText();
    }
}
