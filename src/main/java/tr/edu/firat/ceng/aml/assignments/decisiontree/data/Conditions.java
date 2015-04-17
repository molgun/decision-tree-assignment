/*
 * Copyright 2015 Muhammed Olgun <141129113@ogrenci.edu.tr>.
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

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.edu.tr>
 */
public enum Conditions {

    EQUALS, LESS_OR_EQUAL, GREATER;

    public Condition getCondition(final Object value) {
        if (this.equals(Conditions.EQUALS)) {
            return new Condition() {

                @Override
                public boolean execute(Object conditionValue) {
                    return value.equals(conditionValue);
                }

                @Override
                public Object getValue() {
                    return value;
                }
            };
        }

        if (this.equals(Conditions.GREATER)) {
            if (value instanceof Number) {
                final Number valueNumber = (Number) value;
                return new Condition() {

                    @Override
                    public boolean execute(Object conditionValue) {
                        if (conditionValue instanceof Number) {
                            Number conditionValueNumber = (Number) conditionValue;
                            if (conditionValueNumber.doubleValue() > valueNumber.doubleValue()) {
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public Object getValue() {
                        return value;
                    }
                };
            }
        }

        if (this.equals(Conditions.LESS_OR_EQUAL)) {
            if (value instanceof Number) {
                final Number valueNumber = (Number) value;
                return new Condition() {

                    @Override
                    public boolean execute(Object conditionValue) {
                        if (conditionValue instanceof Number) {
                            Number conditionValueNumber = (Number) conditionValue;
                            if (conditionValueNumber.doubleValue() <= valueNumber.doubleValue()) {
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public Object getValue() {
                        return value;
                    }
                };
            }
        }

        throw new RuntimeException("How did you get this point!");
    }
}
