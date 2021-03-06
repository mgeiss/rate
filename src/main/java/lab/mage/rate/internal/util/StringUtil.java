/**
 * Copyright 2013 Markus Geiss
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
