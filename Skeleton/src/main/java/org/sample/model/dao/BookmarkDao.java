package org.sample.model.dao;

import org.sample.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkDao  extends JpaRepository<Bookmark,Long>
{

}
