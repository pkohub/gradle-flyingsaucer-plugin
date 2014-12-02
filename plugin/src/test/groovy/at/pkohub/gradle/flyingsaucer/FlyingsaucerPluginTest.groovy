/*
 * Copyright 2014 the original author or authors.
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
package at.pkohub.gradle.flyingsaucer

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Task
import spock.lang.Specification
import at.pkohub.gradle.flyingsaucer.tasks.Flyingsaucer

/**
 * Test case for Flyingsaucer plugin.
 *
 * @author Philipp Kolmhofer
 */
class FlyingsaucerPluginTest extends Specification {
    
    private Project project
    
    def setup() {
        project = ProjectBuilder.builder().build()
        project.apply(plugin: FlyingsaucerPlugin)
    }
    
    def "Plugin is applied correctly"() {
        expect:
            project.tasks.withType(Flyingsaucer).size() == 1
    }
}
