package ru.fb2lib.dao;

import ru.fb2lib.datasets.Sequence;

/**
 * Created by hav on 26.01.16.
 */
public interface SequenceDao {

    Sequence getSequence(long id);
    Sequence getSequence(String name, String srcName);

    Sequence insertSequence(Sequence sequence);
}
