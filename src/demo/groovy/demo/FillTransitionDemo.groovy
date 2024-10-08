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

start {
    stage(title: 'GroovyFX Fill Transition Demo', width: 400, height: 300, visible: true, resizable: true) {
        scene(fill: ALICEBLUE) {
            rectangle(x: 20, y: 20, width: 100, height: 50, fill: BLUE) {
                fillTransition(4.s, delay: 1000.ms, interpolator: EASE_IN, to: RED,
                        onFinished: { println 'done'}).playFromStart()
            }
        }
    }
}

