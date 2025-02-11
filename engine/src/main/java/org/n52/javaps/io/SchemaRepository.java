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

import java.util.HashMap;
import java.util.Map;

public class SchemaRepository {

    private static Map<String, String> repository;

    private static Map<String, String> gmlNamespaces;

    public static synchronized String getSchemaLocation(String namespaceURI) {
        if (repository == null) {
            repository = new HashMap<>();
        }
        return repository.get(namespaceURI);

    }

    public static synchronized void registerSchemaLocation(String namespaceURI,
            String schemaLocation) {
        if (repository == null) {
            repository = new HashMap<>();
        }
        repository.put(namespaceURI, schemaLocation);

    }

    public static synchronized void registerGMLVersion(String namespaceURI,
            String gmlNamespace) {
        if (gmlNamespaces == null) {
            gmlNamespaces = new HashMap<>();
        }
        gmlNamespaces.put(namespaceURI, gmlNamespace);

    }

    public static synchronized String getGMLNamespaceForSchema(String namespace) {
        if (gmlNamespaces == null) {
            gmlNamespaces = new HashMap<>();
        }
        return gmlNamespaces.get(namespace);
    }
}
