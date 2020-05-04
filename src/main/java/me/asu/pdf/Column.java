package me.asu.pdf;

public class Column
{

    String    name;
    CellStyle style = new CellStyle();

    public Column(String name)
    {
        this.name = name;
    }

    public static Column create(String name, float width)
    {
        Column col = new Column(name);
        col.style.width(width).alignH(HorizontalAlignment.CENTER).alignV(VerticalAlignment.MIDDLE);
        return col;
    }

    public static Column createIgnore( float width)
    {
        Column col = new Column("_@_");
        col.style.width(width).cellType(CellStyle.IGNORE);
        return col;
    }

    public static Column create(String name, float width, int rowspan, int colspan)
    {
        Column col = new Column(name);
        col.style.width(width)
                 .rowspan(rowspan < 1 ? 1 : rowspan)
                 .colspan(colspan < 1 ? 1 : colspan)
                 .alignH(HorizontalAlignment.CENTER)
                 .alignV(VerticalAlignment.MIDDLE);

        return col;

    }

    public String name()
    {
        return name;
    }

    public void name(String name)
    {
        this.name = name;
    }

    public CellStyle style()
    {
        return style;
    }
}
