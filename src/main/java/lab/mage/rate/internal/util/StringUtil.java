package lab.mage.rate.internal.util;

import java.util.Collection;
import java.util.Iterator;

public class StringUtil {

    private StringUtil() {
        super();
    }

    public static String join(final Collection<?> collection, final String separator) {
        final Iterator<?> iterator = collection.iterator();

        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            return first.toString();
        }

        final StringBuffer buffer = new StringBuffer(256);
        if (first != null) {
            buffer.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buffer.append(separator);
            }

            final Object obj = iterator.next();
            if (obj != null) {
                buffer.append(obj);
            }
        }
        return buffer.toString();
    }
}
