package com.openqa.common;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import java.io.File;
import java.io.IOException;

public class PdfReader
{
	
	public static void main(String[] args) throws IOException
	{
		try (PDDocument document = PDDocument.load(new File("C:\\Users\\wzheng.AVANTI\\Downloads\\123456789.pdf")))
		{
			document.getClass();
			if (!document.isEncrypted())
			{
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				PDFTextStripper tStripper = new PDFTextStripper();
				String pdfFileInText = tStripper.getText(document);
				String lines[] = pdfFileInText.split("\\r?\\n");
				for (String line : lines)
				{
					System.out.println(line);
				}
			}
		}
	}
}
