package me.asu.pdf;

import java.awt.Color;
import java.util.Arrays;
import java.util.Objects;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface TableDataSource
{

    Logger log = LoggerFactory.getLogger(TableDataSource.class);

    BoxCell[] getRow(int idx);
    BoxCell[][] getRowRange(int startIdx, int endIdx);

    public static TableDataSource createArrayDataSource(Object[][] content)
    {
        Objects.requireNonNull(content);
        return new ArrayDataSource(content);
    }

    class ArrayDataSource implements TableDataSource {

        // 表格内容
        private BoxCell[][]      content;
        private static final BoxCell[] EMPTY = new BoxCell[0];
        private static final BoxCell[][] EMPTY_MATRIX = new BoxCell[0][];

        ArrayDataSource (Object[][] content)
        {
            if (content != null && content.length > 0) {
                this.content = new BoxCell[content.length][];
                for (int i = 0; i < content.length; i++) {
                    this.content[i] = new BoxCell[0];
                }
                for (int i = 0; i < content.length; i++) {

                    Object[] c = content[i];
                    int length = c.length;
                    this.content[i] = new BoxCell[length];
                    for (int j = 0; j < c.length; j++) {
                        BoxCell boxCell = BoxCell.create(c[j]);
                        this.content[i][j] = boxCell;
                    }
                }
            }
        }

        @Override
        public BoxCell[] getRow(int idx)
        {
            if (content == null) {
                return EMPTY;
            }

            if (idx < 0 || idx >= content.length) {
                throw new ArrayIndexOutOfBoundsException(idx);
            }

            return content[idx];

        }

        @Override
        public BoxCell[][] getRowRange(int startIdx, int endIdx)
        {
            if (content == null) {
                return EMPTY_MATRIX;
            }

            if (startIdx < 0 || startIdx >= content.length
                    || endIdx < 0 || endIdx > content.length
                    || (startIdx >= endIdx)) {
                log.error("startIdx: {}, endIdx: {}", startIdx, endIdx);
                throw new ArrayIndexOutOfBoundsException();
            }

            return Arrays.copyOfRange(content, startIdx, endIdx);

        }
    }
}

