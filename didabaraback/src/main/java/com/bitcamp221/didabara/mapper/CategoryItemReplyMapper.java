package com.bitcamp221.didabara.mapper;

import com.bitcamp221.didabara.dto.ItemReplyAndUserDataDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryItemReplyMapper {

  @Select("SELECT cr.*, u.nickname, ui.profile_image_url FROM category_item_reply AS cr " +
          "INNER JOIN user AS u ON cr.writer = u.id " +
          "INNER JOIN user_info AS ui ON cr.writer = ui.id WHERE cr.category_item_id = #{categoryItem}")
  List<ItemReplyAndUserDataDTO> findAllReplyData(@Param("categoryItem") final Long categoryItem);
}
