/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.parser.stmt.rfc6020;

import java.util.Collection;

import org.opendaylight.yangtools.yang.model.api.Rfc6020Mapping;
import org.opendaylight.yangtools.yang.model.api.meta.EffectiveStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.BitStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.TypeStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;
import org.opendaylight.yangtools.yang.parser.spi.source.SourceException;
import org.opendaylight.yangtools.yang.parser.stmt.rfc6020.effective.type.BitsSpecificationEffectiveStatementImpl;

public class BitsSpecificationImpl extends AbstractDeclaredStatement<String> implements TypeStatement.BitsSpecification {

    protected BitsSpecificationImpl(StmtContext<String, TypeStatement.BitsSpecification, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<String, TypeStatement.BitsSpecification, EffectiveStatement<String, TypeStatement.BitsSpecification>> {

        public Definition() {
            super(Rfc6020Mapping.TYPE);
        }

        @Override
        public String parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) throws SourceException {
            return value;
        }

        @Override
        public TypeStatement.BitsSpecification createDeclared(
                StmtContext<String, TypeStatement.BitsSpecification, ?> ctx) {
            return new BitsSpecificationImpl(ctx);
        }

        @Override
        public EffectiveStatement<String, TypeStatement.BitsSpecification> createEffective(
                StmtContext<String, TypeStatement.BitsSpecification, EffectiveStatement<String, TypeStatement.BitsSpecification>> ctx) {
            return new BitsSpecificationEffectiveStatementImpl(ctx);
        }
    }

    @Override
    public String getName() {
        return argument();
    }

    @Override
    public Collection<? extends BitStatement> getBits() {
        return allDeclared(BitStatement.class);
    }

}