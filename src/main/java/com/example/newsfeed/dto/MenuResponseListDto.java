package com.example.newsfeed.dto;

import com.example.newsfeed.entity.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponseListDto extends CommonResponseDto{

    List<MenuResponseDto> menuResponseDtoList;

}
