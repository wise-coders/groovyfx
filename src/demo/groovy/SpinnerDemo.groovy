/*
 * Copyright 2011-2016 the original author or authors.
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

import javafx.scene.control.ButtonBar
import javafx.scene.control.SpinnerValueFactory

import static groovyx.javafx.GroovyFX.start

start {
    stage(title: "Spinner", show: true) {
        scene(fill: WHITE, width: 300, height: 200) {
            vbox {
                spinner(valueFactory: new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 50))
                spinner(valueFactory: new SpinnerValueFactory.DoubleSpinnerValueFactory(0d, 1d, 0d, 0.1d))
            }
        }
    }
}

