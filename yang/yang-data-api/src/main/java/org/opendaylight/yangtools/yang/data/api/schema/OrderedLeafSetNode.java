/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.data.api.schema;


/**
 *
 * Leaf set node which preserves user-supplied ordering.
 *
 * This node represents a data instance of <code>leaf-list</code> with
 * <code>ordered-by user;</code> substatement.
 *
 * Except preserving user-ordering all other semantics and behaviour is same
 * as in {@link LeafSetNode}.
 *
 * @param <T> Value type of Leaf entries
 */
public interface OrderedLeafSetNode<T> extends LeafSetNode<T>, OrderedNodeContainer<LeafSetEntryNode<T>> {

}
