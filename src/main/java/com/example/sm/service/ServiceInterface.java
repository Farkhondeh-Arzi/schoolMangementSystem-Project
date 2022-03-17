package com.example.sm.service;

import java.util.List;

public interface ServiceInterface<DTO> {

	public List<DTO> getAll();

	public DTO add(DTO object);

	public DTO get(int Id);

	public DTO update(DTO object, int Id);

	public void delete(int Id);
}
