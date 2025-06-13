package com.sssio.initiatives.repository;

import com.sssio.initiatives.dto.ZoneDTO;
import com.sssio.initiatives.entity.Zone;

import java.util.List;

public interface ZoneDAO {
    public List<ZoneDTO> getZones();
}
