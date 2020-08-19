package t.application.imagetopdf;

import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.ArrayList;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
    ArrayList<model> m1=new ArrayList<>();
    HeaderFooterPageEvent(ArrayList<model> mod)
    {
        m1=mod;
        ArrayList<String> poslist=new ArrayList<>();
        poslist.add("Title");
        poslist.add("center");
        poslist.add("Top Left");
        poslist.add("Top Right");
        poslist.add("Bottom left");
        poslist.add("Bottom Right");

    }

//    public void onStartPage(PdfWriter writer, Document document) {
//        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Left"), 30, 800, 0);
//        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Right"), 550, 800, 0);
//    }

    public void onEndPage(PdfWriter writer, Document document) {
        writer.setCompressionLevel(9);
        Log.i("pagenumber",Integer.toString(writer.getPageNumber()));
       float fntSize = 15.7f;
        float lineSpacing = 1f;
        writer.setMargins(25,25,25,25);
        for(int i=0;i<m1.size()-1;i++)
        {
            if(writer.getPageNumber()==Integer.parseInt(m1.get(i).getValue()))

            {
                switch (Integer.parseInt(m1.get(i).getPosition())-1)
                {
                    case 1:
                    {
                         fntSize = 35.7f;

                        Paragraph p = new Paragraph(new Phrase(lineSpacing,m1.get(i).getText(),
                                FontFactory.getFont(FontFactory.COURIER, fntSize)));
                        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(p), writer.getPageSize().getWidth()/2, 800, 0);
                        break;

                    }
                    case 2:
                    {   fntSize = 15.7f;

                        Paragraph p = new Paragraph(new Phrase(lineSpacing,m1.get(i).getText(),
                                FontFactory.getFont(FontFactory.COURIER, fntSize)));
                        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(p), writer.getPageSize().getWidth()/2, 800, 0);

                        break;
                    }
                    case 3:
                    {
                        fntSize = 15.7f;
                        Paragraph p = new Paragraph(new Phrase(lineSpacing,m1.get(i).getText(),
                                FontFactory.getFont(FontFactory.COURIER, fntSize)));
                        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(p), 50, 800, 0);

                        break;
                    }
                    case 4:
                    {
                        fntSize = 15.7f;
                        Paragraph p = new Paragraph(new Phrase(lineSpacing,m1.get(i).getText(),
                                FontFactory.getFont(FontFactory.COURIER, fntSize)));
                        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(p), 550, 800, 0);
                        break;

                    }
                    case 5:
                    {
                        fntSize = 15.7f;
                        Paragraph p = new Paragraph(new Phrase(lineSpacing,m1.get(i).getText(),
                                FontFactory.getFont(FontFactory.COURIER, fntSize)));
                        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(p), 50, 30, 0);
                        break;


                    }
                    case 6:
                    {
                        fntSize = 15.7f;
                        Paragraph p = new Paragraph(new Phrase(lineSpacing,m1.get(i).getText(),
                                FontFactory.getFont(FontFactory.COURIER, fntSize)));
                        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(p), 550, 30, 0);

                        break;
                    }


                }
                Log.i("insideif",m1.get(i).getText());
//                Paragraph p = new Paragraph(new Phrase(lineSpacing,m1.get(i).getText(),
//                        FontFactory.getFont(FontFactory.COURIER, fntSize)));
//                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(p), 110, 30, 0);
//                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Left"), writer.getPageSize().getWidth()/2, 800, 0);
//                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Right"), 550, 800, 0);


            }
       //     ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);

        }
//        Paragraph p = new Paragraph(new Phrase(lineSpacing,"hello",
//                FontFactory.getFont(FontFactory.COURIER, fntSize)));
//        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(p), 110, 30, 0);
//        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
//        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Left"), writer.getPageSize().getWidth()/2, 800, 0);
//        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Right"), 550, 800, 0);
    }

}