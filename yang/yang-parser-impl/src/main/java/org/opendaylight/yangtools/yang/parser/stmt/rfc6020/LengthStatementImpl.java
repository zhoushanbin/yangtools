/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.parser.stmt.rfc6020;

import java.util.List;

import org.opendaylight.yangtools.yang.model.api.Rfc6020Mapping;
import org.opendaylight.yangtools.yang.model.api.meta.EffectiveStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.DescriptionStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.ErrorAppTagStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.ErrorMessageStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.LengthStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.ReferenceStatement;
import org.opendaylight.yangtools.yang.model.api.type.LengthConstraint;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;
import org.opendaylight.yangtools.yang.parser.stmt.rfc6020.effective.type.LengthEffectiveStatementImpl;

public class LengthStatementImpl extends AbstractDeclaredStatement<List<LengthConstraint>> implements LengthStatement {

    protected LengthStatementImpl(StmtContext<List<LengthConstraint>, LengthStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<List<LengthConstraint>, LengthStatement, EffectiveStatement<List<LengthConstraint>, LengthStatement>> {

        public Definition() {
            super(Rfc6020Mapping.LENGTH);
        }

        @Override
        public List<LengthConstraint> parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) {
            return TypeUtils.parseLengthListFromString(value);
        }

        @Override
        public LengthStatement createDeclared(StmtContext<List<LengthConstraint>, LengthStatement, ?> ctx) {
            return new LengthStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<List<LengthConstraint>, LengthStatement> createEffective(
                StmtContext<List<LengthConstraint>, LengthStatement, EffectiveStatement<List<LengthConstraint>, LengthStatement>> ctx) {
            return new LengthEffectiveStatementImpl(ctx);
        }
    }

    @Override
    public ErrorAppTagStatement getErrorAppTagStatement() {
        return firstDeclared(ErrorAppTagStatement.class);
    }

    @Override
    public ErrorMessageStatement getErrorMessageStatement() {
        return firstDeclared(ErrorMessageStatement.class);
    }

    @Override
    public DescriptionStatement getDescription() {
        return firstDeclared(DescriptionStatement.class);
    }

    @Override
    public ReferenceStatement getReference() {
        return firstDeclared(ReferenceStatement.class);
    }

    @Override
    public List<LengthConstraint> getValue() {
        return argument();
    }
}