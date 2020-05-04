package me.asu.pdf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class BoxParagraph {
	String              text  = "";
	List<BoxParagraph>   paragraphList = new ArrayList<>();
	ParagraphStyle style = new ParagraphStyle();

	public BoxParagraph() {

	}

	public BoxParagraph(String text) {
		this.text = text;
	}



	public static BoxParagraph create(String text, HorizontalAlignment align, PDFont font, int fontSize) {
		BoxParagraph box = new BoxParagraph(text);
		box.text = text;
		box.style.align = align;
		box.style.font = font;
		box.style.fontSize = fontSize;
		return box;
	}
	
	public static BoxParagraph create(String text, HorizontalAlignment align, PDFont font, int fontSize, Color color) {
		BoxParagraph box = new BoxParagraph();
		box.text = text;
		box.style.align = align;
		box.style.font = font;
		box.style.fontSize = fontSize;
		box.style.color = color;

		return box;
	}
	
	public String getText() {
		return this.text;
	}
	

	public void addParagraph(String text, HorizontalAlignment align, PDFont font, int fontSize) {
		BoxParagraph boxParagraph = BoxParagraph.create(text, align, font, fontSize);
		this.paragraphList.add(boxParagraph);
	}
	
	public void addParagraph(BoxParagraph boxParagraph) {
		this.paragraphList.add(boxParagraph);
	}
	
	public List<BoxParagraph> getParagraphList() {
		return this.paragraphList;
	}

	public ParagraphStyle style()
	{
		return style;
	}
}
