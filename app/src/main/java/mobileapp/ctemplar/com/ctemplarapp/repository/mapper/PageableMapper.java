package mobileapp.ctemplar.com.ctemplarapp.repository.mapper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import mobileapp.ctemplar.com.ctemplarapp.net.response.PagableResponse;
import mobileapp.ctemplar.com.ctemplarapp.repository.dto.PageableDTO;

public class PageableMapper {
    @Nullable
    public static <I, O> PageableDTO<O> map(@NonNull Class<?> clazz, @Nullable PagableResponse<I> response) {
        if (response == null) {
            return null;
        }
        return new PageableDTO<>(
                response.getTotalCount(),
                response.getPageCount(),
                response.isNext(),
                response.isPrevious(),
                CommonMapper.map(clazz, response.getResults())
        );
    }
}
