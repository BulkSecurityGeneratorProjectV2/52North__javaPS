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
package org.n52.javaps.io.literal.xsd;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class LiteralStringType extends AbstractXSDLiteralType<String> {

    private static final long serialVersionUID = -6712022215592337318L;

    @Override
    public String getName() {
        return STRING;
    }

    @Override
    public String parse(String value) {
        return value;
    }

    @Override
    public Class<String> getPayloadType() {
        return String.class;
    }

    @Override
    public String generate(String value) {
        return value;
    }

}
