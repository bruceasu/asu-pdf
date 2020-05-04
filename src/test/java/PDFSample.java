import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import me.asu.pdf.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class PDFSample
{

    // Page configuration
    private static final PDRectangle PAGE_SIZE    = PDRectangle.A4;
    private static final float       MARGIN       = 20;
    private static final boolean     IS_LANDSCAPE = true;

    // Font configuration
    private static final PDFont HEADER_FONT = PdfUtil.loadFont("C:\\Windows\\Fonts\\STSONG.TTF");
    private static final PDFont TEXT_FONT   = PdfUtil.loadFont(
            "D:\\12_fonts\\仓耳今楷\\仓耳今楷01-27533-W04.ttf");
    private static final float  FONT_SIZE   = 8;

    // Table configuration
    private static final float ROW_HEIGHT  = 15;
    private static final float CELL_MARGIN = 2;

    public static void main(String[] args) throws IOException
    {
        generatePDF(createContent());
    }

    // Generates document from Table object
    public static void generatePDF(BoxTable table) throws IOException
    {
        PDDocument doc = null;
        try {
            doc = new PDDocument();
            table.drawTable(doc);
            doc.save("sample.pdf");
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

    private static BoxTable createContent()
    {
        // Total size of headers must not be greater than table width.
        List<Column> columns = new ArrayList<Column>();
        Column firstName = Column.create("FirstName", 80, 2, 1);
        Column lastName = Column.create("LastName", 80, 2, 1);
        columns.add(firstName);
        columns.add(lastName);
        columns.add(Column.create("电邮", 100, 2, 1));
        columns.add(Column.create("邮编\n号码", 50, 2, 1));
        columns.add(Column.create("MailOptIn", 50, 2, 1));
        columns.add(Column.create("Code/Branch", 120, 2, 2));
        columns.add(Column.create("Product Date", 230, 1, 2));
        Column ch = Column.create("Channel", 100);
        ch.style().colspan(2).rowJustNeedBottomBorder(true);
        columns.add(ch);

        List<Column> columns2 = new ArrayList<Column>();
        Column ignore = Column.createIgnore(80f);
        columns2.add(ignore);
        columns2.add(ignore);
        columns2.add(Column.createIgnore(100));
        columns2.add(Column.createIgnore(50));
        columns2.add(Column.createIgnore(50));
        columns2.add(Column.createIgnore(70));
        columns2.add(Column.createIgnore(50));
        columns2.add(Column.create("Product", 90));
        columns2.add(Column.create("Date", 140));
        columns2.add(Column.create("Ch1", 50));
        columns2.add(Column.create("Ch2", 50));

        Object[][] content = new Object[10][];

        for (int i = 0; i < 10; i++) {
            content[i] = new Object[]{"名字", "姓氏", "fakemail@mock.com", 12345, "yes",
                                      "XH4234FSD", 4334, "yFone 5 XS", new Date(),
                                      "WEB", "ss"};
        }

        float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN)
                : PAGE_SIZE.getHeight() - (2 * MARGIN);

        BoxTable table = new BoxTable();
        List<List<Column>> headers = new ArrayList<>();
        headers.add(columns);
        headers.add(columns2);
        int rows = content.length;
        table.headers(headers)
             .tableDataSource(TableDataSource.createArrayDataSource(content))
             .numberOfRows(rows)
//            .showHeader(false)
        ;
        TableStyle style = table.style();
        style.marginLeft(MARGIN)
             .marginTop(MARGIN)
             .marginRight(MARGIN)
             .marginBottom(MARGIN)
             .cellMargin(CELL_MARGIN)
             .height(tableHeight)
             .rowHeight(ROW_HEIGHT)
                          .showBorder(true)
                          .borderColor(Color.RED)
//             .textColor(Color.green)
             .pageSize(PAGE_SIZE)
             .landscape(IS_LANDSCAPE)
             .textFont(TEXT_FONT)
             .headerFont(HEADER_FONT)
             .fontSize(FONT_SIZE)

        ;
        return table;
    }
}
