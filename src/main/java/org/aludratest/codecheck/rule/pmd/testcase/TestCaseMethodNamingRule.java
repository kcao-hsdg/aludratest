/*
 * Copyright (C) 2010-2014 Hamburg Sud and the contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aludratest.codecheck.rule.pmd.testcase;

import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;

import org.aludratest.codecheck.rule.pmd.AbstractRegexNamingRule;

/** Requires the names of test case methods to match a regular expression.
 * @author Volker Bergmann */
public class TestCaseMethodNamingRule extends AbstractRegexNamingRule {

    private static final String DEFAULT_REGEX = "[a-z][A-Za-z0-9]*";
    private static final String DEFAULT_MESSAGE = "Test method names should start with a lowercase letter "
            + "and contain only letters and digits";

    /** Default constructor, setting the default regular expression */
    public TestCaseMethodNamingRule() {
        super(DEFAULT_REGEX, DEFAULT_MESSAGE);
    }

    @Override
    public Object visit(ASTMethodDeclarator node, Object data) {
        // Skip methods of non-test-classes
        if (!isTestCaseClass(node)) {
            return super.visit(node, data);
        }

        // Skip methods which do not have a @Test annotation
        AbstractJavaNode parent = (AbstractJavaNode) node.getNthParent(2);
        if (!parent.hasDescendantMatchingXPath("Annotation/MarkerAnnotation[Name/@Image='Test']")) {
            return super.visit(node, data);
        }

        // verify the method name
        String methodName = node.getImage();
        assertMatch(methodName, node, data);

        return data;
    }

}
