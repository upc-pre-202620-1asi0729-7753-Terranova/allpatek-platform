package com.allpatekplatform.parcels.domain.model.valueobjects;

import java.util.List;

public record Crops(List<CropName> items) {

    public Crops {
        items = items == null ? List.of() : List.copyOf(items);
    }

    public static Crops of(List<String> values) {
        if (values == null) {
            return new Crops(List.of());
        }
        return new Crops(values.stream().map(CropName::new).toList());
    }

    public List<String> values() {
        return items.stream().map(CropName::value).toList();
    }
}
