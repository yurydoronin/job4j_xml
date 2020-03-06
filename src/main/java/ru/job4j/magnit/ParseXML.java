package ru.job4j.magnit;

import javax.xml.stream.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class ParseXML.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 10.02.2020
 */
public class ParseXML {

    /**
     * A final sum of all Entries' values.
     */
    private int sum;

    /**
     * A formatted xml-file.
     */
    private final Path source;

    public ParseXML(Path source) {
        this.source = source;
    }

    /**
     * Parsing of the converted xsl-file, using StAX-analyzer.
     *
     * @return the sum of all elements in the table.
     * @throws IOException,        .
     * @throws XMLStreamException, .
     */
    public int sumOfAllElements() throws IOException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = factory.createXMLStreamReader(Files.newInputStream(source));
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                if (parser.getLocalName().equals("entry")) {
                    this.sum = sum + Integer.parseInt(parser.getAttributeValue(null, "field"));
                }
            }
        }
        return this.sum;
    }
}
