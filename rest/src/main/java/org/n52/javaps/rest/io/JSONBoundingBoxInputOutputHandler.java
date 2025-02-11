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
package org.n52.javaps.rest.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.n52.javaps.description.TypedProcessInputDescription;
import org.n52.javaps.description.TypedProcessOutputDescription;
import org.n52.javaps.io.Data;
import org.n52.javaps.io.DecodingException;
import org.n52.javaps.io.EncodingException;
import org.n52.javaps.io.InputHandler;
import org.n52.javaps.io.OutputHandler;
import org.n52.javaps.io.bbox.BoundingBoxData;
import org.n52.shetland.ogc.ows.OwsBoundingBox;
import org.n52.shetland.ogc.wps.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;

@Component
public class JSONBoundingBoxInputOutputHandler implements InputHandler, OutputHandler {
    private static final Set<Format> FORMATS = Collections.singleton(new Format("application/json"));
    private static final Logger log = LoggerFactory.getLogger(JSONBoundingBoxInputOutputHandler.class);
    private static final Set<Class<? extends Data<?>>> BINDINGS = Collections.singleton(BoundingBoxData.class);
    private static final String CRS_KEY = "crs";
    private static final String BBOX = "bbox";
    private final ObjectMapper objectMapper;
    private final JsonNodeFactory jsonNodeFactory;

    @Autowired
    public JSONBoundingBoxInputOutputHandler(ObjectMapper objectMapper, JsonNodeFactory jsonNodeFactory) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.jsonNodeFactory = Objects.requireNonNull(jsonNodeFactory);
    }

    @Override
    public Set<Format> getSupportedFormats() {
        return FORMATS;
    }

    @Override
    public Set<Class<? extends Data<?>>> getSupportedBindings() {
        return BINDINGS;
    }

    @Override
    public InputStream generate(TypedProcessOutputDescription<?> description, Data<?> data, Format format)
            throws IOException, EncodingException {

        InputStream result = null;

        if (data instanceof BoundingBoxData) {
            OwsBoundingBox boundingBox = ((BoundingBoxData) data).getPayload();

            double[] lowerCorner = boundingBox.getLowerCorner();
            double[] upperCorner = boundingBox.getUpperCorner();

            String crs = "";

            try {
                crs = boundingBox.getCRS().get().toString();
            } catch (Exception e) {
                log.info("No crs present.");
            }

            ObjectNode rootNode = jsonNodeFactory.objectNode();

            // TODO what about 6 coordinates?
            ArrayNode arrayNode = rootNode.arrayNode();
            arrayNode.add(lowerCorner[0]);
            arrayNode.add(lowerCorner[1]);
            arrayNode.add(upperCorner[0]);
            arrayNode.add(upperCorner[1]);
            rootNode.set(BBOX, arrayNode);
            rootNode.put(CRS_KEY, crs);

            result = new ByteArrayInputStream(rootNode.toString().getBytes(Charsets.UTF_8));
        }

        return result;
    }

    @Override
    public Data<?> parse(TypedProcessInputDescription<?> description, InputStream input, Format format)
            throws IOException, DecodingException {

        JsonNode bboxNode = objectMapper.readTree(input);

        ArrayNode arrayNode = (ArrayNode) bboxNode.get(BBOX);

        JsonNode crsNode = bboxNode.path(CRS_KEY);

        OwsBoundingBox boundingBox = null;
        try {
            boundingBox = createOWSBoundingbox(crsNode.asText(), arrayNode.get(0).asDouble(),
                    arrayNode.get(1).asDouble(), arrayNode.get(2).asDouble(), arrayNode.get(3).asDouble());
        } catch (URISyntaxException e) {
            throw new DecodingException(e);
        }

        return new BoundingBoxData(boundingBox);
    }

    private OwsBoundingBox createOWSBoundingbox(String crsNodeString, double minX, double minY, double maxX,
            double maxY) throws URISyntaxException {

        double[] lowerCorner = new double[] { minX, minY };
        double[] upperCorner = new double[] { maxX, maxY };
        URI crs = new URI(crsNodeString);
        return new OwsBoundingBox(lowerCorner, upperCorner, crs);
    }

}
