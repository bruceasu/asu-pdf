package me.asu.pdf;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfUtil
{

    private static final Logger log = LoggerFactory.getLogger(PdfUtil.class);
    public static PDFont loadFont(String fontFileLocation) {
        PDFont font;
        try {
            PDDocument documentMock = new PDDocument();
            font = PDType0Font.load(documentMock, new File(fontFileLocation));
        }
        catch (IOException e) {
            throw new RuntimeException("IO exception");
        }
        return font;
    }

    public static PDFont loadFontFromResource(String fontName) {
        try {
            PDDocument  documentMock = new PDDocument();
            InputStream inFont       = BoxDocument.class.getClassLoader().getResourceAsStream(fontName);
            return PDType0Font.load(documentMock, inFont);
        } catch(IOException e) {
            throw new RuntimeException("IO exception");
        }
    }

    public static PDPage generatePage(PDRectangle mediaBox, boolean isLandscape) {
        PDPage page = new PDPage();
        page.setMediaBox(mediaBox);
        page.setRotation(isLandscape ? 90 : 0);
        return page;
    }

    public static void drawLine(PDPageContentStream contentStream,
                                float xStart, float yStart, float xEnd, float yEnd) throws IOException
    {
        contentStream.moveTo(xStart, yStart);
        contentStream.lineTo(xEnd, yEnd);
        contentStream.stroke();


    }

    /**
     * 递归分析文本，并按宽度分割成N行
     * @param text
     * @param width
     * @param lines
     * @return
     * @throws IOException
     */
    public static List<String> parseLinesRecursive(String text, float width, List<String> lines, PDFont font, float fontSize) throws IOException {
        String tmpText = text;
        String[] split = text.split("\n");
        for(String ln : split) {
            for (int i=0; i< ln.length(); i++) {
                tmpText = ln.substring(0, ln.length() - i);

//                float realWidth = fontSize * font.getStringWidth(tmpText) / 1000;
                float realWidth = (fontSize/2) * calcTextLength(tmpText) / 1000;

                if (realWidth > width) {
                    continue;
                } else {
                    lines.add(tmpText);

                    if (0 != i) {
                        parseLinesRecursive(ln.substring(ln.length() - i), width, lines, font, fontSize);
                    }

                    break;
                }
            }
        }


        return lines;
    }

    public static float calcTextLength(String str) {
        float sum = 0;
        for (int i = 0; i < str.length(); i++) {
            int ch = str.codePointAt(i);
            if (ch < 128) {
                sum += 1;
            } else {
                sum += 2;
            }
        }

        return sum;
    }

}
