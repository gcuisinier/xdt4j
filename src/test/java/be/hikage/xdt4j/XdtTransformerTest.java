package be.hikage.xdt4j;

import be.hikage.xdt4j.util.TestUtils;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.dom4j.*;
import org.intellij.lang.annotations.Language;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Collections;

import static be.hikage.xdt4j.util.TestUtils.loadXml;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class XdtTransformerTest {

    private Document baseDocument;

    @Before
    public void setUp() throws Exception {

        baseDocument = loadXml("SampleBase.xml");
    }

    @Test(expected = XdtException.class)
    public void TestInvalidTransform() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <test xdt:Transform=\"..test))(()()(\"/>\n    \n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXMLEqual(baseDocument.asXML(), result.asXML());

    }

    @Test(expected = XdtException.class)
    public void TestUnknownValidator() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <test xdt:Transform=\"Unknown\"/>\n    \n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXMLEqual(baseDocument.asXML(), result.asXML());

    }

    @Test
    public void TestIdentityTransform() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\" />";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXMLEqual(baseDocument.asXML(), result.asXML());

    }

    @Test
    public void TestRemoveTransform() throws DocumentException, IOException, SAXException, XpathException {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <system.web xdt:Transform=\"Remove\"/>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);

        Integer childrenCountBefore =  Integer.valueOf(baseDocument.valueOf("count(/configuration/*)"));

        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo(String.valueOf(--childrenCountBefore), "count(/configuration/*)", result.asXML());


    }

    @Test
    public void TestRemoveTransformWithMultipleElementThatMatch() throws DocumentException, IOException, SAXException, XpathException {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add xdt:Transform=\"Remove\"/> \n    </appSettings>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("2", "count(/configuration/appSettings/add)", result.asXML());


    }

    @Test
    public void TestRemoveAllTransform() throws DocumentException, IOException, SAXException, XpathException {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add xdt:Transform=\"RemoveAll\"/> \n    </appSettings>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("0", "count(/configuration/appSettings/add)", result.asXML());


    }

    @Test
    public void TestSetAttributesTransform() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <system.web>\n        <compilation debug=\"false\" xdt:Transform=\"SetAttributes\"/>\n    </system.web>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);

        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("false", "/configuration/system.web/compilation/@debug", result.asXML());


    }

    @Test
    public void TestSetAttributesTransformWithMultipleElementsThatMatch() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add value=\"newValue\" xdt:Transform=\"SetAttributes\"/>\n    </appSettings>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);

        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("newValue", "/configuration/appSettings/add[@key=\"key1\"]/@value", result.asXML());
        XMLAssert.assertXpathEvaluatesTo("newValue", "/configuration/appSettings/add[@key=\"key2\"]/@value", result.asXML());


    }

    @Test
    public void TestSetAttributesTransformWithArguments() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <system.web>\n        <compilation debug=\"false\" xdt:Transform=\"SetAttributes(debug)\"/>\n    </system.web>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);

        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("false", "/configuration/system.web/compilation/@debug", result.asXML());


    }

    @Test
    public void TestRemoveAttributesTransformWithArguments() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <system.web>\n        <compilation debug=\"false\" xdt:Transform=\"RemoveAttributes(debug)\"/>\n    </system.web>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);

        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("", "/configuration/system.web/compilation/@debug", result.asXML());


    }

    @Test
    public void TestRemoveAttributesTransform() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <system.web>\n        <compilation debug=\"false\" xdt:Transform=\"RemoveAttributes\"/>\n    </system.web>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);

        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("", "/configuration/system.web/compilation/@debug", result.asXML());


    }

    @Test
    public void TestRemoveAttributesTransformWithMultipleElementsThatMatch() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add value=\"newValue\" xdt:Transform=\"RemoveAttributes\"/>\n    </appSettings>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);

        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("", "/configuration/appSettings/add[@key=\"key1\"]/@value", result.asXML());
        XMLAssert.assertXpathEvaluatesTo("", "/configuration/appSettings/add[@key=\"key2\"]/@value", result.asXML());


    }


    @Test
     public void TestInsertTransform() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add key=\"key4\" value=\"value4\" xdt:Transform=\"Insert\"/>\n    </appSettings>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("4", "count(/configuration/appSettings/add)", result.asXML());
        XMLAssert.assertXpathEvaluatesTo("value4", "/configuration/appSettings/add[@key=\"key4\"]/@value", result.asXML());


    }

    @Test
    public void TestInsertTransformSpecificChildren() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    \n    <fileSets>\n        <fileSet id=\"fileset2\">\n            <file xdt:Transform=\"Insert\">myfile4.4</file>\n        </fileSet>\n        \n    </fileSets>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("3", "count(/configuration/fileSets/fileSet[@id='fileset2']/file)", result.asXML());
        XMLAssert.assertXpathEvaluatesTo("myfile4.4", "/configuration/fileSets/fileSet[@id='fileset2']/file[last]/@value", result.asXML());


    }

    @Test
    public void TestInsertBeforeTransform() throws Exception {
        @Language("XML")
        String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add key=\"key2.5\" value=\"value2.5\" xdt:Transform=\"InsertBefore(/configuration/appSettings/add[@key='key3'])\"/>\n    </appSettings>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("4", "count(/configuration/appSettings/add)", result.asXML());
        XMLAssert.assertXpathEvaluatesTo("value2.5", "/configuration/appSettings/add[@key=\"key2.5\"]/@value", result.asXML());


    }

    @Test
    public void TestInsertAfterTransform() throws Exception {

        @Language("XML")
        final String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add key=\"key3.5\" value=\"value3.5\" xdt:Transform=\"InsertAfter(/configuration/appSettings/add[@key='key3'])\"/>\n    </appSettings>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("4", "count(/configuration/appSettings/add)", result.asXML());
        XMLAssert.assertXpathEvaluatesTo("value3.5", "/configuration/appSettings/add[@key=\"key3.5\"]/@value", result.asXML());

        result = transformer.transform(result, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("5", "count(/configuration/appSettings/add)", result.asXML());
        XMLAssert.assertXpathEvaluatesTo("value3.5", "/configuration/appSettings/add[@key=\"key3.5\"]/@value", result.asXML());


    }

    @Test
    public void TestReplaceTransform() throws Exception {

        @Language("XML")
        final String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <system.web xdt:Transform=\"Replace\">\n        <extra content=\"here\"/>\n    </system.web>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("5", "count(/configuration/*)", result.asXML());
        XMLAssert.assertXpathEvaluatesTo("here", "/configuration/system.web/extra/@content", result.asXML());

    }


    @Test
    public void TestConditionLocator() throws Exception {
        @Language("XML")
        final String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add key=\"key2\" value=\"value2-live\" xdt:Locator=\"Condition(@key='key2')\" xdt:Transform=\"SetAttributes\"/>\n    </appSettings>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("value1", "/configuration/appSettings/add[@key=\"key1\"]/@value", result.asXML());
        XMLAssert.assertXpathEvaluatesTo("value2-live", "/configuration/appSettings/add[@key=\"key2\"]/@value", result.asXML());


    }

    @Test
    public void TestMultipleElementsAreTransformed() throws Exception {
        @Language("XML")
        final String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add key=\"key2\" value=\"value2-live\" xdt:Locator=\"Match(key)\" xdt:Transform=\"SetAttributes\"/>\n    </appSettings>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        XMLAssert.assertXpathEvaluatesTo("value1", "/configuration/appSettings/add[@key=\"key1\"]/@value", result.asXML());
        XMLAssert.assertXpathEvaluatesTo("value2-live", "/configuration/appSettings/add[@key=\"key2\"]/@value", result.asXML());


    }

    @Test

    public void TestInputDocumentsWithXmlNamespacesWorkAsExpected() throws Exception {
        @Language("XML")
        final String baseDocumentString = "<configuration>\n    <appSettings>\n        <add key=\"key1\" value=\"value1\"/>\n    </appSettings>\n    <blah xmlns=\"http://test.com\">\n        <add key=\"key2\" value=\"value2\"/>\n    </blah>\n    <flop xmlns=\"http://test.com\">\n        <add key=\"key3\" value=\"value3\" xmlns=\"\"/>\n    </flop>\n</configuration>";
        Document baseDocument = TestUtils.loadXmlFromString(baseDocumentString);
        @Language("XML")
        final String transformInstruction = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add value=\"value1-new\" xdt:Transform=\"SetAttributes\"/>\n    </appSettings>\n    <blah xmlns=\"http://test.com\">\n        <add key=\"key2\" value=\"value2-new\" xdt:Locator=\"Match(key)\" xdt:Transform=\"SetAttributes\"/>\n    </blah>\n    <flop xmlns=\"http://test.com\">\n        <add key=\"key3\" value=\"value3-new\" xmlns=\"\" xdt:Locator=\"Match(key)\" xdt:Transform=\"SetAttributes\"/>\n    </flop>\n</configuration>";
        Document transformDocument = TestUtils.loadXmlFromString(transformInstruction);


        XdtTransformer transformer = new XdtTransformer();
        Document result = transformer.transform(baseDocument, transformDocument);

        String nsString = "http://test.com";

        XPath xPath1 = DocumentHelper.createXPath("/configuration/ns:blah/*[local-name()='add' and @key=\"key2\"]");
        xPath1.setNamespaceURIs(Collections.singletonMap("ns", nsString));

        Element foundElement = (Element) xPath1.selectSingleNode(result);
        assertNotNull("No element found", foundElement);
        assertEquals("Namespace are different", nsString, foundElement.getNamespaceURI());

        XPath xPath2 = DocumentHelper.createXPath("/configuration/ns:flop/*[local-name()='add' and @key=\"key3\"]");
        xPath2.setNamespaceURIs(Collections.singletonMap("ns", nsString));


        foundElement = (Element) xPath2.selectSingleNode(result);
        assertNotNull("No Element found 2", foundElement);
        assertEquals("Namespace are different", "", foundElement.getNamespaceURI());
    }
}


