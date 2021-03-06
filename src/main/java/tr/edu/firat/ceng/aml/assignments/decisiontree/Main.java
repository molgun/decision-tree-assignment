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
package tr.edu.firat.ceng.aml.assignments.decisiontree;

import java.io.IOException;
import java.util.Scanner;
import tr.edu.firat.ceng.aml.assignments.decisiontree.data.Dataset;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.DecisionTree;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.impl.DecisionTreeBuilder;
import tr.edu.firat.ceng.aml.assignments.decisiontree.tree.impl.GiniGainImpl;
import tr.edu.firat.ceng.aml.assignments.decisiontree.util.CSV2DatasetUtil;

/**
 *
 * @author Muhammed Olgun <141129113@ogrenci.firat.edu.tr>
 */
public class Main {

    public static void main(String[] args) {
        CSV2DatasetUtil util = new CSV2DatasetUtil();
        try {
            Dataset dataset = util.convert("iris.data");
            DecisionTreeBuilder builder = new DecisionTreeBuilder(new GiniGainImpl());
            DecisionTree tree = builder.build(dataset);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                if (tree.getResult() != null) {
                    System.out.println(tree.getResult());
                    break;
                }
                System.out.println("Enter " + tree.getPropertyName() + " value:");
                tree = tree.next(scanner.nextDouble());
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
