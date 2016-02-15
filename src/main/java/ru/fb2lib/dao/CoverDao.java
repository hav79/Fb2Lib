package ru.fb2lib.dao;

import ru.fb2lib.datasets.Cover;

/**
 * Created by hav on 09.02.16.
 */
public interface CoverDao {

    Cover getCover(long id);
    Cover insertCover(Cover cover);
}
