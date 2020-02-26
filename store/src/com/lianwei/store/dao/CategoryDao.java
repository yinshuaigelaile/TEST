package com.lianwei.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.lianwei.store.domain.Category;

public interface CategoryDao {

	List<Category> getAllCategories() throws SQLException;

}
