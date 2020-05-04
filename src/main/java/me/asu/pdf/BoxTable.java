package me.asu.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

public class BoxTable
{

    TableStyle    style      = new TableStyle();

    // 行数
    private Integer numberOfRows;

    // 栏位定义 (二维)
    private List<List<Column>> headers;

    private TableDataSource tableDataSource;

    private boolean showHeader = true;


    public static BoxTable create(float width, PDFont pdFont)
    {
        BoxTable table = new BoxTable();
        table.style.textFont(pdFont).width(width).borderColor(Color.LIGHT_GRAY);
        return table;
    }

    public static BoxTable create(float width, PDFont pdFont, float marginTop, float marginBottom)
    {
        BoxTable table = new BoxTable();
        table.style.textFont(pdFont)
                   .width(width)
                   .marginTop(marginTop)
                   .marginBottom(marginBottom)
                   .borderColor(Color.LIGHT_GRAY);

        return table;
    }

    public TableStyle style()
    {
        return style;
    }

//    public BoxCell addCell(String text)
//    {
//        BoxCell boxCell = new BoxCell(text);
//        cellList.add(boxCell);
//        return boxCell;
//    }
//
//    public BoxCell addCell(BoxParagraph boxParagraph)
//    {
//        BoxCell boxCell = new BoxCell(boxParagraph);
//        cellList.add(boxCell);
//        return boxCell;
//    }
//
//    public BoxCell addCell(BoxCell boxCell)
//    {
//        if (1 == boxCell.style.colspan) {
//            cellList.add(boxCell);
//        } else {
//            cellList.add(boxCell);
//            for (int i = 1; i < boxCell.style.colspan; i++) {
//                cellList.add(new BoxCell("_@_"));
//            }
//        }
//        return boxCell;
//    }


    public List<List<Column>> headers()
    {
        return headers;
    }

    public BoxTable headers(List<List<Column>> headers)
    {
        this.headers = headers;
        if (headers != null) {
            style.width(getWidth());
        }
        return this;
    }

    public boolean showHeader()
    {
        return showHeader;
    }

    public BoxTable showHeader(boolean flag)
    {
        this.showHeader = flag;
        return this;
    }


    public Integer numberOfRows()
    {
        return numberOfRows;
    }

    public BoxTable numberOfRows(Integer numberOfRows)
    {
        this.numberOfRows = numberOfRows;
        return this;
    }

    public TableDataSource tableDataSource()
    {
        return tableDataSource;
    }

    public BoxTable tableDataSource(TableDataSource tableDataSource)
    {
        this.tableDataSource = tableDataSource;
        return this;
    }

