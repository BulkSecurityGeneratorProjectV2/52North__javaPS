/*
 * Copyright 2016-2022 52°North Spatial Information Research GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.n52.javaps.io;

import java.util.Objects;

import org.n52.javaps.algorithm.ProcessInputs;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class GroupInputData implements Data<ProcessInputs> {

    private static final long serialVersionUID = -8747518748439185542L;

    private final ProcessInputs payload;

    public GroupInputData(ProcessInputs payload) {
        this.payload = Objects.requireNonNull(payload);
    }

    @Override
    public ProcessInputs getPayload() {
        return this.payload;
    }

    @Override
    public Class<?> getSupportedClass() {
        return ProcessInputs.class;
    }

}
