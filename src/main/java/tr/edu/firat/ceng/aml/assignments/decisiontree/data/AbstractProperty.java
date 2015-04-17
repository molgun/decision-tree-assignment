/*
 * Copyright 2015 Muhammed Olgun <141129113@ogrenci.firat.edu.tr>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tr.edu.firat.ceng.aml.assignments.decisiontree.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 * @param <V>
 */
public abstract class AbstractProperty<V> implements Property<V> {

    private final String name;
    private final List<V> values;

    public AbstractProperty(String name) {
        this.name = name;
        this.values = new ArrayList<V>();
    }

    public AbstractProperty(Property<V> property) {
        this.name = property.getName();
        this.values = property.getCopiedValues();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<V> getCopiedValues() {
        List<V> newValues = new ArrayList<V>();
        newValues.addAll(values);
        return newValues;
    }

    @Override
    public List<V> getValues() {
        return this.values;
    }

    @Override
    public Integer size() {
        return values.size();
    }

    @Override
    public int getRawFrequency(Condition condition) {
        int count = 0;
        for (V value : values) {
            if (condition.execute(value)) {
                count++;
            }
        }
        return count;
    }
}
