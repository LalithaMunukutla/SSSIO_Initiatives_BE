package com.sssio.initiatives.repository;

import com.sssio.initiatives.dto.CountryDTO;

import java.util.List;

public interface CountryDAO {
    public List<CountryDTO> getCountries();
}
