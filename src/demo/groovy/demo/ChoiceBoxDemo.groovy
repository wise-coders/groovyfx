/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2011-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import static groovyx.javafx.GroovyFX.start

/**
 * @author dean
 */
start { primaryStage ->
    stage(title: "GroovyFX Choice Box Demo", width: 400, height: 200, visible: true) {
        scene(fill: ALICEBLUE) {
            vbox(padding: 10, spacing: 5) {
                choiceBox(value: 'blue', items: ['blue', 'green', 'red']) {
                    onSelect { control, item ->
                        println "Got selection for $control, item is $item"
                    }
                }
            }
        }
    }
}

