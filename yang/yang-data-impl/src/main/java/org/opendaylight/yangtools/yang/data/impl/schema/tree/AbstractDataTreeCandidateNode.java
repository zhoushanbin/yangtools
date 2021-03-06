/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.data.impl.schema.tree;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nullable;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier.PathArgument;
import org.opendaylight.yangtools.yang.data.api.schema.NormalizedNode;
import org.opendaylight.yangtools.yang.data.api.schema.NormalizedNodeContainer;
import org.opendaylight.yangtools.yang.data.api.schema.tree.DataTreeCandidateNode;

abstract class AbstractDataTreeCandidateNode implements DataTreeCandidateNode {
    private static final Function<NormalizedNode<?, ?>, DataTreeCandidateNode> TO_DELETED_NODE = new Function<NormalizedNode<?, ?>, DataTreeCandidateNode>() {
        @Override
        public DataTreeCandidateNode apply(final NormalizedNode<?, ?> input) {
            return AbstractRecursiveCandidateNode.deleteNode(input);
        }
    };
    private static final Function<NormalizedNode<?, ?>, DataTreeCandidateNode> TO_WRITTEN_NODE = new Function<NormalizedNode<?, ?>, DataTreeCandidateNode>() {
        @Override
        public DataTreeCandidateNode apply(final NormalizedNode<?, ?> input) {
            return AbstractRecursiveCandidateNode.writeNode(input);
        }
    };

    private static Optional<NormalizedNode<?, ?>> getChild(final NormalizedNodeContainer<?, PathArgument, NormalizedNode<?, ?>> container, final PathArgument identifier) {
        if (container != null) {
            return container.getChild(identifier);
        } else {
            return Optional.absent();
        }
    }

    static DataTreeCandidateNode deltaChild(
            final NormalizedNodeContainer<?, PathArgument, NormalizedNode<?, ?>> oldData,
            final NormalizedNodeContainer<?, PathArgument, NormalizedNode<?, ?>> newData, final PathArgument identifier) {

        final Optional<NormalizedNode<?, ?>> maybeNewChild = getChild(newData, identifier);
        final Optional<NormalizedNode<?, ?>> maybeOldChild = getChild(oldData, identifier);
        if (maybeOldChild.isPresent()) {
            final NormalizedNode<?, ?> oldChild = maybeOldChild.get();
            if (maybeNewChild.isPresent()) {
                return AbstractRecursiveCandidateNode.replaceNode(oldChild, maybeNewChild.get());
            } else {
                return TO_DELETED_NODE.apply(oldChild);
            }
        } else {
            if (maybeNewChild.isPresent()) {
                return TO_WRITTEN_NODE.apply(maybeNewChild.get());
            } else {
                return null;
            }
        }
    }

    static Collection<DataTreeCandidateNode> deltaChildren(@Nullable final NormalizedNodeContainer<?, PathArgument, NormalizedNode<?, ?>> oldData,
            @Nullable final NormalizedNodeContainer<?, PathArgument, NormalizedNode<?, ?>> newData) {
        if (newData == null) {
            return Collections2.transform(oldData.getValue(), TO_DELETED_NODE);
        }
        if (oldData == null) {
            return Collections2.transform(newData.getValue(), TO_WRITTEN_NODE);
        }

        /*
         * This is slightly inefficient, as it requires N*F(M)+M*F(N) lookup operations, where
         * F is dependent on the implementation of NormalizedNodeContainer.getChild().
         *
         * We build the return collection by iterating over new data and looking each child up
         * in old data. Based on that we construct replaced/written nodes. We then proceed to
         * iterate over old data and looking up each child in new data.
         */
        final Collection<DataTreeCandidateNode> result = new ArrayList<>();
        for (NormalizedNode<?, ?> child : newData.getValue()) {
            final DataTreeCandidateNode node;
            final Optional<NormalizedNode<?, ?>> maybeOldChild = oldData.getChild(child.getIdentifier());

            if (maybeOldChild.isPresent()) {
                // This does not find children which have not in fact been modified, as doing that
                // reliably would require us running a full equals() on the two nodes.
                node = AbstractRecursiveCandidateNode.replaceNode(maybeOldChild.get(), child);
            } else {
                node = AbstractRecursiveCandidateNode.writeNode(child);
            }

            result.add(node);
        }

        // Process removals next, looking into new data to see if we processed it
        for (NormalizedNode<?, ?> child : oldData.getValue()) {
            if (!newData.getChild(child.getIdentifier()).isPresent()) {
                result.add(AbstractRecursiveCandidateNode.deleteNode(child));
            }
        }

        return result;
    }

    private final NormalizedNodeContainer<?, PathArgument, NormalizedNode<?,?>> data;

    protected AbstractDataTreeCandidateNode(final NormalizedNodeContainer<?, PathArgument, NormalizedNode<?, ?>> data) {
        this.data = Preconditions.checkNotNull(data);
    }

    protected final Optional<NormalizedNode<?, ?>> dataOptional() {
        return Optional.<NormalizedNode<?, ?>>of(data);
    }

    @Override
    public final PathArgument getIdentifier() {
        return data.getIdentifier();
    }

    protected final NormalizedNodeContainer<?, PathArgument, NormalizedNode<?, ?>> getData() {
        return data;
    }
}
