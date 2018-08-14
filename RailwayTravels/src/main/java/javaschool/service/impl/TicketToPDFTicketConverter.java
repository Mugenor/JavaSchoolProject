package javaschool.service.impl;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BarcodePDF417;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javaschool.entity.Departure;
import javaschool.entity.Passenger;
import javaschool.entity.Ticket;
import javaschool.service.converter.StringToLocalDateConverter;
import javaschool.service.converter.StringToLocalDateTimeConverter;
import javax.annotation.PostConstruct;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class TicketToPDFTicketConverter {
    private static final int FONT_SIZE_SMALL = 16;
    private static final int ROW_SIZE = 26;
    private static final int FONT_SIZE_BIG = 32;
    private static final int X_OFFSET = 40;
    private static final int Y_OFFSET = 705;

    @Value("${ticket.pdf.file}")
    private String pathToTicketTemplate;
    private byte[] ticketTemplate;
    private StringToLocalDateConverter stringToLocalDateConverter;
    private StringToLocalDateTimeConverter stringToLocalDateTimeConverter;

    public TicketToPDFTicketConverter(StringToLocalDateConverter stringToLocalDateConverter,
                                      StringToLocalDateTimeConverter stringToLocalDateTimeConverter) {
        this.stringToLocalDateConverter = stringToLocalDateConverter;
        this.stringToLocalDateTimeConverter = stringToLocalDateTimeConverter;
    }

    @PostConstruct
    public void init() throws IOException {
        String absolutePathToTicketTemplate = new ClassPathResource(pathToTicketTemplate).getFile().getAbsolutePath();
        ticketTemplate = Files.readAllBytes(Paths.get(absolutePathToTicketTemplate));
    }


    public void writePDFTicket(OutputStream dist, Ticket ticket) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(ticketTemplate);
        PdfStamper pdfStamper = new PdfStamper(reader, dist);

        PdfContentByte stream = pdfStamper.getOverContent(1);
        final float pageWidth = reader.getPageSize(1).getWidth();
        stream.beginText();
        writePassengerData(stream, ticket.getPassenger(), pageWidth);
        writeTripData(stream, ticket, pageWidth, Y_OFFSET - ROW_SIZE * 4);
        writeBarcode(stream, ticket, X_OFFSET, Y_OFFSET - ROW_SIZE * 10);
        stream.endText();
        pdfStamper.setFullCompression();
        pdfStamper.close();
    }

    private void writeBarcode(PdfContentByte stream, Ticket ticket, int x, int y) throws BadElementException, DocumentException {
        BarcodePDF417 barcode = new BarcodePDF417();
        barcode.setText(ticket.getUuid().toString());
        Image image = barcode.getImage();
        image.scalePercent(300);
        image.setAbsolutePosition(x, y);
        stream.addImage(image);
    }

    private void writeTripData(PdfContentByte stream, Ticket ticket, float pageWidth, int yOffset)
            throws IOException, DocumentException {
        BaseFont font = BaseFont.createFont();
        Departure departureFrom = ticket.getFrom();
        Departure departureTo = ticket.getTo();
        int x = (int) pageWidth - X_OFFSET;
        int y = yOffset;
        stream.setFontAndSize(font, FONT_SIZE_SMALL);
        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT, departureFrom.getStationFrom().getTitle(),
                x, y, 0.0f);
        y -= ROW_SIZE;
        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT,
                stringToLocalDateTimeConverter.convertFrom(departureFrom.getDateTimeFrom()),
                x, y, 0.0f);
        y -= ROW_SIZE;
        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT, departureTo.getStationTo().getTitle(),
                x, y, 0.0f);
        y -= ROW_SIZE;
        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT,
                stringToLocalDateTimeConverter.convertFrom(departureTo.getDateTimeTo()),
                x, y, 0.0f);
    }

    private void writePassengerData(PdfContentByte stream, Passenger passenger, final float pageWidth) throws IOException, DocumentException {
        BaseFont font = BaseFont.createFont();
        int x = (int) pageWidth - X_OFFSET;
        int y = Y_OFFSET;
        stream.setFontAndSize(font, FONT_SIZE_SMALL);
        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT, passenger.getName(), x, y, 0.0f);
        y -= ROW_SIZE;
        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT, passenger.getSurname(), x, y, 0.0f);
        y -= ROW_SIZE;
        stream.showTextAligned(PdfContentByte.ALIGN_RIGHT,
                stringToLocalDateConverter.convertFrom(passenger.getBirthday()), x, y, 0.0f);
    }
}
