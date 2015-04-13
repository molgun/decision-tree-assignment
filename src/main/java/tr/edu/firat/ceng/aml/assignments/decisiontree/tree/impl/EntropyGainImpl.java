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
package tr.edu.firat.ceng.aml.assignments.decisiontree.tree.impl;

import tr.edu.firat.ceng.aml.assignments.decisiontree.data.ClassProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Dataset;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.NominalProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Property;
import tr.edu.firat.ceng.aml.assignments.decisiontree.exception.UnproperPropertyException;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.DecisionTree;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.Gain;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 */
public class EntropyGainImpl implements Gain {

    private Dataset dataset;

    public EntropyGainImpl() {
    }

    public EntropyGainImpl(Dataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public double getGain(Property property) {
        if (property instanceof NominalProperty) {
            NominalProperty nominalProperty = (NominalProperty) property;
            double totalEntropy = 0;
            for (String uniqueValue : nominalProperty.getUniqueValues()) {
                double propertyRelativeFrequency = nominalProperty.getUniqueValueRawFrequency(uniqueValue) / nominalProperty.size();
                double entropy = 0;
                for (int i = 0; i < dataset.getClassProperty().getUniqueValues().size(); i++) {
                    String get = dataset.getClassProperty().getUniqueValues().get(i);
                    double relativeFrequency = dataset.getClassProperty().getUniqueValueRawFrequency(get) / nominalProperty.getUniqueValueRawFrequency(uniqueValue);
                    entropy -= relativeFrequency * (Math.log(relativeFrequency) * Math.log(2));
                }
                entropy = entropy * propertyRelativeFrequency;
                totalEntropy += entropy;
            }

            double gain = getClassPropertyEntropy() - totalEntropy;
            return gain;
        }

        throw new UnproperPropertyException("Entropy is unreliable for numeric properties. Please use Gini!");
    }

    private double getClassPropertyEntropy() {
        double classPropertyEntropy = 0.0;
        ClassProperty classProperty = dataset.getClassProperty();
        for (String uniquevalue : classProperty.getUniqueValues()) {
            int count = 0;
            for (String classPropertyValue : classProperty.getValues()) {
                if (classPropertyValue.equals(uniquevalue)) {
                    count++;
                }
            }
            double relativeFrequency = count / classProperty.size();
            classPropertyEntropy -= relativeFrequency * (Math.log(relativeFrequency) / Math.log(2));
        }
        return classPropertyEntropy;
    }

    @Override
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public DecisionTree getPart(Property property) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
