package com.atbm.dao.strap;

import com.atbm.models.entity.Strap;

import java.util.List;

public interface StrapDao {
    String STRAP_ID = "strapId";
    String COLOR = "color";
    String MATERIAL = "material";
    String LENGTH = "length";

    Strap getStrapById(long strapId);
    List<Strap> getStraps();
}
