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
package org.n52.javaps.service.operator.validation;

import javax.inject.Inject;

import org.n52.iceland.request.operator.ParameterValidator;
import org.n52.javaps.engine.Engine;
import org.n52.shetland.ogc.ows.service.OwsServiceRequest;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class EngineParameterValidator<T extends OwsServiceRequest> implements ParameterValidator<T> {

    private Engine engine;

    @Inject
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    protected Engine getEngine() {
        return this.engine;
    }

}
