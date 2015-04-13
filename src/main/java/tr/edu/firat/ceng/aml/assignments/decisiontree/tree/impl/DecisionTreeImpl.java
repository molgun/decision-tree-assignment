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

import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.DecisionTree;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 */
public class DecisionTreeImpl implements DecisionTree {

    private String propertyName;
    private Object conditionValue;
    private String result;
    private DecisionTree left;
    private DecisionTree right;

    public DecisionTreeImpl() {
    }

    public DecisionTreeImpl(String result) {
        this.result = result;
    }

    @Override
    public DecisionTree next(Object value) {
        if (value instanceof Number) {
            if (((Number) value).doubleValue() > ((Number) conditionValue).doubleValue()) {
                return right;
            }
            return left;
        }
        throw new RuntimeException();
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    @Override
    public void setLeft(DecisionTree left) {
        this.left = left;
    }

    @Override
    public void setRight(DecisionTree right) {
        this.right = right;
    }

    public void setConditionValue(Object conditionValue) {
        this.conditionValue = conditionValue;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Object getConditionValue() {
        return this.conditionValue;
    }
}
