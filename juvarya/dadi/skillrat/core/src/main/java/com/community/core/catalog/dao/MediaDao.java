package com.community.core.catalog.dao;

import com.community.core.catalog.domain.Media;

public interface MediaDao {
   Media saveMedia(Media Media);
   void deleteMedia(Media media);
}
