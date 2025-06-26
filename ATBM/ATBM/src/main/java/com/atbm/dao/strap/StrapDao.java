package com.atbm.dao.strap;

import com.atbm.models.entity.Strap;

import java.util.List;

public interface StrapDao {
    Strap getStrapById(long strapId);
    List<Strap> getStraps();
}
