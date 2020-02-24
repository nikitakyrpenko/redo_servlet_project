package dao;

import dao.util.pages.Page;
import dao.util.pages.Pageable;

import java.util.Optional;

public interface CrudPageableDao<E> extends CrudDao<E> {

    Pageable<E> findAll(Page page);

    Optional<Integer> countAll();
}