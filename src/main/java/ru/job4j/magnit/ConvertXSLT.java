package ru.job4j.magnit;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
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
     *
     */
    private Path source;

    /**
     *
     */
    private Path dest;

    /**
     *
     */
    private String scheme;

    /**
     *
     * @param source, .
     * @param dest, .
     * @param scheme, .
     * @throws URISyntaxException, .
     */
    public ConvertXSLT(String source, String dest, String scheme) throws URISyntaxException {
        String property = System.getProperty("user.dir");
        String sep = File.separator;
        this.source = Paths.get(Objects.requireNonNull(String.join(
                sep, property, "chapter_007", "src", "main", "java", "io", "tmpdir", source)));
        this.dest = Paths.get(Objects.requireNonNull(String.join(
                sep, property, "chapter_007", "src", "main", "java", "io", "tmpdir", dest)));
        this.scheme = Objects.requireNonNull(
                ConvertXSLT.class.getClassLoader().getResource(scheme)).toURI().getPath();
    }

    /**
     *
     * @return .
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
