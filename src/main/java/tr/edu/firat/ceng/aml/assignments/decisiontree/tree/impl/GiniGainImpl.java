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
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Dataset;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.NumericProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Property;
import tr.edu.firat.ceng.aml.assignments.decisiontree.exception.UnproperPropertyException;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.DecisionTree;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.Gain;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 */
public class GiniGainImpl implements Gain {

    private Dataset dataset;
    private Split split;

    public GiniGainImpl() {
    }

    public GiniGainImpl(Dataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public DecisionTree getPart(Property property) {
        double gain = getGain(property);
        DecisionTreeImpl decisionTreeImpl = new DecisionTreeImpl();
        decisionTreeImpl.setPropertyName(property.getName());
        for (UniqueValueHolder holder : split.getValues()) {
            if (holder.getNumberOfGreater() == split.getTotalNumberOfGreater()) {
                decisionTreeImpl.setRight(new DecisionTreeImpl(holder.getClassName()));
            }

            if (holder.getNumberOfLessOrEqual() == split.getTotalNumberOfLessOrEqual()) {
                decisionTreeImpl.setLeft(new DecisionTreeImpl(holder.getClassName()));
            }

        }

        if (dataset.getProperties().size() == 1) {
            List<UniqueValueHolder> holders = split.getValues();
            String rightClass = "";
            String leftClass = "";
            int rightCount = 0;
            int leftCount = 0;
            for (UniqueValueHolder holder : holders) {
                if (holder.getNumberOfGreater() > rightCount) {
                    rightCount = holder.getNumberOfGreater();
                    rightClass = holder.getClassName();
                }

                if (holder.getNumberOfLessOrEqual() > leftCount) {
                    leftClass = holder.getClassName();
                    leftCount = holder.getNumberOfLessOrEqual();
                }
            }
            decisionTreeImpl.setLeft(new DecisionTreeImpl(leftClass));
            decisionTreeImpl.setRight(new DecisionTreeImpl(rightClass));
            decisionTreeImpl.setConditionValue(split.getSplitValue());
            return decisionTreeImpl;
        }

        decisionTreeImpl.setConditionValue(split.getSplitValue());
        return decisionTreeImpl;
    }

    @Override
    public double getGain(Property property) {
        if (property instanceof NumericProperty) {
            NumericProperty numericProperty = (NumericProperty) property;
            ClassProperty classProperty = dataset.getClassProperty();
            double classPropertyGini = getClassPropertyGini();
            List<GiniIndex> giniIndexes = new ArrayList<GiniIndex>();
            for (int i = 0; i < numericProperty.getValues().size(); i++) {
                Number get = numericProperty.getValues().get(i);
                giniIndexes.add(new GiniIndex(i, get, classProperty.getValues().get(i)));
            }

            List<GiniIndex> sorted = new GiniIndexQuickSorter().quicksort(giniIndexes);

            List<Split> splitTable = splitGiniIndex(sorted);
            for (Split split : splitTable) {
                for (String uniqueValue : classProperty.getUniqueValues()) {
                    UniqueValueHolder holder = new UniqueValueHolder();
                    holder.setClassName(uniqueValue);
                    int numberOfLessOrEqual = 0;
                    int numberOfGreater = 0;
                    for (GiniIndex giniIndex : sorted) {
                        if (giniIndex.getClazz().equals(uniqueValue)) {
                            if (giniIndex.getValue().doubleValue() <= split.getSplitValue().doubleValue()) {
                                numberOfLessOrEqual++;
                            } else if (giniIndex.getValue().doubleValue() > split.getSplitValue().doubleValue()) {
                                numberOfGreater++;
                            }
                        }
                    }
                    holder.setNumberOfGreater(numberOfGreater);
                    holder.setNumberOfLessOrEqual(numberOfLessOrEqual);
                    split.addValue(holder);
                }
            }
            Double gain = 0.0;
            for (int i = 1; i < splitTable.size() - 1; i++) {
                Split split = splitTable.get(i);
                double gini = 0.0;
                double inside = 1.0;
                double lessOrEqual = split.getTotalNumberOfLessOrEqual();
                double greater = split.getTotalNumberOfGreater();
                for (String uniqueValue : classProperty.getUniqueValues()) {
                    UniqueValueHolder holder = split.getHolderByName(uniqueValue);
                    double lessOrEqualNumerator = holder.getNumberOfLessOrEqual();
                    if (lessOrEqual == 0) {
                        continue;
                    }
                    double fraction = lessOrEqualNumerator / split.getTotalNumberOfLessOrEqual();
                    double value = Math.pow(fraction, 2);
                    inside -= value;
                }
                double totalFrequency = lessOrEqual / (double) classProperty.size();
                gini += inside * totalFrequency;

                inside = 1.0;
                double greaterNumerator = 0;
                for (String uniqueValue : classProperty.getUniqueValues()) {
                    UniqueValueHolder holder = split.getHolderByName(uniqueValue);
                    greaterNumerator = holder.getNumberOfGreater();
                    if (greater == 0) {
                        continue;
                    }
                    double fraction = greaterNumerator / greater;
                    double value = Math.pow(fraction, 2);
                    inside -= value;
                }
                totalFrequency = greater / (double) classProperty.size();
                gini += inside * totalFrequency;
                if (classPropertyGini - gini > gain) {
                    gain = classPropertyGini - gini;
                    this.split = split;
                }
            }
            return gain;
        }
        throw new UnproperPropertyException("Gini gain does not support nominal properties! Yet.");
    }

    @Override
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    private double getClassPropertyGini() {
        double classPropertyEntropy = 1.0;
        ClassProperty classProperty = dataset.getClassProperty();
        for (String uniquevalue : classProperty.getUniqueValues()) {
            int rawFrequency = classProperty.getUniqueValueRawFrequency(uniquevalue);
            double relativeFrequency = (double) rawFrequency / classProperty.size();
            classPropertyEntropy -= Math.pow(relativeFrequency, 2);
        }
        return classPropertyEntropy;
    }

    private List<Split> splitGiniIndex(List<GiniIndex> sorted) {
        List<Split> splitTable = new ArrayList<Split>();

        Split firstSplit = new Split(sorted.get(0).getValue().doubleValue() - (sorted.get(0).getValue().doubleValue() / 2));
        //add first value
        splitTable.add(firstSplit);
        for (int i = 0; i < sorted.size() - 1; i++) {
            Split split = new Split((sorted.get(i).getValue().doubleValue() + sorted.get(i + 1).getValue().doubleValue()) / 2);
            splitTable.add(split);
        }
        //add last value
        Split lastSplit = new Split(sorted.get(sorted.size() - 1).getValue().doubleValue() + (sorted.get(sorted.size() - 1).getValue().doubleValue() / 2));
        splitTable.add(lastSplit);
        return splitTable;
    }

    private class Split {

        private final Number splitValue;
        private List<UniqueValueHolder> values;

        public Split(Number splitValue) {
            this.splitValue = splitValue;
            this.values = new ArrayList<UniqueValueHolder>();
        }

        public Number getSplitValue() {
            return splitValue;
        }

        public void addValue(UniqueValueHolder holder) {
            values.add(holder);
        }

        public UniqueValueHolder getHolderByName(String name) {
            for (UniqueValueHolder value : values) {
                if (value.getClassName().equals(name)) {
                    return value;
                }
            }
            return null;
        }

        public List<UniqueValueHolder> getValues() {
            return values;
        }

        public double getTotalNumberOfLessOrEqual() {
            double lessOrEqual = 0;
            for (UniqueValueHolder value : values) {
                lessOrEqual += value.getNumberOfLessOrEqual();
            }
            return lessOrEqual;
        }

        public double getTotalNumberOfGreater() {
            double greater = 0;
            for (UniqueValueHolder value : values) {
                greater += value.getNumberOfGreater();
            }
            return greater;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Split { ")
                    .append("splitValue = " + splitValue + ", ")
                    .append("values = " + values + " ")
                    .append("}");
            return builder.toString();
        }

    }

    private class UniqueValueHolder {

        private String className;
        private int numberOfLessOrEqual;
        private int numberOfGreater;

        public String getClassName() {
            return className;
        }

        public int getNumberOfGreater() {
            return numberOfGreater;
        }

        public int getNumberOfLessOrEqual() {
            return numberOfLessOrEqual;
        }

        public void setNumberOfGreater(int numberOfGreater) {
            this.numberOfGreater = numberOfGreater;
        }

        public void setNumberOfLessOrEqual(int numberOfLessOrEqual) {
            this.numberOfLessOrEqual = numberOfLessOrEqual;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder()
                    .append("{ className = " + className + ", ")
                    .append("numberOfLessOrEqual = " + numberOfLessOrEqual + ", ")
                    .append("numberOfGreater = " + numberOfGreater + " }");
            return builder.toString();
        }
    }

    private class GiniIndex {

        private final int index;
        private final Number value;
        private final String clazz;

        public GiniIndex(int index, Number value, String clazz) {
            this.index = index;
            this.value = value;
            this.clazz = clazz;
        }

        public int getIndex() {
            return index;
        }

        public Number getValue() {
            return value;
        }

        public String getClazz() {
            return clazz;
        }
    }

    private class GiniIndexQuickSorter {

        public GiniIndexQuickSorter() {
        }

        /**
         * This method sort the input ArrayList using quick sort algorithm.
         *
         * @param input the ArrayList of integers.
         * @return sorted ArrayList of integers.
         */
        private List<GiniIndex> quicksort(List<GiniIndex> input) {

            if (input.size() <= 1) {
                return input;
            }

            int middle = (int) Math.ceil((double) input.size() / 2);
            GiniIndex pivot = input.get(middle);

            List<GiniIndex> less = new ArrayList<GiniIndex>();
            List<GiniIndex> greater = new ArrayList<GiniIndex>();

            for (int i = 0; i < input.size(); i++) {
                if (input.get(i).getValue().doubleValue() <= pivot.getValue().doubleValue()) {
                    if (i == middle) {
                        continue;
                    }
                    less.add(input.get(i));
                } else {
                    greater.add(input.get(i));
                }
            }

            return concatenate(quicksort(less), pivot, quicksort(greater));
        }

        /**
         * Join the less array, pivot integer, and greater array to single
         * array.
         *
         * @param less integer ArrayList with values less than pivot.
         * @param pivot the pivot integer.
         * @param greater integer ArrayList with values greater than pivot.
         * @return the integer ArrayList after join.
         */
        private List<GiniIndex> concatenate(List<GiniIndex> less, GiniIndex pivot, List<GiniIndex> greater) {

            List<GiniIndex> list = new ArrayList<GiniIndex>();

            for (int i = 0; i < less.size(); i++) {
                list.add(less.get(i));
            }

            list.add(pivot);

            for (int i = 0; i < greater.size(); i++) {
                list.add(greater.get(i));
            }

            return list;
        }
    }

}
