/**
 * The MIT License
 * Copyright (c) 2018 Estonian Information System Authority (RIA),
 * Nordic Institute for Interoperability Solutions (NIIS), Population Register Centre (VRK)
 * Copyright (c) 2015-2017 Estonian Information System Authority (RIA), Population Register Centre (VRK)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ee.ria.xroad.proxy.testsuite.testcases;

import ee.ria.xroad.common.ErrorCodes;
import ee.ria.xroad.common.SystemProperties;
import ee.ria.xroad.common.TestCertUtil;
import ee.ria.xroad.common.conf.serverconf.IsAuthentication;
import ee.ria.xroad.common.conf.serverconf.ServerConf;
import ee.ria.xroad.common.identifier.ClientId;
import ee.ria.xroad.proxy.testsuite.Message;
import ee.ria.xroad.proxy.testsuite.SslMessageTestCase;
import ee.ria.xroad.proxy.testsuite.TestSuiteServerConf;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

/**
 * Test expired certificate case - normal message and normal response.
 * Result: client receives message and the warning is logged.
 */
public class SslClientAuthExpiredISCert extends SslMessageTestCase {

    /**
     * Constructs the test case.
     */
    public SslClientAuthExpiredISCert() {
        requestFileName = "getstate.query";
        responseFile = "getstate.answer";
    }

    @Override
    protected void startUp() throws Exception {
        super.startUp();

        ServerConf.reload(new TestSuiteServerConf() {
            @Override
            public IsAuthentication getIsAuthentication(
                    ClientId client) {
                return IsAuthentication.SSLAUTH;
            }

            @Override
            public List<X509Certificate> getIsCerts(ClientId client) throws Exception {
                return Arrays.asList(TestCertUtil.loadPKCS12("expiredClient.p12", "1", "test").certChain[0]);
            }
        });

    }

    @Override
    protected void validateNormalResponse(Message receivedResponse)
            throws Exception {
        if (SystemProperties.isClientIsCertValidityPeriodCheckEnforced()) {
            throw new Exception("Received normal response, fault was expected");
        }
    }

    @Override
    protected void validateFaultResponse(Message response) throws Exception {
        if (SystemProperties.isClientIsCertValidityPeriodCheckEnforced()) {
            assertErrorCode(ErrorCodes.SERVER_CLIENTPROXY_X, ErrorCodes.X_SSL_AUTH_FAILED);
        } else {
            throw new Exception(
                    "Received fault response, answer was expected");
        }
    }

    @Override
    public KeyStore getKeyStore() {
        return TestCertUtil.getKeyStore("expiredClient");
    }

    @Override
    public char[] getKeyStorePassword() {
        return TestCertUtil.getKeyStorePassword("expiredClient");
    }
}