package ru.job4j.magnit;

/**
 * Class StartApp.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 03.03.2020
 */
public class StartApp {

    public static void main(String[] args) throws Exception {
        new StoreSQL(new Config()).generate(5);
        new StoreXML("target.xml").save();
        int sum = new ParseXML(
                new ConvertXSLT("target.xml", "output.xml", "scheme.xsl").convert()
        ).sumOfAllElements();
        System.out.printf("The sum of all elements: %d", sum);
    }
}
