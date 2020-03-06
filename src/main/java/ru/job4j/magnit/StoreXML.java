package ru.job4j.magnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Class StoreXML.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 10.02.2020
 */
public class StoreXML {

    private static final Logger LOG = LoggerFactory.getLogger(StoreXML.class);

    /**
     * The file which the data from DP should be saved into.
     */
    private final Path target;

    StoreXML(String target) {
        String property = System.getProperty("user.dir");
        String sep = File.separator;
        this.target = Paths.get(Objects.requireNonNull(String.join(
                sep, property, "src", "main", "java", "io", "tmpdir", target)));
    }

    /**
     * Packing Entry-elements with values into the root Entries-element,
     * and saving its into an xml-file, using JAXB.
     */
    public void save() {
        try (StoreSQL sql = new StoreSQL(new Config())) {
            JAXBContext jaxbContext = JAXBContext.newInstance(XMLUsage.Entries.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.marshal(new XMLUsage.Entries(sql.load()), target.toFile());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