    public Integer getNumberOfColumns()
    {
        // 计算出列数
        // 30 x 10， 超过范围不支持。
        boolean[][] matrix = new boolean[10][30];
        for (int j = 0; j < headers.size(); j++) {
            List<Column> row = headers.get(j);
            int curr = 0;
            for (int i = 0; i < row.size(); i++) {
                Column column = row.get(i);
                CellStyle style = column.style;
                if (style.cellType == CellStyle.IGNORE) {
                    continue;
                }
                for (int k = 0; k < style.colspan; k++) {
                    matrix[j][i+k] = true;
                }
                for (int k = 0; k < style.rowspan; k++) {
                    matrix[j+k][i] = true;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < 10; i++) {
            int k = 0;
            for (int j = 0; j < 30; j++) {
                if (matrix[i][j]) {
                    k++;
                }
            }
            if (max < k) {
                max = k;
            }
        }
        return max;
    }

    public BoxCell[][] getContentForPage(int startRange, int endRange)
    {
        if (tableDataSource == null) {
            throw new NullPointerException("TableDataSource has not set.");
        }
        return tableDataSource.getRowRange(startRange, endRange);
    }

    public float getHeaderHeight() {
        return style.rowHeight * headers.size();
    }

    public List<Column> getLastHeaderRow() {
        return headers.get(headers.size() - 1);
    }

    public PDFont getHeaderFont() {

        PDFont font = style.headerFont;
        if (font == null) {
            font = style.textFont;
        }
        if (font == null) {
            font = PDType1Font.HELVETICA_BOLD;
        }

        return font;
    }

    public PDFont getTextFont() {

        PDFont font =  style.textFont;

        if (font == null) {
            font = PDType1Font.HELVETICA_BOLD;
        }

        return font;
    }

    public Color getTextColor() {
        Color color = Color.BLACK;
        if (style.textColor != null) {
            color = style.textColor;
        }
        return color;
    }

    public float getFontSize() {
        return style.fontSize;
    }
    public Color getBorderColor() {
        Color color = Color.BLACK;
        if (style.borderColor != null) {
            color = style.borderColor;
        }
        return color;
    }

    public float getFontHeight() {
        float fontBBHeight = style.textFont().getFontDescriptor().getFontBoundingBox().getHeight();
        return fontBBHeight / 1000.0f *  style.fontSize;
    }
    public float getFontWidth() {
        float fontBBWidth = style.textFont().getFontDescriptor().getFontBoundingBox().getWidth();
        return fontBBWidth / 1000.0f *  style.fontSize;
    }

    public float getTableTop() {
        return style.landscape()
                ? style.pageSize().getWidth() - style.marginTop()
                : style.pageSize().getHeight() - style.marginTop();
    }

    public float getRowHeight() {
        return style.rowHeight;
    }


    private float getWidth()
    {
        float tableWidth = 0f;
        List<Column> row = headers.get(headers.size() - 1);
        for (Column column : row) {
            tableWidth += column.style.width;
        }
        return tableWidth;
    }



    // Configures basic setup for the table and draws it page by page
    public void drawTable(PDDocument doc) throws IOException
    {
        // Calculate pagination
        float height = style.height();
        float rowHeight = style.rowHeight();
        int rowsPerPage = new Double(Math.floor(height / rowHeight)).intValue() - 1; // subtract
        float rows = numberOfRows().floatValue();
        int numberOfPages = new Double(Math.ceil(rows / rowsPerPage)).intValue();

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page               = generatePage(doc);
            drawCurrentPage(doc, page, pageCount, rowsPerPage);
        }
    }

    // Draws current page table grid and border lines and content
    public void drawCurrentPage(PDDocument doc, PDPage page,  int pageCount, int rowsPerPage)
    throws IOException {
        PDPageContentStream contentStream      = generateContentStream(doc, page);
        Color borderColor = getBorderColor();
        Color textColor = getTextColor();
        contentStream.setStrokingColor(borderColor);
        contentStream.setNonStrokingColor(textColor);

        BoxCell[][] currentPageContent = getContentForCurrentPage(rowsPerPage, pageCount);
        float tableTopY = getTableTop();
        // Position cursor to start drawing content
        float nextTextX = style.marginLeft();
        contentStream.setFont(getHeaderFont(), style.fontSize);
        float nextTextY = tableTopY;
        if (showHeader()) {
            writeHeader(contentStream, nextTextX, nextTextY);

            nextTextY -= getHeaderHeight();
            nextTextX = style.marginLeft() + style.cellMargin();
        }
        // reset font
        contentStream.setFont(getTextFont(), style.fontSize);
        // Draws grid and borders
        if (style.showBorder) {
            drawTableGrid(currentPageContent, contentStream, tableTopY);
        }

        // Write content
        for (int i = 0; i < currentPageContent.length; i++) {
            writeContentLine(currentPageContent[i], contentStream, nextTextX, nextTextY);
            nextTextY -= style.rowHeight();
            nextTextX = style.marginLeft() + style.cellMargin();
        }

        // reset
        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.close();
    }

    private void writeHeader(PDPageContentStream contentStream,
                             final float nextTextX,
                             final float nextTextY) throws IOException
    {
        // Write column headers
        float tableTop =  getTableTop();
        float fontWith = getFontWidth();
        float fontHeight = getFontHeight();
        float xStart = style.marginLeft;
        float xEnd = xStart + style.width;

        if (style.showBorder) {
            // draw top line
            PdfUtil.drawLine(contentStream, xStart, tableTop, xEnd, tableTop);
        }
        float rowHeight = getRowHeight();
        for (int j = 0; j < headers.size(); j++) {
            List<Column> row = headers.get(j);
            float rowX = nextTextX;
            float rowY = nextTextY - rowHeight * j;
            float currLine = rowHeight * j;
            float yStart = tableTop - currLine;

            if (style.showBorder) {
                // draw left col line
                float yEnd = tableTop - currLine - rowHeight;
                PdfUtil.drawLine(contentStream, xStart, yStart, xStart, yEnd);
            }

            //            Color textColor = table.getTextColor();
            for (int i = 0; i < row.size(); i++) {

                float cellX = rowX;

                Column col = row.get(i);

                float colWidth = col.style.width;
                if (col.style.cellType != CellStyle.IGNORE) {
                    // 计算居中
                    String text = col.name;
                    text = text != null ? text : "";


                    List<String> lines = new ArrayList<>();
                    PDFont headerFont = getHeaderFont();
                    float fontSize = getFontSize();
                    PdfUtil.parseLinesRecursive(text, colWidth, lines, headerFont, fontSize);

                    String testText = text;
                    if (lines.size() > 0) {
                        float c = -1;
                        String t = "";
                        for (int k = 0; k < lines.size(); k++) {
                            String tt = lines.get(k);
                            float x = PdfUtil.calcTextLength(tt);
                            if (c < x) {
                                c = x;
                                t = tt;
                            }
                        }
                        testText = t;
                    }
                    float textWith = PdfUtil.calcTextLength(testText) * (fontWith / 2);
                    if (colWidth > textWith) {
                        cellX += (colWidth - textWith) / 2;
                    } else {
                        cellX += 2 ;
                    }

                    float cellY = rowY;
                    int rowspan = col.style.rowspan;
                    float cellHeight = rowspan * rowHeight;
                    cellY = cellY - cellHeight/(2 *lines.size()) - fontHeight/4;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(cellX, cellY);
                    //                    contentStream.showText(text != null ? text : "");

                    for (String line: lines) {
                        contentStream.showText(line);
                        contentStream.newLineAtOffset(0, -1.5f * fontSize);
                    }
                    contentStream.endText();
                    float yEnd = tableTop - currLine - rowHeight * rowspan;
                    if (style.showBorder) {
                        // draw right col line
                        PdfUtil.drawLine(contentStream, rowX + colWidth, yStart, rowX + colWidth, yEnd);
                    }
                    if (style.showBorder || col.style.rowJustNeedBottomBorder) {
                        // draw bottom line
                        PdfUtil.drawLine(contentStream, rowX, yEnd, rowX + colWidth, yEnd);
                    }
                }
                rowX += colWidth;

            }

        }

    }

    // Writes the content for one line
    private void writeContentLine(BoxCell[] lineContent,
                                  PDPageContentStream contentStream,
                                  float nextTextX, float nextTextY) throws IOException {
        List<Column> lastRow = headers.get(headers.size() - 1);
        for (int i = 0; i < lineContent.length; i++) {
            BoxCell cell = lineContent[i];
            Column col = lastRow.get(i);
            cell.draw(contentStream, nextTextX, nextTextY, this, col);
            nextTextX += col.style.width;
        }
    }

    private void drawTableGrid(BoxCell[][] currentPageContent,
                               PDPageContentStream contentStream,
                               float tableTopY)
    throws IOException {

        // Draw row lines
        float xEnd = style.marginLeft + style.width;
        float headerHeight = getHeaderHeight();
        float bodyTop = tableTopY;
        float nextY = bodyTop;
        int lines = currentPageContent.length;
        if (showHeader) {
            bodyTop = tableTopY - headerHeight;
            // 不画顶线，与头共用。
            nextY = bodyTop - style.rowHeight;
        } else {
            lines++;
        }

        for (int i = 0; i < lines; i++) {
            float xStart = style.marginLeft();
            PdfUtil.drawLine(contentStream, xStart, nextY, xEnd, nextY);
            nextY -= style.rowHeight;
        }

        // Draw column lines
        float tableYLength = style.rowHeight * currentPageContent.length;
        if (showHeader) {
            tableYLength += headerHeight;
        }
        final float tableBottomY = tableTopY - tableYLength;
        float nextX = style.marginLeft;

        List<Column> lastHeaderRow = getLastHeaderRow();
        for (int i = 0; i < getNumberOfColumns() + 1; i++) {
            PdfUtil.drawLine(contentStream, nextX, bodyTop, nextX, tableBottomY);
            nextX += lastHeaderRow.get(i).style.width;
        }

        PdfUtil.drawLine(contentStream, nextX, bodyTop, nextX, tableBottomY);

    }

    private BoxCell[][] getContentForCurrentPage(Integer rowsPerPage, int pageCount) {
        int startRange = pageCount * rowsPerPage;
        int endRange = (pageCount * rowsPerPage) + rowsPerPage;
        if (endRange > numberOfRows) {
            endRange = numberOfRows;
        }
        return getContentForPage(startRange, endRange);
    }

    private PDPage generatePage(PDDocument doc) {
        PDPage page = PdfUtil.generatePage(style.pageSize(), style.landscape());
        doc.addPage(page);
        return page;
    }

    private PDPageContentStream generateContentStream(PDDocument doc, PDPage page) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.OVERWRITE, false);
        // User transformation matrix to change the reference when drawing.
        // This is necessary for the landscape position to draw correctly
        if (style.landscape()) {
            Matrix matrix = new Matrix(0, 1, -1, 0, style.pageSize().getWidth(), 0);
            contentStream.transform(matrix);
        }
        contentStream.setFont(style.textFont(), style.fontSize());
        return contentStream;
    }
}
