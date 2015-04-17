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

import java.util.ArrayList;
import java.util.List;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.ClassProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Condition;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Conditions;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Dataset;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.NumericProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Property;
import tr.edu.firat.ceng.aml.assignments.decisiontree.exception.UnproperPropertyException;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.Gain;
import tr.edu.firat.ceng.aml.assignments.decisiontree.util.PreProcessUtils;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 */
public class GiniGainImpl implements Gain {

    private Dataset dataset;
    private double classPropertyGini;

    public GiniGainImpl() {
        classPropertyGini = -1;
    }

    public GiniGainImpl(Dataset dataset) {
        this.dataset = dataset;
        initClassPropertyGini();
    }

    @Override
    public Condition getBestCondition(Property property) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getGain(Property property) {
        if (property instanceof NumericProperty) {
            if (classPropertyGini == -1) {
                initClassPropertyGini();
            }
            NumericProperty numericProperty = (NumericProperty) property;
            ClassProperty classProperty = dataset.getClassProperty();
            List<Number> splitted = PreProcessUtils.split(numericProperty);
            List<Conditions> conditions = new ArrayList<Conditions>();
            conditions.add(Conditions.LESS_OR_EQUAL);
            conditions.add(Conditions.GREATER);
            double bestGini = 0;
            for (Number splittedNumber : splitted) {
                double gini = 0;
                double inside = 1;
                for (Conditions condition : conditions) {
                    inside = 1;
                    int totalFrequency = dataset.getTotalNumberOfValuesProviding(condition.getCondition(splittedNumber));
                    for (String uniqueValue : classProperty.getUniqueValues()) {
                        int rawFrequency = dataset.getPropertyFrequency(property.getName(), condition.getCondition(splittedNumber), Conditions.EQUALS.getCondition(uniqueValue));
                        double fraction = 0;
                        if (totalFrequency != 0) {
                            fraction = rawFrequency / totalFrequency;
                        }
                        double value = Math.pow(fraction, 2);
                        inside -= value;
                    }
                    inside *= totalFrequency / dataset.size();
                }
                gini += inside;
                if (gini > bestGini) {
                    bestGini = gini;
                }
            }
            return classPropertyGini - bestGini;
        }
        throw new UnproperPropertyException("Gini gain does not support nominal properties! Yet.");
    }

    @Override
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
        initClassPropertyGini();
    }

    private void initClassPropertyGini() {
        double gini = 1.0;
        ClassProperty classProperty = dataset.getClassProperty();
        for (String uniquevalue : classProperty.getUniqueValues()) {
            int rawFrequency = classProperty.getUniqueValueRawFrequency(uniquevalue);
            double relativeFrequency = (double) rawFrequency / classProperty.size();
            gini -= Math.pow(relativeFrequency, 2);
        }
        this.classPropertyGini = gini;
    }
}
