package ru.job4j.magnit;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Class ConvertXSLT.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 10.02.2020
 */
public class ConvertXSLT {

    /**
     * A source file.
     */
    private final Path source;

    /**
     * A file in which the formatted result will be saved.
     */
    private final Path dest;

    /**
     * An XSL-transformer.
     */
    private final String scheme;

    public ConvertXSLT(String source, String dest, String scheme) {
        String property = System.getProperty("user.dir");
        String sep = File.separator;
        this.source = Paths.get(Objects.requireNonNull(String.join(
                sep, property, "src", "main", "java", "io", "tmpdir", source)));
        this.dest = Paths.get(Objects.requireNonNull(String.join(
                sep, property, "src", "main", "java", "io", "tmpdir", dest)));
        this.scheme = scheme;
    }

    /**
     * Converts an xml-file to the file with a different xml-style.
     *
     * @return a transformed file.
     * @throws Exception, .
     */
    public Path convert() throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(new FileInputStream(this.scheme)));
        transformer.transform(new StreamSource(Files.newInputStream(this.source)),
                new StreamResult(this.dest.toFile()));
        return this.dest;
    }
}
