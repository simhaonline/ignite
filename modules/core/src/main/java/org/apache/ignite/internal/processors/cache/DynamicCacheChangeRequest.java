/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.processors.cache;

import org.apache.ignite.configuration.*;
import org.apache.ignite.internal.util.tostring.*;
import org.apache.ignite.internal.util.typedef.internal.*;
import org.apache.ignite.lang.*;

import java.io.*;
import java.util.*;

/**
 * Cache start/stop request.
 */
public class DynamicCacheChangeRequest implements Serializable {
    /** */
    private static final long serialVersionUID = 0L;

    /** Start ID. */
    private IgniteUuid deploymentId;

    /** Stop cache name. */
    @GridToStringExclude
    private String cacheName;

    /** Cache start configuration. */
    private CacheConfiguration startCfg;

    /** Near node ID in case if near cache is being started. */
    private UUID clientNodeId;

    /** Near cache configuration. */
    private NearCacheConfiguration nearCacheCfg;

    /**
     * Constructor creates cache start request.
     *
     * @param startCfg Start cache configuration.
     */
    public DynamicCacheChangeRequest(
        CacheConfiguration startCfg
    ) {
        this.startCfg = startCfg;

        deploymentId = IgniteUuid.randomUuid();
    }

    /**
     * Constructor creates cache stop request.
     *
     * @param cacheName Cache stop name.
     */
    public DynamicCacheChangeRequest(String cacheName) {
        this.cacheName = cacheName;
    }

    /**
     * Constructor creates near cache start request.
     *
     * @param clientNodeId Client node ID.
     * @param startCfg Start cache configuration.
     * @param nearCacheCfg Near cache configuration.
     */
    public DynamicCacheChangeRequest(UUID clientNodeId, CacheConfiguration startCfg, NearCacheConfiguration nearCacheCfg) {
        this.clientNodeId = clientNodeId;
        this.startCfg = startCfg;
        this.nearCacheCfg = nearCacheCfg;
    }

    /**
     * @return Deployment ID.
     */
    public IgniteUuid deploymentId() {
        return deploymentId;
    }

    /**
     * @param deploymentId Deployment ID.
     */
    public void deploymentId(IgniteUuid deploymentId) {
        this.deploymentId = deploymentId;
    }

    /**
     * @return {@code True} if this is a start request.
     */
    public boolean isStart() {
        return clientNodeId == null && startCfg != null;
    }

    /**
     * @return If this is a near cache start request.
     */
    public boolean isClientStart() {
        return clientNodeId != null;
    }

    /**
     * @return {@code True} if this is a stop request.
     */
    public boolean isStop() {
        return clientNodeId == null && startCfg == null;
    }

    /**
     * @return Cache name.
     */
    public String cacheName() {
        return cacheName != null ? cacheName : startCfg.getName();
    }

    /**
     * @return Near node ID.
     */
    public UUID clientNodeId() {
        return clientNodeId;
    }

    /**
     * @return Near cache configuration.
     */
    public NearCacheConfiguration nearCacheCfg() {
        return nearCacheCfg;
    }

    /**
     * @return Cache configuration.
     */
    public CacheConfiguration startCacheConfiguration() {
        return startCfg;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(DynamicCacheChangeRequest.class, this, "cacheName", cacheName());
    }
}
