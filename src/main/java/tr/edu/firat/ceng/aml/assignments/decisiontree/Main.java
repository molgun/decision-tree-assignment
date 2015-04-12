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
package tr.edu.firat.ceng.aml.assignments.decisiontree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.ClassProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Dataset;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Property;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.impl.ClassPropertyImpl;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.impl.DatasetImpl;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.impl.NumericPropertyImpl;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.impl.GiniGainImpl;
import tr.edu.firat.ceng.aml.assignments.decisiontree.util.CSV2DatasetUtil;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 */
public class Main {

    public static void main(String[] args) {
//        Main main = new Main();
//        Dataset dataset = main.createDataset();
//        Property property = dataset.getPropertyByName("a3");
//        GiniGainImpl instance = new GiniGainImpl(dataset);
//        Double expResult = null;
//        Double result = instance.getGain(property);
        //System.out.println(result);
//        System.out.println(result);
        CSV2DatasetUtil util = new CSV2DatasetUtil();
        try {
            Dataset dataset = util.convert("iris.data");
            GiniGainImpl instance = new GiniGainImpl(dataset);
            Property mostValuable = null;
            double gainValue = 0;
            for (Property property : dataset.getProperties()) {
                double newGainValue = instance.getGain(property);
                if(mostValuable == null) {
                    mostValuable = property;
                    gainValue = newGainValue;
                } else if (gainValue < newGainValue) {
                    mostValuable = property;
                    gainValue = newGainValue;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
