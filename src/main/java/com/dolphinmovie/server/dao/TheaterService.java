package com.dolphinmovie.server.dao;

import java.util.List;

import com.dolphinmovie.server.entity.Theater;

public interface TheaterService {
	public List<Theater> getAll();
	public void saveTheaters(List<Theater> list);
	public void saveTheater(Theater theater);
	public int getCount();
}
