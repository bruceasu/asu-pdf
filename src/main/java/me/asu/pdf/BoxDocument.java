package me.asu.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;


import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BoxDocument {
    private static final String TARGET_FOLDER = "target";

    private static final float DOCUMENT_PADDING = 50f;

    private static final int DEFAULT_FONT_SIZE = 10;

    private PDFont mDefaultFont = null;

    private Color mDefaultColor = Color.BLACK;

    private List<BoxTable> mTableList = new ArrayList<>();

    private List<Object> mObjectList = new ArrayList<>();

    public BoxDocument(String fontFileLocation) {
    	try {
            PDDocument documentMock = new PDDocument();
            this.mDefaultFont = PDType0Font.load(documentMock, new File(fontFileLocation));
        }
        catch (IOException e) {
            throw new RuntimeException("IO exception");
        }
    }

    public BoxDocument(PDFont font) {
    	this.mDefaultFont = font;
    }

    public void add(String text) {
        mObjectList.add(text);
    }

    public void add(BoxParagraph paragraph) {
        mObjectList.add(paragraph);
    }

    public void add(BoxTable table) {
        mObjectList.add(table);
    }

    /**
     * 增加一段文本
     * @param contentStream
     * @param width
     * @param sx
     * @param sy
     * @param text
     * @param justify
     * @throws IOException
     */
    private static int addParagraph(PDPageContentStream contentStream, float width, float sx,
                                     float sy, String text, boolean justify, PDFont font, int fontSize) throws IOException {
        List<String> lines = new ArrayList<>();
        PdfUtil.parseLinesRecursive(text, width, lines, font, fontSize);

        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(sx, sy);
        for (String line: lines) {
            float charSpacing = 0;
            if (justify){
                if (line.length() > 1) {
                    float size = fontSize * font.getStringWidth(line) / 1000;
                    float free = width - size;
                    if (free > 0 && !lines.get(lines.size() - 1).equals(line)) {
                        charSpacing = free / (line.length() - 1);
                    }
                }
            }
            contentStream.setCharacterSpacing(charSpacing);
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -1.5f * fontSize);
        }
        return lines.size();
    }




}
