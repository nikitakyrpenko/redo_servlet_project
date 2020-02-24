package dao.util.pages;

import java.util.List;

public class Pageable<T> {
    private final List<T> items;
    private final int pageNumber;
    private final int itemsNumberPerPage;
    private final int countAll;

    public Pageable(List<T> items, int pageNumber,
                    int itemsNumberPerPage, int countAll) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.itemsNumberPerPage = itemsNumberPerPage;
        this.countAll = countAll;
    }

    public List<T> getItems() {
        return items;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getItemsNumberPerPage() {
        return itemsNumberPerPage;
    }

    public int getCountAll() {
        return countAll;
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "items=" + items +
                ", pageNumber=" + pageNumber +
                ", itemsNumberPerPage=" + itemsNumberPerPage +
                ", maxPageNumber=" + countAll +
                '}';
    }
}