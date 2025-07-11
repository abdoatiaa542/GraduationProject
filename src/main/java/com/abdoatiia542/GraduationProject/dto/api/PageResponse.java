package com.abdoatiia542.GraduationProject.dto.api;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
public class PageResponse<T> {

    public PageResponse() {
        this.content = null;
        totalElementsCount = 0;
        totalPagesCount = 0;
        pageElementsCount = 0;
        pageSize = 0;
        pageNumber = 0;
        emptyPage = false;
        firstPage = false;
        lastPage = false;
        sortedPage = false;
    }

    public static <U> PageResponse<U> of(Page<U> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getPageable().getPageSize(),
                page.getPageable().getPageNumber(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty(),
                page.getSort().isSorted());
    }

    public static <T, U> PageResponse<U> of(Page<T> page, Function<T, U> mapper) {
        List<U> content = page.getContent().stream().map(mapper).toList();

        return new PageResponse<>(
                content,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getPageable().getPageSize(),
                page.getPageable().getPageNumber(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty(),
                page.getSort().isSorted());
    }

    public static <T, U, V> PageResponse<V> of(Page<T> page, U u, BiFunction<T, U, V> mapper) {
        List<V> content = page.getContent().stream().map(item -> mapper.apply(item, u)).toList();

        return new PageResponse<>(
                content,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getPageable().getPageSize(),
                page.getPageable().getPageNumber(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty(),
                page.getSort().isSorted());
    }

    public <V> PageResponse<V> map(Function<T, V> function) {
        List<V> content = this.content.stream().map(function).toList();

        return new PageResponse<>(
                content,
                this.totalElementsCount,
                this.totalPagesCount,
                this.pageElementsCount,
                this.pageSize,
                this.pageNumber,
                this.firstPage,
                this.lastPage,
                this.emptyPage,
                this.sortedPage);
    }

    public <V, A> PageResponse<V> map(BiFunction<T, A, V> function, A a) {
        List<V> content = this.content.stream().map(item -> function.apply(item, a)).toList();

        return new PageResponse<>(
                content,
                this.totalElementsCount,
                this.totalPagesCount,
                this.pageElementsCount,
                this.pageSize,
                this.pageNumber,
                this.firstPage,
                this.lastPage,
                this.emptyPage,
                this.sortedPage);
    }

    private final List<T> content;
    private final long totalElementsCount;
    private final int totalPagesCount;
    private final int pageElementsCount;
    private final int pageSize;
    private final int pageNumber;
    private final boolean firstPage;
    private final boolean lastPage;
    private final boolean emptyPage;
    private final boolean sortedPage;

    private PageResponse(
            List<T> content,
            long totalElementsCount,
            int totalPagesCount, int pageElementsCount,
            int pageSize,
            int pageNumber,
            boolean firstPage,
            boolean lastPage,
            boolean emptyPage,
            boolean sortedPage) {
        this.content = content;
        this.totalElementsCount = totalElementsCount;
        this.totalPagesCount = totalPagesCount;
        this.pageElementsCount = pageElementsCount;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.emptyPage = emptyPage;
        this.sortedPage = sortedPage;
    }
}