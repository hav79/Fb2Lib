package ru.fb2lib.parser;

import org.dom4j.dom.DOMNodeHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.fb2lib.datasets.*;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hav on 13.01.16.
 */
public class Fb2Parser implements Parser {

    private XPath path;
    private InputSource source;

    public Fb2Parser(String filename) {
        initXPath();
        source = new InputSource("file:" + filename);
    }

    private void initXPath() {
        path = XPathFactory.newInstance().newXPath();
        path.setNamespaceContext(new NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                if ("fb2".equals(prefix)) {
                    return "http://www.gribuser.ru/xml/fictionbook/2.0";
                } else if ("fb2_l".equals(prefix)) {
                    return "http://www.w3.org/1999/xlink";
                } else if ("xml".equals(prefix)) {
                    return XMLConstants.XML_NS_URI;
                } else {
                    return XMLConstants.NULL_NS_URI;
                }
            }

            @Override
            public String getPrefix(String namespaceURI) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Iterator getPrefixes(String namespaceURI) {
                throw new UnsupportedOperationException();
            }
        });
    }

    private void parsePerson(NodeList personNode, Person person) {
        for (int i = 0; i < personNode.getLength(); i++) {
            if (personNode.item(i) instanceof Element) {
                Element item = (Element) personNode.item(i);
                switch (item.getTagName()) {
                    case "first-name":
                        person.setFirstName(item.getTextContent());
                        break;
                    case "middle-name":
                        person.setMiddleName(item.getTextContent());
                        break;
                    case "last-name":
                        person.setLastName(item.getTextContent());
                        break;
                    case "nickname":
                        person.setNickname(item.getTextContent());
                        break;
                    case "email":
                        person.setEmail(item.getTextContent());
                }
            }
        }
    }

    private void parseSrcPerson(NodeList srcPersonNode, Person person) {
        for (int i = 0; i < srcPersonNode.getLength(); i++) {
            if (srcPersonNode.item(i) instanceof Element) {
                Element item = (Element) srcPersonNode.item(i);
                switch (item.getTagName()) {
                    case "first-name":
                        person.setSrcFirstName(item.getTextContent());
                        break;
                    case "middle-name":
                        person.setSrcMiddleName(item.getTextContent());
                        break;
                    case "last-name":
                        person.setSrcLastName(item.getTextContent());
                }
            }
        }
    }

    @Override
    public List<Person> parseAuthors() throws XPathExpressionException {
        List<Person> persons = new ArrayList<>();
        NodeList personNodeList =
                (NodeList) path.evaluate("//fb2:title-info/fb2:author", source, XPathConstants.NODESET);
        NodeList srcPersonNodeList =
                (NodeList) path.evaluate("//fb2:src-title-info/fb2:author", source, XPathConstants.NODESET);
        for (int i = 0; i < personNodeList.getLength(); i++) {
            Person person = new Person();
            if (personNodeList.item(i) instanceof Element) {
                parsePerson(personNodeList.item(i).getChildNodes(), person);
            }
            if (srcPersonNodeList.item(i) instanceof Element) {
                parseSrcPerson(srcPersonNodeList.item(i).getChildNodes(), person);
            }
            persons.add(person);
        }
        return persons;
    }

    @Override
    public List<Person> parseTranslators() throws XPathExpressionException {
        List<Person> persons = new ArrayList<>();
        NodeList personNodeList =
                (NodeList) path.evaluate("//fb2:title-info/fb2:translator", source, XPathConstants.NODESET);
        for (int i = 0; i < personNodeList.getLength(); i++) {
            Person person = new Person();
            if (personNodeList.item(i) instanceof Element) {
                parsePerson(personNodeList.item(i).getChildNodes(), person);
            }
            persons.add(person);
        }
        return persons;
    }

    public List<Person> parseDocAuthors() throws XPathExpressionException {
        List<Person> persons = new ArrayList<>();
        NodeList personNodeList =
                (NodeList) path.evaluate("//fb2:document-info/fb2:author", source, XPathConstants.NODESET);
        for (int i = 0; i < personNodeList.getLength(); i++) {
            Person person = new Person();
            if (personNodeList.item(i) instanceof Element) {
                parsePerson(personNodeList.item(i).getChildNodes(), person);
            }
            persons.add(person);
        }
        return persons;
    }

    @Override
    public String parseTitle() throws XPathExpressionException {
        return (String) path.evaluate("//fb2:title-info/fb2:book-title", source, XPathConstants.STRING);
    }

    @Override
    public String parseSrcTitle() throws XPathExpressionException {
        return (String) path.evaluate("//fb2:src-title-info/fb2:book-title", source, XPathConstants.STRING);
    }

    @Override
    public String parseYear() throws XPathExpressionException {
        return (String) path.evaluate("//fb2:title-info/fb2:date", source, XPathConstants.STRING);
    }

    @Override
    public Language parseLang() throws XPathExpressionException {
        return Language.valueOf((String) path.evaluate("//fb2:title-info/fb2:lang", source, XPathConstants.STRING));
    }

    @Override
    public Language parseSrcLang() throws XPathExpressionException {
        String lang = (String) path.evaluate("//fb2:title-info/fb2:src-lang", source, XPathConstants.STRING);
        return lang.isEmpty() ? Language.valueOf("ru") : Language.valueOf(lang);
    }

    @Override
    public List<Genre> parseGenres() throws XPathExpressionException {
        List<Genre> genres = new ArrayList<>();
        NodeList genreNodes = (NodeList) path.evaluate("//fb2:title-info/fb2:genre", source, XPathConstants.NODESET);
        for (int i = 0; i < genreNodes.getLength(); i++) {
            if (genreNodes.item(i) instanceof Element)
                genres.add(Genre.valueOf(genreNodes.item(i).getTextContent()));
        }
        return genres;
    }

    @Override
    public Edition parseEdition() throws XPathExpressionException {
        Edition edition = new Edition();
        edition.setBookName((String) path.evaluate("//fb2:publish-info/fb2:book-name", source, XPathConstants.STRING));
        edition.setCity((String) path.evaluate("//fb2:publish-info/fb2:city", source, XPathConstants.STRING));
        edition.setPublisher((String) path.evaluate("//fb2:publish-info/fb2:publisher", source, XPathConstants.STRING));
//        String isbn = (String) path.evaluate("//fb2:publish-info/fb2:isbn", source, XPathConstants.STRING);
//        List<String> isbnList = Arrays.asList(isbn.split(", "));
//        for (String el : isbnList) {
//            if (ISBN.isValidISBN(el))
//                edition.addIsbn(new ISBN(el));
//        }
        edition.setIsbn((String) path.evaluate("//fb2:publish-info/fb2:isbn", source, XPathConstants.STRING));
        edition.setYear((String) path.evaluate("//fb2:publish-info/fb2:year", source, XPathConstants.STRING));

        Node sequence = (Node) path.evaluate("//fb2:publish-info/fb2:sequence", source, XPathConstants.NODE);
        boolean hasSequence = sequence != null;
        NamedNodeMap attrs = hasSequence ? sequence.getAttributes() : null;
        Node number = hasSequence ? attrs.getNamedItem("number") : null;
        edition.setSequenceName(hasSequence ? attrs.getNamedItem("name").getNodeValue() : "");
        edition.setSequenceNumber(number != null ? Integer.parseInt(number.getNodeValue()) : 0);
        return edition;
    }

    private Cover parseImage(String imageName) throws XPathExpressionException {
        NodeList binaries = (NodeList) path.evaluate("//fb2:binary", source, XPathConstants.NODESET);
        for (int i = 0; i < binaries.getLength(); i++) {
            Node coverBin = binaries.item(i);
            if (coverBin.getAttributes().getNamedItem("id").getNodeValue().equals(imageName)) {
                return new Cover(imageName, coverBin.getTextContent(),
                        coverBin.getAttributes().getNamedItem("content-type").getNodeValue());
//                    String coverBase64 = Base64.getDecoder().decode(binaries.item(i).getTextContent());
            }
        }
        return null;
    }

    @Override
    public Cover parseCover() throws XPathExpressionException {
        Node coverNode = (Node) path.evaluate("//fb2:title-info/fb2:coverpage/fb2:image", source, XPathConstants.NODE);
        if (coverNode != null) {
            String imageName = coverNode.getAttributes()
                    .getNamedItemNS("http://www.w3.org/1999/xlink", "href").getNodeValue().substring(1);
            return parseImage(imageName);
        }
        return null;
    }

    @Override
    public Cover parseSrcCover() throws XPathExpressionException {
        Node coverNode = (Node) path.evaluate("//fb2:src-title-info/fb2:coverpage/fb2:image", source, XPathConstants.NODE);
        if (coverNode != null) {
            String imageName = coverNode.getAttributes()
                    .getNamedItemNS("http://www.w3.org/1999/xlink", "href").getNodeValue().substring(1);
            return parseImage(imageName);
        }
        return null;
    }

    private List<String> parseHistory() throws XPathExpressionException {
        List<String> lines = new ArrayList<>();
        NodeList historyLines = (NodeList) path.evaluate("//fb2:document-info/fb2:history/fb2:p",
                source, XPathConstants.NODESET);
        for (int i = 0; i < historyLines.getLength(); i++) {
            if (historyLines.item(i) instanceof Element)
                lines.add(historyLines.item(i).getTextContent());
        }
        return lines;
    }

    @Override
    public Document parseDocumentInfo() throws XPathExpressionException {
        Document doc = new Document();
        doc.setProgramUsed((String) path.evaluate("//fb2:document-info/fb2:program-used", source, XPathConstants.STRING));
        doc.setDocumentId((String) path.evaluate("//fb2:document-info/fb2:id", source, XPathConstants.STRING));
        doc.setVersion((String) path.evaluate("//fb2:document-info/fb2:version", source, XPathConstants.STRING));
        doc.setSrcOcr((String) path.evaluate("//fb2:document-info/fb2:src-ocr", source, XPathConstants.STRING));
        doc.setSrcUrl((String) path.evaluate("//fb2:document-info/fb2:src-url", source, XPathConstants.STRING));
        doc.setHistory(parseHistory());
        Node dateNode = (Node) path.evaluate("//fb2:document-info/fb2:date", source, XPathConstants.NODE);
        if (dateNode != null) {
            String strDate = dateNode.getTextContent();
            LocalDate date = LocalDate.parse(dateNode.getAttributes().getNamedItem("value").getNodeValue());
            doc.setDocDate(new DocDate(strDate, date));
        }
        return doc;
    }

    private List<BookSequences> parseSequenceTree(Node sequenceNode, Node srcSequenceNode, Sequence parent) {
        //TODO Обработка пустых серий?
        Sequence sequence = new Sequence();
        List<BookSequences> sequences = new ArrayList<>();
        boolean hasSequence = sequenceNode != null;
        boolean hasSrcSequence = srcSequenceNode != null;

        NamedNodeMap attrs = hasSequence ? sequenceNode.getAttributes() : null;
        NamedNodeMap srcAttrs = hasSrcSequence ? srcSequenceNode.getAttributes() : null;

        sequence.setName(hasSequence ? attrs.getNamedItem("name").getNodeValue() : "");
        sequence.setSrcName(hasSrcSequence ? srcAttrs.getNamedItem("name").getNodeValue() : "");
        sequence.setParent(parent);
//        parent.getChildren().add(sequence);

        sequences.add(new BookSequences(sequence,
                Long.parseLong(hasSequence ? attrs.getNamedItem("number").getNodeValue() : "")));
        NodeList children = hasSequence ? sequenceNode.getChildNodes() : new DOMNodeHelper.EmptyNodeList();
        NodeList srcChildren = hasSrcSequence ? srcSequenceNode.getChildNodes() : new DOMNodeHelper.EmptyNodeList();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            Node srcChild = srcChildren.item(i);
            if (child instanceof Element
                    && ((Element) child).getTagName() == "sequence") {
                sequences.addAll(parseSequenceTree(child, srcChild, sequence));
            }
        }
        return sequences;
    }

    @Override
    public List<BookSequences> parseSequences() throws XPathExpressionException {
        List<BookSequences> bookSequences = new ArrayList<>();
        NodeList sequenceNodes =
                (NodeList) path.evaluate("//fb2:title-info/fb2:sequence", source, XPathConstants.NODESET);
        NodeList srcSequenceNodes =
                (NodeList) path.evaluate("//fb2:src-title-info/fb2:sequence", source, XPathConstants.NODESET);
        for (int i = 0; i < sequenceNodes.getLength(); i++) {
            if (sequenceNodes.item(i) instanceof Element)
                bookSequences.addAll(parseSequenceTree(sequenceNodes.item(i), srcSequenceNodes.item(i), null));
        }
        return bookSequences;
    }

    @Override
    public List<String> parseKeywords() throws XPathExpressionException {
        String keywordsString = (String) path.evaluate("//fb2:title-info/fb2:keywords", source, XPathConstants.STRING);
        return Arrays.asList(keywordsString.split(", "));
    }

    @Override
    public String parseAnnotation() throws XPathExpressionException {
        Node annotationNode = (Node) path.evaluate("//fb2:title-info/fb2:annotation", source, XPathConstants.NODE);
        NodeList lines = annotationNode.getChildNodes();
        StringBuilder annotation = new StringBuilder();
        for (int i = 0; i < lines.getLength(); i++) {
            if (lines.item(i) instanceof Element) {
                annotation.append("\n");
                annotation.append(lines.item(i).getTextContent());
            }
        }
        return annotation.toString().trim();
    }
}
