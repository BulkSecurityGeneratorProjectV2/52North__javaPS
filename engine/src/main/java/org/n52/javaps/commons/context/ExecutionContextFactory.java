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
package org.n52.javaps.commons.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ExecutionContextFactory {

    private static Logger LOG = LoggerFactory.getLogger(ExecutionContextFactory.class);

    private static final ThreadLocal<ExecutionContext> THREAD_CONTEXTS = new ThreadLocal<ExecutionContext>();

    private static ExecutionContext defaultContext;

    public static ExecutionContext getContext() {
        return getContext(true);
    }

    public static ExecutionContext getContext(boolean fallBackToDefault) {
        ExecutionContext executionContext = null;
        synchronized (THREAD_CONTEXTS) {
            executionContext = THREAD_CONTEXTS.get();
            if (executionContext == null && fallBackToDefault) {
                executionContext = getDefault();
            }
        }
        return executionContext;
    }

    public static synchronized ExecutionContext getDefault() {
        if (defaultContext == null) {
            defaultContext = new ExecutionContext();
        }
        return defaultContext;
    }

    public static void registerContext(ExecutionContext context) {
        synchronized (THREAD_CONTEXTS) {
            THREAD_CONTEXTS.set(context);
        }

        LOG.info("Context registered");
    }

    public static void unregisterContext() {
        synchronized (THREAD_CONTEXTS) {
            THREAD_CONTEXTS.remove();
        }

        LOG.info("Context unregistered");
    }
}
