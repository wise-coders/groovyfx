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
    final videoURL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv"
    stage(title: "GroovyFX MediaView Demo", visible: true) {
        scene(width: 540, height: 200) {
            mediaView(mediaPlayer: mediaPlayer(source: videoURL, autoPlay: true))
        }
    }
}
