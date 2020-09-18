/*
 * The MIT License
 * Copyright (c) 2019- Nordic Institute for Interoperability Solutions (NIIS)
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

const commands = [{
  openAddServiceClient: function() {
    this.click('@addServiceClient');
    return this;
  },
  enterServiceClientSearchWord: function(searchWord) {
    this.clearValue2('@searchField');
    this.setValue('@searchField', searchWord);
    return this;
  },
}];

module.exports = {
  url: (subsystemId) => `${process.env.VUE_DEV_SERVER_URL}/#/subsystem/serviceclients/${subsystemId}`,
  commands: commands,
  elements: {
    addServiceClientButton: {
      selector: '//button[@data-test="add-service-client"]',
      locateStrategy: 'xpath' },
    unregisterButton: {
      selector: '//button[@data-test="unregister-client-button"]',
      locateStrategy: 'xpath' },
    addSubjectWizardHeader: {
      selector: '//div[@data-test="add-subject-title"]//span[contains(@class, "cert-headline") and contains(text(), "Add a subject")]',
      locateStrategy: 'xpath' },
    searchField: {
      selector: '//input[contains(@data-test, "search-service-client")]',
      locateStrategy: 'xpath' },
    serviceClientsTab: {
      selector: '//div[contains(@class, "xrd-view-common") and .//*[contains(@class, "v-tab--active") and contains(text(), "service clients")]]',
      locateStrategy: 'xpath' },
  },
  sections: {
    serviceClientsTab: {
      selector: '//div[contains(@class, "xrd-view-common") and .//*[contains(@class, "v-tab--active") and contains(text(), "service clients")]]',
      locateStrategy: 'xpath' },
    wizardSelectServices: {
      selector: '//div[contains(@class, "view-wrap")]//div[contains(@class, "v-stepper"]//span[contains(@class, "primary") and contains(text(), "2"]'
    }
  }
};
