/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.parser.stmt.rfc6020;

import java.math.BigDecimal;
import java.util.List;

import org.opendaylight.yangtools.yang.model.api.Rfc6020Mapping;
import org.opendaylight.yangtools.yang.model.api.meta.EffectiveStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.DescriptionStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.ErrorAppTagStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.ErrorMessageStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.RangeStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.ReferenceStatement;
import org.opendaylight.yangtools.yang.model.api.type.RangeConstraint;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;
import org.opendaylight.yangtools.yang.parser.stmt.rfc6020.effective.type.RangeEffectiveStatementImpl;

public class RangeStatementImpl extends AbstractDeclaredStatement<List<RangeConstraint>> implements RangeStatement {

    // these objects are to compare whether range has MAX or MIN value
    // none of these values should appear as Yang number according to spec so they are safe to use
    public static final BigDecimal YANG_MIN_NUM = BigDecimal.valueOf(-Double.MAX_VALUE);
    public static final BigDecimal YANG_MAX_NUM = BigDecimal.valueOf(Double.MAX_VALUE);

    protected RangeStatementImpl(StmtContext<List<RangeConstraint>, RangeStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<List<RangeConstraint>, RangeStatement, EffectiveStatement<List<RangeConstraint>, RangeStatement>> {

        public Definition() {
            super(Rfc6020Mapping.RANGE);
        }

        @Override
        public List<RangeConstraint> parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) {
            return TypeUtils.parseRangeListFromString(value);
        }

        @Override
        public RangeStatement createDeclared(StmtContext<List<RangeConstraint>, RangeStatement, ?> ctx) {
            return new RangeStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<List<RangeConstraint>, RangeStatement> createEffective(
                StmtContext<List<RangeConstraint>, RangeStatement, EffectiveStatement<List<RangeConstraint>, RangeStatement>> ctx) {
            return new RangeEffectiveStatementImpl(ctx);
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
    public List<RangeConstraint> getRange() {
        return argument();
    }
}
