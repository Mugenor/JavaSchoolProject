import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BarcodePDF417;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PdfTest {
    static int FONT_SIZE_SMALL = 16;
    static int FONT_SIZE_BIG = 32;
    static int OFFSET = 40;

    @Test
    public void test() throws Exception {
//        createTemplate();

        Receipt receipt = new Receipt(
                "This is a veeeeeeeeeeeeeeeeeeeeee" +
                        "eeeeeeeeeeeeeeeeeeeeery long purpose " +
                        "text, so it will overflow with font size = 16",
                123.45,
                "Name Surname");

        fillInReceipt(receipt);
    }

    public static void createTemplate() throws Exception {
        Document document = new Document();

        Font font1 = new Font(Font.HELVETICA,
                FONT_SIZE_BIG, Font.BOLD);
        Font font2 = new Font(Font.HELVETICA,
                FONT_SIZE_SMALL, Font.ITALIC | Font.UNDERLINE);

        PdfWriter.getInstance(document,
                new FileOutputStream("D://ticketTemplate.pdf"));

        document.open();

        // отцентрированный параграф
        Paragraph title = new Paragraph("Ticket", font1);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(FONT_SIZE_BIG);
        document.add(title);

        // параграф с текстом
//        purpose.setSpacingAfter(FONT_SIZE_BIG);
        document.add(new Paragraph("Name", font2));
        document.add(new Paragraph("Surname", font2));
        Paragraph birthday = new Paragraph("Birthday", font2);
        birthday.setSpacingAfter(FONT_SIZE_BIG);
        document.add(birthday);
        document.add(new Paragraph("Departure station", font2));
        document.add(new Paragraph("Departure time", font2));
        document.add(new Paragraph("Arrival station", font2));
        document.add(new Paragraph("Arrival time", font2));

        // параграф с добавленным чанком текста
//        Paragraph amount = new Paragraph();
//        amount.setFont(font2);
//        amount.setSpacingAfter(8);
//        amount.add(new Chunk("Surname"));
//        document.add(amount);
//
//         параграф с фразой, в которую добавлен чанк
//        Paragraph date = new Paragraph();
//        date.setFont(font2);
//        Phrase phrase = new Phrase();
//        phrase.add(new Chunk("Birthday"));
//        date.add(phrase);
//        document.add(date);

//        document.add(new Paragraph("Name", font2));

//        Paragraph footer = new Paragraph(
//                "Important - please retain for your records - ");
//
//         ссылка
//        Anchor anchor = new Anchor("Javenue");
//        anchor.setReference("http://www.javenue.info");
//        footer.add(anchor);
//
//        footer.setAlignment(Element.ALIGN_CENTER);
//        footer.setSpacingBefore(FONT_SIZE_BIG);
//        document.add(footer);
//
//         картинка, загруженная по URL
//        String imageUrl = "http://www.javenue.info/files/sample.png";
//         Image.getInstance("sample.png")
//        Image stamp = Image.getInstance(new URL(imageUrl));
//        stamp.setAlignment(Element.ALIGN_RIGHT);
//        document.add(stamp);
//
        document.close();
    }

    public static void fillInReceipt(Receipt receipt)
            throws Exception {
        final int Y_OFFSET = 705;
        final int Y_OFF = 10;
        PdfReader reader = new PdfReader(
                new FileInputStream("D://ticketTemplate.pdf"));
        PdfStamper stamper = new PdfStamper(reader,
                new FileOutputStream("D://ticket.pdf"));

        PdfContentByte stream = stamper.getOverContent(1);
        stream.beginText();

        float pageWidth = reader.getPageSize(1).getWidth();


        BaseFont font = BaseFont.createFont();
        stream.setFontAndSize(font, FONT_SIZE_SMALL);
        float effectiveStringWidth = stream.getEffectiveStringWidth("Ilusha", false);
//        stream.setTextMatrix(pageWidth - OFFSET - effectiveStringWidth, Y_OFFSET);
        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Ilusha", pageWidth - OFFSET, Y_OFFSET, 0.0f);

        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Chernov", pageWidth - OFFSET, Y_OFFSET - (FONT_SIZE_SMALL + Y_OFF), 0.0f);

        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT, LocalDateTime.now().toString(), pageWidth - OFFSET, Y_OFFSET - (Y_OFF + FONT_SIZE_SMALL) * 2, 0.0f);

        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Baltiyskaya", pageWidth - OFFSET, Y_OFFSET - (Y_OFF + FONT_SIZE_SMALL) * 3 - FONT_SIZE_SMALL, 0.0f);

        BarcodePDF417 barcode = new BarcodePDF417();
        barcode.setText("asldkjadlkasdljalskdj");
        Image image = barcode.getImage();
        image.scalePercent(500, 500);
        image.setAbsolutePosition(OFFSET, OFFSET + 300);

        stream.addImage(image);
//        stream.setColorFill(Color.BLUE);
//
//        BaseFont font = BaseFont.createFont();
//
//        float pageWidth = reader.getPageSize(1).getWidth();
//        stream.setFontAndSize(font, FONT_SIZE_SMALL);
//        float v = stream.getEffectiveStringWidth(
//                receipt.getPurpose(), false);
//
//        float fitSize = (pageWidth-OFFSET*2) * FONT_SIZE_SMALL/v;
//        stream.setFontAndSize(font, fitSize);
//        stream.setTextMatrix(OFFSET, 680);
//        stream.showText(receipt.getPurpose());
//
//        stream.setFontAndSize(font, FONT_SIZE_SMALL);
//
//        String amount = NumberFormat.getCurrencyInstance()
//                .format(receipt.getAmount());
//        v = stream.getEffectiveStringWidth(amount, false);
//        stream.setTextMatrix(pageWidth - v - OFFSET, 655);
//        stream.showText(amount);
//
//        v = stream.getEffectiveStringWidth(
//                receipt.getDate() + "", false);
//        stream.setTextMatrix(pageWidth - v - OFFSET, 630);
//        stream.showText(receipt.getDate() + "");
//
//        v = stream.getEffectiveStringWidth(
//                receipt.getName(), false);
//        stream.setTextMatrix(pageWidth - v - OFFSET, 605);
//        stream.showText(receipt.getName());
//
        stream.endText();
        stamper.setFullCompression();
        stamper.close();
    }

    static class Receipt {
        private String purpose;
        private double amount;
        private Date date = new Date();
        private String name;

        public Receipt(String purpose, double amount, String name) {
            this.purpose = purpose;
            this.amount = amount;
            this.name = name;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
