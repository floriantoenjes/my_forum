package com.floriantoenjes.forum.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Pagination<E> {
    private List<Integer> pages;
    private List<E> elements;

    public Pagination(List<E> list, Integer page, Integer PAGE_SIZE) {

        int startIndex = page * PAGE_SIZE;
        int endIndex = startIndex + PAGE_SIZE;

        if (list.size() < endIndex) {
            endIndex = list.size();
        }

        elements = list.subList(startIndex, endIndex);

        pages = new ArrayList<>();
        IntStream.range(0, (int) Math.ceil(list.size() / (double) PAGE_SIZE)).forEach(pages::add);
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public List<E> getElements() {
        return elements;
    }

    public void setElements(List<E> elements) {
        this.elements = elements;
    }
}
