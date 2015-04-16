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
package tr.edu.firat.ceng.aml.assignments.decisiontree.util;

import java.util.ArrayList;
import java.util.List;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.NumericProperty;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.edu.tr>
 */
public final class PreProcessUtils {

    public static final List<Number> split(NumericProperty property) {
        List<Number> splitted = new ArrayList<Number>();
        List<Number> sorted = QuickSort.quicksort(splitted);
        splitted.add(sorted.get(0).doubleValue() - (sorted.get(0).doubleValue() / 2));

        for (int i = 0; i < sorted.size() - 1; i++) {
            splitted.add((sorted.get(i).doubleValue() + sorted.get(i + 1).doubleValue()) / 2);
        }

        splitted.add(sorted.get(0).doubleValue() + (sorted.get(0).doubleValue() / 2));
        return splitted;
    }

    private static class QuickSort {

        public QuickSort() {
        }

        /**
         * This method sort the input ArrayList using quick sort algorithm.
         *
         * @param input the ArrayList of integers.
         * @return sorted ArrayList of integers.
         */
        private static List<Number> quicksort(List<Number> input) {

            if (input.size() <= 1) {
                return input;
            }

            int middle = (int) Math.ceil((double) input.size() / 2);
            Number pivot = input.get(middle);

            List<Number> less = new ArrayList<Number>();
            List<Number> greater = new ArrayList<Number>();

            for (int i = 0; i < input.size(); i++) {
                if (input.get(i).doubleValue() <= pivot.doubleValue()) {
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
        private static List<Number> concatenate(List<Number> less, Number pivot, List<Number> greater) {

            List<Number> list = new ArrayList<Number>();

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
