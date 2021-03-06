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
package tr.edu.firat.ceng.aml.assignments.decisiontree.data;

import java.util.List;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 * @param <V>
 */
public interface Property<V> {

    public String getName();

    public Integer size();

    public List<V> getValues();

    public List<V> getCopiedValues();
    
    public int getNumberOfValuesProviding(Condition condition);
}
