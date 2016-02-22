import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.fb2lib.dao.*;

/**
 * Created by hav on 15.02.16.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BookDaoTest.class,
        PersonDaoTest.class,
        EditionDaoTest.class,
        DocumentDaoTest.class,
        SequenceDaoTest.class,
        CoverDaoTest.class
})
public class DaoTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
