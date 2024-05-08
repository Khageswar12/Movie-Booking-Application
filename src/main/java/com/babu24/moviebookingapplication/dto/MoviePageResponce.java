package com.babu24.moviebookingapplication.dto;

import java.util.List;

public record MoviePageResponce (List<MoveiDto> moveiDtos,
                                 Integer pageNumber,
                                 Integer  pageSize,
                                 long totalElements,
                                 int totalPages,
                                 boolean lastpage){
}
