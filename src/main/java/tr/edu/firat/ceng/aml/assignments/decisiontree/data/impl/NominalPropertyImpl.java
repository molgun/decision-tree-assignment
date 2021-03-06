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
package tr.edu.firat.ceng.aml.assignments.decisiontree.data.impl;

import java.util.ArrayList;
import java.util.List;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.AbstractProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.NominalProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Property;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 */
public class NominalPropertyImpl extends AbstractProperty<String> implements NominalProperty {


    public NominalPropertyImpl(String name) {
        super(name);
    }

    public NominalPropertyImpl(Property<String> property) {
        super(property);
    }
    
    @Override
    public List<String> getUniqueValues() {
        List<String> uniques = new ArrayList<String>();
        for (String value : getValues()) {
            if (!uniques.contains(value)) {
                uniques.add(value);
            }
        }
        return uniques;
    }

    @Override
    public Integer getUniqueValueRawFrequency(String uniqueValue) {
        Integer rawFrequency = 0;
        for (String value : getValues()) {
            if (uniqueValue.equals(value)) {
                rawFrequency++;
            }
        }
        return rawFrequency;
    }
}
