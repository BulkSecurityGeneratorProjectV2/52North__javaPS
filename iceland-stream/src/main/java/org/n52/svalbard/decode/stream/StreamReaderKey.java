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
package org.n52.svalbard.decode.stream;

import java.util.Objects;

import org.n52.janmayen.http.MediaType;
import org.n52.janmayen.similar.Similar;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class StreamReaderKey implements Similar<StreamReaderKey> {
    private final MediaType mediaType;

    public StreamReaderKey(MediaType mediaType) {
        this.mediaType = Objects.requireNonNull(mediaType);
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    @Override
    public int getSimilarity(StreamReaderKey other) {
        return equals(other) ? 0 : -1;
    }

}
