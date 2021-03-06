/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.data.impl.schema.transform.base.parser;

import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier;
import org.opendaylight.yangtools.yang.data.api.schema.MapEntryNode;
import org.opendaylight.yangtools.yang.data.api.schema.OrderedMapNode;
import org.opendaylight.yangtools.yang.data.impl.schema.Builders;
import org.opendaylight.yangtools.yang.data.impl.schema.builder.api.CollectionNodeBuilder;
import org.opendaylight.yangtools.yang.model.api.ListSchemaNode;

/**
 * Abstract(base) parser for OrderedListNodes, parses elements of type E.
 *
 * @param <E>
 *            type of elements to be parsed
 */
public abstract class OrderedListNodeBaseParser<E> extends
        ListNodeBaseParser<E, MapEntryNode, OrderedMapNode, ListSchemaNode> {

    public OrderedListNodeBaseParser() {
    }

    public OrderedListNodeBaseParser(final BuildingStrategy<YangInstanceIdentifier.NodeIdentifier, OrderedMapNode> buildingStrategy) {
        super(buildingStrategy);
    }

    @Override
    protected CollectionNodeBuilder<MapEntryNode, OrderedMapNode> provideBuilder(ListSchemaNode schema) {
        return Builders.orderedMapBuilder(schema);
    }

}
