package ru.fb2lib.gui;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.web.WebView;
import ru.fb2lib.datasets.Book;
import ru.fb2lib.datasets.Person;
import ru.fb2lib.db.Library;
import ru.fb2lib.templater.PageGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hav on 09.01.16.
 */
public class CenterViewController {

    @FXML
    private ListView<Person> authorsListView;

    @FXML
    private TableView<Book> booksTableView;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> srcTitleColumn;
    @FXML
    private TableColumn<Book, String> langColumn;

    @FXML
    private WebView bookInfoView;
//    private Label bookInfoView;
//    private TextArea bookInfoView;

    private Library library;
    private String resourceDir;

    public CenterViewController() {
    }

    @FXML
    private void initialize() {
        authorsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBooks(newValue)
        );

        titleColumn.setCellValueFactory(
                (TableColumn.CellDataFeatures<Book, String> p)
                        -> new ReadOnlyStringWrapper(p.getValue().getTitle())
        );
        srcTitleColumn.setCellValueFactory(
                (TableColumn.CellDataFeatures<Book, String> p)
                        -> new ReadOnlyStringWrapper(p.getValue().getSrcTitle())
        );
        langColumn.setCellValueFactory(
                (TableColumn.CellDataFeatures<Book, String> p)
                        -> new ReadOnlyStringWrapper(p.getValue().getLang()
                        + " (" + p.getValue().getSrcLang() + ")")
        );

        booksTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null)
                        showBookInfo(newValue);
                });
    }

    public void setLibrary(Library library) {
        this.library = library;
//        authorsListView.setItems(FXCollections.observableArrayList(library.getAllPersons()));
//        authorsListView.setItems(library.getAuthorsList());
//        authorsListView.itemsProperty().bind(library.authorsProperty());
        library.getAuthors().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                while (c.next())
                    if (c.wasAdded())
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                authorsListView.getItems().addAll(c.getAddedSubList());
                            }
                        });
            }
        });
//        library.addListener(observable -> {
//            Library lib = (Library) observable;
//            authorsListView.setItems(FXCollections.observableArrayList(lib.getAllPersons()));
//            authorsListView.getItems().addAll(lib.getAddedPersons());
//        });
    }

    private void showBooks(Person person) {
        long id = person != null ? person.getId() : 0;
        List<Book> books = library.getBooksOfAuthor(id);
        booksTableView.setItems(FXCollections.observableList(books));
    }

    private void showBookInfo(Book book) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("filename", book.getFilename());
        pageVariables.put("title", book.getTitle());
        pageVariables.put("src_title", book.getSrcTitle());
        pageVariables.put("authors", library.getAuthorsOfBook(book.getId()));
        pageVariables.put("year", book.getDate());
        pageVariables.put("lang", book.getLang());
        pageVariables.put("src_lang", book.getSrcLang());
        pageVariables.put("genres", library.getGenresOfBook(book.getId()));
        pageVariables.put("doc_id", book.getDocument().getDocumentId());
        pageVariables.put("version", book.getDocument().getVersion());
        pageVariables.put("program_used", book.getDocument().getProgramUsed());
        pageVariables.put("edition", book.getEdition());
        pageVariables.put("sequences", library.getSequencesOfBook(book.getId()));

        PageGenerator pageGenerator = PageGenerator.getInstance();
        pageGenerator.setHtmlDir(getResourceDir());
        bookInfoView.getEngine().loadContent(PageGenerator.getInstance().getPage("bookInfo2.ftl", pageVariables));
//        bookInfoView.setText(PageGenerator.getInstance().getPage("bookInfo.ftl", pageVariables));
    }

    public String getResourceDir() {
        return resourceDir;
    }

    public void setResourceDir(String resourceDir) {
        this.resourceDir = resourceDir;
    }
}
