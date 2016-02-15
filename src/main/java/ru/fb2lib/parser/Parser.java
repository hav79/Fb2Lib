package ru.fb2lib.parser;

import ru.fb2lib.datasets.*;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;

/**
 * Created by hav on 14.01.16.
 */
public interface Parser {
    //TODO cover, custom-info, some isbn, some city, some publisher
    List<Person> parseAuthors() throws XPathExpressionException;
    List<Person> parseTranslators() throws XPathExpressionException;
    List<Person> parseDocAuthors() throws XPathExpressionException;

    String parseTitle() throws XPathExpressionException;
    String parseSrcTitle() throws XPathExpressionException;
    String parseYear() throws XPathExpressionException;
    Language parseLang() throws XPathExpressionException;
    Language parseSrcLang() throws XPathExpressionException;
    List<Genre> parseGenres() throws XPathExpressionException;
    List<BookSequences> parseSequences() throws XPathExpressionException;
    List<String> parseKeywords() throws XPathExpressionException;
    String parseAnnotation() throws XPathExpressionException;

    Cover parseSrcCover() throws XPathExpressionException;

    Document parseDocumentInfo() throws XPathExpressionException;
    Edition parseEdition() throws XPathExpressionException;

    Cover parseCover() throws XPathExpressionException;
}
