package ru.job4j.magnit;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Class XMLUsage.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 10.02.2020
 */
public class XMLUsage {

    @XmlRootElement
    public static class Entries {

        private List<Entry> values;

        public Entries() {
        }

        public Entries(List<Entry> values) {
            this.values = values;
        }

        @XmlElement(name = "entry")
        public List<Entry> getValues() {
            return values;
        }

        public void setValues(List<Entry> values) {
            this.values = values;
        }
    }

    public static class Entry {

        private int field;

        public Entry() {
        }

        public Entry(int value) {
            this.field = value;
        }

        public int getField() {
            return field;
        }

        public void setField(int field) {
            this.field = field;
        }
    }
}
