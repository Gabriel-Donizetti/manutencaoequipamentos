package com.web2.manutencaoequipamentos.utils;

public record Return(Object data, int page, int pageSize, int totalPages, long totalElements) {
}