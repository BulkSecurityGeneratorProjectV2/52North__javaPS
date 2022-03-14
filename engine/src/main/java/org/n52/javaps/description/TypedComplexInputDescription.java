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
package org.n52.javaps.description;

import org.n52.shetland.ogc.wps.description.ComplexInputDescription;
import org.n52.javaps.io.complex.ComplexData;

/**
 *
 * @author Christian Autermann
 */
public interface TypedComplexInputDescription extends ComplexInputDescription, TypedComplexDescription,
        TypedProcessInputDescription<Class<? extends ComplexData<?>>> {
    @Override
    default TypedComplexInputDescription asComplex() {
        return this;
    }

    interface Builder<T extends TypedComplexInputDescription, B extends Builder<T, B>> extends
            ComplexInputDescription.Builder<T, B>, TypedComplexDescription.Builder<T, B> {

    }

}
