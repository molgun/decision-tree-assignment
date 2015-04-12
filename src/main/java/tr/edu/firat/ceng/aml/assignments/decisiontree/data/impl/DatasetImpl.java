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
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.ClassProperty;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Dataset;
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

    @Override
    public ClassProperty getClassProperty() {
        return classProperty;
    }

    @Override
    public List<Property> getProperties() {
        return properties;
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
}
