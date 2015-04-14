/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.firat.ceng.aml.assignments.decisiontree.tree.impl;

import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Dataset;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Property;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.DecisionTree;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.Gain;

/**
 *
 * @author molgun
 */
public class DecisionTreeBuilder {

    private final Gain gain;

    public DecisionTreeBuilder(Gain gain) {
        this.gain = gain;
    }

    public DecisionTree build(Dataset dataset) {
        gain.setDataset(dataset);
        Property valuable = getMostValuable(dataset);
        DecisionTree dt = gain.getPart(valuable);
        if (dt.getLeft() == null) {
            Dataset newDataset = dataset.createCopyOfDataset(valuable, dt.getConditionValue(),false);
            dt.setLeft(build(newDataset));

        }
        if (dt.getRight() == null) {
            Dataset newDataset = dataset.createCopyOfDataset(valuable, dt.getConditionValue(),true);
            dt.setRight(build(newDataset));

        }
        return dt;
    }

    private Property getMostValuable(Dataset dataset) {
        gain.setDataset(dataset);
        Property mostValuable = null;
        double bestGain = -1;
        for (Property property : dataset.getProperties()) {
            double propertyGain = gain.getGain(property);
            if (propertyGain > bestGain) {
                bestGain = propertyGain;
                mostValuable = property;
            }
        }
        return mostValuable;
    }
}
