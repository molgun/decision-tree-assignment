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

import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.AbstractRule;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.NominalRule;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.Rule;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 */
public class NominalRuleImpl extends AbstractRule implements NominalRule {
    
    private String nominalRule;

    public NominalRuleImpl(String propertyName) {
        super(propertyName);
    }
    
    @Override
    public Rule executeRule(String rule) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
