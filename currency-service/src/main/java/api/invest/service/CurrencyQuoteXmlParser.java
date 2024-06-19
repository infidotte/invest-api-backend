package api.invest.service;

import api.invest.domain.CurrencyQuote;
import api.invest.service.api.CurrencyQuotesParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static javax.xml.XMLConstants.*;

@Component
public class CurrencyQuoteXmlParser implements CurrencyQuotesParser {
    private static final Logger log = LoggerFactory.getLogger(CurrencyQuoteXmlParser.class);

    public Map<String, CurrencyQuote> parse(String quotesAsString) {
        Map<String, CurrencyQuote> currencyQuotes = new HashMap<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setAttribute(ACCESS_EXTERNAL_DTD, "");
        documentBuilderFactory.setAttribute(ACCESS_EXTERNAL_SCHEMA, "");
        log.info("Start parsing quotes");
        try {
            documentBuilderFactory.setFeature(FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            try (Reader reader = new StringReader(quotesAsString)) {
                Document document = documentBuilder.parse(new InputSource(reader));
                document.getDocumentElement().normalize();
                NodeList nodeList = document.getElementsByTagName("Valute");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        CurrencyQuote currencyQuote = new CurrencyQuote(
                                element.getElementsByTagName("NumCode").item(0).getTextContent(),
                                element.getElementsByTagName("CharCode").item(0).getTextContent(),
                                element.getElementsByTagName("Nominal").item(0).getTextContent(),
                                element.getElementsByTagName("Name").item(0).getTextContent(),
                                element.getElementsByTagName("Value").item(0).getTextContent()
                        );
                        currencyQuotes.put(element.getAttribute("ID"), currencyQuote);
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            log.error(e.getMessage());
        }
        log.info("End parsing quotes");
        return currencyQuotes;
    }
}
