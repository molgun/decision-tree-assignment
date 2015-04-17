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
import java.util.Objects;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.AbstractProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.ClassProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Condition;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Dataset;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.NominalProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.NumericProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Property;
import tr.edu.firat.ceng.aml.assignments.decisiontree.exception.UnproperPropertyException;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 */
public class DatasetImpl implements Dataset {

    private final ClassProperty classProperty;
    private List<Property> properties;

    public DatasetImpl(ClassProperty classProperty) {
        this.classProperty = classProperty;
        this.properties = new ArrayList<Property>();
    }

    public DatasetImpl(Dataset dataset) {
        classProperty = new ClassPropertyImpl(dataset.getClassProperty());
        properties = dataset.getCopiedProperties();
    }

    @Override
    public void printDataset() {
        for (Property property : properties) {
            int countTop = property.getName().length();
            for (int i = 0; i < countTop; i++) {
                System.out.print("-");
            }
        }
        System.out.println("");
        for (Property property : properties) {
            System.out.print(property.getName() + "\t");
        }
        System.out.println(classProperty.getName());
        for (int i = 0; i < classProperty.getValues().size(); i++) {
            for (Property property : properties) {
                Object get = property.getValues().get(i);
                System.out.print(get + "\t");
            }
            System.out.println(classProperty.getValues().get(i));
        }
    }

    @Override
    public Dataset createCopyOfDataset(Property property, Object value, boolean isGreater) {
        DatasetImpl datasetImpl = new DatasetImpl(this);
        property = datasetImpl.getPropertyByName(property.getName());
        for (int i = 0; i < property.getValues().size(); i++) {
            Object val = property.getValues().get(i);
            if (property instanceof NumericProperty) {
                if ((((Number) val).doubleValue() > ((Number) value).doubleValue()) ^ isGreater) {
                    datasetImpl.removeRaw(i);
                }
            }
        }
        datasetImpl.removeProperty(property);
        return datasetImpl;
    }

    @Override
    public ClassProperty getClassProperty() {
        return classProperty;
    }

    @Override
    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public List<Property> getCopiedProperties() {
        List<Property> properties2Return = new ArrayList<Property>();
        for (Property property : properties) {
            if (property instanceof NumericProperty) {
                properties2Return.add(new NumericPropertyImpl((AbstractProperty<Number>) property));
            } else if (property instanceof NominalProperty) {
                properties2Return.add(new NominalPropertyImpl((AbstractProperty<String>) property));
            }
        }
        return properties2Return;
    }

    @Override
    public void addProperty(Property property) {
        if (!Objects.equals(property.size(), classProperty.size())) {
            throw new UnproperPropertyException("Property values and class values must be equal!");
        }

        for (Property prop : properties) {
            if (prop.getName().equals(property.getName())) {
                throw new UnproperPropertyException("There is a property with the same name!");
            }
        }

        properties.add(property);
    }

    @Override
    public void removeProperty(Property property) {
        properties.remove(property);
    }

    @Override
    public Property getPropertyByName(String name) {
        for (Property property : properties) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }

    @Override
    public void addProperties(List<Property> properties) {
        for (Property prop : properties) {
            addProperty(prop);
        }
    }

    public void removeRaw(int index) {
        for (Property property : properties) {
            property.getValues().remove(index);
        }
        classProperty.getValues().remove(index);
    }

    @Override
    public int size() {
        return classProperty.size();
    }
    
    @Override
    public int getTotalNumberOfValuesProviding(Condition condition) {
        int total = 0;
        for (Property property : properties) {
            total += property.getRawFrequency(condition);
        }
        return total;
    }

    @Override
    public int getPropertyFrequency(String propertyName, Condition propertyCondition, Condition classPropertyCondition) {
        Property property = getPropertyByName(propertyName);
        if (property == null) {
            throw new RuntimeException("Property not found!");
        }
        int frequency = 0;
        for (int i = 0; i < property.getValues().size(); i++) {
            Object get = property.getValues().get(i);
            if (propertyCondition.execute(get) && classPropertyCondition.execute(classProperty.getValues().get(i))) {
                frequency++;
            }
        }
        return frequency;
    }
}
