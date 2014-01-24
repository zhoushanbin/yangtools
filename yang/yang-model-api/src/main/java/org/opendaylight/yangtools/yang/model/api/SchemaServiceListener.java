/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.model.api;

import java.util.EventListener;

import org.opendaylight.yangtools.yang.model.api.SchemaContext;

// TODO rename to schemaContextListener
public interface SchemaServiceListener extends EventListener {

    void onGlobalContextUpdated(SchemaContext context);

}