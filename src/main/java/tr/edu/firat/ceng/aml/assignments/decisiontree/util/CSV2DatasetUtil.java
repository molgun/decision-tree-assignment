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
package tr.edu.firat.ceng.aml.assignments.decisiontree.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.ClassProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Dataset;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.NumericProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Property;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.impl.ClassPropertyImpl;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.impl.DatasetImpl;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.impl.NumericPropertyImpl;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 */
public final class CSV2DatasetUtil {

    public Dataset convert(String resourceName) throws UnsupportedEncodingException, IOException {
        Reader reader = null;
        try {
            List<Property> properties = new ArrayList<Property>();
            properties.add(new NumericPropertyImpl("sepal_length"));
            properties.add(new NumericPropertyImpl("sepal_width"));
            properties.add(new NumericPropertyImpl("petal_length"));
            properties.add(new NumericPropertyImpl("petal_width"));
            ClassProperty classProperty = new ClassPropertyImpl("class");
            final URL url = getClass().getResource(resourceName);
            reader = new InputStreamReader(url.openStream(), "UTF-8");
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);
            for (CSVRecord record : parser) {
                for (int i = 0; i < properties.size(); i++) {
                    Property get = properties.get(i);
                    if (get instanceof NumericProperty) {
                        NumericProperty numericProperty = (NumericProperty) get;
                        numericProperty.addValue(new Double(record.get(i)));
                    }
                }
                classProperty.addValue(record.get(properties.size()));
            }
            Dataset dataset = new DatasetImpl(classProperty);
            dataset.addProperties(properties);
            return dataset;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    private List<String> getNominalProperty(CSVParser parser, int index) {
        List<String> values = new ArrayList<String>();
        for (CSVRecord record : parser) {
            values.add(record.get(index));
        }
        return values;
    }

    private List<Number> getNumericProperty(CSVParser parser, int index) {
        List<Number> values = new ArrayList<Number>();
        for (CSVRecord record : parser) {
            values.add(new Double(record.get(index)));
        }
        return values;
    }
}
