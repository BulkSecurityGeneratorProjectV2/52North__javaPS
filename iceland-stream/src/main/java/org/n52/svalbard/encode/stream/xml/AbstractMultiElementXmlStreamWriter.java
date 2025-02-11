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
package org.n52.svalbard.encode.stream.xml;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.n52.svalbard.encode.stream.StreamWriterKey;
import org.n52.svalbard.encode.stream.UnsupportedStreamWriterInputException;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AbstractMultiElementXmlStreamWriter extends AbstractElementXmlStreamWriter {

    private final Set<StreamWriterKey> keys;

    public AbstractMultiElementXmlStreamWriter(Class<?>... supportedClasses) {
        this.keys = Arrays.stream(supportedClasses).map(XmlStreamWriterKey::new).collect(toSet());
    }

    protected UnsupportedStreamWriterInputException unsupported(Object object) {
        return new UnsupportedStreamWriterInputException(object);
    }

    @Override
    public Set<StreamWriterKey> getKeys() {
        return Collections.unmodifiableSet(this.keys);
    }
}
