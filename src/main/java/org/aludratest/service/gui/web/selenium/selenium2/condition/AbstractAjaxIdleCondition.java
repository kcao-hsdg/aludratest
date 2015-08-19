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
package org.aludratest.service.gui.web.selenium.selenium2.condition;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public abstract class AbstractAjaxIdleCondition implements ExpectedCondition<Boolean> {

    protected abstract String getBooleanAjaxIdleScript();

    @Override
    public Boolean apply(WebDriver input) {
        try {
            if (input instanceof JavascriptExecutor) {
                JavascriptExecutor exec = (JavascriptExecutor) input;
                Object o = exec.executeScript(getBooleanAjaxIdleScript());
                if (o instanceof Boolean) {
                    return (Boolean) o;
                }
                else if (o instanceof String) {
                    return "true".equals(o);
                }
                else {
                    // e.g. "undefined"
                    return Boolean.TRUE;
                }
            }
            else {
                // no JavaScript executor - expect AJAX not to be run (--> to be idle)
                return Boolean.TRUE;
            }
        }
        catch (Exception e) {
            // e.g. framework not present
            return Boolean.TRUE;
        }
    }
}
