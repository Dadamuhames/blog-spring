package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Paginate {
    public static final int on_each_side = 3;
    public static final int on_ends = 2;

    public static List<Integer> get_pagination(int totalPages, int currentPage) {
        List<Integer> pagination = new ArrayList<>();

        if (totalPages <= (on_each_side + on_ends) * 2) {
            return IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
        }

        if(currentPage > (1 + on_each_side + on_ends) + 1) {
            for(int i = 1; i <= on_ends; i++) {
                pagination.add(i);
            }

            pagination.add(0);

            int l = currentPage - on_each_side;

            for(; l <= currentPage; l++) {
                pagination.add(l);
            }

        } else {
            for(int j = 1; j <= currentPage; j++) {
                pagination.add(j);
            }
        }


        if (currentPage < (totalPages - on_each_side - on_ends) - 1) {
            int end = currentPage + on_each_side + 1;

            for(int i = currentPage + 1; i <= end; i++) {
                pagination.add(i);
            }

            pagination.add(0);

            int t = totalPages - on_ends + 1;

            for(; t <= totalPages; t++) {
                pagination.add(t);
            }
        } else {
            for(int y = currentPage + 1; y <= totalPages; y++) {
                pagination.add(y);
            }
        }

        return pagination;
    }
}
