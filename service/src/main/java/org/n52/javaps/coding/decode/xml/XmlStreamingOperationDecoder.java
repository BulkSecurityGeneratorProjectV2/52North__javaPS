/*
 * Copyright 2016 52°North Initiative for Geospatial Open Source
 * Software GmbH
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
package org.n52.javaps.coding.decode.xml;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.xml.namespace.QName;

import org.n52.iceland.coding.OperationKey;
import org.n52.iceland.coding.decode.Decoder;
import org.n52.iceland.coding.decode.DecoderKey;
import org.n52.iceland.coding.decode.DecodingException;
import org.n52.iceland.coding.decode.OperationDecoderKey;
import org.n52.iceland.coding.decode.OwsDecodingException;
import org.n52.iceland.coding.decode.XmlStringOperationDecoderKey;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.util.http.MediaTypes;
import org.n52.javaps.coding.stream.MissingStreamReaderException;
import org.n52.javaps.coding.stream.StreamReader;
import org.n52.javaps.coding.stream.StreamReaderRepository;
import org.n52.javaps.coding.stream.xml.XmlStreamReaderKey;

/**
 * TODO JavaDoc
 * @author Christian Autermann
 */
public class XmlStreamingOperationDecoder implements Decoder<Object, String> {
    private final QName name;
    private final OperationKey operation;
    private final StreamReaderRepository streamReaderRepository;

    public XmlStreamingOperationDecoder(OperationKey operation, QName name, StreamReaderRepository streamReaderRepository) {
        this.name = Objects.requireNonNull(name);
        this.operation = Objects.requireNonNull(operation);
        this.streamReaderRepository = Objects.requireNonNull(streamReaderRepository);
    }

    @Override
    public Object decode(String string)
            throws DecodingException {
        XmlStreamReaderKey key = new XmlStreamReaderKey(this.name);
        StreamReader<Object> reader = streamReaderRepository.getReader(key)
                        .orElseThrow(() -> new MissingStreamReaderException(key));
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        try {
            return reader.read(new ByteArrayInputStream(bytes));
        } catch (OwsExceptionReport ex) {
            throw new OwsDecodingException(ex);
        }
    }

    @Override
    public Set<DecoderKey> getKeys() {
        return new HashSet<>(Arrays
                .asList(new OperationDecoderKey(operation, MediaTypes.TEXT_XML),
                        new OperationDecoderKey(operation, MediaTypes.APPLICATION_XML),
                        new XmlStringOperationDecoderKey(operation, MediaTypes.APPLICATION_XML),
                        new XmlStringOperationDecoderKey(operation, MediaTypes.TEXT_XML)));

    }

}
