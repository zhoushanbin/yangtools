/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.stmt.test;

import org.junit.Test;
import org.opendaylight.yangtools.yang.parser.spi.meta.ReactorException;
import org.opendaylight.yangtools.yang.parser.spi.source.SourceException;
import org.opendaylight.yangtools.yang.parser.spi.source.StatementStreamSource;
import org.opendaylight.yangtools.yang.parser.stmt.reactor.CrossSourceStatementReactor;
import org.opendaylight.yangtools.yang.parser.stmt.reactor.EffectiveModelContext;
import org.opendaylight.yangtools.yang.parser.stmt.rfc6020.YangInferencePipeline;
import org.opendaylight.yangtools.yang.parser.stmt.rfc6020.YangStatementSourceImpl;

import static org.junit.Assert.assertNotNull;

public class YangFileStmtTest {
        private static final YangStatementSourceImpl YANGFILE = new YangStatementSourceImpl("/semantic-statement-parser/test.yang");
        private static final YangStatementSourceImpl IMPORTEDYANGFILE = new YangStatementSourceImpl("/semantic-statement-parser/importedtest.yang");
        private static final YangStatementSourceImpl SIMPLENODES = new YangStatementSourceImpl("/semantic-statement-parser/simple-nodes-semantic.yang");
        private static final YangStatementSourceImpl FOO = new YangStatementSourceImpl("/semantic-statement-parser/foo.yang");
        private static final YangStatementSourceImpl FILE1 = new YangStatementSourceImpl("/model/bar.yang");
        private static final YangStatementSourceImpl FILE2 = new YangStatementSourceImpl("/model/baz.yang");
        private static final YangStatementSourceImpl FILE3 = new YangStatementSourceImpl("/model/foo.yang");
        private static final YangStatementSourceImpl FILE4 = new YangStatementSourceImpl("/model/subfoo.yang");
        private static final YangStatementSourceImpl EXTFILE = new YangStatementSourceImpl("/semantic-statement-parser/ext-typedef.yang");
        private static final YangStatementSourceImpl EXTUSE = new YangStatementSourceImpl("/semantic-statement-parser/ext-use.yang");

        @Test
        public void readAndParseYangFileTest1() throws SourceException, ReactorException {
                CrossSourceStatementReactor.BuildAction reactor = YangInferencePipeline.RFC6020_REACTOR.newBuild();
                addSources(reactor, YANGFILE, SIMPLENODES, IMPORTEDYANGFILE, FOO);
                EffectiveModelContext result = reactor.build();
                assertNotNull(result);
        }

        // TODO uncomment when Augment in Uses implemented
//        @Test
//        public void readAndParseYangFileTest2() throws SourceException, ReactorException {
//                CrossSourceStatementReactor.BuildAction reactor = YangInferencePipeline.RFC6020_REACTOR.newBuild();
//                addSources(reactor, FILE1, FILE2, FILE3, FILE4);
//                EffectiveModelContext result = reactor.build();
//                assertNotNull(result);
//        }

        @Test
        public void readAndParseYangFileTest3() throws SourceException, ReactorException {
                CrossSourceStatementReactor.BuildAction reactor = YangInferencePipeline.RFC6020_REACTOR.newBuild();
                addSources(reactor, EXTFILE, EXTUSE);
                EffectiveModelContext result = reactor.build();
                assertNotNull(result);
        }

        private void addSources(CrossSourceStatementReactor.BuildAction reactor, StatementStreamSource... sources) {
                for (StatementStreamSource source : sources) {
                        reactor.addSource(source);
                }
        }
}