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

    /**
     * A logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(StoreXML.class);

    /**
     * The file which the data from DP should be saved into.
     */
    private Path target;

    /**
     *
     * @param target, .
     */
    StoreXML(String target) {
        String property = System.getProperty("user.dir");
        String sep = File.separator;
        this.target = Paths.get(Objects.requireNonNull(String.join(
                sep, property, "chapter_007", "src", "main", "java", "io", "tmpdir", target)));
    }

    /**
     * Saves unloading from a database into an xml-file.
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
